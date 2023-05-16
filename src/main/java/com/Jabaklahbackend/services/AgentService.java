package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.payloads.ClientRequest;
import com.Jabaklahbackend.repositories.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final ClientRepo clientRepo;

    public Client saveClient(ClientRequest request){

        Client client = Client.builder()
                .address(request.getAddress())
                .email(request.getEmail())
                .phone(request.getPhone())
                .cin(request.getCin())
                .balance(request.getBalance())
                .accountType(request.getAccountType())
                .build();

        client.setLastName(request.getLastName());
        client.setFirstName(request.getFirstName());
        client.setBirthDate(request.getBirthDate());
        client.setRole(request.getRole());
        client.setUsername(request.getUsername());
        client.setPassword(request.getPassword());

        return clientRepo.save(client);

    }

    public List<Client> getListClient() {
        return clientRepo.findAll();
    }

    public Client getClientByPhone(String phone){
        return clientRepo.findByPhone(phone).orElseThrow();
    }

    public Client updateClient(ClientRequest updatedClient, Long id){
        Client client = clientRepo.findById(id).get();

        client.setFirstName(updatedClient.getFirstName());
        client.setLastName(updatedClient.getLastName());
        client.setBirthDate(updatedClient.getBirthDate());
        client.setRole(updatedClient.getRole());
        client.setUsername(updatedClient.getUsername());
        client.setPassword(updatedClient.getPassword());

        client.setAddress(updatedClient.getAddress());
        client.setEmail(updatedClient.getEmail());
        client.setPhone(updatedClient.getPhone());
        client.setCin(updatedClient.getCin());
        client.setBalance(updatedClient.getBalance());
        client.setAccountType(updatedClient.getAccountType());


        return clientRepo.save(client);
    }

    public Boolean deleteClient(Long id){

        Client client = clientRepo.findById(id).orElseThrow();
        clientRepo.delete(client);
        return true;
    }
}
