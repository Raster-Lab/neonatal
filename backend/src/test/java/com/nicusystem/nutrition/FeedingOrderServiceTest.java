package com.nicusystem.nutrition;

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
 * Tests for {@link FeedingOrderService}.
 */
@ExtendWith(MockitoExtension.class)
class FeedingOrderServiceTest {

    @Mock
    private FeedingOrderRepository feedingOrderRepository;

    @Mock
    private FeedingOrderMapper feedingOrderMapper;

    @InjectMocks
    private FeedingOrderService feedingOrderService;

    @Test
    void createFeedingOrder_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateFeedingOrderRequest request = new CreateFeedingOrderRequest(
                patientId, FeedingType.BREAST_MILK, FeedingRoute.NASOGASTRIC,
                30.0, 3, null, null, null);
        final FeedingOrder entity = new FeedingOrder();
        final FeedingOrder saved = new FeedingOrder();
        final FeedingOrderDto dto = new FeedingOrderDto(UUID.randomUUID(), patientId,
                FeedingType.BREAST_MILK, FeedingRoute.NASOGASTRIC, 30.0, 3,
                null, null, null, null, null, null);
        when(feedingOrderMapper.toEntity(request)).thenReturn(entity);
        when(feedingOrderRepository.save(entity)).thenReturn(saved);
        when(feedingOrderMapper.toDto(saved)).thenReturn(dto);

        // When
        final FeedingOrderDto result = feedingOrderService.createFeedingOrder(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(feedingOrderRepository).save(entity);
    }

    @Test
    void getFeedingOrderById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final FeedingOrder entity = new FeedingOrder();
        final FeedingOrderDto dto = new FeedingOrderDto(id, UUID.randomUUID(),
                FeedingType.FORMULA, FeedingRoute.ORAL, 20.0, 4,
                null, null, null, null, null, null);
        when(feedingOrderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(feedingOrderMapper.toDto(entity)).thenReturn(dto);

        // When
        final FeedingOrderDto result = feedingOrderService.getFeedingOrderById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getFeedingOrderById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(feedingOrderRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> feedingOrderService.getFeedingOrderById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("FeedingOrder");
    }

    @Test
    void getFeedingOrdersByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final FeedingOrder entity = new FeedingOrder();
        final FeedingOrderDto dto = new FeedingOrderDto(UUID.randomUUID(), patientId,
                FeedingType.BREAST_MILK, FeedingRoute.NASOGASTRIC, 30.0, 3,
                null, null, null, null, null, null);
        when(feedingOrderRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(feedingOrderMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<FeedingOrderDto> result =
                feedingOrderService.getFeedingOrdersByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getFeedingOrdersByPatientAndType_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final FeedingOrder entity = new FeedingOrder();
        final FeedingOrderDto dto = new FeedingOrderDto(UUID.randomUUID(), patientId,
                FeedingType.DONOR_MILK, FeedingRoute.NASOGASTRIC, 25.0, 3,
                null, null, null, null, null, null);
        when(feedingOrderRepository.findByPatientIdAndFeedingType(
                patientId, FeedingType.DONOR_MILK, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(feedingOrderMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<FeedingOrderDto> result =
                feedingOrderService.getFeedingOrdersByPatientAndType(
                        patientId, FeedingType.DONOR_MILK, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).feedingType()).isEqualTo(FeedingType.DONOR_MILK);
    }

    @Test
    void discontinueFeedingOrder_existingId_shouldSetDiscontinuedAtAndReturn() {
        // Given
        final UUID id = UUID.randomUUID();
        final FeedingOrder entity = new FeedingOrder();
        final FeedingOrder saved = new FeedingOrder();
        final FeedingOrderDto dto = new FeedingOrderDto(id, UUID.randomUUID(),
                FeedingType.BREAST_MILK, FeedingRoute.NASOGASTRIC, 30.0, 3,
                null, Instant.now(), null, null, null, null);
        when(feedingOrderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(feedingOrderRepository.save(entity)).thenReturn(saved);
        when(feedingOrderMapper.toDto(saved)).thenReturn(dto);

        // When
        final FeedingOrderDto result = feedingOrderService.discontinueFeedingOrder(id);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(feedingOrderRepository).save(entity);
        assertThat(entity.getDiscontinuedAt()).isNotNull();
    }

    @Test
    void discontinueFeedingOrder_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(feedingOrderRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> feedingOrderService.discontinueFeedingOrder(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("FeedingOrder");
    }
}
