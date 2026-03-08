package com.nicusystem.vitals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for comparing current vital signs against historical baselines.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VitalSignComparisonService {

    private final VitalSignRepository vitalSignRepository;

    /**
     * Compares the latest vital sign value against a historical baseline.
     *
     * @param patientId     the patient UUID
     * @param type          the vital sign type
     * @param baselineStart start of the baseline period
     * @param baselineEnd   end of the baseline period
     * @return comparison DTO with current value, baseline stats, and deviation
     */
    @Transactional(readOnly = true)
    public VitalSignComparisonDto compare(
            final UUID patientId,
            final VitalSignType type,
            final Instant baselineStart,
            final Instant baselineEnd) {
        final List<VitalSign> baselineVitals = vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, baselineStart, baselineEnd)
                .stream()
                .filter(v -> v.getVitalType() == type)
                .toList();
        if (baselineVitals.isEmpty()) {
            throw new ResourceNotFoundException("BaselineData", patientId.toString());
        }
        final double baselineAvg = baselineVitals.stream()
                .mapToDouble(VitalSign::getValue).average().orElse(0.0);
        final double baselineMin = baselineVitals.stream()
                .mapToDouble(VitalSign::getValue).min().orElse(0.0);
        final double baselineMax = baselineVitals.stream()
                .mapToDouble(VitalSign::getValue).max().orElse(0.0);

        final List<VitalSign> latestList = vitalSignRepository
                .findByPatientIdAndVitalTypeOrderByRecordedAtDesc(
                        patientId, type, PageRequest.of(0, 1))
                .getContent();
        if (latestList.isEmpty()) {
            throw new ResourceNotFoundException("CurrentVitalSign", patientId.toString());
        }
        final VitalSign latest = latestList.get(0);
        final double deviation = baselineAvg == 0.0
                ? 0.0
                : ((latest.getValue() - baselineAvg) / baselineAvg) * 100.0;
        log.info("Vital sign comparison computed: patientId={}, type={}", patientId, type);
        return new VitalSignComparisonDto(
                type,
                latest.getValue(),
                latest.getRecordedAt(),
                baselineAvg,
                baselineMin,
                baselineMax,
                baselineVitals.size(),
                baselineStart,
                baselineEnd,
                deviation
        );
    }
}
