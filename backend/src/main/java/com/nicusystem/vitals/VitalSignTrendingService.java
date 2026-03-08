package com.nicusystem.vitals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for computing vital sign trending data over configurable time ranges.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VitalSignTrendingService {

    private final VitalSignRepository vitalSignRepository;

    /**
     * Computes trending statistics for a specific vital sign type within a time range.
     *
     * @param patientId the patient UUID
     * @param type      the vital sign type
     * @param start     start of the time range
     * @param end       end of the time range
     * @return list containing a single trending data point (empty if no data)
     */
    @Transactional(readOnly = true)
    public List<VitalSignTrendingDto> getTrending(
            final UUID patientId,
            final VitalSignType type,
            final Instant start,
            final Instant end) {
        final List<VitalSign> vitals = vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(patientId, start, end)
                .stream()
                .filter(v -> v.getVitalType() == type)
                .toList();
        if (vitals.isEmpty()) {
            return List.of();
        }
        final VitalSignTrendingDto dto = buildTrendingDto(type, vitals, start, end);
        log.info("Vital sign trending computed: patientId={}, type={}", patientId, type);
        return List.of(dto);
    }

    /**
     * Computes trending statistics for all vital sign types within a time range.
     *
     * @param patientId the patient UUID
     * @param start     start of the time range
     * @param end       end of the time range
     * @return list of trending data points grouped by vital sign type
     */
    @Transactional(readOnly = true)
    public List<VitalSignTrendingDto> getTrendingAllTypes(
            final UUID patientId,
            final Instant start,
            final Instant end) {
        final List<VitalSign> allVitals = vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(patientId, start, end);
        return allVitals.stream()
                .collect(Collectors.groupingBy(VitalSign::getVitalType))
                .entrySet()
                .stream()
                .map(entry -> buildTrendingDto(entry.getKey(), entry.getValue(), start, end))
                .toList();
    }

    private VitalSignTrendingDto buildTrendingDto(
            final VitalSignType type,
            final List<VitalSign> vitals,
            final Instant start,
            final Instant end) {
        final double min = vitals.stream().mapToDouble(VitalSign::getValue).min().orElse(0.0);
        final double max = vitals.stream().mapToDouble(VitalSign::getValue).max().orElse(0.0);
        final double avg = vitals.stream().mapToDouble(VitalSign::getValue).average().orElse(0.0);
        return new VitalSignTrendingDto(type, min, max, avg, vitals.size(), start, end);
    }
}
