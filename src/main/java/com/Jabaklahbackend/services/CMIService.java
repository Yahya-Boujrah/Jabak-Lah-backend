package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.repositories.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CMIService {

    private final ClientRepo clientRepo;

    public boolean chargeSolde(String phone, BigDecimal amount){
        if(amount == null){
            throw new IllegalStateException("cannot charge null amount");
        }

        Client client = clientRepo.findByPhone(phone).orElseThrow();
        BigDecimal newBalance = client.getBalance().add(amount);

        client.setBalance(newBalance);

        clientRepo.save(client);
        return Boolean.TRUE;
    }

}
