package com.nicusystem.nutrition;

import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between {@link FeedingOrder} entities and DTOs.
 */
@Component
public class FeedingOrderMapper {

    /**
     * Converts a {@link FeedingOrder} entity to a {@link FeedingOrderDto}.
     *
     * @param entity the feeding order entity
     * @return the corresponding DTO
     */
    public FeedingOrderDto toDto(final FeedingOrder entity) {
        return new FeedingOrderDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getFeedingType(),
                entity.getFeedingRoute(),
                entity.getVolumeMl(),
                entity.getFrequencyHours(),
                entity.getStartedAt(),
                entity.getDiscontinuedAt(),
                entity.getOrderedBy(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a {@link CreateFeedingOrderRequest} to a new {@link FeedingOrder} entity.
     *
     * @param request the creation request
     * @return a new, unpersisted feeding order entity
     */
    public FeedingOrder toEntity(final CreateFeedingOrderRequest request) {
        final FeedingOrder entity = new FeedingOrder();
        entity.setPatientId(request.patientId());
        entity.setFeedingType(request.feedingType());
        entity.setFeedingRoute(request.feedingRoute());
        entity.setVolumeMl(request.volumeMl());
        entity.setFrequencyHours(request.frequencyHours());
        entity.setStartedAt(request.startedAt());
        entity.setOrderedBy(request.orderedBy());
        entity.setNotes(request.notes());
        return entity;
    }
}
