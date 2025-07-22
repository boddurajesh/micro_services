package com.rajesh.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/public/**").permitAll()
//                        .anyExchange().authenticated()
//                )
//                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);  // ✅ Only use this
//
//        return http.build();


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//
//        System.out.println("✅ Security config initialized!");
//      return http
////                .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll())
////                .csrf(ServerHttpSecurity.CsrfSpec::disable)
////                .build();
//
//              .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/auth/**").permitAll()// allow unauthenticated access to login service
//                        .pathMatchers("/login/oauth2/**").permitAll()
//                        .pathMatchers("/favicon.ico").permitAll()
//                        .pathMatchers("/webjars/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                        .anyExchange().authenticated()        // require authentication for all others
//                )
////                .oauth2ResourceServer(oauth2 -> oauth2
////                        .jwt(Customizer.withDefaults())       // enable JWT validation
//              .oauth2Login(Customizer.withDefaults()) // ✅ Handle login via browser
//              .oauth2ResourceServer(oauth2 -> oauth2
//                      .jwt(Customizer.withDefaults())     // ✅ Handle JWTs for API calls
//                )
//                .build();

        System.out.println("✅ Security config initialized!");

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/auth/**",
                                "/login/oauth2/**",
                                "/favicon.ico",
                                "/default-ui.css",              // ✅ add your static CSS
                                "/webjars/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/images/**",                   // ✅ optional: images
                                "/js/**",                       // ✅ optional: JS
                                "/css/**"                       // ✅ optional: catch static CSS
                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())              // for browser login
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())                 // for API JWT auth
                )
                .build();
    }
}