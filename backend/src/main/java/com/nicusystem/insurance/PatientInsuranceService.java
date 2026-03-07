package com.nicusystem.insurance;

import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing patient insurance records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PatientInsuranceService {

    private final PatientInsuranceRepository patientInsuranceRepository;
    private final PatientInsuranceMapper patientInsuranceMapper;

    /**
     * Creates a new patient insurance record.
     *
     * @param request the insurance creation request
     * @return the created insurance DTO
     */
    @Transactional
    public PatientInsuranceDto createInsurance(final CreatePatientInsuranceRequest request) {
        final PatientInsurance insurance = patientInsuranceMapper.toEntity(request);
        final PatientInsurance saved = patientInsuranceRepository.save(insurance);
        log.info("Patient insurance created: type={}, patientId={}",
                request.insuranceType(), request.patientId());
        return patientInsuranceMapper.toDto(saved);
    }

    /**
     * Retrieves a patient insurance record by ID.
     *
     * @param id the insurance UUID
     * @return the insurance DTO
     */
    @Transactional(readOnly = true)
    public PatientInsuranceDto getInsuranceById(final UUID id) {
        return patientInsuranceRepository.findById(id)
                .map(patientInsuranceMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "PatientInsurance", id.toString()));
    }

    /**
     * Gets insurance records for a patient.
     *
     * @param patientId the patient UUID
     * @return list of insurance DTOs
     */
    @Transactional(readOnly = true)
    public List<PatientInsuranceDto> getInsurancesByPatient(final UUID patientId) {
        return patientInsuranceRepository
                .findByPatientIdOrderByInsuranceTypeAsc(patientId)
                .stream()
                .map(patientInsuranceMapper::toDto)
                .toList();
    }

    /**
     * Gets insurance records for a patient filtered by insurance type.
     *
     * @param patientId the patient UUID
     * @param type      the insurance type
     * @return list of insurance DTOs
     */
    @Transactional(readOnly = true)
    public List<PatientInsuranceDto> getInsurancesByPatientAndType(
            final UUID patientId, final InsuranceType type) {
        return patientInsuranceRepository
                .findByPatientIdAndInsuranceType(patientId, type)
                .stream()
                .map(patientInsuranceMapper::toDto)
                .toList();
    }

    /**
     * Updates all editable fields of a patient insurance record.
     *
     * @param id      the insurance UUID
     * @param request the update request
     * @return the updated insurance DTO
     */
    @Transactional
    public PatientInsuranceDto updateInsurance(
            final UUID id, final CreatePatientInsuranceRequest request) {
        final PatientInsurance insurance = patientInsuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "PatientInsurance", id.toString()));
        insurance.setPatientId(request.patientId());
        insurance.setInsuranceType(request.insuranceType());
        insurance.setInsurerName(request.insurerName());
        insurance.setPolicyNumber(request.policyNumber());
        insurance.setGroupNumber(request.groupNumber());
        insurance.setSubscriberName(request.subscriberName());
        insurance.setSubscriberDob(request.subscriberDob());
        insurance.setRelationshipToPatient(request.relationshipToPatient());
        insurance.setEffectiveDate(request.effectiveDate());
        insurance.setTerminationDate(request.terminationDate());
        insurance.setNotes(request.notes());
        final PatientInsurance saved = patientInsuranceRepository.save(insurance);
        log.info("Patient insurance updated: id={}", id);
        return patientInsuranceMapper.toDto(saved);
    }
}
