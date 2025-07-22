package com.rajesh.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;

import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;


    @Configuration
    public class OAuth2ClientConfig {

        @Bean
        public ReactiveOAuth2AuthorizedClientManager reactiveOAuth2AuthorizedClientManager(
                ReactiveClientRegistrationRepository clientRegistrationRepository,
                ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {

            ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
                    ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                            .authorizationCode()
                            .refreshToken()
                            .build();

            DefaultReactiveOAuth2AuthorizedClientManager manager =
                    new DefaultReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);

            manager.setAuthorizedClientProvider(authorizedClientProvider);

            return manager;
        }
    }

