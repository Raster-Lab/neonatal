package com.nicusystem.growth;

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
 * Service for managing growth measurements.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GrowthMeasurementService {

    private final GrowthMeasurementRepository growthMeasurementRepository;
    private final GrowthMeasurementMapper growthMeasurementMapper;

    /**
     * Records a new growth measurement.
     *
     * @param request the growth measurement creation request
     * @return the recorded growth measurement DTO
     */
    @Transactional
    public GrowthMeasurementDto recordMeasurement(final CreateGrowthMeasurementRequest request) {
        final GrowthMeasurement measurement = growthMeasurementMapper.toEntity(request);
        final GrowthMeasurement saved = growthMeasurementRepository.save(measurement);
        log.info("Growth measurement recorded: type={}, patientId={}",
                request.measurementType(), request.patientId());
        return growthMeasurementMapper.toDto(saved);
    }

    /**
     * Retrieves a growth measurement by ID.
     *
     * @param id the growth measurement UUID
     * @return the growth measurement DTO
     */
    @Transactional(readOnly = true)
    public GrowthMeasurementDto getMeasurementById(final UUID id) {
        return growthMeasurementRepository.findById(id)
                .map(growthMeasurementMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "GrowthMeasurement", id.toString()));
    }

    /**
     * Gets growth measurements for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of growth measurement DTOs
     */
    @Transactional(readOnly = true)
    public Page<GrowthMeasurementDto> getMeasurementsByPatient(
            final UUID patientId, final Pageable pageable) {
        return growthMeasurementRepository
                .findByPatientIdOrderByMeasuredAtDesc(patientId, pageable)
                .map(growthMeasurementMapper::toDto);
    }

    /**
     * Gets growth measurements for a patient filtered by type.
     *
     * @param patientId       the patient UUID
     * @param measurementType the measurement type
     * @param pageable        pagination information
     * @return page of growth measurement DTOs
     */
    @Transactional(readOnly = true)
    public Page<GrowthMeasurementDto> getMeasurementsByPatientAndType(
            final UUID patientId,
            final MeasurementType measurementType,
            final Pageable pageable) {
        return growthMeasurementRepository
                .findByPatientIdAndMeasurementTypeOrderByMeasuredAtDesc(
                        patientId, measurementType, pageable)
                .map(growthMeasurementMapper::toDto);
    }

    /**
     * Gets growth trend data for a patient and measurement type, ordered ascending for charting.
     *
     * @param patientId       the patient UUID
     * @param measurementType the measurement type
     * @return list of growth measurement DTOs ordered by measured time ascending
     */
    @Transactional(readOnly = true)
    public List<GrowthMeasurementDto> getGrowthTrend(
            final UUID patientId, final MeasurementType measurementType) {
        return growthMeasurementRepository
                .findByPatientIdAndMeasurementTypeOrderByMeasuredAtAsc(
                        patientId, measurementType)
                .stream()
                .map(growthMeasurementMapper::toDto)
                .toList();
    }
}
