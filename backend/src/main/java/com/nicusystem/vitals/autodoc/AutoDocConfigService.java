package com.nicusystem.vitals.autodoc;

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
 * Service for managing automated vital signs documentation configurations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AutoDocConfigService {

    private final AutoDocConfigRepository autoDocConfigRepository;
    private final AutoDocConfigMapper autoDocConfigMapper;

    /**
     * Creates a new auto-doc configuration.
     *
     * @param request the creation request
     * @return the created configuration DTO
     */
    @Transactional
    public AutoDocConfigDto create(final CreateAutoDocConfigRequest request) {
        final AutoDocConfig entity = autoDocConfigMapper.toEntity(request);
        final AutoDocConfig saved = autoDocConfigRepository.save(entity);
        log.info("Auto-doc config created: type={}, patientId={}", request.vitalType(), request.patientId());
        return autoDocConfigMapper.toDto(saved);
    }

    /**
     * Retrieves an auto-doc configuration by ID.
     *
     * @param id the configuration UUID
     * @return the configuration DTO
     */
    @Transactional(readOnly = true)
    public AutoDocConfigDto getById(final UUID id) {
        return autoDocConfigRepository.findById(id)
                .map(autoDocConfigMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("AutoDocConfig", id.toString()));
    }

    /**
     * Gets auto-doc configurations for a patient with pagination.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of configuration DTOs
     */
    @Transactional(readOnly = true)
    public Page<AutoDocConfigDto> getByPatient(final UUID patientId, final Pageable pageable) {
        return autoDocConfigRepository
                .findByPatientIdOrderByCreatedAtDesc(patientId, pageable)
                .map(autoDocConfigMapper::toDto);
    }

    /**
     * Gets enabled auto-doc configurations for a patient.
     *
     * @param patientId the patient UUID
     * @return list of enabled configuration DTOs
     */
    @Transactional(readOnly = true)
    public List<AutoDocConfigDto> getEnabledByPatient(final UUID patientId) {
        return autoDocConfigRepository
                .findByPatientIdAndEnabledTrue(patientId)
                .stream()
                .map(autoDocConfigMapper::toDto)
                .toList();
    }

    /**
     * Toggles the enabled state of an auto-doc configuration.
     *
     * @param id      the configuration UUID
     * @param enabled the new enabled state
     * @return the updated configuration DTO
     */
    @Transactional
    public AutoDocConfigDto toggleEnabled(final UUID id, final boolean enabled) {
        final AutoDocConfig config = autoDocConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AutoDocConfig", id.toString()));
        config.setEnabled(enabled);
        final AutoDocConfig saved = autoDocConfigRepository.save(config);
        return autoDocConfigMapper.toDto(saved);
    }
}
