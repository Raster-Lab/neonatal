package com.nicusystem.assessment;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for NeonatalAssessment entities.
 */
@Repository
public interface NeonatalAssessmentRepository extends JpaRepository<NeonatalAssessment, UUID> {

    /**
     * Finds assessments for a patient ordered by assessment time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of neonatal assessments
     */
    Page<NeonatalAssessment> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Finds assessments for a patient filtered by assessment type.
     *
     * @param patientId      the patient UUID
     * @param assessmentType the assessment type
     * @param pageable       pagination information
     * @return page of neonatal assessments
     */
    Page<NeonatalAssessment> findByPatientIdAndAssessmentType(
            UUID patientId, AssessmentType assessmentType, Pageable pageable);
}
