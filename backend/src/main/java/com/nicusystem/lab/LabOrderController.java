package com.nicusystem.lab;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for laboratory order management.
 */
@RestController
@RequestMapping("/api/v1/lab-orders")
@RequiredArgsConstructor
@Tag(name = "Lab Orders", description = "Laboratory order management")
public class LabOrderController {

    private final LabOrderService labOrderService;

    /**
     * Creates a new laboratory order.
     *
     * @param request the lab order creation data
     * @return the created lab order with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new laboratory order")
    public ResponseEntity<LabOrderDto> createLabOrder(
            @Valid @RequestBody final CreateLabOrderRequest request) {
        final LabOrderDto created = labOrderService.createLabOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a laboratory order by ID.
     *
     * @param id the lab order UUID
     * @return the lab order DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get laboratory order by ID")
    public ResponseEntity<LabOrderDto> getLabOrderById(@PathVariable final UUID id) {
        return ResponseEntity.ok(labOrderService.getLabOrderById(id));
    }

    /**
     * Gets all laboratory orders for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of lab orders
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get laboratory orders for a patient")
    public ResponseEntity<Page<LabOrderDto>> getLabOrdersByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(labOrderService.getLabOrdersByPatient(patientId, pageable));
    }

    /**
     * Gets laboratory orders for a patient filtered by status.
     *
     * @param patientId the patient UUID
     * @param status    the order status filter
     * @param pageable  pagination information
     * @return page of lab orders matching the status
     */
    @GetMapping("/patient/{patientId}/status/{status}")
    @Operation(summary = "Get laboratory orders for a patient by status")
    public ResponseEntity<Page<LabOrderDto>> getLabOrdersByPatientAndStatus(
            @PathVariable final UUID patientId,
            @PathVariable final LabOrderStatus status,
            final Pageable pageable) {
        return ResponseEntity.ok(
                labOrderService.getLabOrdersByPatientAndStatus(patientId, status, pageable));
    }

    /**
     * Updates the status of a laboratory order.
     *
     * @param id     the lab order UUID
     * @param status the new status
     * @return the updated lab order DTO
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update laboratory order status")
    public ResponseEntity<LabOrderDto> updateLabOrderStatus(
            @PathVariable final UUID id,
            @RequestParam final LabOrderStatus status) {
        return ResponseEntity.ok(labOrderService.updateLabOrderStatus(id, status));
    }
}
