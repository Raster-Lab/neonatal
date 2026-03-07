package com.nicusystem.vitals;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for VitalSignAlarmThreshold entities.
 */
@Repository
public interface VitalSignAlarmThresholdRepository
        extends JpaRepository<VitalSignAlarmThreshold, UUID> {

    /**
     * Finds active alarm thresholds for a specific vital sign type.
     *
     * @param vitalType the vital sign type
     * @return list of active alarm thresholds
     */
    List<VitalSignAlarmThreshold> findByVitalTypeAndActiveTrue(VitalSignType vitalType);

    /**
     * Finds all active alarm thresholds with pagination.
     *
     * @param pageable pagination information
     * @return page of active alarm thresholds
     */
    Page<VitalSignAlarmThreshold> findByActiveTrue(Pageable pageable);
}
