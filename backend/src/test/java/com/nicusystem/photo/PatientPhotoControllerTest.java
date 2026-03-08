package com.nicusystem.photo;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicusystem.common.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link PatientPhotoController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PatientPhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PatientPhotoService patientPhotoService;

    private final UUID testId = UUID.randomUUID();
    private final UUID testPatientId = UUID.randomUUID();
    private final Instant now = Instant.parse("2024-06-15T10:30:00Z");

    @Test
    @WithMockUser
    void createPhoto_validRequest_returnsCreated() throws Exception {
        // Given
        final CreatePatientPhotoRequest request = new CreatePatientPhotoRequest(
                testPatientId, "photo.jpg", "image/jpeg", 3L,
                new byte[]{1, 2, 3}, "desc", now, "nurse");
        final PatientPhotoDto dto = buildDto();
        when(patientPhotoService.createPhoto(any(CreatePatientPhotoRequest.class)))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/patient-photos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fileName").value("photo.jpg"))
                .andExpect(jsonPath("$.contentType").value("image/jpeg"));
    }

    @Test
    @WithMockUser
    void getPhoto_existingId_returnsOk() throws Exception {
        // Given
        final PatientPhotoDto dto = buildDto();
        when(patientPhotoService.getPhotoById(testId)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/patient-photos/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("photo.jpg"));
    }

    @Test
    @WithMockUser
    void getPhotoData_existingId_returnsBytesWithCorrectContentType() throws Exception {
        // Given
        final byte[] data = {10, 20, 30, 40};
        final PatientPhotoDto dto = buildDto();
        when(patientPhotoService.getPhotoById(testId)).thenReturn(dto);
        when(patientPhotoService.getPhotoData(testId)).thenReturn(data);

        // When & Then
        mockMvc.perform(get("/api/v1/patient-photos/{id}/data", testId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "image/jpeg"))
                .andExpect(header().exists("Content-Disposition"))
                .andExpect(content().bytes(data));
    }

    @Test
    @WithMockUser
    void getPhotoData_nonExistingId_returnsNotFound() throws Exception {
        // Given
        final UUID missingId = UUID.randomUUID();
        when(patientPhotoService.getPhotoById(missingId))
                .thenThrow(new ResourceNotFoundException("PatientPhoto", missingId.toString()));

        // When & Then
        mockMvc.perform(get("/api/v1/patient-photos/{id}/data", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getPhotosByPatient_returnsPage() throws Exception {
        // Given
        final PatientPhotoDto dto = buildDto();
        final var page = new PageImpl<>(List.of(dto));
        when(patientPhotoService.getPhotosByPatient(eq(testPatientId), any()))
                .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/patient-photos/patient/{patientId}", testPatientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fileName").value("photo.jpg"));
    }

    @Test
    @WithMockUser
    void deletePhoto_existingId_returnsNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/patient-photos/{id}", testId))
                .andExpect(status().isNoContent());

        verify(patientPhotoService).deletePhoto(testId);
    }

    @Test
    @WithMockUser
    void deletePhoto_nonExistingId_returnsNotFound() throws Exception {
        // Given
        final UUID missingId = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("PatientPhoto", missingId.toString()))
                .when(patientPhotoService).deletePhoto(missingId);

        // When & Then
        mockMvc.perform(delete("/api/v1/patient-photos/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    private PatientPhotoDto buildDto() {
        return new PatientPhotoDto(
                testId, testPatientId, "photo.jpg", "image/jpeg",
                2048L, "desc", now, "nurse", now, now);
    }
}
