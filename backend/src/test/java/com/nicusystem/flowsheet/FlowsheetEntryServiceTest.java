package com.nicusystem.flowsheet;

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
 * Tests for FlowsheetEntryService.
 */
@ExtendWith(MockitoExtension.class)
class FlowsheetEntryServiceTest {

    @Mock
    private FlowsheetEntryRepository flowsheetEntryRepository;

    @Mock
    private FlowsheetEntryMapper flowsheetEntryMapper;

    @InjectMocks
    private FlowsheetEntryService flowsheetEntryService;

    @Test
    void create_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant entryTime = Instant.now();
        final CreateFlowsheetEntryRequest request =
                new CreateFlowsheetEntryRequest(
                        patientId, FlowsheetCategory.VITAL_SIGNS,
                        entryTime, "heart_rate", "145",
                        "nurse-001", null);
        final FlowsheetEntry entity = new FlowsheetEntry();
        final FlowsheetEntry saved = new FlowsheetEntry();
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                UUID.randomUUID(), patientId,
                FlowsheetCategory.VITAL_SIGNS,
                entryTime, "heart_rate", "145",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryMapper.toEntity(request)).thenReturn(entity);
        when(flowsheetEntryRepository.save(entity)).thenReturn(saved);
        when(flowsheetEntryMapper.toDto(saved)).thenReturn(dto);

        // When
        final FlowsheetEntryDto result =
                flowsheetEntryService.create(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(flowsheetEntryRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final FlowsheetEntry entity = new FlowsheetEntry();
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                id, UUID.randomUUID(),
                FlowsheetCategory.INTAKE_OUTPUT,
                Instant.now(), "iv_fluid_in", "25",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(flowsheetEntryMapper.toDto(entity)).thenReturn(dto);

        // When
        final FlowsheetEntryDto result =
                flowsheetEntryService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(flowsheetEntryRepository.findById(id))
                .thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> flowsheetEntryService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("FlowsheetEntry");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final FlowsheetEntry entity = new FlowsheetEntry();
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                UUID.randomUUID(), patientId,
                FlowsheetCategory.ASSESSMENT,
                Instant.now(), "skin_color", "Pink",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryRepository
                .findByPatientIdOrderByEntryTimeDesc(
                        patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(flowsheetEntryMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<FlowsheetEntryDto> result =
                flowsheetEntryService.getByPatient(
                        patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getByPatientAndCategory_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final FlowsheetEntry entity = new FlowsheetEntry();
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                UUID.randomUUID(), patientId,
                FlowsheetCategory.INTERVENTION,
                Instant.now(), "suction", "Performed",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryRepository
                .findByPatientIdAndCategoryOrderByEntryTimeDesc(
                        patientId,
                        FlowsheetCategory.INTERVENTION,
                        pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(flowsheetEntryMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<FlowsheetEntryDto> result =
                flowsheetEntryService.getByPatientAndCategory(
                        patientId,
                        FlowsheetCategory.INTERVENTION,
                        pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).category())
                .isEqualTo(FlowsheetCategory.INTERVENTION);
    }

    @Test
    void getByPatientAndTimeRange_shouldReturnList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2025-01-01T00:00:00Z");
        final Instant end = Instant.parse("2025-01-01T23:59:59Z");
        final FlowsheetEntry entity = new FlowsheetEntry();
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                UUID.randomUUID(), patientId,
                FlowsheetCategory.VITAL_SIGNS,
                Instant.parse("2025-01-01T12:00:00Z"),
                "temperature", "36.8",
                "nurse-001", null,
                Instant.now(), Instant.now());
        when(flowsheetEntryRepository
                .findByPatientIdAndEntryTimeBetweenOrderByEntryTimeAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity));
        when(flowsheetEntryMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<FlowsheetEntryDto> result =
                flowsheetEntryService.getByPatientAndTimeRange(
                        patientId, start, end);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }
}
