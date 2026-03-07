package com.nicusystem.lab;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing laboratory orders in the NICU.
 *
 * <p>Handles creation, retrieval, and status updates for lab panel orders.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LabOrderService {

    private final LabOrderRepository labOrderRepository;
    private final LabOrderMapper labOrderMapper;

    /**
     * Creates a new laboratory order for a patient.
     *
     * @param request the lab order creation request
     * @return the persisted lab order DTO
     */
    @Transactional
    public LabOrderDto createLabOrder(final CreateLabOrderRequest request) {
        final LabOrder entity = labOrderMapper.toEntity(request);
        final LabOrder saved = labOrderRepository.save(entity);
        log.info("Lab order created: panelType={}, patientId={}", request.panelType(), request.patientId());
        return labOrderMapper.toDto(saved);
    }

    /**
     * Retrieves a laboratory order by its unique identifier.
     *
     * @param id the lab order UUID
     * @return the lab order DTO
     * @throws ResourceNotFoundException if no lab order exists with the given ID
     */
    @Transactional(readOnly = true)
    public LabOrderDto getLabOrderById(final UUID id) {
        return labOrderRepository.findById(id)
                .map(labOrderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("LabOrder", id.toString()));
    }

    /**
     * Returns a paginated list of lab orders for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of lab order DTOs
     */
    @Transactional(readOnly = true)
    public Page<LabOrderDto> getLabOrdersByPatient(final UUID patientId, final Pageable pageable) {
        return labOrderRepository.findByPatientId(patientId, pageable)
                .map(labOrderMapper::toDto);
    }

    /**
     * Returns a paginated list of lab orders for a patient filtered by status.
     *
     * @param patientId the patient UUID
     * @param status    the order status filter
     * @param pageable  pagination information
     * @return page of lab order DTOs matching the status
     */
    @Transactional(readOnly = true)
    public Page<LabOrderDto> getLabOrdersByPatientAndStatus(
            final UUID patientId, final LabOrderStatus status, final Pageable pageable) {
        return labOrderRepository.findByPatientIdAndStatus(patientId, status, pageable)
                .map(labOrderMapper::toDto);
    }

    /**
     * Updates the status of an existing laboratory order.
     *
     * @param id     the lab order UUID
     * @param status the new status to apply
     * @return the updated lab order DTO
     * @throws ResourceNotFoundException if no lab order exists with the given ID
     */
    @Transactional
    public LabOrderDto updateLabOrderStatus(final UUID id, final LabOrderStatus status) {
        final LabOrder order = labOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabOrder", id.toString()));
        order.setStatus(status);
        final LabOrder saved = labOrderRepository.save(order);
        log.info("Lab order status updated: id={}, status={}", id, status);
        return labOrderMapper.toDto(saved);
    }
}
