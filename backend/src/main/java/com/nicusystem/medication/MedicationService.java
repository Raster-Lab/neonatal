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
 * Service for managing medication orders.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;
    private final DrugInteractionRepository drugInteractionRepository;

    /**
     * Creates a new medication order with ORDERED status.
     *
     * @param request the medication creation request
     * @return the created medication DTO
     */
    @Transactional
    public MedicationDto createMedication(final CreateMedicationRequest request) {
        final Medication medication = medicationMapper.toEntity(request);
        if (medication.getMaxDoseMgKgPerDay() != null
                && request.weightAtPrescription() != null
                && request.weightAtPrescription() > 0
                && request.dosageUnit() != null
                && request.dosageUnit().toLowerCase().contains("mg")
                && request.dosage() > medication.getMaxDoseMgKgPerDay()) {
            throw new MaxDoseExceededException(
                    "Dose %.2f %s/kg exceeds maximum allowed dose of %.2f mg/kg for medication %s"
                            .formatted(request.dosage(), request.dosageUnit(),
                                    medication.getMaxDoseMgKgPerDay(), medication.getName()));
        }
        final List<Medication> existingMedications =
                medicationRepository.findAllByPatientId(request.patientId());
        for (final Medication existing : existingMedications) {
            final List<DrugInteraction> interactions =
                    drugInteractionRepository.findInteractionBetween(
                            request.name(), existing.getName());
            for (final DrugInteraction interaction : interactions) {
                if (interaction.getInteractionSeverity() == DrugInteractionSeverity.CONTRAINDICATED) {
                    throw new DrugInteractionException(
                            "Contraindicated drug interaction between %s and %s: %s"
                                    .formatted(interaction.getDrug1Name(),
                                            interaction.getDrug2Name(),
                                            interaction.getDescription()));
                } else if (interaction.getInteractionSeverity() == DrugInteractionSeverity.MAJOR) {
                    log.warn("Major drug interaction detected: {} and {}: {}",
                            interaction.getDrug1Name(), interaction.getDrug2Name(),
                            interaction.getDescription());
                }
            }
        }
        final Medication saved = medicationRepository.save(medication);
        log.info("Medication created: name={}, patientId={}",
                request.name(), request.patientId());
        return medicationMapper.toDto(saved);
    }

    /**
     * Retrieves a medication by ID.
     *
     * @param id the medication UUID
     * @return the medication DTO
     */
    @Transactional(readOnly = true)
    public MedicationDto getMedicationById(final UUID id) {
        return medicationRepository.findById(id)
                .map(medicationMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medication", id.toString()));
    }

    /**
     * Gets medications for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of medication DTOs
     */
    @Transactional(readOnly = true)
    public Page<MedicationDto> getMedicationsByPatient(
            final UUID patientId, final Pageable pageable) {
        return medicationRepository.findByPatientId(patientId, pageable)
                .map(medicationMapper::toDto);
    }

    /**
     * Gets medications for a patient filtered by status.
     *
     * @param patientId the patient UUID
     * @param status    the medication status
     * @param pageable  pagination information
     * @return page of medication DTOs
     */
    @Transactional(readOnly = true)
    public Page<MedicationDto> getMedicationsByPatientAndStatus(
            final UUID patientId,
            final MedicationStatus status,
            final Pageable pageable) {
        return medicationRepository
                .findByPatientIdAndStatus(patientId, status, pageable)
                .map(medicationMapper::toDto);
    }

    /**
     * Updates the status of a medication order.
     *
     * @param id     the medication UUID
     * @param status the new status
     * @return the updated medication DTO
     */
    @Transactional
    public MedicationDto updateMedicationStatus(
            final UUID id, final MedicationStatus status) {
        final Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medication", id.toString()));
        medication.setStatus(status);
        final Medication saved = medicationRepository.save(medication);
        log.info("Medication status updated: id={}, status={}", id, status);
        return medicationMapper.toDto(saved);
    }
}
