package com.nicusystem.insurance;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for PatientInsurance entities.
 */
@Repository
public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, UUID> {

    /**
     * Finds insurance records for a patient ordered by insurance type ascending.
     *
     * @param patientId the patient UUID
     * @return list of patient insurance records
     */
    List<PatientInsurance> findByPatientIdOrderByInsuranceTypeAsc(UUID patientId);

    /**
     * Finds insurance records for a patient filtered by insurance type.
     *
     * @param patientId     the patient UUID
     * @param insuranceType the insurance type
     * @return list of patient insurance records
     */
    List<PatientInsurance> findByPatientIdAndInsuranceType(
            UUID patientId, InsuranceType insuranceType);
}
