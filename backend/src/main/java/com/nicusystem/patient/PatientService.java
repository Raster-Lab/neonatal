package com.nicusystem.patient;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.nicusystem.common.ResourceNotFoundException;
import com.nicusystem.transfer.PatientTransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing neonatal patient records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final MotherRepository motherRepository;
    private final PatientMapper patientMapper;
    private final PatientTransferRepository patientTransferRepository;

    /** Counter for generating sequential MRN numbers. */
    private final AtomicLong mrnCounter = new AtomicLong(System.currentTimeMillis() % 100000);

    /**
     * Creates a new patient record with auto-generated MRN.
     *
     * @param request the patient creation request
     * @return the created patient DTO
     */
    @Transactional
    public PatientDto createPatient(final CreatePatientRequest request) {
        final String mrn = generateMrn();
        final Patient patient = patientMapper.toEntity(request, mrn);
        final Patient saved = patientRepository.save(patient);
        log.info("Patient created with MRN: {}", mrn);
        return patientMapper.toDto(saved);
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id the patient UUID
     * @return the patient DTO
     */
    @Transactional(readOnly = true)
    public PatientDto getPatientById(final UUID id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id.toString()));
    }

    /**
     * Retrieves a patient by MRN.
     *
     * @param mrn the medical record number
     * @return the patient DTO
     */
    @Transactional(readOnly = true)
    public PatientDto getPatientByMrn(final String mrn) {
        return patientRepository.findByMrn(mrn)
                .map(patientMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", mrn));
    }

    /**
     * Returns a page of all active patients.
     *
     * @param pageable pagination information
     * @return page of patient DTOs
     */
    @Transactional(readOnly = true)
    public Page<PatientDto> getActivePatients(final Pageable pageable) {
        return patientRepository.findByActiveTrue(pageable)
                .map(patientMapper::toDto);
    }

    /**
     * Searches patients by name.
     *
     * @param name     the name fragment to search
     * @param pageable pagination information
     * @return page of matching patient DTOs
     */
    @Transactional(readOnly = true)
    public Page<PatientDto> searchPatients(final String name, final Pageable pageable) {
        return patientRepository.searchByName(name, pageable)
                .map(patientMapper::toDto);
    }

    /**
     * Finds all patients (siblings) linked to a mother.
     *
     * @param motherId the mother's UUID
     * @return list of patient DTOs
     */
    @Transactional(readOnly = true)
    public List<PatientDto> getPatientsByMotherId(final UUID motherId) {
        return patientRepository.findByMotherId(motherId).stream()
                .map(patientMapper::toDto)
                .toList();
    }

    /**
     * Updates an existing patient record.
     *
     * @param id      the patient UUID
     * @param request the update data
     * @return the updated patient DTO
     */
    @Transactional
    public PatientDto updatePatient(final UUID id, final CreatePatientRequest request) {
        final Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id.toString()));
        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setGender(request.gender());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setBirthWeightGrams(request.birthWeightGrams());
        patient.setBirthLengthCm(request.birthLengthCm());
        patient.setHeadCircumferenceCm(request.headCircumferenceCm());
        patient.setGestationalAgeWeeks(request.gestationalAgeWeeks());
        patient.setGestationalAgeDays(request.gestationalAgeDays());
        patient.setDeliveryType(request.deliveryType());
        patient.setApgarOneMinute(request.apgarOneMinute());
        patient.setApgarFiveMinute(request.apgarFiveMinute());
        patient.setApgarTenMinute(request.apgarTenMinute());
        patient.setMotherId(request.motherId());
        patient.setAdmissionDate(request.admissionDate());
        patient.setBedNumber(request.bedNumber());
        patient.setBirthFacility(request.birthFacility());
        patient.setReferringFacility(request.referringFacility());
        patient.setTransportDetails(request.transportDetails());
        final Patient saved = patientRepository.save(patient);
        log.info("Patient updated with id: {}", id);
        return patientMapper.toDto(saved);
    }

    /**
     * Soft deletes a patient by setting active to false.
     *
     * @param id the patient UUID
     */
    @Transactional
    public void deletePatient(final UUID id) {
        final Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id.toString()));
        patient.setActive(false);
        patientRepository.save(patient);
        log.info("Patient soft-deleted with id: {}", id);
    }

    /**
     * Creates a new mother record.
     *
     * @param request the mother creation request
     * @return the created mother DTO
     */
    @Transactional
    public MotherDto createMother(final CreateMotherRequest request) {
        final Mother mother = patientMapper.toMotherEntity(request);
        final Mother saved = motherRepository.save(mother);
        log.info("Mother record created with id: {}", saved.getId());
        return patientMapper.toMotherDto(saved);
    }

    /**
     * Retrieves a mother by ID.
     *
     * @param id the mother UUID
     * @return the mother DTO
     */
    @Transactional(readOnly = true)
    public MotherDto getMotherById(final UUID id) {
        return motherRepository.findById(id)
                .map(patientMapper::toMotherDto)
                .orElseThrow(() -> new ResourceNotFoundException("Mother", id.toString()));
    }

    /**
     * Returns a page of all active mothers.
     *
     * @param pageable pagination information
     * @return page of mother DTOs
     */
    @Transactional(readOnly = true)
    public Page<MotherDto> getActiveMothers(final Pageable pageable) {
        return motherRepository.findByActiveTrue(pageable)
                .map(patientMapper::toMotherDto);
    }

    /**
     * Builds a demographic summary for a patient, including mother info, siblings,
     * and transfer count.
     *
     * @param id the patient UUID
     * @return the demographic summary DTO
     */
    @Transactional(readOnly = true)
    public PatientDemographicSummaryDto getDemographicSummary(final UUID id) {
        final Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id.toString()));
        final PatientDto patientDto = patientMapper.toDto(patient);

        final MotherDto motherInfo = patient.getMotherId() == null ? null
                : motherRepository.findById(patient.getMotherId())
                        .map(patientMapper::toMotherDto)
                        .orElse(null);

        final List<PatientDto> siblings = patient.getMotherId() == null
                ? List.of()
                : patientRepository.findByMotherId(patient.getMotherId()).stream()
                        .filter(p -> !id.equals(p.getId()))
                        .map(patientMapper::toDto)
                        .toList();

        final int transferCount =
                (int) patientTransferRepository.countByPatientId(id);

        log.info("Demographic summary retrieved for patient id: {}", id);
        return new PatientDemographicSummaryDto(patientDto, motherInfo, siblings, transferCount);
    }

    /**
     * Generates a unique MRN with the format NICU-XXXXX.
     *
     * @return the generated MRN string
     */
    String generateMrn() {
        return String.format("NICU-%05d", mrnCounter.incrementAndGet());
    }
}
