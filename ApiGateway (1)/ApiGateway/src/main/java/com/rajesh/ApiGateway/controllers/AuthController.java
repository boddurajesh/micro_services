package com.rajesh.ApiGateway.controllers;


import com.rajesh.ApiGateway.models.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ReactiveOAuth2AuthorizedClientManager authorizedClientManager;

    public AuthController(ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @GetMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(
            ServerWebExchange exchange,
            OAuth2AuthenticationToken authentication) {

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId(
                        authentication.getAuthorizedClientRegistrationId())
                .principal(authentication)
                .attribute(ServerWebExchange.class.getName(), exchange)
                .build();

        return authorizedClientManager.authorize(authorizeRequest)
                .map(client -> {
                    OidcUser user = (OidcUser) authentication.getPrincipal();

                    AuthResponse response = new AuthResponse();
                    response.setUserId(user.getSubject());
                    response.setAccessToken(client.getAccessToken().getTokenValue());
                    response.setRefreshToken(client.getRefreshToken() != null
                            ? client.getRefreshToken().getTokenValue() : null);
                    response.setExpireAt(client.getAccessToken().getExpiresAt().toEpochMilli());
                    response.setAuthorities(authentication.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()));

                    return ResponseEntity.ok(response);
                });
    }


}
