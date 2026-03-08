package com.nicusystem.photo;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link PhotoAnnotationService}.
 */
@ExtendWith(MockitoExtension.class)
class PhotoAnnotationServiceTest {

    @Mock
    private PhotoAnnotationRepository photoAnnotationRepository;

    @Mock
    private PhotoAnnotationMapper photoAnnotationMapper;

    @InjectMocks
    private PhotoAnnotationService photoAnnotationService;

    @Test
    void create_shouldSaveAndReturnDto() {
        // Given
        final UUID photoId = UUID.randomUUID();
        final CreatePhotoAnnotationRequest request = new CreatePhotoAnnotationRequest(
                photoId, AnnotationType.TEXT, "Rash observed",
                100.0, 200.0, null, null, "#FF0000", "Dr. Smith");
        final PhotoAnnotation entity = new PhotoAnnotation();
        final PhotoAnnotation saved = new PhotoAnnotation();
        final PhotoAnnotationDto dto = new PhotoAnnotationDto(
                UUID.randomUUID(), photoId, AnnotationType.TEXT, "Rash observed",
                100.0, 200.0, null, null, "#FF0000", "Dr. Smith",
                Instant.now(), Instant.now());
        when(photoAnnotationMapper.toEntity(request)).thenReturn(entity);
        when(photoAnnotationRepository.save(entity)).thenReturn(saved);
        when(photoAnnotationMapper.toDto(saved)).thenReturn(dto);

        // When
        final PhotoAnnotationDto result = photoAnnotationService.create(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(photoAnnotationRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final PhotoAnnotation entity = new PhotoAnnotation();
        final PhotoAnnotationDto dto = new PhotoAnnotationDto(
                id, UUID.randomUUID(), AnnotationType.ARROW, null,
                50.0, 75.0, null, null, null, "Nurse Jane",
                Instant.now(), Instant.now());
        when(photoAnnotationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(photoAnnotationMapper.toDto(entity)).thenReturn(dto);

        // When
        final PhotoAnnotationDto result = photoAnnotationService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(photoAnnotationRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> photoAnnotationService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PhotoAnnotation");
    }

    @Test
    void getByPhoto_shouldReturnList() {
        // Given
        final UUID photoId = UUID.randomUUID();
        final PhotoAnnotation entity = new PhotoAnnotation();
        final PhotoAnnotationDto dto = new PhotoAnnotationDto(
                UUID.randomUUID(), photoId, AnnotationType.CIRCLE, null,
                10.0, 20.0, 30.0, 30.0, "#00FF00", "nurse",
                Instant.now(), Instant.now());
        when(photoAnnotationRepository.findByPhotoIdOrderByCreatedAtAsc(photoId))
                .thenReturn(List.of(entity));
        when(photoAnnotationMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<PhotoAnnotationDto> result = photoAnnotationService.getByPhoto(photoId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }

    @Test
    void getByPhotoAndType_shouldReturnFilteredList() {
        // Given
        final UUID photoId = UUID.randomUUID();
        final PhotoAnnotation entity = new PhotoAnnotation();
        final PhotoAnnotationDto dto = new PhotoAnnotationDto(
                UUID.randomUUID(), photoId, AnnotationType.FREEHAND, null,
                5.0, 10.0, null, null, "#0000FF", "Dr. Smith",
                Instant.now(), Instant.now());
        when(photoAnnotationRepository.findByPhotoIdAndAnnotationType(
                photoId, AnnotationType.FREEHAND))
                .thenReturn(List.of(entity));
        when(photoAnnotationMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<PhotoAnnotationDto> result =
                photoAnnotationService.getByPhotoAndType(photoId, AnnotationType.FREEHAND);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).annotationType()).isEqualTo(AnnotationType.FREEHAND);
    }

    @Test
    void delete_existingId_shouldDelete() {
        // Given
        final UUID id = UUID.randomUUID();
        final PhotoAnnotation entity = new PhotoAnnotation();
        when(photoAnnotationRepository.findById(id)).thenReturn(Optional.of(entity));

        // When
        photoAnnotationService.delete(id);

        // Then
        verify(photoAnnotationRepository).delete(entity);
    }

    @Test
    void delete_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(photoAnnotationRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> photoAnnotationService.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PhotoAnnotation");
    }
}
