package com.nicusystem.nutrition;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link BreastMilkInventory} entities.
 */
public interface BreastMilkInventoryRepository extends JpaRepository<BreastMilkInventory, UUID> {

    /**
     * Returns a page of breast milk inventory entries for the given patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of breast milk inventory entries
     */
    Page<BreastMilkInventory> findByPatientId(UUID patientId, Pageable pageable);

    /**
     * Returns a page of breast milk inventory entries for the given patient
     * filtered by whether the milk is donor milk.
     *
     * <p>Note: the JPA derived query uses the field name {@code donorMilk}
     * (not {@code isDonorMilk}) which maps to the column {@code is_donor_milk}.</p>
     *
     * @param patientId the patient UUID
     * @param donorMilk {@code true} to return donor milk entries; {@code false} for own milk
     * @param pageable  pagination information
     * @return page of matching breast milk inventory entries
     */
    Page<BreastMilkInventory> findByPatientIdAndDonorMilk(
            UUID patientId, boolean donorMilk, Pageable pageable);
}
