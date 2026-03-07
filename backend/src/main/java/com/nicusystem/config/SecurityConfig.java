package com.nicusystem.config;

import java.util.List;

import com.nicusystem.security.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Security configuration for the NICU system.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String allowedOrigins;

    /**
     * Creates a new SecurityConfig.
     *
     * @param allowedOrigins CORS allowed origins from configuration
     */
    public SecurityConfig(
            @Value("${app.cors.allowed-origins}") final String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/actuator/health",
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/api/v1/health"
                ).permitAll()
                .anyRequest().authenticated());
        http.httpBasic(basic -> { });

        return http.build();
    }

    /**
     * Provides an in-memory user details service for development.
     *
     * @param passwordEncoder the password encoder
     * @return the user details service
     */
    @Bean
    public UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
        final var physician = User.builder()
                .username("physician")
                .password(passwordEncoder.encode("password"))
                .roles(Role.PHYSICIAN.name())
                .build();

        final var nurse = User.builder()
                .username("nurse")
                .password(passwordEncoder.encode("password"))
                .roles(Role.NURSE.name())
                .build();

        final var pharmacist = User.builder()
                .username("pharmacist")
                .password(passwordEncoder.encode("password"))
                .roles(Role.PHARMACIST.name())
                .build();

        final var parent = User.builder()
                .username("parent")
                .password(passwordEncoder.encode("password"))
                .roles(Role.PARENT.name())
                .build();

        final var admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles(Role.ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(physician, nurse, pharmacist, parent, admin);
    }

    /**
     * Provides a BCrypt password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides CORS configuration.
     *
     * @return the CORS configuration source
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
