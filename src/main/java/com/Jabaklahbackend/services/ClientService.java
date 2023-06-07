package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Agent;
import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.Prospect;
import com.Jabaklahbackend.payloads.ChangePasswordRequest;
import com.Jabaklahbackend.payloads.ProspectRequest;
import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.ProspectRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ProspectRepo prospectRepo;
    private final ClientRepo clientRepo;

    private final PasswordEncoder passwordEncoder;

    public Prospect saveProspect(ProspectRequest request){

        if (prospectRepo.existsByPhone(request.getPhone())) throw new IllegalStateException("prospect already exists");

        if (clientRepo.existsByPhone(request.getPhone())) throw new IllegalStateException("client already exists");

        Prospect prospect = Prospect.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .cin(request.getCin())
                .email(request.getEmail())
                .username(request.getUsername())
                .type(request.getType())
                .phone(request.getPhone())
                .build();

        return prospectRepo.save(prospect);
    }
    public Client getInfos(){
        String currentUserPhone = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        return clientRepo.findByPhone(currentUserPhone.split(":")[0]).orElseThrow();

    }


    public Boolean changePassword(String password){
        String phone  = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();

        client.setPassword(passwordEncoder.encode(password));
        client.setPasswordChanged(Boolean.TRUE);
        clientRepo.save(client);

        return Boolean.TRUE;
    }
}
