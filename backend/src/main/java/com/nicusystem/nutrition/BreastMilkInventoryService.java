package com.nicusystem.nutrition;

import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing breast milk inventory in the NICU.
 *
 * <p>Handles creation and retrieval of breast milk inventory entries
 * for neonatal patients, including filtering by donor milk status.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BreastMilkInventoryService {

    private final BreastMilkInventoryRepository breastMilkInventoryRepository;
    private final BreastMilkInventoryMapper breastMilkInventoryMapper;

    /**
     * Creates a new breast milk inventory entry.
     *
     * @param request the creation request
     * @return the persisted inventory entry DTO
     */
    @Transactional
    public BreastMilkInventoryDto createEntry(final CreateBreastMilkInventoryRequest request) {
        final BreastMilkInventory entity = breastMilkInventoryMapper.toEntity(request);
        final BreastMilkInventory saved = breastMilkInventoryRepository.save(entity);
        log.info("Breast milk inventory entry created: patientId={}, donorMilk={}",
                request.patientId(), request.donorMilk());
        return breastMilkInventoryMapper.toDto(saved);
    }

    /**
     * Retrieves a breast milk inventory entry by its unique identifier.
     *
     * @param id the inventory entry UUID
     * @return the inventory entry DTO
     * @throws ResourceNotFoundException if no entry exists with the given ID
     */
    @Transactional(readOnly = true)
    public BreastMilkInventoryDto getById(final UUID id) {
        return breastMilkInventoryRepository.findById(id)
                .map(breastMilkInventoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "BreastMilkInventory", id.toString()));
    }

    /**
     * Returns a paginated list of breast milk inventory entries for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of inventory entry DTOs
     */
    @Transactional(readOnly = true)
    public Page<BreastMilkInventoryDto> getByPatient(
            final UUID patientId, final Pageable pageable) {
        return breastMilkInventoryRepository.findByPatientId(patientId, pageable)
                .map(breastMilkInventoryMapper::toDto);
    }

    /**
     * Returns a paginated list of breast milk inventory entries for a patient
     * filtered by donor milk status.
     *
     * @param patientId the patient UUID
     * @param donorMilk {@code true} to return donor milk only; {@code false} for own milk
     * @param pageable  pagination information
     * @return page of inventory entry DTOs
     */
    @Transactional(readOnly = true)
    public Page<BreastMilkInventoryDto> getByPatientAndDonorMilk(
            final UUID patientId, final boolean donorMilk, final Pageable pageable) {
        return breastMilkInventoryRepository
                .findByPatientIdAndDonorMilk(patientId, donorMilk, pageable)
                .map(breastMilkInventoryMapper::toDto);
    }
}
