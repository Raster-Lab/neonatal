package com.nicusystem.vitals;

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
 * Service for managing vital sign alarm thresholds.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VitalSignAlarmThresholdService {

    private final VitalSignAlarmThresholdRepository vitalSignAlarmThresholdRepository;
    private final VitalSignAlarmThresholdMapper vitalSignAlarmThresholdMapper;

    /**
     * Creates a new vital sign alarm threshold.
     *
     * @param request the threshold creation request
     * @return the created threshold DTO
     */
    @Transactional
    public VitalSignAlarmThresholdDto createThreshold(
            final CreateVitalSignAlarmThresholdRequest request) {
        final VitalSignAlarmThreshold threshold =
                vitalSignAlarmThresholdMapper.toEntity(request);
        final VitalSignAlarmThreshold saved =
                vitalSignAlarmThresholdRepository.save(threshold);
        log.info("Vital sign alarm threshold created: vitalType={}", request.vitalType());
        return vitalSignAlarmThresholdMapper.toDto(saved);
    }

    /**
     * Retrieves a vital sign alarm threshold by ID.
     *
     * @param id the threshold UUID
     * @return the threshold DTO
     */
    @Transactional(readOnly = true)
    public VitalSignAlarmThresholdDto getThresholdById(final UUID id) {
        return vitalSignAlarmThresholdRepository.findById(id)
                .map(vitalSignAlarmThresholdMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "VitalSignAlarmThreshold", id.toString()));
    }

    /**
     * Gets active alarm thresholds for a specific vital sign type.
     *
     * @param vitalType the vital sign type
     * @return list of threshold DTOs
     */
    @Transactional(readOnly = true)
    public List<VitalSignAlarmThresholdDto> getThresholdsByVitalType(
            final VitalSignType vitalType) {
        return vitalSignAlarmThresholdRepository
                .findByVitalTypeAndActiveTrue(vitalType)
                .stream()
                .map(vitalSignAlarmThresholdMapper::toDto)
                .toList();
    }

    /**
     * Gets all active alarm thresholds with pagination.
     *
     * @param pageable pagination information
     * @return page of threshold DTOs
     */
    @Transactional(readOnly = true)
    public Page<VitalSignAlarmThresholdDto> getAllActiveThresholds(final Pageable pageable) {
        return vitalSignAlarmThresholdRepository
                .findByActiveTrue(pageable)
                .map(vitalSignAlarmThresholdMapper::toDto);
    }

    /**
     * Updates all fields of a vital sign alarm threshold.
     *
     * @param id      the threshold UUID
     * @param request the update request
     * @return the updated threshold DTO
     */
    @Transactional
    public VitalSignAlarmThresholdDto updateThreshold(
            final UUID id, final CreateVitalSignAlarmThresholdRequest request) {
        final VitalSignAlarmThreshold threshold =
                vitalSignAlarmThresholdRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "VitalSignAlarmThreshold", id.toString()));
        threshold.setVitalType(request.vitalType());
        threshold.setMinimumGestationalAgeWeeks(request.minimumGestationalAgeWeeks());
        threshold.setMaximumGestationalAgeWeeks(request.maximumGestationalAgeWeeks());
        threshold.setMinimumWeightGrams(request.minimumWeightGrams());
        threshold.setMaximumWeightGrams(request.maximumWeightGrams());
        threshold.setLowAlarmValue(request.lowAlarmValue());
        threshold.setHighAlarmValue(request.highAlarmValue());
        threshold.setCriticalLowValue(request.criticalLowValue());
        threshold.setCriticalHighValue(request.criticalHighValue());
        threshold.setUnit(request.unit());
        threshold.setDescription(request.description());
        final VitalSignAlarmThreshold saved =
                vitalSignAlarmThresholdRepository.save(threshold);
        log.info("Vital sign alarm threshold updated: id={}", id);
        return vitalSignAlarmThresholdMapper.toDto(saved);
    }

    /**
     * Deactivates a vital sign alarm threshold (soft delete).
     *
     * @param id the threshold UUID
     * @return the deactivated threshold DTO
     */
    @Transactional
    public VitalSignAlarmThresholdDto deactivateThreshold(final UUID id) {
        final VitalSignAlarmThreshold threshold =
                vitalSignAlarmThresholdRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "VitalSignAlarmThreshold", id.toString()));
        threshold.setActive(false);
        final VitalSignAlarmThreshold saved =
                vitalSignAlarmThresholdRepository.save(threshold);
        log.info("Vital sign alarm threshold deactivated: id={}", id);
        return vitalSignAlarmThresholdMapper.toDto(saved);
    }
}
