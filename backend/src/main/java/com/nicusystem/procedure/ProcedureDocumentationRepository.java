package com.nicusystem.procedure;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ProcedureDocumentation entities.
 */
@Repository
public interface ProcedureDocumentationRepository
        extends JpaRepository<ProcedureDocumentation, UUID> {

    /**
     * Finds procedure documentation for a patient ordered by performed time descending.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of procedure documentation records
     */
    Page<ProcedureDocumentation> findByPatientIdOrderByPerformedAtDesc(
            UUID patientId, Pageable pageable);

    /**
     * Finds procedure documentation for a patient filtered by type, ordered descending.
     *
     * @param patientId     the patient UUID
     * @param procedureType the procedure type
     * @param pageable      pagination information
     * @return page of procedure documentation records
     */
    Page<ProcedureDocumentation>
            findByPatientIdAndProcedureTypeOrderByPerformedAtDesc(
                    UUID patientId, ProcedureType procedureType,
                    Pageable pageable);
}
