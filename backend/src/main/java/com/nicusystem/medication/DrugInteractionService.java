package com.nicusystem.medication;

import java.util.ArrayList;
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
 * Service for managing drug-drug interactions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DrugInteractionService {

    private final DrugInteractionRepository drugInteractionRepository;
    private final DrugInteractionMapper drugInteractionMapper;

    /**
     * Creates a new drug interaction record.
     *
     * @param request the creation request
     * @return the created DrugInteractionDto
     */
    @Transactional
    public DrugInteractionDto createInteraction(
            final CreateDrugInteractionRequest request) {
        final DrugInteraction entity = drugInteractionMapper.toEntity(request);
        final DrugInteraction saved = drugInteractionRepository.save(entity);
        log.info("Drug interaction created: drug1={}, drug2={}, severity={}",
                request.drug1Name(), request.drug2Name(),
                request.interactionSeverity());
        return drugInteractionMapper.toDto(saved);
    }

    /**
     * Retrieves a drug interaction by ID.
     *
     * @param id the interaction UUID
     * @return the DrugInteractionDto
     */
    @Transactional(readOnly = true)
    public DrugInteractionDto getInteractionById(final UUID id) {
        return drugInteractionRepository.findById(id)
                .map(drugInteractionMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DrugInteraction", id.toString()));
    }

    /**
     * Returns all active drug interactions, paginated.
     *
     * @param pageable pagination information
     * @return page of DrugInteractionDto
     */
    @Transactional(readOnly = true)
    public Page<DrugInteractionDto> getInteractions(final Pageable pageable) {
        return drugInteractionRepository.findByActiveTrue(pageable)
                .map(drugInteractionMapper::toDto);
    }

    /**
     * Checks for known interactions among the given list of medication names.
     *
     * @param medicationNames list of medication names to check
     * @return list of DrugInteractionDto for all detected interactions
     */
    @Transactional(readOnly = true)
    public List<DrugInteractionDto> checkInteractions(
            final List<String> medicationNames) {
        final List<DrugInteractionDto> results = new ArrayList<>();
        for (int i = 0; i < medicationNames.size(); i++) {
            for (int j = i + 1; j < medicationNames.size(); j++) {
                drugInteractionRepository.findInteractionBetween(
                        medicationNames.get(i), medicationNames.get(j))
                        .stream()
                        .map(drugInteractionMapper::toDto)
                        .forEach(results::add);
            }
        }
        return results;
    }

    /**
     * Soft-deletes a drug interaction by setting active to false.
     *
     * @param id the interaction UUID
     */
    @Transactional
    public void deleteInteraction(final UUID id) {
        final DrugInteraction interaction = drugInteractionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DrugInteraction", id.toString()));
        interaction.setActive(false);
        drugInteractionRepository.save(interaction);
        log.info("Drug interaction soft-deleted: id={}", id);
    }
}
