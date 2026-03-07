package com.nicusystem.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Implementation of AuditorAware that returns the current authenticated user.
 */
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor (authenticated username or "system").
     *
     * @return the current auditor
     */
    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        final Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.of("system");
        }
        return Optional.of(authentication.getName());
    }
}
