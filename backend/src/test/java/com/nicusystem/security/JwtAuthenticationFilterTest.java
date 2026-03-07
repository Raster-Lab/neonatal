package com.nicusystem.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Tests for JwtAuthenticationFilter.
 */
@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @Mock
    private FilterChain filterChain;

    @Test
    void doFilterInternal_shouldPassThrough() throws ServletException, IOException {
        // Given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/patients");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        // When
        filter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotFilter_publicHealthPath_shouldReturnTrue() {
        // Given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/health");

        // When/Then
        assertThat(filter.shouldNotFilter(request)).isTrue();
    }

    @Test
    void shouldNotFilter_actuatorHealth_shouldReturnTrue() {
        // Given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/actuator/health");

        // When/Then
        assertThat(filter.shouldNotFilter(request)).isTrue();
    }

    @Test
    void shouldNotFilter_swaggerUi_shouldReturnTrue() {
        // Given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/swagger-ui/index.html");

        // When/Then
        assertThat(filter.shouldNotFilter(request)).isTrue();
    }

    @Test
    void shouldNotFilter_apiDocs_shouldReturnTrue() {
        // Given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api-docs/v3");

        // When/Then
        assertThat(filter.shouldNotFilter(request)).isTrue();
    }

    @Test
    void shouldNotFilter_protectedPath_shouldReturnFalse() {
        // Given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/patients");

        // When/Then
        assertThat(filter.shouldNotFilter(request)).isFalse();
    }
}
