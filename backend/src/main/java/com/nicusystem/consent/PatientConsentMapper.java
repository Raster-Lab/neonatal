package com.nicusystem.consent;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between PatientConsent entities and DTOs.
 */
@Component
public class PatientConsentMapper {

    /**
     * Converts a PatientConsent entity to a PatientConsentDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public PatientConsentDto toDto(final PatientConsent entity) {
        return new PatientConsentDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getConsentType(),
                entity.getConsentStatus(),
                entity.getSignedBy(),
                entity.getRelationship(),
                entity.getSignedAt(),
                entity.getExpiresAt(),
                entity.getNotes(),
                entity.isActive()
        );
    }

    /**
     * Converts a CreatePatientConsentRequest to a PatientConsent entity.
     *
     * @param request the creation request
     * @return the PatientConsent entity
     */
    public PatientConsent toEntity(final CreatePatientConsentRequest request) {
        final PatientConsent consent = new PatientConsent();
        consent.setPatientId(request.patientId());
        consent.setConsentType(request.consentType());
        consent.setConsentStatus(request.consentStatus());
        consent.setSignedBy(request.signedBy());
        consent.setRelationship(request.relationship());
        consent.setSignedAt(request.signedAt());
        consent.setExpiresAt(request.expiresAt());
        consent.setNotes(request.notes());
        return consent;
    }
}
