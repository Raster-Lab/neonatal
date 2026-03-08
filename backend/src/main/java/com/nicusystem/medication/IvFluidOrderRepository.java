package com.nicusystem.medication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for IV fluid order persistence operations.
 */
@Repository
public interface IvFluidOrderRepository extends JpaRepository<IvFluidOrder, UUID> {

    /**
     * Finds IV fluid orders by patient ID.
     *
     * @param patientId the patient identifier
     * @param pageable pagination information
     * @return a page of IV fluid orders
     */
    Page<IvFluidOrder> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Finds IV fluid orders by patient ID and status.
     *
     * @param patientId the patient identifier
     * @param status the order status
     * @param pageable pagination information
     * @return a page of IV fluid orders
     */
    Page<IvFluidOrder> findByPatientIdAndStatus(UUID patientId, IvFluidStatus status, Pageable pageable);
}
