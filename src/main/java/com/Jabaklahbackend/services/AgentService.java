package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.Prospect;
import com.Jabaklahbackend.entities.Role;
import com.Jabaklahbackend.payloads.ClientRequest;
import com.Jabaklahbackend.payloads.ProspectRequest;
import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.ProspectRepo;
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
    private final ProspectRepo prospectRepo;

    public Client saveClient(ClientRequest request){

        if (clientRepo.existsByPhone(request.getPhone())) throw new IllegalStateException("client already exists");

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
        return Boolean.TRUE;
    }

    public List<Prospect> getListProspects() {
        return prospectRepo.findAll();
    }

    public Client convertProspectToClient(ProspectRequest request){
        Client client = new Client();
        client.setAccountType(request.getType());
        client.setBalance(BigDecimal.ZERO);
        client.setPhone(request.getPhone());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setCin(request.getCin());
        client.setFirstName(request.getFirstName());
        client.setRole(Role.CLIENT);
        client.setUsername(request.getUsername());
        client.setPassword(new BCryptPasswordEncoder().encode("123"));

        clientRepo.save(client);
        prospectRepo.delete(prospectRepo.findByPhone(request.getPhone()).orElseThrow());

        return client;
    }

    public Boolean deleteProspect(Long id){

        Prospect prospect = prospectRepo.findById(id).orElseThrow();
        prospectRepo.delete(prospect);
        return Boolean.TRUE;
    }
}
