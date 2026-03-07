package com.nicusystem.insurance;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between PatientInsurance entities and DTOs.
 */
@Component
public class PatientInsuranceMapper {

    /**
     * Converts a PatientInsurance entity to a PatientInsuranceDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public PatientInsuranceDto toDto(final PatientInsurance entity) {
        return new PatientInsuranceDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getInsuranceType(),
                entity.getInsurerName(),
                entity.getPolicyNumber(),
                entity.getGroupNumber(),
                entity.getSubscriberName(),
                entity.getSubscriberDob(),
                entity.getRelationshipToPatient(),
                entity.getEffectiveDate(),
                entity.getTerminationDate(),
                entity.getNotes(),
                entity.isActive()
        );
    }

    /**
     * Converts a CreatePatientInsuranceRequest to a PatientInsurance entity.
     *
     * @param request the creation request
     * @return the PatientInsurance entity
     */
    public PatientInsurance toEntity(final CreatePatientInsuranceRequest request) {
        final PatientInsurance insurance = new PatientInsurance();
        insurance.setPatientId(request.patientId());
        insurance.setInsuranceType(request.insuranceType());
        insurance.setInsurerName(request.insurerName());
        insurance.setPolicyNumber(request.policyNumber());
        insurance.setGroupNumber(request.groupNumber());
        insurance.setSubscriberName(request.subscriberName());
        insurance.setSubscriberDob(request.subscriberDob());
        insurance.setRelationshipToPatient(request.relationshipToPatient());
        insurance.setEffectiveDate(request.effectiveDate());
        insurance.setTerminationDate(request.terminationDate());
        insurance.setNotes(request.notes());
        return insurance;
    }
}
