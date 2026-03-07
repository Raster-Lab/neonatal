package com.nicusystem.audit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for AuditInterceptor.
 */
@ExtendWith(MockitoExtension.class)
class AuditInterceptorTest {

    @Mock
    private AuditService auditService;

    @InjectMocks
    private AuditInterceptor auditInterceptor;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void preHandle_authenticatedUser_shouldLogEvent() {
        // Given
        final var auth = new UsernamePasswordAuthenticationToken(
                "physician", "password", java.util.Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);

        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setRequestURI("/api/v1/patients");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final AuditEvent event = new AuditEvent();
        when(auditService.logEvent(any(), any(), any(), any(), any(), any())).thenReturn(event);

        // When
        final boolean result = auditInterceptor.preHandle(request, response, new Object());

        // Then
        assertThat(result).isTrue();
        verify(auditService).logEvent(
                eq("GET /api/v1/patients"),
                eq("API"),
                eq("/api/v1/patients"),
                eq("physician"),
                isNull(),
                eq("127.0.0.1")
        );
    }

    @Test
    void preHandle_anonymousUser_shouldLogEventWithAnonymous() {
        // Given
        SecurityContextHolder.clearContext();

        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/api/v1/health");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final AuditEvent event = new AuditEvent();
        when(auditService.logEvent(any(), any(), any(), any(), any(), any())).thenReturn(event);

        // When
        final boolean result = auditInterceptor.preHandle(request, response, new Object());

        // Then
        assertThat(result).isTrue();
        verify(auditService).logEvent(
                eq("POST /api/v1/health"),
                eq("API"),
                eq("/api/v1/health"),
                eq("anonymous"),
                isNull(),
                eq("127.0.0.1")
        );
    }

    @Test
    void preHandle_unauthenticatedUser_shouldLogEventWithAnonymous() {
        // Given - auth token without authorities is not authenticated
        final var auth = new UsernamePasswordAuthenticationToken("user", "pass");
        SecurityContextHolder.getContext().setAuthentication(auth);

        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("DELETE");
        request.setRequestURI("/api/v1/records");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final AuditEvent event = new AuditEvent();
        when(auditService.logEvent(any(), any(), any(), any(), any(), any())).thenReturn(event);

        // When
        final boolean result = auditInterceptor.preHandle(request, response, new Object());

        // Then
        assertThat(result).isTrue();
        verify(auditService).logEvent(
                eq("DELETE /api/v1/records"),
                eq("API"),
                eq("/api/v1/records"),
                eq("anonymous"),
                isNull(),
                eq("127.0.0.1")
        );
    }
}
