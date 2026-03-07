package com.nicusystem.clinical;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for ClinicalNoteService.
 */
@ExtendWith(MockitoExtension.class)
class ClinicalNoteServiceTest {

    @Mock
    private ClinicalNoteRepository clinicalNoteRepository;

    @Mock
    private ClinicalNoteMapper clinicalNoteMapper;

    @InjectMocks
    private ClinicalNoteService clinicalNoteService;

    @Test
    void createNote_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateClinicalNoteRequest request = new CreateClinicalNoteRequest(
                patientId, NoteType.PROGRESS,
                "subjective", "objective", "assessment", "plan",
                null, "author-001", "Physician", recordedAt);
        final ClinicalNote entity = new ClinicalNote();
        final ClinicalNote saved = new ClinicalNote();
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                UUID.randomUUID(), patientId, NoteType.PROGRESS,
                "subjective", "objective", "assessment", "plan",
                null, "author-001", "Physician", null, null, recordedAt, true);
        when(clinicalNoteMapper.toEntity(request)).thenReturn(entity);
        when(clinicalNoteRepository.save(entity)).thenReturn(saved);
        when(clinicalNoteMapper.toDto(saved)).thenReturn(dto);

        // When
        final ClinicalNoteDto result = clinicalNoteService.createNote(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(clinicalNoteRepository).save(entity);
    }

    @Test
    void getNoteById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final ClinicalNote entity = new ClinicalNote();
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                id, UUID.randomUUID(), NoteType.ADMISSION,
                null, null, null, null,
                null, "author-001", null, null, null, Instant.now(), true);
        when(clinicalNoteRepository.findById(id)).thenReturn(Optional.of(entity));
        when(clinicalNoteMapper.toDto(entity)).thenReturn(dto);

        // When
        final ClinicalNoteDto result = clinicalNoteService.getNoteById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getNoteById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(clinicalNoteRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> clinicalNoteService.getNoteById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("ClinicalNote");
    }

    @Test
    void getNotesByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final ClinicalNote entity = new ClinicalNote();
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                UUID.randomUUID(), patientId, NoteType.PROGRESS,
                null, null, null, null,
                null, "author-001", null, null, null, Instant.now(), true);
        when(clinicalNoteRepository.findByPatientIdOrderByRecordedAtDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(clinicalNoteMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<ClinicalNoteDto> result =
                clinicalNoteService.getNotesByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getNotesByPatientAndType_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final ClinicalNote entity = new ClinicalNote();
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                UUID.randomUUID(), patientId, NoteType.PROCEDURE,
                null, null, null, null,
                null, "author-001", null, null, null, Instant.now(), true);
        when(clinicalNoteRepository.findByPatientIdAndNoteTypeOrderByRecordedAtDesc(
                patientId, NoteType.PROCEDURE, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(clinicalNoteMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<ClinicalNoteDto> result =
                clinicalNoteService.getNotesByPatientAndType(
                        patientId, NoteType.PROCEDURE, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).noteType()).isEqualTo(NoteType.PROCEDURE);
    }

    @Test
    void addCoSignature_existingNote_updatesAndReturnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final ClinicalNote entity = new ClinicalNote();
        final ClinicalNote saved = new ClinicalNote();
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                id, UUID.randomUUID(), NoteType.PROGRESS,
                null, null, null, null,
                null, "author-001", null, "cosigner-001", Instant.now(), Instant.now(), true);
        when(clinicalNoteRepository.findById(id)).thenReturn(Optional.of(entity));
        when(clinicalNoteRepository.save(entity)).thenReturn(saved);
        when(clinicalNoteMapper.toDto(saved)).thenReturn(dto);

        // When
        final ClinicalNoteDto result = clinicalNoteService.addCoSignature(id, "cosigner-001");

        // Then
        assertThat(result).isEqualTo(dto);
        assertThat(entity.getCoSignerId()).isEqualTo("cosigner-001");
        assertThat(entity.getCoSignedAt()).isNotNull();
        verify(clinicalNoteRepository).save(entity);
    }

    @Test
    void addCoSignature_nonExistingNote_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(clinicalNoteRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> clinicalNoteService.addCoSignature(id, "cosigner-001"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("ClinicalNote");
    }
}
