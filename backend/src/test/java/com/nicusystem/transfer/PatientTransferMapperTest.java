package com.nicusystem.transfer;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTransferMapperTest {

    private final PatientTransferMapper mapper = new PatientTransferMapper();
    private final UUID patientId = UUID.randomUUID();
    private final Instant now = Instant.parse("2024-01-15T10:30:00Z");

    @Test
    void toDto_mapsAllFields() {
        // Given
        final PatientTransfer transfer = new PatientTransfer();
        transfer.setPatientId(patientId);
        transfer.setFromUnit("NICU A");
        transfer.setToUnit("NICU B");
        transfer.setFromFacility("Hospital A");
        transfer.setToFacility("Hospital B");
        transfer.setTransferType(PatientTransferType.EXTERNAL);
        transfer.setTransferReason("Higher level of care");
        transfer.setTransferredAt(now);
        transfer.setTransferredBy("Dr. Jones");
        transfer.setTransportMode("HELICOPTER");
        transfer.setNotes("Stable for transport");

        // When
        final PatientTransferDto dto = mapper.toDto(transfer);

        // Then
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.fromUnit()).isEqualTo("NICU A");
        assertThat(dto.toUnit()).isEqualTo("NICU B");
        assertThat(dto.fromFacility()).isEqualTo("Hospital A");
        assertThat(dto.toFacility()).isEqualTo("Hospital B");
        assertThat(dto.transferType()).isEqualTo(PatientTransferType.EXTERNAL);
        assertThat(dto.transferReason()).isEqualTo("Higher level of care");
        assertThat(dto.transferredAt()).isEqualTo(now);
        assertThat(dto.transferredBy()).isEqualTo("Dr. Jones");
        assertThat(dto.transportMode()).isEqualTo("HELICOPTER");
        assertThat(dto.notes()).isEqualTo("Stable for transport");
    }

    @Test
    void toEntity_mapsAllFields() {
        // Given
        final CreatePatientTransferRequest request = new CreatePatientTransferRequest(
                patientId, "NICU A", "NICU B",
                "Hospital A", "Hospital B",
                PatientTransferType.EXTERNAL,
                "Higher level of care", now,
                "Dr. Jones", "HELICOPTER", "Stable for transport");

        // When
        final PatientTransfer transfer = mapper.toEntity(request);

        // Then
        assertThat(transfer.getPatientId()).isEqualTo(patientId);
        assertThat(transfer.getFromUnit()).isEqualTo("NICU A");
        assertThat(transfer.getToUnit()).isEqualTo("NICU B");
        assertThat(transfer.getFromFacility()).isEqualTo("Hospital A");
        assertThat(transfer.getToFacility()).isEqualTo("Hospital B");
        assertThat(transfer.getTransferType()).isEqualTo(PatientTransferType.EXTERNAL);
        assertThat(transfer.getTransferReason()).isEqualTo("Higher level of care");
        assertThat(transfer.getTransferredAt()).isEqualTo(now);
        assertThat(transfer.getTransferredBy()).isEqualTo("Dr. Jones");
        assertThat(transfer.getTransportMode()).isEqualTo("HELICOPTER");
        assertThat(transfer.getNotes()).isEqualTo("Stable for transport");
    }
}
