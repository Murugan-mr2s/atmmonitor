package com.example.controller;

import com.example.config.AtmClient;
import com.example.controller.response.AuthTokenResponse;
import com.example.service.JwtAuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final  JwtAuthService authService;

    public UserController(JwtAuthService authService) {
        this.authService = authService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/user-token")
    public AuthTokenResponse genUserToken(Authentication authentication) {
        return authService.generateUserToken(authentication);
    }

    @PreAuthorize("{hasRole('ADMIN')}")
    @PostMapping("/atm-token")
    public AuthTokenResponse genMachineToken(@RequestBody AtmClient atmClient) {
        return authService.generateATMToken(atmClient);
    }

}
