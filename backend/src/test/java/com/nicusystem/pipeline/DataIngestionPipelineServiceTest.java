package com.nicusystem.pipeline;

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
 * Tests for DataIngestionPipelineService.
 */
@ExtendWith(MockitoExtension.class)
class DataIngestionPipelineServiceTest {

    @Mock
    private DataIngestionPipelineRepository dataIngestionPipelineRepository;

    @Mock
    private DataIngestionPipelineMapper dataIngestionPipelineMapper;

    @InjectMocks
    private DataIngestionPipelineService dataIngestionPipelineService;

    @Test
    void create_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateDataIngestionPipelineRequest request = new CreateDataIngestionPipelineRequest(
                patientId, MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.ACTIVE, "tcp://192.168.1.100:5000", 5,
                null, null);
        final DataIngestionPipeline entity = new DataIngestionPipeline();
        final DataIngestionPipeline saved = new DataIngestionPipeline();
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                UUID.randomUUID(), patientId, MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.ACTIVE, "tcp://192.168.1.100:5000", 5,
                null, null);
        when(dataIngestionPipelineMapper.toEntity(request)).thenReturn(entity);
        when(dataIngestionPipelineRepository.save(entity)).thenReturn(saved);
        when(dataIngestionPipelineMapper.toDto(saved)).thenReturn(dto);

        // When
        final DataIngestionPipelineDto result = dataIngestionPipelineService.create(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(dataIngestionPipelineRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final DataIngestionPipeline entity = new DataIngestionPipeline();
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                id, UUID.randomUUID(), MonitorDataSource.PULSE_OXIMETER, "OX-042",
                PipelineStatus.ACTIVE, "tcp://192.168.1.50:4000", 10,
                Instant.now(), null);
        when(dataIngestionPipelineRepository.findById(id)).thenReturn(Optional.of(entity));
        when(dataIngestionPipelineMapper.toDto(entity)).thenReturn(dto);

        // When
        final DataIngestionPipelineDto result = dataIngestionPipelineService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(dataIngestionPipelineRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> dataIngestionPipelineService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("DataIngestionPipeline");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final DataIngestionPipeline entity = new DataIngestionPipeline();
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                UUID.randomUUID(), patientId, MonitorDataSource.VENTILATOR, "VENT-007",
                PipelineStatus.ACTIVE, "tcp://10.0.0.1:3000", 30,
                null, null);
        when(dataIngestionPipelineRepository.findByPatientIdOrderByCreatedAtDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(dataIngestionPipelineMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<DataIngestionPipelineDto> result =
                dataIngestionPipelineService.getByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getActiveByStatus_shouldReturnList() {
        // Given
        final DataIngestionPipeline entity = new DataIngestionPipeline();
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                UUID.randomUUID(), UUID.randomUUID(), MonitorDataSource.TEMPERATURE_PROBE, "TP-003",
                PipelineStatus.ACTIVE, "tcp://10.0.0.2:4000", 10,
                null, null);
        when(dataIngestionPipelineRepository.findByStatus(PipelineStatus.ACTIVE))
                .thenReturn(List.of(entity));
        when(dataIngestionPipelineMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<DataIngestionPipelineDto> result =
                dataIngestionPipelineService.getActiveByStatus(PipelineStatus.ACTIVE);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }

    @Test
    void updateStatus_existingId_returnsUpdatedDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final DataIngestionPipeline entity = new DataIngestionPipeline();
        final DataIngestionPipeline saved = new DataIngestionPipeline();
        final DataIngestionPipelineDto expectedDto = new DataIngestionPipelineDto(
                id, patientId, MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.PAUSED, "tcp://192.168.1.100:5000", 5,
                null, null);
        when(dataIngestionPipelineRepository.findById(id)).thenReturn(Optional.of(entity));
        when(dataIngestionPipelineRepository.save(entity)).thenReturn(saved);
        when(dataIngestionPipelineMapper.toDto(saved)).thenReturn(expectedDto);

        // When
        final DataIngestionPipelineDto result =
                dataIngestionPipelineService.updateStatus(id, PipelineStatus.PAUSED);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(dataIngestionPipelineRepository).save(entity);
    }

    @Test
    void updateStatus_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(dataIngestionPipelineRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> dataIngestionPipelineService.updateStatus(id, PipelineStatus.ERROR))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("DataIngestionPipeline");
    }
}
