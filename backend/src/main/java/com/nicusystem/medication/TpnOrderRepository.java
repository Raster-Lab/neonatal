package com.nicusystem.medication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for TPN order persistence operations.
 */
@Repository
public interface TpnOrderRepository extends JpaRepository<TpnOrder, UUID> {

    /**
     * Finds TPN orders by patient ID.
     *
     * @param patientId the patient identifier
     * @param pageable pagination information
     * @return a page of TPN orders
     */
    Page<TpnOrder> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Finds TPN orders by patient ID and status.
     *
     * @param patientId the patient identifier
     * @param status the order status
     * @param pageable pagination information
     * @return a page of TPN orders
     */
    Page<TpnOrder> findByPatientIdAndStatus(UUID patientId, TpnStatus status, Pageable pageable);
}
