package com.nicusystem.medication;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for DrugAllergy entities.
 */
@Repository
public interface DrugAllergyRepository
        extends JpaRepository<DrugAllergy, UUID> {

    /**
     * Finds all active allergies for a patient.
     *
     * @param patientId the patient UUID
     * @return list of active drug allergies
     */
    List<DrugAllergy> findByPatientIdAndActiveTrue(UUID patientId);

    /**
     * Finds an active allergy for a patient by allergen name
     * (case-insensitive).
     *
     * @param patientId    the patient UUID
     * @param allergenName the allergen name
     * @return list of matching active drug allergies
     */
    List<DrugAllergy> findByPatientIdAndAllergenNameIgnoreCaseAndActiveTrue(
            UUID patientId, String allergenName);

    /**
     * Finds all allergies for a patient with pagination.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of drug allergies
     */
    Page<DrugAllergy> findByPatientId(UUID patientId, Pageable pageable);
}
