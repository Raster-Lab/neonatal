package com.nicusystem.vitals.autodoc;

import java.util.UUID;

import com.nicusystem.vitals.VitalSignType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateAutoDocConfigRequestTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CreateAutoDocConfigRequest request = new CreateAutoDocConfigRequest(
                patientId, VitalSignType.TEMPERATURE,
                AutoDocInterval.EVERY_15_MINUTES, "Fever monitoring");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.vitalType()).isEqualTo(VitalSignType.TEMPERATURE);
        assertThat(request.interval()).isEqualTo(AutoDocInterval.EVERY_15_MINUTES);
        assertThat(request.notes()).isEqualTo("Fever monitoring");
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateAutoDocConfigRequest req1 = new CreateAutoDocConfigRequest(
                patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, null);
        final CreateAutoDocConfigRequest req2 = new CreateAutoDocConfigRequest(
                patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, null);

        // When & Then
        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final CreateAutoDocConfigRequest request = new CreateAutoDocConfigRequest(
                null, null, null, null);

        // When & Then
        assertThat(request.toString()).contains("CreateAutoDocConfigRequest");
    }
}
