package com.nicusystem.flowsheet;

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
 * Service for managing flowsheet entries.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FlowsheetEntryService {

    private final FlowsheetEntryRepository flowsheetEntryRepository;
    private final FlowsheetEntryMapper flowsheetEntryMapper;

    /**
     * Creates a new flowsheet entry.
     *
     * @param request the flowsheet entry creation request
     * @return the created flowsheet entry DTO
     */
    @Transactional
    public FlowsheetEntryDto create(
            final CreateFlowsheetEntryRequest request) {
        final FlowsheetEntry entity =
                flowsheetEntryMapper.toEntity(request);
        final FlowsheetEntry saved =
                flowsheetEntryRepository.save(entity);
        log.info("Flowsheet entry created: category={}, patientId={}",
                request.category(), request.patientId());
        return flowsheetEntryMapper.toDto(saved);
    }

    /**
     * Retrieves a flowsheet entry by ID.
     *
     * @param id the flowsheet entry UUID
     * @return the flowsheet entry DTO
     */
    @Transactional(readOnly = true)
    public FlowsheetEntryDto getById(final UUID id) {
        return flowsheetEntryRepository.findById(id)
                .map(flowsheetEntryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FlowsheetEntry", id.toString()));
    }

    /**
     * Gets flowsheet entries for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of flowsheet entry DTOs
     */
    @Transactional(readOnly = true)
    public Page<FlowsheetEntryDto> getByPatient(
            final UUID patientId, final Pageable pageable) {
        return flowsheetEntryRepository
                .findByPatientIdOrderByEntryTimeDesc(patientId, pageable)
                .map(flowsheetEntryMapper::toDto);
    }

    /**
     * Gets flowsheet entries for a patient filtered by category.
     *
     * @param patientId the patient UUID
     * @param category  the flowsheet category
     * @param pageable  pagination information
     * @return page of flowsheet entry DTOs
     */
    @Transactional(readOnly = true)
    public Page<FlowsheetEntryDto> getByPatientAndCategory(
            final UUID patientId,
            final FlowsheetCategory category,
            final Pageable pageable) {
        return flowsheetEntryRepository
                .findByPatientIdAndCategoryOrderByEntryTimeDesc(
                        patientId, category, pageable)
                .map(flowsheetEntryMapper::toDto);
    }

    /**
     * Gets flowsheet entries for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     start of the time range (inclusive)
     * @param end       end of the time range (inclusive)
     * @return list of flowsheet entry DTOs
     */
    @Transactional(readOnly = true)
    public List<FlowsheetEntryDto> getByPatientAndTimeRange(
            final UUID patientId,
            final Instant start,
            final Instant end) {
        return flowsheetEntryRepository
                .findByPatientIdAndEntryTimeBetweenOrderByEntryTimeAsc(
                        patientId, start, end)
                .stream()
                .map(flowsheetEntryMapper::toDto)
                .toList();
    }
}
