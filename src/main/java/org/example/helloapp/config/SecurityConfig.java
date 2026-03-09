package org.example.helloapp.config;

import lombok.RequiredArgsConstructor;
import org.example.helloapp.exception.UnauthorisedException;
import org.example.helloapp.util.JwtAuthenticationFilter;
import org.example.helloapp.util.RateLimitFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final RateLimitFilter rateLimitFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws UnauthorisedException { // Configure security settings

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/uploads/**",
                                "/",
                                "/home",
                                "/post",
                                "/profile",
                                "/chat",
                                "/login",
                                "/verify",
                                "/postView",
                                "/guestProfile",
                                "/chatApp/**"

                        ).permitAll()

                        .anyRequest().authenticated()
                ).addFilterBefore(
                        rateLimitFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}