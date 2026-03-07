package com.nicusystem.lab;

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
 * Tests for LabResultController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class LabResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LabResultService labResultService;

    @Test
    @WithMockUser
    void recordLabResult_shouldReturnCreated() throws Exception {
        // Given
        final UUID labOrderId = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final CreateLabResultRequest request = new CreateLabResultRequest(
                labOrderId, patientId, "Hemoglobin", "12.5", "g/dL",
                null, null, false, false, null, null, null);
        final LabResultDto dto = new LabResultDto(UUID.randomUUID(), labOrderId, patientId,
                "Hemoglobin", "12.5", "g/dL", null, null, false, false,
                null, null, null, Instant.now(), Instant.now());
        when(labResultService.recordLabResult(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/lab-results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.testName").value("Hemoglobin"))
                .andExpect(jsonPath("$.resultValue").value("12.5"));
    }

    @Test
    @WithMockUser
    void getLabResultById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final LabResultDto dto = new LabResultDto(id, UUID.randomUUID(), UUID.randomUUID(),
                "WBC", "8.5", "K/uL", null, null, false, false,
                null, null, null, Instant.now(), Instant.now());
        when(labResultService.getLabResultById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/lab-results/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testName").value("WBC"));
    }

    @Test
    @WithMockUser
    void getLabResultsByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LabResultDto dto = new LabResultDto(UUID.randomUUID(), UUID.randomUUID(), patientId,
                "Platelets", "250", "K/uL", null, null, false, false,
                null, null, null, Instant.now(), Instant.now());
        when(labResultService.getLabResultsByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/lab-results/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].testName").value("Platelets"));
    }

    @Test
    @WithMockUser
    void getLabResultsByLabOrder_shouldReturnList() throws Exception {
        // Given
        final UUID labOrderId = UUID.randomUUID();
        final LabResultDto dto = new LabResultDto(UUID.randomUUID(), labOrderId, UUID.randomUUID(),
                "Hemoglobin", "13.0", "g/dL", null, null, false, false,
                null, null, null, Instant.now(), Instant.now());
        when(labResultService.getLabResultsByLabOrder(labOrderId)).thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/lab-results/order/{labOrderId}", labOrderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].testName").value("Hemoglobin"));
    }

    @Test
    @WithMockUser
    void getCriticalLabResultsByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LabResultDto dto = new LabResultDto(UUID.randomUUID(), UUID.randomUUID(), patientId,
                "Potassium", "7.2", "mEq/L", null, null, true, true,
                null, null, null, Instant.now(), Instant.now());
        when(labResultService.getCriticalLabResultsByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/lab-results/patient/{patientId}/critical", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].isCritical").value(true));
    }
}
