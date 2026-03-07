package com.nicusystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for OpenApiConfig.
 */
class OpenApiConfigTest {

    @Test
    void nicuOpenApi_shouldReturnConfiguredOpenApi() {
        // Given
        final OpenApiConfig config = new OpenApiConfig();

        // When
        final OpenAPI openApi = config.nicuOpenApi();

        // Then
        assertThat(openApi).isNotNull();
        assertThat(openApi.getInfo().getTitle()).isEqualTo("NICU Management System API");
        assertThat(openApi.getInfo().getVersion()).isEqualTo("1.0.0");
        assertThat(openApi.getInfo().getDescription()).isNotNull();
        assertThat(openApi.getComponents().getSecuritySchemes()).containsKey("bearerAuth");
    }
}
