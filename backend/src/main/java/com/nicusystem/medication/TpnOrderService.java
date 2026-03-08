package com.nicusystem.medication;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing TPN orders with GIR calculation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TpnOrderService {

    private final TpnOrderRepository tpnOrderRepository;
    private final TpnOrderMapper tpnOrderMapper;

    /**
     * Creates a new TPN order with automatic GIR calculation.
     *
     * @param request the creation request
     * @return the created TPN order DTO
     */
    @Transactional
    public TpnOrderDto createOrder(final CreateTpnOrderRequest request) {
        final TpnOrder entity = tpnOrderMapper.toEntity(request);
        entity.setStatus(TpnStatus.ORDERED);
        if (entity.getGir() == null && entity.getWeightGrams() != null
                && entity.getWeightGrams() > 0
                && entity.getDextrosePercent() != null
                && entity.getInfusionRateMlPerHr() != null) {
            final double gir = calculateGir(
                    entity.getDextrosePercent(),
                    entity.getInfusionRateMlPerHr(),
                    entity.getWeightGrams());
            entity.setGir(gir);
        }
        final TpnOrder saved = tpnOrderRepository.save(entity);
        log.info("TPN order created: patientId={}, day={}",
                request.patientId(), request.dayNumber());
        return tpnOrderMapper.toDto(saved);
    }

    /**
     * Calculates the Glucose Infusion Rate (GIR).
     * GIR = (dextrosePercent * rateMlPerHr * 1000) / (weightGrams * 60 * 100)
     *
     * @param dextrosePercent  dextrose concentration percentage
     * @param rateMlPerHr      infusion rate in mL/hr
     * @param weightGrams      patient weight in grams
     * @return the GIR in mg/kg/min
     */
    public double calculateGir(final double dextrosePercent,
                                final double rateMlPerHr,
                                final int weightGrams) {
        return (dextrosePercent * rateMlPerHr * 1000.0)
                / (weightGrams * 60.0 * 100.0);
    }

    /**
     * Retrieves a TPN order by ID.
     *
     * @param id the order UUID
     * @return the TPN order DTO
     */
    @Transactional(readOnly = true)
    public TpnOrderDto getOrderById(final UUID id) {
        return tpnOrderRepository.findById(id)
                .map(tpnOrderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "TpnOrder", id.toString()));
    }

    /**
     * Gets TPN orders for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of TPN order DTOs
     */
    @Transactional(readOnly = true)
    public Page<TpnOrderDto> getOrdersByPatient(
            final UUID patientId, final Pageable pageable) {
        return tpnOrderRepository.findByPatientId(patientId, pageable)
                .map(tpnOrderMapper::toDto);
    }

    /**
     * Gets TPN orders for a patient by status.
     *
     * @param patientId the patient UUID
     * @param status    the order status
     * @param pageable  pagination information
     * @return page of TPN order DTOs
     */
    @Transactional(readOnly = true)
    public Page<TpnOrderDto> getOrdersByPatientAndStatus(
            final UUID patientId,
            final TpnStatus status,
            final Pageable pageable) {
        return tpnOrderRepository
                .findByPatientIdAndStatus(patientId, status, pageable)
                .map(tpnOrderMapper::toDto);
    }

    /**
     * Updates the status of a TPN order.
     *
     * @param id     the order UUID
     * @param status the new status
     * @return the updated TPN order DTO
     */
    @Transactional
    public TpnOrderDto updateOrderStatus(
            final UUID id, final TpnStatus status) {
        final TpnOrder order = tpnOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "TpnOrder", id.toString()));
        order.setStatus(status);
        final TpnOrder saved = tpnOrderRepository.save(order);
        log.info("TPN order status updated: id={}, status={}", id, status);
        return tpnOrderMapper.toDto(saved);
    }
}
