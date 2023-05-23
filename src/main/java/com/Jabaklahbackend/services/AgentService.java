package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.Role;
import com.Jabaklahbackend.payloads.ClientRequest;
import com.Jabaklahbackend.repositories.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final ClientRepo clientRepo;

    public Client saveClient(ClientRequest request){

        Client client = Client.builder()
                .balance(BigDecimal.ZERO)
                .accountType(request.getAccountType())
                .build();

        client.setPhone(request.getPhone());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setCin(request.getCin());
        client.setFirstName(request.getFirstName());
        client.setBirthDate(request.getBirthDate());
        client.setRole(Role.CLIENT);
        client.setUsername(request.getUsername());
        client.setPassword(request.getPassword());
        client.setPassword(new BCryptPasswordEncoder().encode("123"));

        return clientRepo.save(client);

    }

    public List<Client> getListClient() {
        return clientRepo.findAll();
    }

    public Client getClientByPhone(String phone){
        return clientRepo.findByPhone(phone).orElseThrow();
    }

    public Client updateClient(Client updatedClient, Long id){

        Client client = clientRepo.findById(id).orElseThrow();
        client.setFirstName(updatedClient.getFirstName());
        client.setLastName(updatedClient.getLastName());
        client.setBirthDate(updatedClient.getBirthDate());
        client.setUsername(updatedClient.getUsername());

        client.setEmail(updatedClient.getEmail());
        client.setPhone(updatedClient.getPhone());
        client.setCin(updatedClient.getCin());
        client.setAccountType(updatedClient.getAccountType());

        return clientRepo.save(client);
    }

    public Boolean deleteClient(Long id){

        Client client = clientRepo.findById(id).orElseThrow();
        clientRepo.delete(client);
        return true;
    }
}
