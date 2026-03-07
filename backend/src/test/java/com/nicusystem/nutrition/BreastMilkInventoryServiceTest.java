package com.nicusystem.nutrition;

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
 * Tests for {@link BreastMilkInventoryService}.
 */
@ExtendWith(MockitoExtension.class)
class BreastMilkInventoryServiceTest {

    @Mock
    private BreastMilkInventoryRepository breastMilkInventoryRepository;

    @Mock
    private BreastMilkInventoryMapper breastMilkInventoryMapper;

    @InjectMocks
    private BreastMilkInventoryService breastMilkInventoryService;

    @Test
    void createEntry_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateBreastMilkInventoryRequest request = new CreateBreastMilkInventoryRequest(
                patientId, "BMK-001", 60.0,
                java.time.Instant.now(), null, false, false, null);
        final BreastMilkInventory entity = new BreastMilkInventory();
        final BreastMilkInventory saved = new BreastMilkInventory();
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                UUID.randomUUID(), patientId, "BMK-001", 60.0,
                java.time.Instant.now(), null, false, false, null, null, null);
        when(breastMilkInventoryMapper.toEntity(request)).thenReturn(entity);
        when(breastMilkInventoryRepository.save(entity)).thenReturn(saved);
        when(breastMilkInventoryMapper.toDto(saved)).thenReturn(dto);

        // When
        final BreastMilkInventoryDto result = breastMilkInventoryService.createEntry(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(breastMilkInventoryRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final BreastMilkInventory entity = new BreastMilkInventory();
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                id, UUID.randomUUID(), "BMK-002", 30.0,
                java.time.Instant.now(), null, true, false, null, null, null);
        when(breastMilkInventoryRepository.findById(id)).thenReturn(Optional.of(entity));
        when(breastMilkInventoryMapper.toDto(entity)).thenReturn(dto);

        // When
        final BreastMilkInventoryDto result = breastMilkInventoryService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(breastMilkInventoryRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> breastMilkInventoryService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("BreastMilkInventory");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final BreastMilkInventory entity = new BreastMilkInventory();
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                UUID.randomUUID(), patientId, "BMK-003", 45.0,
                java.time.Instant.now(), null, false, false, null, null, null);
        when(breastMilkInventoryRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(breastMilkInventoryMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<BreastMilkInventoryDto> result =
                breastMilkInventoryService.getByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getByPatientAndDonorMilk_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final BreastMilkInventory entity = new BreastMilkInventory();
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                UUID.randomUUID(), patientId, "DONOR-001", 50.0,
                java.time.Instant.now(), null, true, false, null, null, null);
        when(breastMilkInventoryRepository.findByPatientIdAndDonorMilk(patientId, true, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(breastMilkInventoryMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<BreastMilkInventoryDto> result =
                breastMilkInventoryService.getByPatientAndDonorMilk(patientId, true, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).donorMilk()).isTrue();
    }
}
