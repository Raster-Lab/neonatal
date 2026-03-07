package com.nicusystem.assessment;

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
 * Tests for NeonatalAssessmentController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class NeonatalAssessmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NeonatalAssessmentService assessmentService;

    private NeonatalAssessmentDto buildDto(final UUID id, final UUID patientId,
            final AssessmentType type) {
        return new NeonatalAssessmentDto(
                id, patientId, type, Instant.now(), "nurse-001",
                null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null, null,
                null, null,
                null, null, null,
                null, null, null, null, null,
                null);
    }

    @Test
    @WithMockUser
    void createAssessment_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant assessedAt = Instant.now();
        final CreateNeonatalAssessmentRequest request = new CreateNeonatalAssessmentRequest(
                patientId, AssessmentType.ADMISSION, assessedAt, "nurse-001",
                null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null, null,
                null, null,
                null, null, null,
                null, null, null, null, null,
                null);
        final NeonatalAssessmentDto dto = buildDto(UUID.randomUUID(), patientId, AssessmentType.ADMISSION);
        when(assessmentService.createAssessment(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/assessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assessmentType").value("ADMISSION"))
                .andExpect(jsonPath("$.assessedBy").value("nurse-001"));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final NeonatalAssessmentDto dto = buildDto(id, UUID.randomUUID(), AssessmentType.SHIFT);
        when(assessmentService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/assessments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assessmentType").value("SHIFT"));
    }

    @Test
    @WithMockUser
    void getByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final NeonatalAssessmentDto dto = buildDto(UUID.randomUUID(), patientId, AssessmentType.DAILY_ROUND);
        when(assessmentService.getByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/assessments/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].assessedBy").value("nurse-001"));
    }

    @Test
    @WithMockUser
    void getByPatientAndType_shouldReturnFilteredPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final NeonatalAssessmentDto dto = buildDto(UUID.randomUUID(), patientId, AssessmentType.DISCHARGE);
        when(assessmentService.getByPatientAndType(
                eq(patientId), eq(AssessmentType.DISCHARGE), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/assessments/patient/{patientId}/type/{assessmentType}",
                        patientId, "DISCHARGE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].assessmentType").value("DISCHARGE"));
    }
}
