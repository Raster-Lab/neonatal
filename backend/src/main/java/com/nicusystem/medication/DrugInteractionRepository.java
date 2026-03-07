package com.nicusystem.medication;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for DrugInteraction entities.
 */
@Repository
public interface DrugInteractionRepository
        extends JpaRepository<DrugInteraction, UUID> {

    /**
     * Finds an active interaction between two specific drugs (ordered).
     *
     * @param drug1Name first drug name
     * @param drug2Name second drug name
     * @return list of matching active interactions
     */
    List<DrugInteraction> findByDrug1NameIgnoreCaseAndDrug2NameIgnoreCaseAndActiveTrue(
            String drug1Name, String drug2Name);

    /**
     * Finds all active interactions involving the specified drug as drug1.
     *
     * @param drug1Name first drug name
     * @param pageable  pagination information
     * @return page of interactions
     */
    Page<DrugInteraction> findByDrug1NameIgnoreCaseAndActiveTrue(
            String drug1Name, Pageable pageable);

    /**
     * Finds all active interactions between two drugs regardless of order.
     *
     * @param drug1 first drug name
     * @param drug2 second drug name
     * @return list of matching active interactions
     */
    @Query("SELECT d FROM DrugInteraction d WHERE d.active = true AND "
            + "((LOWER(d.drug1Name) = LOWER(:drug1) AND LOWER(d.drug2Name) = LOWER(:drug2)) "
            + "OR (LOWER(d.drug1Name) = LOWER(:drug2) AND LOWER(d.drug2Name) = LOWER(:drug1)))")
    List<DrugInteraction> findInteractionBetween(
            @Param("drug1") String drug1, @Param("drug2") String drug2);

    /**
     * Finds all active drug interactions with pagination.
     *
     * @param pageable pagination information
     * @return page of active interactions
     */
    Page<DrugInteraction> findByActiveTrue(Pageable pageable);
}
