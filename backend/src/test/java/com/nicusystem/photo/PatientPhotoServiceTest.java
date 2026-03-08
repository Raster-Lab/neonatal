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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link PatientPhotoService}.
 */
@ExtendWith(MockitoExtension.class)
class PatientPhotoServiceTest {

    @Mock
    private PatientPhotoRepository patientPhotoRepository;

    @Mock
    private PatientPhotoMapper patientPhotoMapper;

    @InjectMocks
    private PatientPhotoService patientPhotoService;

    @Test
    void createPhoto_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final byte[] data = {1, 2, 3};
        final CreatePatientPhotoRequest request = new CreatePatientPhotoRequest(
                patientId, "photo.jpg", "image/jpeg", 3L,
                data, "desc", Instant.now(), "nurse");
        final PatientPhoto entity = new PatientPhoto();
        final PatientPhoto saved = new PatientPhoto();
        final PatientPhotoDto dto = new PatientPhotoDto(
                UUID.randomUUID(), patientId, "photo.jpg", "image/jpeg",
                3L, "desc", Instant.now(), "nurse", null, null);
        when(patientPhotoMapper.toEntity(request)).thenReturn(entity);
        when(patientPhotoRepository.save(entity)).thenReturn(saved);
        when(patientPhotoMapper.toDto(saved)).thenReturn(dto);

        // When
        final PatientPhotoDto result = patientPhotoService.createPhoto(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(patientPhotoRepository).save(entity);
    }

    @Test
    void getPhotoById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final PatientPhoto entity = new PatientPhoto();
        final PatientPhotoDto dto = new PatientPhotoDto(
                id, UUID.randomUUID(), "photo.jpg", "image/jpeg",
                2048L, null, Instant.now(), null, null, null);
        when(patientPhotoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(patientPhotoMapper.toDto(entity)).thenReturn(dto);

        // When
        final PatientPhotoDto result = patientPhotoService.getPhotoById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getPhotoById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(patientPhotoRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> patientPhotoService.getPhotoById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PatientPhoto");
    }

    @Test
    void getPhotoData_existingId_returnsBytes() {
        // Given
        final UUID id = UUID.randomUUID();
        final byte[] data = {10, 20, 30};
        final PatientPhoto entity = new PatientPhoto();
        entity.setPhotoData(data);
        when(patientPhotoRepository.findById(id)).thenReturn(Optional.of(entity));

        // When
        final byte[] result = patientPhotoService.getPhotoData(id);

        // Then
        assertThat(result).isEqualTo(data);
    }

    @Test
    void getPhotoData_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(patientPhotoRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> patientPhotoService.getPhotoData(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PatientPhoto");
    }

    @Test
    void getPhotosByPatient_returnsPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 10);
        final PatientPhoto entity = new PatientPhoto();
        final PatientPhotoDto dto = new PatientPhotoDto(
                UUID.randomUUID(), patientId, "photo.jpg", "image/jpeg",
                2048L, null, Instant.now(), null, null, null);
        final Page<PatientPhoto> page = new PageImpl<>(List.of(entity));
        when(patientPhotoRepository.findByPatientId(patientId, pageable)).thenReturn(page);
        when(patientPhotoMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<PatientPhotoDto> result =
                patientPhotoService.getPhotosByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void deletePhoto_existingId_shouldDelete() {
        // Given
        final UUID id = UUID.randomUUID();
        final PatientPhoto entity = new PatientPhoto();
        when(patientPhotoRepository.findById(id)).thenReturn(Optional.of(entity));

        // When
        patientPhotoService.deletePhoto(id);

        // Then
        verify(patientPhotoRepository).delete(entity);
    }

    @Test
    void deletePhoto_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(patientPhotoRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> patientPhotoService.deletePhoto(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PatientPhoto");
    }
}
