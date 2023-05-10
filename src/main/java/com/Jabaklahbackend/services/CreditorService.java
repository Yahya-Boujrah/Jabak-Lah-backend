package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Creditor;
import com.Jabaklahbackend.payloads.CreditorRequest;
import com.Jabaklahbackend.repositories.CreditorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditorService {
    private final CreditorRepo creditorRepo;

    public List listCreditors(){
        return creditorRepo.findAll();
    }

    public void addCreditor(CreditorRequest request){
            Creditor creditor = Creditor.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .image(request.getImage())
                    .build();

            creditorRepo.save(creditor);
    }
}
