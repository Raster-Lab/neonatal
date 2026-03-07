package com.nicusystem.lab;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between BloodDrawVolume entities and DTOs.
 */
@Component
public class BloodDrawVolumeMapper {

    /**
     * Converts a BloodDrawVolume entity to a BloodDrawVolumeDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public BloodDrawVolumeDto toDto(final BloodDrawVolume entity) {
        return new BloodDrawVolumeDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getLabOrderId(),
                entity.getDrawnAt(),
                entity.getVolumeUl(),
                entity.getDrawnBy(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateBloodDrawVolumeRequest to a BloodDrawVolume entity.
     *
     * @param request the creation request
     * @return the BloodDrawVolume entity
     */
    public BloodDrawVolume toEntity(final CreateBloodDrawVolumeRequest request) {
        final BloodDrawVolume draw = new BloodDrawVolume();
        draw.setPatientId(request.patientId());
        draw.setLabOrderId(request.labOrderId());
        draw.setDrawnAt(request.drawnAt());
        draw.setVolumeUl(request.volumeUl());
        draw.setDrawnBy(request.drawnBy());
        draw.setNotes(request.notes());
        return draw;
    }
}
