package com.nicusystem.consent;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for PatientConsent entities.
 */
@Repository
public interface PatientConsentRepository extends JpaRepository<PatientConsent, UUID> {

    /**
     * Finds consent records for a patient ordered by signed time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of patient consent records
     */
    Page<PatientConsent> findByPatientIdOrderBySignedAtDesc(
            UUID patientId, Pageable pageable);

    /**
     * Finds consent records for a patient filtered by consent type.
     *
     * @param patientId   the patient UUID
     * @param consentType the consent type
     * @return list of patient consent records
     */
    List<PatientConsent> findByPatientIdAndConsentType(
            UUID patientId, ConsentType consentType);
}
