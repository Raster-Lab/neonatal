package com.nicusystem.assessment;

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
 * Tests for NeonatalAssessmentService.
 */
@ExtendWith(MockitoExtension.class)
class NeonatalAssessmentServiceTest {

    @Mock
    private NeonatalAssessmentRepository assessmentRepository;

    @Mock
    private NeonatalAssessmentMapper assessmentMapper;

    @InjectMocks
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
    void createAssessment_shouldSaveAndReturnDto() {
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
        final NeonatalAssessment entity = new NeonatalAssessment();
        final NeonatalAssessment saved = new NeonatalAssessment();
        final NeonatalAssessmentDto dto = buildDto(UUID.randomUUID(), patientId, AssessmentType.ADMISSION);
        when(assessmentMapper.toEntity(request)).thenReturn(entity);
        when(assessmentRepository.save(entity)).thenReturn(saved);
        when(assessmentMapper.toDto(saved)).thenReturn(dto);

        // When
        final NeonatalAssessmentDto result = assessmentService.createAssessment(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(assessmentRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final NeonatalAssessment entity = new NeonatalAssessment();
        final NeonatalAssessmentDto dto = buildDto(id, UUID.randomUUID(), AssessmentType.SHIFT);
        when(assessmentRepository.findById(id)).thenReturn(Optional.of(entity));
        when(assessmentMapper.toDto(entity)).thenReturn(dto);

        // When
        final NeonatalAssessmentDto result = assessmentService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(assessmentRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> assessmentService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("NeonatalAssessment");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final NeonatalAssessment entity = new NeonatalAssessment();
        final NeonatalAssessmentDto dto = buildDto(UUID.randomUUID(), patientId, AssessmentType.DAILY_ROUND);
        when(assessmentRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(assessmentMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<NeonatalAssessmentDto> result =
                assessmentService.getByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getByPatientAndType_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final NeonatalAssessment entity = new NeonatalAssessment();
        final NeonatalAssessmentDto dto = buildDto(UUID.randomUUID(), patientId, AssessmentType.DISCHARGE);
        when(assessmentRepository.findByPatientIdAndAssessmentType(
                patientId, AssessmentType.DISCHARGE, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(assessmentMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<NeonatalAssessmentDto> result =
                assessmentService.getByPatientAndType(patientId, AssessmentType.DISCHARGE, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).assessmentType()).isEqualTo(AssessmentType.DISCHARGE);
    }
}
