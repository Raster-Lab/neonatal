package com.nicusystem.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.FhirVersionEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FhirConfig.
 */
class FhirConfigTest {

    @Test
    void fhirContext_shouldReturnR4Context() {
        // Given
        final FhirConfig config = new FhirConfig();

        // When
        final FhirContext context = config.fhirContext();

        // Then
        assertThat(context).isNotNull();
        assertThat(context.getVersion().getVersion()).isEqualTo(FhirVersionEnum.R4);
    }
}
