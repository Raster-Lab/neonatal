package com.nicusystem.lab;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for BloodDrawVolume entities.
 */
public interface BloodDrawVolumeRepository extends JpaRepository<BloodDrawVolume, UUID> {

    /**
     * Finds all blood draw records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of blood draw volume records
     */
    Page<BloodDrawVolume> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Calculates the total blood volume drawn for a patient in microliters.
     *
     * @param patientId the patient UUID
     * @return sum of all blood draw volumes, or null if no records exist
     */
    @Query("SELECT SUM(b.volumeUl) FROM BloodDrawVolume b WHERE b.patientId = :patientId")
    Double sumVolumeUlByPatientId(@Param("patientId") UUID patientId);
}
