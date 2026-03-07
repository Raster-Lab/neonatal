package com.nicusystem.medication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for DrugInteractionService.
 */
@ExtendWith(MockitoExtension.class)
class DrugInteractionServiceTest {

    @Mock
    private DrugInteractionRepository drugInteractionRepository;

    @Mock
    private DrugInteractionMapper drugInteractionMapper;

    @InjectMocks
    private DrugInteractionService drugInteractionService;

    @Test
    void createInteraction_validRequest_returnsDto() {
        // Given
        final CreateDrugInteractionRequest request = new CreateDrugInteractionRequest(
                "Warfarin", "Aspirin", DrugInteractionSeverity.CONTRAINDICATED,
                "Bleeding risk", "Hemorrhage", "Avoid combination");
        final DrugInteraction entity = new DrugInteraction();
        final DrugInteraction saved = new DrugInteraction();
        final DrugInteractionDto expectedDto = buildDto();
        when(drugInteractionMapper.toEntity(request)).thenReturn(entity);
        when(drugInteractionRepository.save(entity)).thenReturn(saved);
        when(drugInteractionMapper.toDto(saved)).thenReturn(expectedDto);

        // When
        final DrugInteractionDto result =
                drugInteractionService.createInteraction(request);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(drugInteractionRepository).save(entity);
    }

    @Test
    void getInteractionById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final DrugInteraction entity = new DrugInteraction();
        final DrugInteractionDto expectedDto = buildDto();
        when(drugInteractionRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(drugInteractionMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final DrugInteractionDto result =
                drugInteractionService.getInteractionById(id);

        // Then
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getInteractionById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(drugInteractionRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> drugInteractionService.getInteractionById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("DrugInteraction");
    }

    @Test
    void getInteractions_returnsPage() {
        // Given
        final Pageable pageable = PageRequest.of(0, 20);
        final DrugInteraction entity = new DrugInteraction();
        final DrugInteractionDto expectedDto = buildDto();
        final Page<DrugInteraction> page = new PageImpl<>(List.of(entity));
        when(drugInteractionRepository.findByActiveTrue(pageable))
                .thenReturn(page);
        when(drugInteractionMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final Page<DrugInteractionDto> result =
                drugInteractionService.getInteractions(pageable);

        // Then
        assertThat(result.getContent()).containsExactly(expectedDto);
    }

    @Test
    void getInteractions_emptyResult_returnsEmptyPage() {
        // Given
        final Pageable pageable = PageRequest.of(0, 20);
        when(drugInteractionRepository.findByActiveTrue(pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // When
        final Page<DrugInteractionDto> result =
                drugInteractionService.getInteractions(pageable);

        // Then
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void checkInteractions_withInteractions_returnsInteractions() {
        // Given
        final List<String> names = List.of("Warfarin", "Aspirin", "Ibuprofen");
        final DrugInteraction interaction = new DrugInteraction();
        final DrugInteractionDto expectedDto = buildDto();
        when(drugInteractionRepository.findInteractionBetween("Warfarin", "Aspirin"))
                .thenReturn(List.of(interaction));
        when(drugInteractionRepository.findInteractionBetween("Warfarin", "Ibuprofen"))
                .thenReturn(Collections.emptyList());
        when(drugInteractionRepository.findInteractionBetween("Aspirin", "Ibuprofen"))
                .thenReturn(Collections.emptyList());
        when(drugInteractionMapper.toDto(interaction)).thenReturn(expectedDto);

        // When
        final List<DrugInteractionDto> result =
                drugInteractionService.checkInteractions(names);

        // Then
        assertThat(result).containsExactly(expectedDto);
    }

    @Test
    void checkInteractions_noInteractions_returnsEmpty() {
        // Given
        final List<String> names = List.of("Drug1", "Drug2");
        when(drugInteractionRepository.findInteractionBetween("Drug1", "Drug2"))
                .thenReturn(Collections.emptyList());

        // When
        final List<DrugInteractionDto> result =
                drugInteractionService.checkInteractions(names);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void checkInteractions_singleMedication_returnsEmpty() {
        // Given
        final List<String> names = List.of("Drug1");

        // When
        final List<DrugInteractionDto> result =
                drugInteractionService.checkInteractions(names);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void checkInteractions_emptyList_returnsEmpty() {
        // When
        final List<DrugInteractionDto> result =
                drugInteractionService.checkInteractions(Collections.emptyList());

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteInteraction_existingId_setsActiveToFalse() {
        // Given
        final UUID id = UUID.randomUUID();
        final DrugInteraction entity = new DrugInteraction();
        entity.setActive(true);
        when(drugInteractionRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(drugInteractionRepository.save(entity)).thenReturn(entity);

        // When
        drugInteractionService.deleteInteraction(id);

        // Then
        assertThat(entity.isActive()).isFalse();
        verify(drugInteractionRepository).save(entity);
    }

    @Test
    void deleteInteraction_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(drugInteractionRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> drugInteractionService.deleteInteraction(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("DrugInteraction");
    }

    private DrugInteractionDto buildDto() {
        return new DrugInteractionDto(
                UUID.randomUUID(), "Warfarin", "Aspirin",
                DrugInteractionSeverity.CONTRAINDICATED,
                "Bleeding risk", "Hemorrhage", "Avoid combination", true);
    }
}
