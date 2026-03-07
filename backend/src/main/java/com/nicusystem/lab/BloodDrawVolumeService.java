package com.nicusystem.lab;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing blood draw volume tracking in the NICU.
 *
 * <p>Cumulative blood draw tracking is critical for premature infants to prevent
 * iatrogenic anaemia caused by frequent laboratory phlebotomy.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BloodDrawVolumeService {

    private final BloodDrawVolumeRepository bloodDrawVolumeRepository;
    private final BloodDrawVolumeMapper bloodDrawVolumeMapper;

    /**
     * Records a new blood draw event.
     *
     * @param request the blood draw creation request
     * @return the persisted blood draw volume DTO
     */
    @Transactional
    public BloodDrawVolumeDto recordBloodDraw(final CreateBloodDrawVolumeRequest request) {
        final BloodDrawVolume entity = bloodDrawVolumeMapper.toEntity(request);
        final BloodDrawVolume saved = bloodDrawVolumeRepository.save(entity);
        log.info("Blood draw recorded: volumeUl={}, patientId={}", request.volumeUl(), request.patientId());
        return bloodDrawVolumeMapper.toDto(saved);
    }

    /**
     * Retrieves a blood draw record by its unique identifier.
     *
     * @param id the blood draw record UUID
     * @return the blood draw volume DTO
     * @throws ResourceNotFoundException if no record exists with the given ID
     */
    @Transactional(readOnly = true)
    public BloodDrawVolumeDto getBloodDrawById(final UUID id) {
        return bloodDrawVolumeRepository.findById(id)
                .map(bloodDrawVolumeMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("BloodDrawVolume", id.toString()));
    }

    /**
     * Returns a paginated list of blood draw records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of blood draw volume DTOs
     */
    @Transactional(readOnly = true)
    public Page<BloodDrawVolumeDto> getBloodDrawsByPatient(
            final UUID patientId, final Pageable pageable) {
        return bloodDrawVolumeRepository.findByPatientId(patientId, pageable)
                .map(bloodDrawVolumeMapper::toDto);
    }

    /**
     * Returns the cumulative blood draw volume in microliters for a patient.
     *
     * <p>Returns 0.0 if no blood draw records exist for the patient.</p>
     *
     * @param patientId the patient UUID
     * @return total blood volume drawn in microliters
     */
    @Transactional(readOnly = true)
    public Double getCumulativeBloodDrawVolumeUl(final UUID patientId) {
        final Double sum = bloodDrawVolumeRepository.sumVolumeUlByPatientId(patientId);
        return sum != null ? sum : 0.0;
    }
}
