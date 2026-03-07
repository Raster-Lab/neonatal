package com.nicusystem.fluid;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for FluidBalanceController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class FluidBalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FluidBalanceService fluidBalanceService;

    @Test
    @WithMockUser
    void recordFluidEntry_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateFluidEntryRequest request = new CreateFluidEntryRequest(
                patientId, FluidEntryType.INTAKE, FluidCategory.IV_FLUID,
                50.0, null, recordedAt, "nurse-001");
        final FluidEntryDto dto = new FluidEntryDto(
                UUID.randomUUID(), patientId, FluidEntryType.INTAKE, FluidCategory.IV_FLUID,
                50.0, null, recordedAt, "nurse-001");
        when(fluidBalanceService.recordFluidEntry(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/fluid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.entryType").value("INTAKE"))
                .andExpect(jsonPath("$.volumeMl").value(50.0));
    }

    @Test
    @WithMockUser
    void getFluidEntryById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final FluidEntryDto dto = new FluidEntryDto(
                id, UUID.randomUUID(), FluidEntryType.OUTPUT, FluidCategory.URINE,
                30.0, null, Instant.now(), null);
        when(fluidBalanceService.getFluidEntryById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/fluid/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.entryType").value("OUTPUT"));
    }

    @Test
    @WithMockUser
    void getFluidEntriesByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final FluidEntryDto dto = new FluidEntryDto(
                UUID.randomUUID(), patientId, FluidEntryType.INTAKE, FluidCategory.ORAL_FEED,
                20.0, null, Instant.now(), null);
        when(fluidBalanceService.getFluidEntriesByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/fluid/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].volumeMl").value(20.0));
    }

    @Test
    @WithMockUser
    void getFluidBalanceSummary_withWeight_shouldReturnOk() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-01T00:00:00Z");
        final Instant end = Instant.parse("2024-01-02T00:00:00Z");
        final FluidBalanceSummaryDto dto = new FluidBalanceSummaryDto(
                patientId, start, end, 500.0, 400.0, 100.0, 1200, 416.67, 333.33);
        when(fluidBalanceService.getFluidBalanceSummary(
                eq(patientId), any(Instant.class), any(Instant.class), eq(1200)))
                .thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/fluid/patient/{patientId}/summary", patientId)
                        .param("start", "2024-01-01T00:00:00Z")
                        .param("end", "2024-01-02T00:00:00Z")
                        .param("weightGrams", "1200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIntakeMl").value(500.0))
                .andExpect(jsonPath("$.netBalanceMl").value(100.0));
    }

    @Test
    @WithMockUser
    void getFluidBalanceSummary_withoutWeight_shouldReturnOk() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-01T00:00:00Z");
        final Instant end = Instant.parse("2024-01-02T00:00:00Z");
        final FluidBalanceSummaryDto dto = new FluidBalanceSummaryDto(
                patientId, start, end, 300.0, 250.0, 50.0, null, null, null);
        when(fluidBalanceService.getFluidBalanceSummary(
                eq(patientId), any(Instant.class), any(Instant.class), isNull()))
                .thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/fluid/patient/{patientId}/summary", patientId)
                        .param("start", "2024-01-01T00:00:00Z")
                        .param("end", "2024-01-02T00:00:00Z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIntakeMl").value(300.0));
    }
}
