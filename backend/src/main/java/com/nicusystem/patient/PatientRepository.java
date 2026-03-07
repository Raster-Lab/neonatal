package com.nicusystem.patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Patient entities.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    /**
     * Finds a patient by MRN.
     *
     * @param mrn the medical record number
     * @return optional patient
     */
    Optional<Patient> findByMrn(String mrn);

    /**
     * Finds all active patients.
     *
     * @param pageable pagination information
     * @return page of active patients
     */
    Page<Patient> findByActiveTrue(Pageable pageable);

    /**
     * Searches patients by name (first or last, case-insensitive).
     *
     * @param name     the name to search for
     * @param pageable pagination information
     * @return page of matching patients
     */
    @Query("SELECT p FROM Patient p WHERE p.active = true "
            + "AND (LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) "
            + "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Patient> searchByName(@Param("name") String name, Pageable pageable);

    /**
     * Finds all patients linked to a specific mother.
     *
     * @param motherId the mother's UUID
     * @return list of patients
     */
    List<Patient> findByMotherId(UUID motherId);

    /**
     * Finds a patient by bed number.
     *
     * @param bedNumber the bed number
     * @return optional patient
     */
    Optional<Patient> findByBedNumberAndActiveTrue(String bedNumber);
}
