package com.Jabaklahbackend.security.auth;

import com.Jabaklahbackend.entities.User;
import com.Jabaklahbackend.payloads.AdminAuthRequest;
import com.Jabaklahbackend.payloads.AuthenticationResponse;
import com.Jabaklahbackend.repositories.AdminRepo;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    AdminRepo adminRepo;

    @Autowired
    AgentRepo agentRepo;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AdminAuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = agentRepo.findByUsername(request.getUsername().split(":")[0]).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .user(user)
                .token(jwtToken)
                .build();
    }
}