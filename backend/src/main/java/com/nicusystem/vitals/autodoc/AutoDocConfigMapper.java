package com.nicusystem.vitals.autodoc;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between AutoDocConfig entities and DTOs.
 */
@Component
public class AutoDocConfigMapper {

    /**
     * Converts an AutoDocConfig entity to an AutoDocConfigDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public AutoDocConfigDto toDto(final AutoDocConfig entity) {
        return new AutoDocConfigDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getVitalType(),
                entity.getInterval(),
                entity.isEnabled(),
                entity.getNotes()
        );
    }

    /**
     * Converts a CreateAutoDocConfigRequest to an AutoDocConfig entity.
     *
     * @param request the creation request
     * @return the AutoDocConfig entity
     */
    public AutoDocConfig toEntity(final CreateAutoDocConfigRequest request) {
        final AutoDocConfig config = new AutoDocConfig();
        config.setPatientId(request.patientId());
        config.setVitalType(request.vitalType());
        config.setInterval(request.interval());
        config.setNotes(request.notes());
        return config;
    }
}
