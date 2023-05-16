package com.Jabaklahbackend.controllers;


import com.Jabaklahbackend.payloads.AdminAuthRequest;
import com.Jabaklahbackend.payloads.AuthenticationResponse;
import com.Jabaklahbackend.security.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService service;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AdminAuthRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
