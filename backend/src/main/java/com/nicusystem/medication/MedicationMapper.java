package com.nicusystem.medication;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Medication entities and DTOs.
 */
@Component
public class MedicationMapper {

    /**
     * Converts a Medication entity to a MedicationDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public MedicationDto toDto(final Medication entity) {
        return new MedicationDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getName(),
                entity.getDosage(),
                entity.getDosageUnit(),
                entity.getRoute(),
                entity.getFrequency(),
                entity.getStatus(),
                entity.getPrescribedAt(),
                entity.getPrescribedBy(),
                entity.getWeightAtPrescription(),
                entity.getNotes(),
                entity.isHighAlert()
        );
    }

    /**
     * Converts a CreateMedicationRequest to a Medication entity.
     *
     * @param request the creation request
     * @return the Medication entity
     */
    public Medication toEntity(final CreateMedicationRequest request) {
        final Medication medication = new Medication();
        medication.setPatientId(request.patientId());
        medication.setName(request.name());
        medication.setDosage(request.dosage());
        medication.setDosageUnit(request.dosageUnit());
        medication.setRoute(request.route());
        medication.setFrequency(request.frequency());
        medication.setStatus(MedicationStatus.ORDERED);
        medication.setPrescribedAt(request.prescribedAt());
        medication.setPrescribedBy(request.prescribedBy());
        medication.setWeightAtPrescription(request.weightAtPrescription());
        medication.setNotes(request.notes());
        medication.setHighAlert(request.highAlert());
        return medication;
    }
}
