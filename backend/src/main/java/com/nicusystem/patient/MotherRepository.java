package com.nicusystem.patient;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Mother entities.
 */
@Repository
public interface MotherRepository extends JpaRepository<Mother, UUID> {

    /**
     * Finds all active mother records.
     *
     * @param pageable pagination information
     * @return page of active mothers
     */
    Page<Mother> findByActiveTrue(Pageable pageable);
}
