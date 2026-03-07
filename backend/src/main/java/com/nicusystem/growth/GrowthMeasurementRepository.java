package com.nicusystem.growth;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for GrowthMeasurement entities.
 */
@Repository
public interface GrowthMeasurementRepository extends JpaRepository<GrowthMeasurement, UUID> {

    /**
     * Finds growth measurements for a patient ordered by measured time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of growth measurements
     */
    Page<GrowthMeasurement> findByPatientIdOrderByMeasuredAtDesc(
            UUID patientId, Pageable pageable);

    /**
     * Finds growth measurements for a patient filtered by type, ordered descending.
     *
     * @param patientId       the patient UUID
     * @param measurementType the measurement type
     * @param pageable        pagination information
     * @return page of growth measurements
     */
    Page<GrowthMeasurement> findByPatientIdAndMeasurementTypeOrderByMeasuredAtDesc(
            UUID patientId, MeasurementType measurementType, Pageable pageable);

    /**
     * Finds growth measurements for a patient filtered by type, ordered ascending (for charting).
     *
     * @param patientId       the patient UUID
     * @param measurementType the measurement type
     * @return list of growth measurements ordered by measured time ascending
     */
    List<GrowthMeasurement> findByPatientIdAndMeasurementTypeOrderByMeasuredAtAsc(
            UUID patientId, MeasurementType measurementType);
}
