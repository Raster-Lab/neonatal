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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link InfectionSurveillanceRecordController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
class InfectionSurveillanceRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InfectionSurveillanceRecordService service;

    @Test
    @WithMockUser
    void createRecord_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant eventDate = Instant.now();
        final CreateInfectionSurveillanceRecordRequest request =
                new CreateInfectionSurveillanceRecordRequest(
                        patientId,
                        InfectionSurveillanceType.CLABSI,
                        eventDate, true,
                        "Staph aureus",
                        7, 14, 5, "Positive");
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        UUID.randomUUID(), patientId,
                        InfectionSurveillanceType.CLABSI,
                        eventDate, true,
                        "Staph aureus",
                        7, 14, 5, "Positive",
                        null, null);
        when(service.createRecord(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post(
                "/api/v1/infection-surveillance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.surveillanceType")
                        .value("CLABSI"))
                .andExpect(jsonPath("$.confirmed")
                        .value(true))
                .andExpect(jsonPath("$.pathogen")
                        .value("Staph aureus"));
    }

    @Test
    @WithMockUser
    void getRecord_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        id, UUID.randomUUID(),
                        InfectionSurveillanceType.VAE,
                        Instant.now(), false,
                        null, null, null, 3, null,
                        null, null);
        when(service.getRecordById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get(
                "/api/v1/infection-surveillance/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.surveillanceType")
                        .value("VAE"))
                .andExpect(jsonPath("$.confirmed")
                        .value(false));
    }

    @Test
    @WithMockUser
    void getRecordsByPatient_shouldReturnPage()
            throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        UUID.randomUUID(), patientId,
                        InfectionSurveillanceType.CAUTI,
                        Instant.now(), true,
                        "E. coli", 5, null, null, null,
                        null, null);
        when(service.getRecordsByPatient(
                eq(patientId), any(Pageable.class)))
                .thenReturn(
                        new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get(
                "/api/v1/infection-surveillance"
                        + "/patient/{patientId}",
                patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                        "$.content[0].surveillanceType")
                        .value("CAUTI"));
    }

    @Test
    @WithMockUser
    void getRecordsByPatientAndType_shouldReturnPage()
            throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        UUID.randomUUID(), patientId,
                        InfectionSurveillanceType
                                .EARLY_ONSET_SEPSIS,
                        Instant.now(), false,
                        null, null, null, null, null,
                        null, null);
        when(service.getRecordsByPatientAndType(
                eq(patientId),
                eq(InfectionSurveillanceType
                        .EARLY_ONSET_SEPSIS),
                any(Pageable.class)))
                .thenReturn(
                        new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get(
                "/api/v1/infection-surveillance"
                        + "/patient/{patientId}"
                        + "/type/{surveillanceType}",
                patientId, "EARLY_ONSET_SEPSIS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                        "$.content[0].surveillanceType")
                        .value("EARLY_ONSET_SEPSIS"));
    }
}
