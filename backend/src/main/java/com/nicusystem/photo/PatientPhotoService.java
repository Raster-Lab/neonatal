package com.nicusystem.photo;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing patient photos in the NICU.
 *
 * <p>Handles creation, retrieval, and deletion of photos
 * captured for neonatal patients.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PatientPhotoService {

    private final PatientPhotoRepository patientPhotoRepository;
    private final PatientPhotoMapper patientPhotoMapper;

    /**
     * Creates a new patient photo record.
     *
     * @param request the photo creation request
     * @return the persisted photo DTO
     */
    @Transactional
    public PatientPhotoDto createPhoto(final CreatePatientPhotoRequest request) {
        final PatientPhoto entity = patientPhotoMapper.toEntity(request);
        final PatientPhoto saved = patientPhotoRepository.save(entity);
        log.info("Patient photo created: fileName={}, patientId={}",
                request.fileName(), request.patientId());
        return patientPhotoMapper.toDto(saved);
    }

    /**
     * Retrieves a patient photo by its unique identifier.
     *
     * @param id the photo UUID
     * @return the photo DTO (without binary data)
     * @throws ResourceNotFoundException if no photo exists with the given ID
     */
    @Transactional(readOnly = true)
    public PatientPhotoDto getPhotoById(final UUID id) {
        return patientPhotoRepository.findById(id)
                .map(patientPhotoMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("PatientPhoto", id.toString()));
    }

    /**
     * Retrieves the binary image data for a patient photo.
     *
     * @param id the photo UUID
     * @return the raw image bytes
     * @throws ResourceNotFoundException if no photo exists with the given ID
     */
    @Transactional(readOnly = true)
    public byte[] getPhotoData(final UUID id) {
        final PatientPhoto photo = patientPhotoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PatientPhoto", id.toString()));
        return photo.getPhotoData();
    }

    /**
     * Returns a paginated list of photos for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of photo DTOs
     */
    @Transactional(readOnly = true)
    public Page<PatientPhotoDto> getPhotosByPatient(final UUID patientId,
                                                     final Pageable pageable) {
        return patientPhotoRepository.findByPatientId(patientId, pageable)
                .map(patientPhotoMapper::toDto);
    }

    /**
     * Deletes a patient photo by its unique identifier.
     *
     * @param id the photo UUID
     * @throws ResourceNotFoundException if no photo exists with the given ID
     */
    @Transactional
    public void deletePhoto(final UUID id) {
        final PatientPhoto photo = patientPhotoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PatientPhoto", id.toString()));
        patientPhotoRepository.delete(photo);
        log.info("Patient photo deleted: id={}", id);
    }
}
