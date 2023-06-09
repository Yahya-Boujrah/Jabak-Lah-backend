package com.Jabaklahbackend.security.auth;

import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.User;
import com.Jabaklahbackend.payloads.AdminAuthRequest;
import com.Jabaklahbackend.payloads.AuthenticationResponse;
import com.Jabaklahbackend.repositories.AdminRepo;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.DeliveryManRepo;
import com.Jabaklahbackend.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    ClientRepo clientRepo;

    @Autowired
    DeliveryManRepo deliveryManRepo;

    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AdminAuthRequest request) {
        
        System.out.println(request.getUsername() + " " + request.getPassword());
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = new User();

        switch (request.getUsername().split(":")[1]){
            case "ADMIN" :
                user = adminRepo.findByUsername(request.getUsername().split(":")[0]).orElseThrow();
                break;
            case "AGENT" :
                user = agentRepo.findByUsername(request.getUsername().split(":")[0]).orElseThrow();
                break;
            case "CLIENT" :
                user = clientRepo.findByPhone(request.getUsername().split(":")[0]).orElseThrow();
                break;
            case "LIVREUR" :
                user = deliveryManRepo.findByUsername(request.getUsername().split(":")[0]).orElseThrow();
                break;
            default:
                throw new UsernameNotFoundException("User not found");
        }

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .user(user)
                .token(jwtToken)
                .build();
    }


    public Boolean isPasswordChanged(){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();
        return client.isPasswordChanged();
    }

}
