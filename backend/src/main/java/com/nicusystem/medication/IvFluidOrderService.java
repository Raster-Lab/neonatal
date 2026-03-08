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
 * Service for managing IV fluid orders.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IvFluidOrderService {

    private final IvFluidOrderRepository ivFluidOrderRepository;
    private final IvFluidOrderMapper ivFluidOrderMapper;

    /**
     * Creates a new IV fluid order.
     *
     * @param request the creation request
     * @return the created IV fluid order DTO
     */
    @Transactional
    public IvFluidOrderDto createOrder(final CreateIvFluidOrderRequest request) {
        final IvFluidOrder entity = ivFluidOrderMapper.toEntity(request);
        entity.setStatus(IvFluidStatus.ORDERED);
        final IvFluidOrder saved = ivFluidOrderRepository.save(entity);
        log.info("IV fluid order created: patientId={}", request.patientId());
        return ivFluidOrderMapper.toDto(saved);
    }

    /**
     * Retrieves an IV fluid order by ID.
     *
     * @param id the order UUID
     * @return the IV fluid order DTO
     */
    @Transactional(readOnly = true)
    public IvFluidOrderDto getOrderById(final UUID id) {
        return ivFluidOrderRepository.findById(id)
                .map(ivFluidOrderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "IvFluidOrder", id.toString()));
    }

    /**
     * Gets IV fluid orders for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of IV fluid order DTOs
     */
    @Transactional(readOnly = true)
    public Page<IvFluidOrderDto> getOrdersByPatient(
            final UUID patientId, final Pageable pageable) {
        return ivFluidOrderRepository.findByPatientId(patientId, pageable)
                .map(ivFluidOrderMapper::toDto);
    }

    /**
     * Gets IV fluid orders for a patient by status.
     *
     * @param patientId the patient UUID
     * @param status    the order status
     * @param pageable  pagination information
     * @return page of IV fluid order DTOs
     */
    @Transactional(readOnly = true)
    public Page<IvFluidOrderDto> getOrdersByPatientAndStatus(
            final UUID patientId,
            final IvFluidStatus status,
            final Pageable pageable) {
        return ivFluidOrderRepository
                .findByPatientIdAndStatus(patientId, status, pageable)
                .map(ivFluidOrderMapper::toDto);
    }

    /**
     * Updates the status of an IV fluid order.
     *
     * @param id     the order UUID
     * @param status the new status
     * @return the updated IV fluid order DTO
     */
    @Transactional
    public IvFluidOrderDto updateOrderStatus(
            final UUID id, final IvFluidStatus status) {
        final IvFluidOrder order = ivFluidOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "IvFluidOrder", id.toString()));
        order.setStatus(status);
        final IvFluidOrder saved = ivFluidOrderRepository.save(order);
        log.info("IV fluid order status updated: id={}, status={}", id, status);
        return ivFluidOrderMapper.toDto(saved);
    }
}
