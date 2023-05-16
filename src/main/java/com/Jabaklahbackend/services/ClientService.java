package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.Prospect;
import com.Jabaklahbackend.payloads.ProspectRequest;
import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.ProspectRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ProspectRepo prospectRepo;
    private final ClientRepo clientRepo;

    public Prospect saveProspect(ProspectRequest request){
        Prospect prospect = Prospect.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .cin(request.getCin())
                .email(request.getEmail())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();

        return prospectRepo.save(prospect);
    }
    public Client getInfos(){
        String currentUserPhone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientRepo.findByPhone(currentUserPhone).orElseThrow();

    }

}
