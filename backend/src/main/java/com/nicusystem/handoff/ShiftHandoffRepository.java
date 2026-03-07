package com.nicusystem.handoff;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ShiftHandoff entities.
 */
@Repository
public interface ShiftHandoffRepository extends JpaRepository<ShiftHandoff, UUID> {

    /**
     * Finds handoff records for a patient ordered by handoff time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of shift handoffs
     */
    Page<ShiftHandoff> findByPatientId(UUID patientId, Pageable pageable);
}
