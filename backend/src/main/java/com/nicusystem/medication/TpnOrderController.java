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
 * REST controller for TPN order management.
 */
@RestController
@RequestMapping("/api/v1/tpn-orders")
@RequiredArgsConstructor
@Tag(name = "TPN Orders",
        description = "Total parenteral nutrition ordering")
public class TpnOrderController {

    private final TpnOrderService tpnOrderService;

    /**
     * Creates a new TPN order.
     *
     * @param request the creation request
     * @return the created order with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new TPN order")
    public ResponseEntity<TpnOrderDto> createOrder(
            @Valid @RequestBody final CreateTpnOrderRequest request) {
        final TpnOrderDto created = tpnOrderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a TPN order by ID.
     *
     * @param id the order UUID
     * @return the order DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get TPN order by ID")
    public ResponseEntity<TpnOrderDto> getOrder(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(tpnOrderService.getOrderById(id));
    }

    /**
     * Gets TPN orders for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of orders
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get TPN orders for a patient")
    public ResponseEntity<Page<TpnOrderDto>> getOrdersByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                tpnOrderService.getOrdersByPatient(patientId, pageable));
    }

    /**
     * Gets TPN orders by patient and status.
     *
     * @param patientId the patient UUID
     * @param status    the order status
     * @param pageable  pagination information
     * @return page of orders
     */
    @GetMapping("/patient/{patientId}/status/{status}")
    @Operation(summary = "Get TPN orders by patient and status")
    public ResponseEntity<Page<TpnOrderDto>> getOrdersByPatientAndStatus(
            @PathVariable final UUID patientId,
            @PathVariable final TpnStatus status,
            final Pageable pageable) {
        return ResponseEntity.ok(
                tpnOrderService.getOrdersByPatientAndStatus(
                        patientId, status, pageable));
    }

    /**
     * Updates the status of a TPN order.
     *
     * @param id     the order UUID
     * @param status the new status
     * @return the updated order DTO
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update TPN order status")
    public ResponseEntity<TpnOrderDto> updateStatus(
            @PathVariable final UUID id,
            @RequestParam final TpnStatus status) {
        return ResponseEntity.ok(
                tpnOrderService.updateOrderStatus(id, status));
    }
}
