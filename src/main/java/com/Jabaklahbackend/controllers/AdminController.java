package com.Jabaklahbackend.controllers;



import com.Jabaklahbackend.entities.Agent;
import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import exeption.ResourveNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/admin")

@RestController
public class AdminController {
    @Autowired
    private AgentRepo agentRepository;
    @Autowired
    private ClientRepo clientRepository;

    @GetMapping("/findAllAgents")
    public List<Agent> findAllAgents(){
        return agentRepository.findAll();

    }
    @PostMapping("/saveAgent")
    public ResponseEntity<?> saveAgent(@Validated @RequestBody Agent agent){
        Agent savedAgent = agentRepository.save(agent);

        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .data(Map.of("agent", savedAgent))
                        .message("Agent created")
                        .build()
        );
    }

    @GetMapping("/getAgentById/{id}")
    public ResponseEntity <Agent> getAgentById( @PathVariable long id){
        Agent agent =agentRepository.findById(id).orElseThrow(()->new ResourveNotFound("Agent dosnt exist  with id :"+id));
        return ResponseEntity.ok(agent);
    }
    @PutMapping("/updateAgent/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable Long id, @RequestBody Agent updatedAgent) {
        Agent agent =agentRepository.findById(id).orElseThrow(()->new ResourveNotFound("Agent dosnt exist  with id :"+id));

        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        agent.setFirstName(updatedAgent.getFirstName());
        agent.setLastName(updatedAgent.getLastName());
        agent.setBirthDate(updatedAgent.getBirthDate());
        agent.setRole(updatedAgent.getRole());
        agent.setUsername(updatedAgent.getUsername());
        agent.setPassword(updatedAgent.getPassword());

        agent.setAddress(updatedAgent.getAddress());
        agent.setEmail(updatedAgent.getEmail());
        agent.setPhone(updatedAgent.getPhone());
        agent.setCin(updatedAgent.getCin());
        agent.setPatentNumber(updatedAgent.getPatentNumber());
        agent.setImmatricule(updatedAgent.getImmatricule());

        agentRepository.save(agent);
        return  ResponseEntity.ok(agent);
    }
    @DeleteMapping("/deletAgent/{id}")
    public ResponseEntity<Response> deleteAgent(@PathVariable long id) {
        Agent agent = agentRepository.findById(id).orElseThrow(() -> new ResourveNotFound("Agent doesn't exist with id: " + id));
        agentRepository.delete(agent);

        Response response = Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Agent deleted successfully")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Deleted", "true")
                .body(response);
    }


    @GetMapping("/findAllClients")
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @PostMapping("/saveClient")
    public ResponseEntity<?> saveClient(@Validated @RequestBody Client client) {
        clientRepository.save(client);

        Response response = Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Client saved successfully")
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isClientSaved", "true")
                .body(response);
    }



    @GetMapping("/getClientById/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client =clientRepository.findById(id).orElseThrow(()->new ResourveNotFound("Agent dosnt exist  with id :"+id));
        return ResponseEntity.ok(client);
    }

    @PutMapping("/updateClient/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Client client =clientRepository.findById(id).orElseThrow(()->new ResourveNotFound("Agent dosnt exist  with id :"+id));

        client.setFirstName(updatedClient.getFirstName());
        client.setLastName(updatedClient.getLastName());
        client.setBirthDate(updatedClient.getBirthDate());
        client.setAddress(updatedClient.getAddress());
        client.setEmail(updatedClient.getEmail());
        client.setPhone(updatedClient.getPhone());
        client.setCin(updatedClient.getCin());

        clientRepository.save(client);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<Response> deleteClient(@PathVariable Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourveNotFound("Agent doesn't exist with id: " + id));
        clientRepository.delete(client);

        Response response = Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Client deleted successfully")
                .build();

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(response);
    }

}


