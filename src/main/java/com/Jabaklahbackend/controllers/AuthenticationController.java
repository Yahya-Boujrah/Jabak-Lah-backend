package com.Jabaklahbackend.controllers;


import com.Jabaklahbackend.payloads.AdminAuthRequest;
import com.Jabaklahbackend.payloads.AuthenticationResponse;
import com.Jabaklahbackend.payloads.ProspectRequest;
import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.security.auth.AuthenticationService;
import com.Jabaklahbackend.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    public final AuthenticationService service;
    public final ClientService clientService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AdminAuthRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody ProspectRequest request){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .data(Map.of("prospect",clientService.saveProspect(request)))
                        .message("prospect saved")
                        .build()
        );

    }
}
