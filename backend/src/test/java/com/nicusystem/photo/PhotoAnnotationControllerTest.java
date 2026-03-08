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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link PhotoAnnotationController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PhotoAnnotationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PhotoAnnotationService photoAnnotationService;

    private final UUID testId = UUID.randomUUID();
    private final UUID testPhotoId = UUID.randomUUID();
    private final Instant now = Instant.parse("2024-06-15T10:30:00Z");

    @Test
    @WithMockUser
    void create_validRequest_returnsCreated() throws Exception {
        // Given
        final CreatePhotoAnnotationRequest request = new CreatePhotoAnnotationRequest(
                testPhotoId, AnnotationType.TEXT, "Rash observed",
                100.0, 200.0, null, null, "#FF0000", "Dr. Smith");
        final PhotoAnnotationDto dto = buildDto();
        when(photoAnnotationService.create(any(CreatePhotoAnnotationRequest.class)))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/photo-annotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.annotationType").value("TEXT"))
                .andExpect(jsonPath("$.xCoordinate").value(100.0));
    }

    @Test
    @WithMockUser
    void getById_existingId_returnsOk() throws Exception {
        // Given
        final PhotoAnnotationDto dto = buildDto();
        when(photoAnnotationService.getById(testId)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/photo-annotations/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annotationType").value("TEXT"));
    }

    @Test
    @WithMockUser
    void getByPhoto_returnsListOfAnnotations() throws Exception {
        // Given
        final PhotoAnnotationDto dto = buildDto();
        when(photoAnnotationService.getByPhoto(testPhotoId))
                .thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(get("/api/v1/photo-annotations/photo/{photoId}", testPhotoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].annotationType").value("TEXT"));
    }

    @Test
    @WithMockUser
    void getByPhotoAndType_returnsFilteredList() throws Exception {
        // Given
        final PhotoAnnotationDto dto = buildDto();
        when(photoAnnotationService.getByPhotoAndType(testPhotoId, AnnotationType.TEXT))
                .thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(get("/api/v1/photo-annotations/photo/{photoId}/type/{type}",
                        testPhotoId, "TEXT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Rash observed"));
    }

    @Test
    @WithMockUser
    void delete_existingId_returnsNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/photo-annotations/{id}", testId))
                .andExpect(status().isNoContent());

        verify(photoAnnotationService).delete(testId);
    }

    @Test
    @WithMockUser
    void delete_nonExistingId_returnsNotFound() throws Exception {
        // Given
        final UUID missingId = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("PhotoAnnotation", missingId.toString()))
                .when(photoAnnotationService).delete(missingId);

        // When & Then
        mockMvc.perform(delete("/api/v1/photo-annotations/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    private PhotoAnnotationDto buildDto() {
        return new PhotoAnnotationDto(
                testId, testPhotoId, AnnotationType.TEXT, "Rash observed",
                100.0, 200.0, 50.0, 30.0, "#FF0000", "Dr. Smith",
                now, now);
    }
}
