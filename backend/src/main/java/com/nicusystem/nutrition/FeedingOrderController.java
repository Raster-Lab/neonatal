package com.nicusystem.nutrition;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing enteral feeding orders.
 *
 * <p>Provides endpoints to create, retrieve, and discontinue feeding orders
 * for neonatal patients in the NICU.</p>
 */
@RestController
@RequestMapping("/api/v1/feeding-orders")
@RequiredArgsConstructor
@Tag(name = "Feeding Orders", description = "Enteral feeding order management")
public class FeedingOrderController {

    private final FeedingOrderService feedingOrderService;

    /**
     * Creates a new feeding order.
     *
     * @param request the feeding order data
     * @return the created feeding order with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new feeding order")
    public ResponseEntity<FeedingOrderDto> createFeedingOrder(
            @Valid @RequestBody final CreateFeedingOrderRequest request) {
        final FeedingOrderDto created = feedingOrderService.createFeedingOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a feeding order by its ID.
     *
     * @param id the feeding order UUID
     * @return the feeding order DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a feeding order by ID")
    public ResponseEntity<FeedingOrderDto> getFeedingOrder(@PathVariable final UUID id) {
        return ResponseEntity.ok(feedingOrderService.getFeedingOrderById(id));
    }

    /**
     * Returns paginated feeding orders for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of feeding order DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get feeding orders for a patient")
    public ResponseEntity<Page<FeedingOrderDto>> getFeedingOrdersByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                feedingOrderService.getFeedingOrdersByPatient(patientId, pageable));
    }

    /**
     * Returns paginated feeding orders for a patient filtered by feeding type.
     *
     * @param patientId the patient UUID
     * @param type      the feeding type to filter by
     * @param pageable  pagination information
     * @return page of feeding order DTOs
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get feeding orders for a patient by type")
    public ResponseEntity<Page<FeedingOrderDto>> getFeedingOrdersByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final FeedingType type,
            final Pageable pageable) {
        return ResponseEntity.ok(
                feedingOrderService.getFeedingOrdersByPatientAndType(patientId, type, pageable));
    }

    /**
     * Discontinues an active feeding order.
     *
     * @param id the UUID of the feeding order to discontinue
     * @return the updated feeding order DTO
     */
    @PatchMapping("/{id}/discontinue")
    @Operation(summary = "Discontinue a feeding order")
    public ResponseEntity<FeedingOrderDto> discontinueFeedingOrder(@PathVariable final UUID id) {
        return ResponseEntity.ok(feedingOrderService.discontinueFeedingOrder(id));
    }
}
