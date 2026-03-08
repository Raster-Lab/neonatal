package com.nicusystem.vitals;

import java.time.Instant;
import java.util.List;
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
 * REST controller for vital sign trending dashboard.
 */
@RestController
@RequestMapping("/api/v1/vitals/trending")
@RequiredArgsConstructor
@Tag(name = "Vital Signs Trending", description = "Vital sign trending dashboard with configurable time ranges")
public class VitalSignTrendingController {

    private final VitalSignTrendingService vitalSignTrendingService;

    /**
     * Gets trending data for a specific vital sign type within a time range.
     *
     * @param patientId the patient UUID
     * @param type      the vital sign type
     * @param start     start of the time range
     * @param end       end of the time range
     * @return list of trending data points
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get trending data for a specific vital sign type")
    public ResponseEntity<List<VitalSignTrendingDto>> getTrending(
            @PathVariable final UUID patientId,
            @PathVariable final VitalSignType type,
            @RequestParam final Instant start,
            @RequestParam final Instant end) {
        return ResponseEntity.ok(
                vitalSignTrendingService.getTrending(patientId, type, start, end));
    }

    /**
     * Gets trending data for all vital sign types within a time range.
     *
     * @param patientId the patient UUID
     * @param start     start of the time range
     * @param end       end of the time range
     * @return list of trending data points grouped by type
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get trending data for all vital sign types")
    public ResponseEntity<List<VitalSignTrendingDto>> getTrendingAllTypes(
            @PathVariable final UUID patientId,
            @RequestParam final Instant start,
            @RequestParam final Instant end) {
        return ResponseEntity.ok(
                vitalSignTrendingService.getTrendingAllTypes(patientId, start, end));
    }
}
