package com.nicusystem.consent;

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
 * Service for managing patient consent records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PatientConsentService {

    private final PatientConsentRepository patientConsentRepository;
    private final PatientConsentMapper patientConsentMapper;

    /**
     * Creates a new patient consent record.
     *
     * @param request the consent creation request
     * @return the created consent DTO
     */
    @Transactional
    public PatientConsentDto createConsent(final CreatePatientConsentRequest request) {
        final PatientConsent consent = patientConsentMapper.toEntity(request);
        final PatientConsent saved = patientConsentRepository.save(consent);
        log.info("Patient consent created: type={}, patientId={}",
                request.consentType(), request.patientId());
        return patientConsentMapper.toDto(saved);
    }

    /**
     * Retrieves a patient consent record by ID.
     *
     * @param id the consent UUID
     * @return the consent DTO
     */
    @Transactional(readOnly = true)
    public PatientConsentDto getConsentById(final UUID id) {
        return patientConsentRepository.findById(id)
                .map(patientConsentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "PatientConsent", id.toString()));
    }

    /**
     * Gets consent records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of consent DTOs
     */
    @Transactional(readOnly = true)
    public Page<PatientConsentDto> getConsentsByPatient(
            final UUID patientId, final Pageable pageable) {
        return patientConsentRepository
                .findByPatientIdOrderBySignedAtDesc(patientId, pageable)
                .map(patientConsentMapper::toDto);
    }

    /**
     * Gets consent records for a patient filtered by consent type.
     *
     * @param patientId   the patient UUID
     * @param consentType the consent type
     * @return list of consent DTOs
     */
    @Transactional(readOnly = true)
    public List<PatientConsentDto> getConsentsByPatientAndType(
            final UUID patientId, final ConsentType consentType) {
        return patientConsentRepository
                .findByPatientIdAndConsentType(patientId, consentType)
                .stream()
                .map(patientConsentMapper::toDto)
                .toList();
    }

    /**
     * Updates the status of a patient consent record.
     *
     * @param id     the consent UUID
     * @param status the new consent status
     * @return the updated consent DTO
     */
    @Transactional
    public PatientConsentDto updateConsentStatus(final UUID id, final ConsentStatus status) {
        final PatientConsent consent = patientConsentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "PatientConsent", id.toString()));
        consent.setConsentStatus(status);
        final PatientConsent saved = patientConsentRepository.save(consent);
        log.info("Patient consent status updated: id={}, status={}", id, status);
        return patientConsentMapper.toDto(saved);
    }
}
