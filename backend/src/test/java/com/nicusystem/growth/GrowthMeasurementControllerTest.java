package com.nicusystem.growth;

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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for GrowthMeasurementController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class GrowthMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GrowthMeasurementService growthMeasurementService;

    @Test
    @WithMockUser
    void recordMeasurement_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant measuredAt = Instant.now();
        final CreateGrowthMeasurementRequest request = new CreateGrowthMeasurementRequest(
                patientId, MeasurementType.WEIGHT, 1500.0,
                null, null, null, measuredAt, null);
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                UUID.randomUUID(), patientId, MeasurementType.WEIGHT, 1500.0,
                null, null, null, measuredAt, null);
        when(growthMeasurementService.recordMeasurement(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/growth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.measurementType").value("WEIGHT"))
                .andExpect(jsonPath("$.value").value(1500.0));
    }

    @Test
    @WithMockUser
    void getMeasurement_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                id, UUID.randomUUID(), MeasurementType.LENGTH, 45.0,
                null, null, null, Instant.now(), null);
        when(growthMeasurementService.getMeasurementById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/growth/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.measurementType").value("LENGTH"));
    }

    @Test
    @WithMockUser
    void getMeasurementsByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                UUID.randomUUID(), patientId, MeasurementType.WEIGHT, 1500.0,
                null, null, null, Instant.now(), null);
        when(growthMeasurementService.getMeasurementsByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/growth/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].value").value(1500.0));
    }

    @Test
    @WithMockUser
    void getMeasurementsByPatientAndType_shouldReturnFilteredPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                UUID.randomUUID(), patientId, MeasurementType.HEAD_CIRCUMFERENCE, 30.0,
                null, null, null, Instant.now(), null);
        when(growthMeasurementService.getMeasurementsByPatientAndType(
                eq(patientId), eq(MeasurementType.HEAD_CIRCUMFERENCE), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/growth/patient/{patientId}/type/{type}",
                        patientId, "HEAD_CIRCUMFERENCE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].measurementType").value("HEAD_CIRCUMFERENCE"));
    }

    @Test
    @WithMockUser
    void getGrowthTrend_shouldReturnList() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                UUID.randomUUID(), patientId, MeasurementType.WEIGHT, 1500.0,
                null, null, null, Instant.now(), null);
        when(growthMeasurementService.getGrowthTrend(patientId, MeasurementType.WEIGHT))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/growth/patient/{patientId}/type/{type}/trend",
                        patientId, "WEIGHT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].value").value(1500.0));
    }
}
