package com.nicusystem.nirs;

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
 * Service for managing near-infrared spectroscopy (NIRS) readings in the NICU.
 *
 * <p>Handles creation and retrieval of NIRS cerebral oxygenation readings,
 * including filtering by patient, sensor site, and time range.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NirsReadingService {

    private final NirsReadingRepository nirsReadingRepository;
    private final NirsReadingMapper nirsReadingMapper;

    /**
     * Creates a new NIRS reading for a patient.
     *
     * @param request the NIRS reading creation request
     * @return the persisted NIRS reading DTO
     */
    @Transactional
    public NirsReadingDto create(final CreateNirsReadingRequest request) {
        final NirsReading entity = nirsReadingMapper.toEntity(request);
        final NirsReading saved = nirsReadingRepository.save(entity);
        log.info("NIRS reading recorded: site={}, patientId={}", request.site(), request.patientId());
        return nirsReadingMapper.toDto(saved);
    }

    /**
     * Retrieves a NIRS reading by its unique identifier.
     *
     * @param id the NIRS reading UUID
     * @return the NIRS reading DTO
     * @throws ResourceNotFoundException if no reading exists with the given ID
     */
    @Transactional(readOnly = true)
    public NirsReadingDto getById(final UUID id) {
        return nirsReadingRepository.findById(id)
                .map(nirsReadingMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("NirsReading", id.toString()));
    }

    /**
     * Returns a paginated list of NIRS readings for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of NIRS reading DTOs
     */
    @Transactional(readOnly = true)
    public Page<NirsReadingDto> getByPatient(final UUID patientId, final Pageable pageable) {
        return nirsReadingRepository.findByPatientIdOrderByRecordedAtDesc(patientId, pageable)
                .map(nirsReadingMapper::toDto);
    }

    /**
     * Returns a paginated list of NIRS readings for a patient filtered by sensor site.
     *
     * @param patientId the patient UUID
     * @param site      the NIRS sensor site
     * @param pageable  pagination information
     * @return page of NIRS reading DTOs filtered by site
     */
    @Transactional(readOnly = true)
    public Page<NirsReadingDto> getByPatientAndSite(
            final UUID patientId, final NirsSite site, final Pageable pageable) {
        return nirsReadingRepository
                .findByPatientIdAndSiteOrderByRecordedAtDesc(patientId, site, pageable)
                .map(nirsReadingMapper::toDto);
    }

    /**
     * Returns NIRS readings for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     the start of the time range
     * @param end       the end of the time range
     * @return list of NIRS reading DTOs within the time range
     */
    @Transactional(readOnly = true)
    public List<NirsReadingDto> getByPatientAndTimeRange(
            final UUID patientId, final Instant start, final Instant end) {
        return nirsReadingRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(patientId, start, end)
                .stream()
                .map(nirsReadingMapper::toDto)
                .toList();
    }
}
