package com.nicusystem.flowsheet;

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
 * Tests for FlowsheetEntryController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class FlowsheetEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FlowsheetEntryService flowsheetEntryService;

    @Test
    @WithMockUser
    void create_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant entryTime = Instant.now();
        final CreateFlowsheetEntryRequest request =
                new CreateFlowsheetEntryRequest(
                        patientId, FlowsheetCategory.VITAL_SIGNS,
                        entryTime, "heart_rate", "145",
                        "nurse-001", null);
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                UUID.randomUUID(), patientId,
                FlowsheetCategory.VITAL_SIGNS,
                entryTime, "heart_rate", "145",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryService.create(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/flowsheet-entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.category")
                        .value("VITAL_SIGNS"))
                .andExpect(jsonPath("$.fieldName")
                        .value("heart_rate"));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                id, UUID.randomUUID(),
                FlowsheetCategory.INTAKE_OUTPUT,
                Instant.now(), "iv_fluid_in", "25",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/flowsheet-entries/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category")
                        .value("INTAKE_OUTPUT"));
    }

    @Test
    @WithMockUser
    void getByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                UUID.randomUUID(), patientId,
                FlowsheetCategory.ASSESSMENT,
                Instant.now(), "skin_color", "Pink",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryService.getByPatient(
                eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get(
                "/api/v1/flowsheet-entries/patient/{patientId}",
                        patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].documentedBy")
                        .value("nurse-001"));
    }

    @Test
    @WithMockUser
    void getByPatientAndCategory_shouldReturnFilteredPage()
            throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                UUID.randomUUID(), patientId,
                FlowsheetCategory.INTERVENTION,
                Instant.now(), "suction", "Performed",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryService.getByPatientAndCategory(
                eq(patientId),
                eq(FlowsheetCategory.INTERVENTION),
                any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get(
                "/api/v1/flowsheet-entries/patient/{patientId}"
                        + "/category/{category}",
                        patientId, "INTERVENTION"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].category")
                        .value("INTERVENTION"));
    }

    @Test
    @WithMockUser
    void getByPatientAndTimeRange_shouldReturnList()
            throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2025-01-01T00:00:00Z");
        final Instant end = Instant.parse("2025-01-01T23:59:59Z");
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                UUID.randomUUID(), patientId,
                FlowsheetCategory.VITAL_SIGNS,
                Instant.parse("2025-01-01T12:00:00Z"),
                "temperature", "36.8",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryService.getByPatientAndTimeRange(
                eq(patientId), eq(start), eq(end)))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get(
                "/api/v1/flowsheet-entries/patient/{patientId}"
                        + "/range",
                        patientId)
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fieldName")
                        .value("temperature"))
                .andExpect(jsonPath("$[0].fieldValue")
                        .value("36.8"));
    }
}
