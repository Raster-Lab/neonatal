package com.nicusystem.vitals;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between VitalSign entities and DTOs.
 */
@Component
public class VitalSignMapper {

    /**
     * Converts a VitalSign entity to a VitalSignDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public VitalSignDto toDto(final VitalSign entity) {
        return new VitalSignDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getVitalType(),
                entity.getValue(),
                entity.getUnit(),
                entity.getRecordedAt(),
                entity.getTemperatureSite(),
                entity.isManualEntry(),
                entity.getNotes()
        );
    }

    /**
     * Converts a CreateVitalSignRequest to a VitalSign entity.
     *
     * @param request the creation request
     * @return the VitalSign entity
     */
    public VitalSign toEntity(final CreateVitalSignRequest request) {
        final VitalSign vitalSign = new VitalSign();
        vitalSign.setPatientId(request.patientId());
        vitalSign.setVitalType(request.vitalType());
        vitalSign.setValue(request.value());
        vitalSign.setUnit(request.unit());
        vitalSign.setRecordedAt(request.recordedAt());
        vitalSign.setTemperatureSite(request.temperatureSite());
        vitalSign.setManualEntry(request.manualEntry());
        vitalSign.setNotes(request.notes());
        return vitalSign;
    }
}
