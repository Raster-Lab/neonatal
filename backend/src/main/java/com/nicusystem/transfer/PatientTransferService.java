package com.nicusystem.transfer;

import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing patient transfer records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PatientTransferService {

    private final PatientTransferRepository patientTransferRepository;
    private final PatientTransferMapper patientTransferMapper;

    /**
     * Records a new patient transfer.
     *
     * @param request the transfer creation request
     * @return the created transfer DTO
     */
    @Transactional
    public PatientTransferDto createTransfer(final CreatePatientTransferRequest request) {
        final PatientTransfer transfer = patientTransferMapper.toEntity(request);
        final PatientTransfer saved = patientTransferRepository.save(transfer);
        log.info("Patient transfer recorded for patient id: {}", request.patientId());
        return patientTransferMapper.toDto(saved);
    }

    /**
     * Retrieves a transfer by ID.
     *
     * @param id the transfer UUID
     * @return the transfer DTO
     */
    @Transactional(readOnly = true)
    public PatientTransferDto getTransferById(final UUID id) {
        return patientTransferRepository.findById(id)
                .map(patientTransferMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("PatientTransfer", id.toString()));
    }

    /**
     * Retrieves all transfers for a given patient.
     *
     * @param patientId the patient UUID
     * @return list of transfer DTOs
     */
    @Transactional(readOnly = true)
    public List<PatientTransferDto> getTransfersByPatientId(final UUID patientId) {
        return patientTransferRepository.findByPatientId(patientId).stream()
                .map(patientTransferMapper::toDto)
                .toList();
    }
}
