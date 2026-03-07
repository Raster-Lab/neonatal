package com.nicusystem.lab;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for LabOrder entities.
 */
public interface LabOrderRepository extends JpaRepository<LabOrder, UUID> {

    /**
     * Finds all lab orders for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of lab orders
     */
    Page<LabOrder> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Finds lab orders for a patient filtered by status.
     *
     * @param patientId the patient UUID
     * @param status    the order status filter
     * @param pageable  pagination information
     * @return page of lab orders matching the status
     */
    Page<LabOrder> findByPatientIdAndStatus(UUID patientId, LabOrderStatus status, Pageable pageable);

    /**
     * Finds lab orders for a patient filtered by panel type.
     *
     * @param patientId the patient UUID
     * @param panelType the panel type filter
     * @param pageable  pagination information
     * @return page of lab orders matching the panel type
     */
    Page<LabOrder> findByPatientIdAndPanelType(UUID patientId, LabPanelType panelType, Pageable pageable);
}
