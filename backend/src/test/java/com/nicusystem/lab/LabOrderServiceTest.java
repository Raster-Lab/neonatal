package com.nicusystem.lab;

import java.time.Instant;
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
 * Tests for LabOrderService.
 */
@ExtendWith(MockitoExtension.class)
class LabOrderServiceTest {

    @Mock
    private LabOrderRepository labOrderRepository;

    @Mock
    private LabOrderMapper labOrderMapper;

    @InjectMocks
    private LabOrderService labOrderService;

    @Test
    void createLabOrder_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateLabOrderRequest request = new CreateLabOrderRequest(
                patientId, LabPanelType.COMPLETE_BLOOD_COUNT, Instant.now(), "Dr. Smith", null, null);
        final LabOrder entity = new LabOrder();
        final LabOrder saved = new LabOrder();
        final LabOrderDto dto = new LabOrderDto(UUID.randomUUID(), patientId,
                LabPanelType.COMPLETE_BLOOD_COUNT, LabOrderStatus.ORDERED,
                Instant.now(), "Dr. Smith", null, null, null, null, Instant.now(), Instant.now());
        when(labOrderMapper.toEntity(request)).thenReturn(entity);
        when(labOrderRepository.save(entity)).thenReturn(saved);
        when(labOrderMapper.toDto(saved)).thenReturn(dto);

        // When
        final LabOrderDto result = labOrderService.createLabOrder(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(labOrderRepository).save(entity);
    }

    @Test
    void getLabOrderById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final LabOrder entity = new LabOrder();
        final LabOrderDto dto = new LabOrderDto(id, UUID.randomUUID(),
                LabPanelType.BILIRUBIN_PANEL, LabOrderStatus.RESULTED,
                null, null, null, null, null, null, Instant.now(), Instant.now());
        when(labOrderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(labOrderMapper.toDto(entity)).thenReturn(dto);

        // When
        final LabOrderDto result = labOrderService.getLabOrderById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getLabOrderById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(labOrderRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> labOrderService.getLabOrderById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("LabOrder");
    }

    @Test
    void getLabOrdersByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final LabOrder entity = new LabOrder();
        final LabOrderDto dto = new LabOrderDto(UUID.randomUUID(), patientId,
                LabPanelType.BLOOD_GAS_ARTERIAL, LabOrderStatus.COLLECTED,
                null, null, null, null, null, null, Instant.now(), Instant.now());
        when(labOrderRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(labOrderMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<LabOrderDto> result = labOrderService.getLabOrdersByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getLabOrdersByPatientAndStatus_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final LabOrder entity = new LabOrder();
        final LabOrderDto dto = new LabOrderDto(UUID.randomUUID(), patientId,
                LabPanelType.COMPLETE_BLOOD_COUNT, LabOrderStatus.ORDERED,
                null, null, null, null, null, null, Instant.now(), Instant.now());
        when(labOrderRepository.findByPatientIdAndStatus(patientId, LabOrderStatus.ORDERED, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(labOrderMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<LabOrderDto> result = labOrderService.getLabOrdersByPatientAndStatus(
                patientId, LabOrderStatus.ORDERED, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).status()).isEqualTo(LabOrderStatus.ORDERED);
    }

    @Test
    void updateLabOrderStatus_existingId_updatesAndReturnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final LabOrder entity = new LabOrder();
        entity.setStatus(LabOrderStatus.ORDERED);
        final LabOrder saved = new LabOrder();
        final LabOrderDto dto = new LabOrderDto(id, UUID.randomUUID(),
                LabPanelType.COMPLETE_BLOOD_COUNT, LabOrderStatus.COLLECTED,
                null, null, null, null, null, null, Instant.now(), Instant.now());
        when(labOrderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(labOrderRepository.save(entity)).thenReturn(saved);
        when(labOrderMapper.toDto(saved)).thenReturn(dto);

        // When
        final LabOrderDto result = labOrderService.updateLabOrderStatus(id, LabOrderStatus.COLLECTED);

        // Then
        assertThat(result).isEqualTo(dto);
        assertThat(entity.getStatus()).isEqualTo(LabOrderStatus.COLLECTED);
        verify(labOrderRepository).save(entity);
    }

    @Test
    void updateLabOrderStatus_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(labOrderRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> labOrderService.updateLabOrderStatus(id, LabOrderStatus.CANCELLED))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("LabOrder");
    }
}
