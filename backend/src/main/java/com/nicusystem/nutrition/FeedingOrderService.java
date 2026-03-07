package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing feeding orders in the NICU.
 *
 * <p>Handles creation, retrieval, and discontinuation of enteral feeding orders
 * for neonatal patients.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FeedingOrderService {

    private final FeedingOrderRepository feedingOrderRepository;
    private final FeedingOrderMapper feedingOrderMapper;

    /**
     * Creates a new feeding order for a patient.
     *
     * @param request the feeding order creation request
     * @return the persisted feeding order DTO
     */
    @Transactional
    public FeedingOrderDto createFeedingOrder(final CreateFeedingOrderRequest request) {
        final FeedingOrder entity = feedingOrderMapper.toEntity(request);
        final FeedingOrder saved = feedingOrderRepository.save(entity);
        log.info("Feeding order created: type={}, route={}, patientId={}",
                request.feedingType(), request.feedingRoute(), request.patientId());
        return feedingOrderMapper.toDto(saved);
    }

    /**
     * Retrieves a feeding order by its unique identifier.
     *
     * @param id the feeding order UUID
     * @return the feeding order DTO
     * @throws ResourceNotFoundException if no feeding order exists with the given ID
     */
    @Transactional(readOnly = true)
    public FeedingOrderDto getFeedingOrderById(final UUID id) {
        return feedingOrderRepository.findById(id)
                .map(feedingOrderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("FeedingOrder", id.toString()));
    }

    /**
     * Returns a paginated list of feeding orders for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of feeding order DTOs
     */
    @Transactional(readOnly = true)
    public Page<FeedingOrderDto> getFeedingOrdersByPatient(
            final UUID patientId, final Pageable pageable) {
        return feedingOrderRepository.findByPatientId(patientId, pageable)
                .map(feedingOrderMapper::toDto);
    }

    /**
     * Returns a paginated list of feeding orders for a patient filtered by feeding type.
     *
     * @param patientId   the patient UUID
     * @param feedingType the feeding type filter
     * @param pageable    pagination information
     * @return page of feeding order DTOs matching the type
     */
    @Transactional(readOnly = true)
    public Page<FeedingOrderDto> getFeedingOrdersByPatientAndType(
            final UUID patientId,
            final FeedingType feedingType,
            final Pageable pageable) {
        return feedingOrderRepository
                .findByPatientIdAndFeedingType(patientId, feedingType, pageable)
                .map(feedingOrderMapper::toDto);
    }

    /**
     * Discontinues an active feeding order by setting its discontinuation timestamp.
     *
     * @param id the UUID of the feeding order to discontinue
     * @return the updated feeding order DTO
     * @throws ResourceNotFoundException if no feeding order exists with the given ID
     */
    @Transactional
    public FeedingOrderDto discontinueFeedingOrder(final UUID id) {
        final FeedingOrder order = feedingOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FeedingOrder", id.toString()));
        order.setDiscontinuedAt(Instant.now());
        final FeedingOrder saved = feedingOrderRepository.save(order);
        log.info("Feeding order discontinued: id={}", id);
        return feedingOrderMapper.toDto(saved);
    }
}
