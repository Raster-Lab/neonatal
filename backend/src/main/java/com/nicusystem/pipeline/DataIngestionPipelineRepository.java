package com.nicusystem.pipeline;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for DataIngestionPipeline entities.
 */
@Repository
public interface DataIngestionPipelineRepository extends JpaRepository<DataIngestionPipeline, UUID> {

    /**
     * Finds pipelines for a patient ordered by creation time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of data ingestion pipelines
     */
    Page<DataIngestionPipeline> findByPatientIdOrderByCreatedAtDesc(UUID patientId, Pageable pageable);

    /**
     * Finds pipelines by their current status.
     *
     * @param status the pipeline status
     * @return list of data ingestion pipelines with the given status
     */
    List<DataIngestionPipeline> findByStatus(PipelineStatus status);
}
