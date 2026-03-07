package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing apnea events in the NICU.
 *
 * <p>Handles recording, retrieval, and counting of apnea episodes for neonatal patients.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ApneaEventService {

    private final ApneaEventRepository apneaEventRepository;
    private final ApneaEventMapper apneaEventMapper;

    /**
     * Records a new apnea event for a patient.
     *
     * @param request the apnea event creation request
     * @return the persisted apnea event DTO
     */
    @Transactional
    public ApneaEventDto recordApneaEvent(final CreateApneaEventRequest request) {
        final ApneaEvent entity = apneaEventMapper.toEntity(request);
        final ApneaEvent saved = apneaEventRepository.save(entity);
        log.info("Apnea event recorded: patientId={}, occurredAt={}",
                request.patientId(), request.occurredAt());
        return apneaEventMapper.toDto(saved);
    }

    /**
     * Retrieves an apnea event by its unique identifier.
     *
     * @param id the apnea event UUID
     * @return the apnea event DTO
     * @throws ResourceNotFoundException if no apnea event exists with the given ID
     */
    @Transactional(readOnly = true)
    public ApneaEventDto getApneaEventById(final UUID id) {
        return apneaEventRepository.findById(id)
                .map(apneaEventMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("ApneaEvent", id.toString()));
    }

    /**
     * Returns a paginated list of apnea events for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of apnea event DTOs
     */
    @Transactional(readOnly = true)
    public Page<ApneaEventDto> getApneaEventsByPatient(
            final UUID patientId, final Pageable pageable) {
        return apneaEventRepository.findByPatientId(patientId, pageable)
                .map(apneaEventMapper::toDto);
    }

    /**
     * Returns a paginated list of apnea events for a patient within a date range.
     *
     * @param patientId the patient UUID
     * @param start     start of the time range (inclusive)
     * @param end       end of the time range (inclusive)
     * @param pageable  pagination information
     * @return page of apnea event DTOs within the specified range
     */
    @Transactional(readOnly = true)
    public Page<ApneaEventDto> getApneaEventsByPatientAndDateRange(
            final UUID patientId,
            final Instant start,
            final Instant end,
            final Pageable pageable) {
        return apneaEventRepository
                .findByPatientIdAndOccurredAtBetween(patientId, start, end, pageable)
                .map(apneaEventMapper::toDto);
    }

    /**
     * Counts the total number of apnea events recorded for a patient.
     *
     * @param patientId the patient UUID
     * @return total count of apnea events for the patient
     */
    @Transactional(readOnly = true)
    public long countApneaEventsForPatient(final UUID patientId) {
        return apneaEventRepository.countByPatientId(patientId);
    }
}
