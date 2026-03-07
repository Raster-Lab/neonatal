package com.nicusystem.assessment;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing neonatal assessments.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NeonatalAssessmentService {

    private final NeonatalAssessmentRepository assessmentRepository;
    private final NeonatalAssessmentMapper assessmentMapper;

    /**
     * Creates a new neonatal assessment.
     *
     * @param request the assessment creation request
     * @return the created assessment DTO
     */
    @Transactional
    public NeonatalAssessmentDto createAssessment(
            final CreateNeonatalAssessmentRequest request) {
        final NeonatalAssessment entity = assessmentMapper.toEntity(request);
        final NeonatalAssessment saved = assessmentRepository.save(entity);
        log.info("Neonatal assessment created: type={}, patientId={}, assessedBy={}",
                request.assessmentType(), request.patientId(), request.assessedBy());
        return assessmentMapper.toDto(saved);
    }

    /**
     * Retrieves a neonatal assessment by ID.
     *
     * @param id the assessment UUID
     * @return the assessment DTO
     */
    @Transactional(readOnly = true)
    public NeonatalAssessmentDto getById(final UUID id) {
        return assessmentRepository.findById(id)
                .map(assessmentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "NeonatalAssessment", id.toString()));
    }

    /**
     * Gets assessments for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of assessment DTOs
     */
    @Transactional(readOnly = true)
    public Page<NeonatalAssessmentDto> getByPatient(
            final UUID patientId, final Pageable pageable) {
        return assessmentRepository.findByPatientId(patientId, pageable)
                .map(assessmentMapper::toDto);
    }

    /**
     * Gets assessments for a patient filtered by assessment type.
     *
     * @param patientId      the patient UUID
     * @param assessmentType the assessment type
     * @param pageable       pagination information
     * @return page of assessment DTOs
     */
    @Transactional(readOnly = true)
    public Page<NeonatalAssessmentDto> getByPatientAndType(
            final UUID patientId,
            final AssessmentType assessmentType,
            final Pageable pageable) {
        return assessmentRepository
                .findByPatientIdAndAssessmentType(patientId, assessmentType, pageable)
                .map(assessmentMapper::toDto);
    }
}
