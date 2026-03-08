package com.nicusystem.medication;

import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing drug allergies.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DrugAllergyService {

    private final DrugAllergyRepository drugAllergyRepository;
    private final DrugAllergyMapper drugAllergyMapper;

    /**
     * Creates a new drug allergy record.
     *
     * @param request the creation request
     * @return the created DrugAllergyDto
     */
    @Transactional
    public DrugAllergyDto createAllergy(
            final CreateDrugAllergyRequest request) {
        final DrugAllergy entity = drugAllergyMapper.toEntity(request);
        final DrugAllergy saved = drugAllergyRepository.save(entity);
        log.info("Drug allergy created: patientId={}, allergen={}, severity={}",
                request.patientId(), request.allergenName(),
                request.severity());
        return drugAllergyMapper.toDto(saved);
    }

    /**
     * Retrieves a drug allergy by ID.
     *
     * @param id the allergy UUID
     * @return the DrugAllergyDto
     */
    @Transactional(readOnly = true)
    public DrugAllergyDto getAllergyById(final UUID id) {
        return drugAllergyRepository.findById(id)
                .map(drugAllergyMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DrugAllergy", id.toString()));
    }

    /**
     * Returns all allergies for a patient, paginated.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of DrugAllergyDto
     */
    @Transactional(readOnly = true)
    public Page<DrugAllergyDto> getAllergiesByPatient(
            final UUID patientId, final Pageable pageable) {
        return drugAllergyRepository.findByPatientId(patientId, pageable)
                .map(drugAllergyMapper::toDto);
    }

    /**
     * Checks whether a patient has an active allergy that matches the
     * given medication name (case-insensitive contains match).
     * Throws {@link DrugAllergyException} if a match is found.
     *
     * @param patientId      the patient UUID
     * @param medicationName the medication name to check
     */
    @Transactional(readOnly = true)
    public void checkAllergyForMedication(final UUID patientId,
                                          final String medicationName) {
        final List<DrugAllergy> activeAllergies =
                drugAllergyRepository.findByPatientIdAndActiveTrue(patientId);
        final String lowerMedName = medicationName.toLowerCase();
        for (final DrugAllergy allergy : activeAllergies) {
            if (lowerMedName.contains(
                    allergy.getAllergenName().toLowerCase())) {
                throw new DrugAllergyException(
                        String.format(
                                "Patient has allergy to %s which conflicts"
                                        + " with medication %s",
                                allergy.getAllergenName(), medicationName),
                        allergy.getAllergenName(),
                        medicationName);
            }
        }
    }

    /**
     * Soft-deletes a drug allergy by setting active to false.
     *
     * @param id the allergy UUID
     */
    @Transactional
    public void deleteAllergy(final UUID id) {
        final DrugAllergy allergy = drugAllergyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DrugAllergy", id.toString()));
        allergy.setActive(false);
        drugAllergyRepository.save(allergy);
        log.info("Drug allergy soft-deleted: id={}", id);
    }
}
