package com.nicusystem.rounding;

import java.time.LocalDate;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing daily rounding summary records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DailyRoundingSummaryService {

    private final DailyRoundingSummaryRepository
            dailyRoundingSummaryRepository;
    private final DailyRoundingSummaryMapper dailyRoundingSummaryMapper;

    /**
     * Creates a new daily rounding summary record.
     *
     * @param request the daily rounding summary creation request
     * @return the created daily rounding summary DTO
     */
    @Transactional
    public DailyRoundingSummaryDto create(
            final CreateDailyRoundingSummaryRequest request) {
        final DailyRoundingSummary entity =
                dailyRoundingSummaryMapper.toEntity(request);
        final DailyRoundingSummary saved =
                dailyRoundingSummaryRepository.save(entity);
        log.info("Daily rounding summary created: date={}, patientId={}",
                request.roundingDate(), request.patientId());
        return dailyRoundingSummaryMapper.toDto(saved);
    }

    /**
     * Retrieves a daily rounding summary by ID.
     *
     * @param id the daily rounding summary UUID
     * @return the daily rounding summary DTO
     */
    @Transactional(readOnly = true)
    public DailyRoundingSummaryDto getById(final UUID id) {
        return dailyRoundingSummaryRepository.findById(id)
                .map(dailyRoundingSummaryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DailyRoundingSummary", id.toString()));
    }

    /**
     * Gets daily rounding summaries for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of daily rounding summary DTOs
     */
    @Transactional(readOnly = true)
    public Page<DailyRoundingSummaryDto> getByPatient(
            final UUID patientId, final Pageable pageable) {
        return dailyRoundingSummaryRepository
                .findByPatientIdOrderByRoundingDateDesc(
                        patientId, pageable)
                .map(dailyRoundingSummaryMapper::toDto);
    }

    /**
     * Gets a daily rounding summary for a patient on a specific date.
     *
     * @param patientId    the patient UUID
     * @param roundingDate the rounding date
     * @return the daily rounding summary DTO
     */
    @Transactional(readOnly = true)
    public DailyRoundingSummaryDto getByPatientAndDate(
            final UUID patientId, final LocalDate roundingDate) {
        return dailyRoundingSummaryRepository
                .findByPatientIdAndRoundingDate(patientId, roundingDate)
                .map(dailyRoundingSummaryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DailyRoundingSummary",
                        patientId + "/" + roundingDate));
    }
}
