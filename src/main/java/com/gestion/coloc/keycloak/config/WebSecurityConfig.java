package com.gestion.coloc.keycloak.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String DEMANDEUR = "DEMANDEUR";
    public static final String PROPRIETAIRE = "PROPRIETAIRE";
    private final JwtAuthConverter jwtAuthConverter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/test/anonymous", "/test/anonymous/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/test/demandeur", "/test/demandeur/**").hasRole(DEMANDEUR)
                        .requestMatchers(HttpMethod.GET, "/test/proprietaire").hasAnyRole(PROPRIETAIRE)
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthConverter)
                        )
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> {
            web.ignoring().requestMatchers(
                    HttpMethod.POST,
                    "/public/**",
                    "/api/user",
                    "/api/categories/**",
                    "/api/expenses/**",
                    "/api/flatshare-applications/**",
                    "/api/flatshares/**",
                    "/api/tasks/**",
                    "/api/users/**"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.GET,
                    "/public/**",
                    "/api/categories/**",
                    "/api/expenses/**",
                    "/api/flatshare-applications/**",
                    "/api/flatshares/**",
                    "/api/tasks/**",
                    "/api/users/**",
                    "/api/users/login"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.DELETE,
                    "/public/**",
                    "/api/categories/**",
                    "/api/expenses/**",
                    "/api/flatshare-applications/**",
                    "/api/flatshares/**",
                    "/api/tasks/**",
                    "/api/users/**"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.PUT,
                    "/public/**",
                    "/api/categories/**",
                    "/api/expenses/**",
                    "/api/flatshare-applications/**",
                    "/api/flatshares/**",
                    "/api/tasks/**",
                    "/api/users/**"
            );
            web.ignoring().requestMatchers(
                            HttpMethod.OPTIONS,
                            "/**"
                    )
                    .requestMatchers("/v3/api-docs/**", "/configuration/**", "/swagger-ui/**",
                            "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**");

        };
    }

}