package com.nicusystem.photo;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link PatientPhoto} entities.
 */
public interface PatientPhotoRepository extends JpaRepository<PatientPhoto, UUID> {

    /**
     * Returns a page of patient photos for the given patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of patient photos
     */
    Page<PatientPhoto> findByPatientId(UUID patientId, Pageable pageable);
}
