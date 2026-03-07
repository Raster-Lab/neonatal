package com.nicusystem.clinical;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for ClinicalNoteController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ClinicalNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClinicalNoteService clinicalNoteService;

    @Test
    @WithMockUser
    void createNote_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateClinicalNoteRequest request = new CreateClinicalNoteRequest(
                patientId, NoteType.PROGRESS,
                "subjective", "objective", "assessment", "plan",
                null, "author-001", "Physician", recordedAt);
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                UUID.randomUUID(), patientId, NoteType.PROGRESS,
                "subjective", "objective", "assessment", "plan",
                null, "author-001", "Physician", null, null, recordedAt, true);
        when(clinicalNoteService.createNote(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/clinical-notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.noteType").value("PROGRESS"))
                .andExpect(jsonPath("$.authorId").value("author-001"));
    }

    @Test
    @WithMockUser
    void getNoteById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                id, UUID.randomUUID(), NoteType.ADMISSION,
                null, null, null, null,
                null, "author-001", null, null, null, Instant.now(), true);
        when(clinicalNoteService.getNoteById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/clinical-notes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.noteType").value("ADMISSION"));
    }

    @Test
    @WithMockUser
    void getNotesByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                UUID.randomUUID(), patientId, NoteType.PROGRESS,
                null, null, null, null,
                null, "author-001", null, null, null, Instant.now(), true);
        when(clinicalNoteService.getNotesByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/clinical-notes/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].authorId").value("author-001"));
    }

    @Test
    @WithMockUser
    void getNotesByPatientAndType_shouldReturnFilteredPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                UUID.randomUUID(), patientId, NoteType.PROCEDURE,
                null, null, null, null,
                null, "author-001", null, null, null, Instant.now(), true);
        when(clinicalNoteService.getNotesByPatientAndType(
                eq(patientId), eq(NoteType.PROCEDURE), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/clinical-notes/patient/{patientId}/type/{type}",
                        patientId, "PROCEDURE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].noteType").value("PROCEDURE"));
    }

    @Test
    @WithMockUser
    void addCoSignature_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                id, UUID.randomUUID(), NoteType.PROGRESS,
                null, null, null, null,
                null, "author-001", null, "cosigner-001", Instant.now(), Instant.now(), true);
        when(clinicalNoteService.addCoSignature(eq(id), eq("cosigner-001"))).thenReturn(dto);

        // When / Then
        mockMvc.perform(put("/api/v1/clinical-notes/{id}/co-sign", id)
                        .param("coSignerId", "cosigner-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coSignerId").value("cosigner-001"));
    }
}
