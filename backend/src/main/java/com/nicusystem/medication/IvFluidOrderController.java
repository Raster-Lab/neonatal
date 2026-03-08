package com.nicusystem.medication;

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
 * REST controller for IV fluid order management.
 */
@RestController
@RequestMapping("/api/v1/iv-fluids")
@RequiredArgsConstructor
@Tag(name = "IV Fluids", description = "IV fluid ordering and management")
public class IvFluidOrderController {

    private final IvFluidOrderService ivFluidOrderService;

    /**
     * Creates a new IV fluid order.
     *
     * @param request the creation request
     * @return the created order with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new IV fluid order")
    public ResponseEntity<IvFluidOrderDto> createOrder(
            @Valid @RequestBody final CreateIvFluidOrderRequest request) {
        final IvFluidOrderDto created =
                ivFluidOrderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves an IV fluid order by ID.
     *
     * @param id the order UUID
     * @return the order DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get IV fluid order by ID")
    public ResponseEntity<IvFluidOrderDto> getOrder(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(ivFluidOrderService.getOrderById(id));
    }

    /**
     * Gets IV fluid orders for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of orders
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get IV fluid orders for a patient")
    public ResponseEntity<Page<IvFluidOrderDto>> getOrdersByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                ivFluidOrderService.getOrdersByPatient(
                        patientId, pageable));
    }

    /**
     * Gets IV fluid orders by patient and status.
     *
     * @param patientId the patient UUID
     * @param status    the order status
     * @param pageable  pagination information
     * @return page of orders
     */
    @GetMapping("/patient/{patientId}/status/{status}")
    @Operation(summary = "Get IV fluid orders by patient and status")
    public ResponseEntity<Page<IvFluidOrderDto>> getOrdersByPatientAndStatus(
            @PathVariable final UUID patientId,
            @PathVariable final IvFluidStatus status,
            final Pageable pageable) {
        return ResponseEntity.ok(
                ivFluidOrderService.getOrdersByPatientAndStatus(
                        patientId, status, pageable));
    }

    /**
     * Updates the status of an IV fluid order.
     *
     * @param id     the order UUID
     * @param status the new status
     * @return the updated order DTO
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update IV fluid order status")
    public ResponseEntity<IvFluidOrderDto> updateStatus(
            @PathVariable final UUID id,
            @RequestParam final IvFluidStatus status) {
        return ResponseEntity.ok(
                ivFluidOrderService.updateOrderStatus(id, status));
    }
}
