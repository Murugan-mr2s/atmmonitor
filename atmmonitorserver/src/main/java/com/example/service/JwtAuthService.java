package com.example.service;

import com.example.config.AtmClient;
import com.example.controller.response.AuthTokenResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class JwtAuthService {

    private final JwtEncoder jwtEncoder;
    private  final InMemoryUserDetailsManager manager;

    public JwtAuthService(JwtEncoder jwtEncoder,InMemoryUserDetailsManager manager) {
        this.jwtEncoder = jwtEncoder;
        this.manager = manager;
    }

    public AuthTokenResponse generateATMToken(AtmClient atmClient) {

        manager.createUser(
                User.withUsername(atmClient.atmid())
                        .password("{noop}atm123"+atmClient.bank())
                        .roles("ATM").build() );
        Authentication  authentication=new UsernamePasswordAuthenticationToken(atmClient.atmid(), null,
                                        manager.loadUserByUsername(atmClient.atmid()).getAuthorities());
        return  new AuthTokenResponse(generateToken(authentication,atmClient.nvalidDays())) ;
    }

    public AuthTokenResponse generateUserToken( Authentication authentication) {
            return  new AuthTokenResponse(
                    generateToken(authentication,30));
    }

    private String generateToken(Authentication authentication,int validDays) {

        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("admin")
                .issuedAt(now)
                .expiresAt(now.plus(validDays, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();


        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

}
