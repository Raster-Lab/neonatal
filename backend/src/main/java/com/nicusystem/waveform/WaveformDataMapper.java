package com.nicusystem.waveform;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between WaveformData entities and DTOs.
 */
@Component
public class WaveformDataMapper {

    /**
     * Converts a WaveformData entity to a WaveformDataDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public WaveformDataDto toDto(final WaveformData entity) {
        return new WaveformDataDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getWaveformType(),
                entity.getDataPoints(),
                entity.getSamplingRateHz(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getDeviceIdentifier(),
                entity.getUnit(),
                entity.getNotes()
        );
    }

    /**
     * Converts a CreateWaveformDataRequest to a WaveformData entity.
     *
     * @param request the creation request
     * @return the WaveformData entity
     */
    public WaveformData toEntity(final CreateWaveformDataRequest request) {
        final WaveformData waveformData = new WaveformData();
        waveformData.setPatientId(request.patientId());
        waveformData.setWaveformType(request.waveformType());
        waveformData.setDataPoints(request.dataPoints());
        waveformData.setSamplingRateHz(request.samplingRateHz());
        waveformData.setStartTime(request.startTime());
        waveformData.setEndTime(request.endTime());
        waveformData.setDeviceIdentifier(request.deviceIdentifier());
        waveformData.setUnit(request.unit());
        waveformData.setNotes(request.notes());
        return waveformData;
    }
}
