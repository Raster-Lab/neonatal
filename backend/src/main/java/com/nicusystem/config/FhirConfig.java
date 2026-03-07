package com.nicusystem.config;

import ca.uhn.fhir.context.FhirContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FHIR context configuration for HL7 FHIR R4 interoperability.
 */
@Configuration
public class FhirConfig {

    /**
     * Creates a FHIR R4 context bean.
     *
     * @return the FHIR context configured for R4
     */
    @Bean
    public FhirContext fhirContext() {
        return FhirContext.forR4();
    }
}
