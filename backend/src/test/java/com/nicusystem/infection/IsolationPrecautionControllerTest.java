package com.nicusystem.infection;

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
 * Tests for {@link IsolationPrecautionController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
class IsolationPrecautionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IsolationPrecautionService service;

    @Test
    @WithMockUser
    void createPrecaution_shouldReturnCreated()
            throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant initiatedAt = Instant.now();
        final CreateIsolationPrecautionRequest request =
                new CreateIsolationPrecautionRequest(
                        patientId,
                        IsolationPrecautionType.CONTACT,
                        initiatedAt, "Dr. Smith",
                        "MRSA", "Contact precautions");
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        UUID.randomUUID(), patientId,
                        IsolationPrecautionType.CONTACT,
                        initiatedAt, null,
                        "Dr. Smith", "MRSA",
                        "Contact precautions",
                        null, null);
        when(service.createPrecaution(any()))
                .thenReturn(dto);

        // When / Then
        mockMvc.perform(post(
                "/api/v1/isolation-precautions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.precautionType")
                        .value("CONTACT"))
                .andExpect(jsonPath("$.initiatedBy")
                        .value("Dr. Smith"));
    }

    @Test
    @WithMockUser
    void getPrecaution_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        id, UUID.randomUUID(),
                        IsolationPrecautionType.DROPLET,
                        Instant.now(), null,
                        "Dr. Jones", "RSV",
                        null, null, null);
        when(service.getPrecautionById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get(
                "/api/v1/isolation-precautions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precautionType")
                        .value("DROPLET"))
                .andExpect(jsonPath("$.initiatedBy")
                        .value("Dr. Jones"));
    }

    @Test
    @WithMockUser
    void getPrecautionsByPatient_shouldReturnPage()
            throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        UUID.randomUUID(), patientId,
                        IsolationPrecautionType.AIRBORNE,
                        Instant.now(), null,
                        null, "TB suspect",
                        null, null, null);
        when(service.getPrecautionsByPatient(
                eq(patientId), any(Pageable.class)))
                .thenReturn(
                        new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get(
                "/api/v1/isolation-precautions"
                        + "/patient/{patientId}",
                patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                        "$.content[0].precautionType")
                        .value("AIRBORNE"));
    }

    @Test
    @WithMockUser
    void getPrecautionsByPatientAndType_shouldReturnPage()
            throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        UUID.randomUUID(), patientId,
                        IsolationPrecautionType
                                .ENHANCED_CONTACT,
                        Instant.now(), null,
                        null, "VRE",
                        null, null, null);
        when(service.getPrecautionsByPatientAndType(
                eq(patientId),
                eq(IsolationPrecautionType
                        .ENHANCED_CONTACT),
                any(Pageable.class)))
                .thenReturn(
                        new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get(
                "/api/v1/isolation-precautions"
                        + "/patient/{patientId}"
                        + "/type/{precautionType}",
                patientId, "ENHANCED_CONTACT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                        "$.content[0].precautionType")
                        .value("ENHANCED_CONTACT"));
    }

    @Test
    @WithMockUser
    void discontinuePrecaution_shouldReturnOk()
            throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final Instant now = Instant.now();
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        id, UUID.randomUUID(),
                        IsolationPrecautionType.CONTACT,
                        Instant.now(), now,
                        "Dr. Smith", "MRSA",
                        null, null, null);
        when(service.discontinuePrecaution(id))
                .thenReturn(dto);

        // When / Then
        mockMvc.perform(patch(
                "/api/v1/isolation-precautions"
                        + "/{id}/discontinue", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discontinuedAt")
                        .isNotEmpty());
    }
}
