package com.nicusystem.vitals;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for vital sign comparison between current and historical baseline.
 */
@RestController
@RequestMapping("/api/v1/vitals/comparison")
@RequiredArgsConstructor
@Tag(name = "Vital Signs Comparison", description = "Compare current vital signs with historical baselines")
public class VitalSignComparisonController {

    private final VitalSignComparisonService vitalSignComparisonService;

    /**
     * Compares the latest vital sign against a historical baseline period.
     *
     * @param patientId     the patient UUID
     * @param type          the vital sign type
     * @param baselineStart start of the baseline period
     * @param baselineEnd   end of the baseline period
     * @return comparison DTO with deviation analysis
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Compare current vital sign against historical baseline")
    public ResponseEntity<VitalSignComparisonDto> compare(
            @PathVariable final UUID patientId,
            @PathVariable final VitalSignType type,
            @RequestParam final Instant baselineStart,
            @RequestParam final Instant baselineEnd) {
        return ResponseEntity.ok(
                vitalSignComparisonService.compare(patientId, type, baselineStart, baselineEnd));
    }
}
