package com.nicusystem.vitals.autodoc;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AutoDocConfig entities.
 */
@Repository
public interface AutoDocConfigRepository extends JpaRepository<AutoDocConfig, UUID> {

    /**
     * Finds enabled auto-doc configurations for a patient.
     *
     * @param patientId the patient UUID
     * @return list of enabled configurations
     */
    List<AutoDocConfig> findByPatientIdAndEnabledTrue(UUID patientId);

    /**
     * Finds auto-doc configurations for a patient ordered by creation date.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of configurations
     */
    Page<AutoDocConfig> findByPatientIdOrderByCreatedAtDesc(UUID patientId, Pageable pageable);
}
