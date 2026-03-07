package com.nicusystem.nutrition;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link FeedingOrder} entities.
 */
public interface FeedingOrderRepository extends JpaRepository<FeedingOrder, UUID> {

    /**
     * Returns a page of feeding orders for the given patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of feeding orders
     */
    Page<FeedingOrder> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Returns a page of feeding orders for the given patient filtered by feeding type.
     *
     * @param patientId   the patient UUID
     * @param feedingType the feeding type to filter by
     * @param pageable    pagination information
     * @return page of feeding orders matching the type
     */
    Page<FeedingOrder> findByPatientIdAndFeedingType(
            UUID patientId, FeedingType feedingType, Pageable pageable);

    /**
     * Returns all active (non-discontinued) feeding orders for a patient.
     *
     * <p>Used by {@link NutritionService} for caloric intake calculations.</p>
     *
     * @param patientId the patient UUID
     * @return list of active feeding orders
     */
    List<FeedingOrder> findByPatientIdAndDiscontinuedAtIsNull(UUID patientId);
}
