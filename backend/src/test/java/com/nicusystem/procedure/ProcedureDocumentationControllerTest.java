package com.nicusystem.procedure;

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
 * Tests for ProcedureDocumentationController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ProcedureDocumentationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProcedureDocumentationService procedureDocumentationService;

    @Test
    @WithMockUser
    void create_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant performedAt = Instant.now();
        final CreateProcedureDocumentationRequest request =
                new CreateProcedureDocumentationRequest(
                        patientId, ProcedureType.INTUBATION,
                        "doctor-001", "nurse-002",
                        "Respiratory failure", "Oral intubation",
                        "Successful", "None", null, performedAt);
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                UUID.randomUUID(), patientId, ProcedureType.INTUBATION,
                "doctor-001", "nurse-002",
                "Respiratory failure", "Oral intubation",
                "Successful", "None", null, performedAt,
                Instant.now(), Instant.now());
        when(procedureDocumentationService.create(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/procedure-docs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.procedureType").value("INTUBATION"))
                .andExpect(jsonPath("$.performedBy").value("doctor-001"));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                id, UUID.randomUUID(), ProcedureType.LINE_PLACEMENT,
                "doctor-001", null, null, null, null, null, null,
                Instant.now(), Instant.now(), Instant.now());
        when(procedureDocumentationService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/procedure-docs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.procedureType")
                        .value("LINE_PLACEMENT"));
    }

    @Test
    @WithMockUser
    void getByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                UUID.randomUUID(), patientId,
                ProcedureType.LUMBAR_PUNCTURE,
                "doctor-001", null, null, null, null, null, null,
                Instant.now(), Instant.now(), Instant.now());
        when(procedureDocumentationService.getByPatient(
                eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/procedure-docs/patient/{patientId}",
                        patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].performedBy")
                        .value("doctor-001"));
    }

    @Test
    @WithMockUser
    void getByPatientAndType_shouldReturnFilteredPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                UUID.randomUUID(), patientId, ProcedureType.CHEST_TUBE,
                "doctor-001", null, null, null, null, null, null,
                Instant.now(), Instant.now(), Instant.now());
        when(procedureDocumentationService.getByPatientAndType(
                eq(patientId), eq(ProcedureType.CHEST_TUBE),
                any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get(
                "/api/v1/procedure-docs/patient/{patientId}/type/{type}",
                        patientId, "CHEST_TUBE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].procedureType")
                        .value("CHEST_TUBE"));
    }
}
