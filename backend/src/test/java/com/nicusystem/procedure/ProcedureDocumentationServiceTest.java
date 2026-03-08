package com.nicusystem.procedure;

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
 * Tests for ProcedureDocumentationService.
 */
@ExtendWith(MockitoExtension.class)
class ProcedureDocumentationServiceTest {

    @Mock
    private ProcedureDocumentationRepository procedureDocumentationRepository;

    @Mock
    private ProcedureDocumentationMapper procedureDocumentationMapper;

    @InjectMocks
    private ProcedureDocumentationService procedureDocumentationService;

    @Test
    void create_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant performedAt = Instant.now();
        final CreateProcedureDocumentationRequest request =
                new CreateProcedureDocumentationRequest(
                        patientId, ProcedureType.INTUBATION,
                        "doctor-001", "nurse-002",
                        "Respiratory failure", "Oral intubation",
                        "Successful", "None", null, performedAt);
        final ProcedureDocumentation entity = new ProcedureDocumentation();
        final ProcedureDocumentation saved = new ProcedureDocumentation();
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                UUID.randomUUID(), patientId, ProcedureType.INTUBATION,
                "doctor-001", "nurse-002",
                "Respiratory failure", "Oral intubation",
                "Successful", "None", null, performedAt,
                Instant.now(), Instant.now());
        when(procedureDocumentationMapper.toEntity(request)).thenReturn(entity);
        when(procedureDocumentationRepository.save(entity)).thenReturn(saved);
        when(procedureDocumentationMapper.toDto(saved)).thenReturn(dto);

        // When
        final ProcedureDocumentationDto result =
                procedureDocumentationService.create(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(procedureDocumentationRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final ProcedureDocumentation entity = new ProcedureDocumentation();
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                id, UUID.randomUUID(), ProcedureType.LINE_PLACEMENT,
                "doctor-001", null, null, null, null, null, null,
                Instant.now(), Instant.now(), Instant.now());
        when(procedureDocumentationRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(procedureDocumentationMapper.toDto(entity)).thenReturn(dto);

        // When
        final ProcedureDocumentationDto result =
                procedureDocumentationService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(procedureDocumentationRepository.findById(id))
                .thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> procedureDocumentationService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("ProcedureDocumentation");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final ProcedureDocumentation entity = new ProcedureDocumentation();
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                UUID.randomUUID(), patientId, ProcedureType.LUMBAR_PUNCTURE,
                "doctor-001", null, null, null, null, null, null,
                Instant.now(), Instant.now(), Instant.now());
        when(procedureDocumentationRepository
                .findByPatientIdOrderByPerformedAtDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(procedureDocumentationMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<ProcedureDocumentationDto> result =
                procedureDocumentationService.getByPatient(
                        patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getByPatientAndType_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final ProcedureDocumentation entity = new ProcedureDocumentation();
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                UUID.randomUUID(), patientId, ProcedureType.CHEST_TUBE,
                "doctor-001", null, null, null, null, null, null,
                Instant.now(), Instant.now(), Instant.now());
        when(procedureDocumentationRepository
                .findByPatientIdAndProcedureTypeOrderByPerformedAtDesc(
                        patientId, ProcedureType.CHEST_TUBE, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(procedureDocumentationMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<ProcedureDocumentationDto> result =
                procedureDocumentationService.getByPatientAndType(
                        patientId, ProcedureType.CHEST_TUBE, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).procedureType())
                .isEqualTo(ProcedureType.CHEST_TUBE);
    }
}
