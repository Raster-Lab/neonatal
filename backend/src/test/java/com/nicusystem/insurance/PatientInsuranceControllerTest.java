package com.nicusystem.insurance;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for PatientInsuranceController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PatientInsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientInsuranceService patientInsuranceService;

    @Test
    @WithMockUser
    void createInsurance_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreatePatientInsuranceRequest request = new CreatePatientInsuranceRequest(
                patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                null, null, null, null, Instant.now(), null, null);
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                UUID.randomUUID(), patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                null, null, null, null, Instant.now(), null, null, true);
        when(patientInsuranceService.createInsurance(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/insurance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.insuranceType").value("PRIMARY"))
                .andExpect(jsonPath("$.insurerName").value("Blue Cross"));
    }

    @Test
    @WithMockUser
    void getInsurance_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                id, UUID.randomUUID(), InsuranceType.SECONDARY, "Aetna", "POL789",
                null, null, null, null, null, null, null, true);
        when(patientInsuranceService.getInsuranceById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/insurance/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insuranceType").value("SECONDARY"));
    }

    @Test
    @WithMockUser
    void getInsurancesByPatient_shouldReturnList() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                UUID.randomUUID(), patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                null, null, null, null, null, null, null, true);
        when(patientInsuranceService.getInsurancesByPatient(patientId))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/insurance/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].insurerName").value("Blue Cross"));
    }

    @Test
    @WithMockUser
    void getInsurancesByPatientAndType_shouldReturnFilteredList() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                UUID.randomUUID(), patientId, InsuranceType.TERTIARY, "Cigna", "POL456",
                null, null, null, null, null, null, null, true);
        when(patientInsuranceService.getInsurancesByPatientAndType(
                eq(patientId), eq(InsuranceType.TERTIARY)))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/insurance/patient/{patientId}/type/{type}",
                        patientId, "TERTIARY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].insuranceType").value("TERTIARY"));
    }

    @Test
    @WithMockUser
    void updateInsurance_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final CreatePatientInsuranceRequest request = new CreatePatientInsuranceRequest(
                patientId, InsuranceType.PRIMARY, "Blue Cross Updated", "POL999",
                null, null, null, null, null, null, null);
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                id, patientId, InsuranceType.PRIMARY, "Blue Cross Updated", "POL999",
                null, null, null, null, null, null, null, true);
        when(patientInsuranceService.updateInsurance(eq(id), any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(put("/api/v1/insurance/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insurerName").value("Blue Cross Updated"));
    }
}
