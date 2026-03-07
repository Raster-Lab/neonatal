package com.nicusystem.fluid;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing fluid balance and intake/output tracking.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FluidBalanceService {

    private final FluidEntryRepository fluidEntryRepository;
    private final FluidEntryMapper fluidEntryMapper;

    /**
     * Records a new fluid entry.
     *
     * @param request the fluid entry creation request
     * @return the recorded fluid entry DTO
     */
    @Transactional
    public FluidEntryDto recordFluidEntry(final CreateFluidEntryRequest request) {
        final FluidEntry entry = fluidEntryMapper.toEntity(request);
        final FluidEntry saved = fluidEntryRepository.save(entry);
        log.info("Fluid entry recorded: type={}, category={}, patientId={}",
                request.entryType(), request.category(), request.patientId());
        return fluidEntryMapper.toDto(saved);
    }

    /**
     * Retrieves a fluid entry by ID.
     *
     * @param id the fluid entry UUID
     * @return the fluid entry DTO
     */
    @Transactional(readOnly = true)
    public FluidEntryDto getFluidEntryById(final UUID id) {
        return fluidEntryRepository.findById(id)
                .map(fluidEntryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FluidEntry", id.toString()));
    }

    /**
     * Gets fluid entries for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of fluid entry DTOs
     */
    @Transactional(readOnly = true)
    public Page<FluidEntryDto> getFluidEntriesByPatient(
            final UUID patientId, final Pageable pageable) {
        return fluidEntryRepository
                .findByPatientIdOrderByRecordedAtDesc(patientId, pageable)
                .map(fluidEntryMapper::toDto);
    }

    /**
     * Calculates a fluid balance summary for a patient over a time period.
     * If patient weight is provided, per-kg-per-day values are also calculated.
     *
     * @param patientId     the patient UUID
     * @param start         start of the period
     * @param end           end of the period
     * @param weightGrams   patient weight in grams (nullable)
     * @return the fluid balance summary DTO
     */
    @Transactional(readOnly = true)
    public FluidBalanceSummaryDto getFluidBalanceSummary(
            final UUID patientId,
            final Instant start,
            final Instant end,
            final Integer weightGrams) {
        final List<FluidEntry> intakeEntries = fluidEntryRepository
                .findByPatientIdAndEntryTypeAndRecordedAtBetween(
                        patientId, FluidEntryType.INTAKE, start, end);
        final List<FluidEntry> outputEntries = fluidEntryRepository
                .findByPatientIdAndEntryTypeAndRecordedAtBetween(
                        patientId, FluidEntryType.OUTPUT, start, end);

        final double totalIntake = intakeEntries.stream()
                .mapToDouble(FluidEntry::getVolumeMl)
                .sum();
        final double totalOutput = outputEntries.stream()
                .mapToDouble(FluidEntry::getVolumeMl)
                .sum();
        final double netBalance = totalIntake - totalOutput;

        Double intakePerKgPerDay = null;
        Double outputPerKgPerDay = null;

        if (weightGrams != null && weightGrams > 0) {
            final double weightKg = weightGrams / 1000.0;
            final double periodDays = Duration.between(start, end).toSeconds() / 86400.0;
            if (periodDays > 0) {
                intakePerKgPerDay = totalIntake / weightKg / periodDays;
                outputPerKgPerDay = totalOutput / weightKg / periodDays;
            }
        }

        return new FluidBalanceSummaryDto(
                patientId, start, end,
                totalIntake, totalOutput, netBalance,
                weightGrams, intakePerKgPerDay, outputPerKgPerDay);
    }
}
