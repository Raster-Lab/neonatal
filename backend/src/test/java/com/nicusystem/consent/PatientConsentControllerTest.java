package com.nicusystem.consent;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for PatientConsentController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PatientConsentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientConsentService patientConsentService;

    @Test
    @WithMockUser
    void createConsent_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant signedAt = Instant.now();
        final CreatePatientConsentRequest request = new CreatePatientConsentRequest(
                patientId, ConsentType.TREATMENT, ConsentStatus.GRANTED,
                "Jane Doe", "Mother", signedAt, null, null);
        final PatientConsentDto dto = new PatientConsentDto(
                UUID.randomUUID(), patientId, ConsentType.TREATMENT, ConsentStatus.GRANTED,
                "Jane Doe", "Mother", signedAt, null, null, true);
        when(patientConsentService.createConsent(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/consents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.consentType").value("TREATMENT"))
                .andExpect(jsonPath("$.consentStatus").value("GRANTED"));
    }

    @Test
    @WithMockUser
    void getConsent_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final PatientConsentDto dto = new PatientConsentDto(
                id, UUID.randomUUID(), ConsentType.SURGERY, ConsentStatus.PENDING,
                null, null, null, null, null, true);
        when(patientConsentService.getConsentById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/consents/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consentType").value("SURGERY"));
    }

    @Test
    @WithMockUser
    void getConsentsByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final PatientConsentDto dto = new PatientConsentDto(
                UUID.randomUUID(), patientId, ConsentType.TREATMENT, ConsentStatus.GRANTED,
                null, null, null, null, null, true);
        when(patientConsentService.getConsentsByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/consents/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].consentType").value("TREATMENT"));
    }

    @Test
    @WithMockUser
    void getConsentsByPatientAndType_shouldReturnList() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final PatientConsentDto dto = new PatientConsentDto(
                UUID.randomUUID(), patientId, ConsentType.PHOTOGRAPHY, ConsentStatus.GRANTED,
                null, null, null, null, null, true);
        when(patientConsentService.getConsentsByPatientAndType(
                eq(patientId), eq(ConsentType.PHOTOGRAPHY)))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/consents/patient/{patientId}/type/{type}",
                        patientId, "PHOTOGRAPHY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].consentType").value("PHOTOGRAPHY"));
    }

    @Test
    @WithMockUser
    void updateConsentStatus_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final PatientConsentDto dto = new PatientConsentDto(
                id, UUID.randomUUID(), ConsentType.TREATMENT, ConsentStatus.REVOKED,
                null, null, null, null, null, true);
        when(patientConsentService.updateConsentStatus(id, ConsentStatus.REVOKED)).thenReturn(dto);

        // When / Then
        mockMvc.perform(patch("/api/v1/consents/{id}/status", id)
                        .param("status", "REVOKED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consentStatus").value("REVOKED"));
    }
}
