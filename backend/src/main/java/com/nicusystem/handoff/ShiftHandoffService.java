package com.nicusystem.handoff;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing shift handoff reports.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ShiftHandoffService {

    private final ShiftHandoffRepository handoffRepository;
    private final ShiftHandoffMapper handoffMapper;

    /**
     * Creates a new shift handoff report.
     *
     * @param request the handoff creation request
     * @return the created handoff DTO
     */
    @Transactional
    public ShiftHandoffDto createHandoff(final CreateShiftHandoffRequest request) {
        final ShiftHandoff entity = handoffMapper.toEntity(request);
        final ShiftHandoff saved = handoffRepository.save(entity);
        log.info("Shift handoff created: format={}, patientId={}, handingOff={}, receiving={}",
                request.handoffFormat(), request.patientId(),
                request.handingOffProvider(), request.receivingProvider());
        return handoffMapper.toDto(saved);
    }

    /**
     * Retrieves a shift handoff report by ID.
     *
     * @param id the handoff UUID
     * @return the handoff DTO
     */
    @Transactional(readOnly = true)
    public ShiftHandoffDto getById(final UUID id) {
        return handoffRepository.findById(id)
                .map(handoffMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ShiftHandoff", id.toString()));
    }

    /**
     * Gets handoff reports for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of handoff DTOs
     */
    @Transactional(readOnly = true)
    public Page<ShiftHandoffDto> getByPatient(
            final UUID patientId, final Pageable pageable) {
        return handoffRepository.findByPatientId(patientId, pageable)
                .map(handoffMapper::toDto);
    }
}
