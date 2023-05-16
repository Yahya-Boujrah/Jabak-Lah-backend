package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Agent;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import exeption.ResourveNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AgentRepo agentRepository;
    private final ClientRepo clientRepository;

    public List<Agent> findAllAgents(){
        return agentRepository.findAll();

    }
    public Agent saveAgent(Agent agent){
        return agentRepository.save(agent);
    }

    public Agent findAgent(Long id){
        return agentRepository.findById(id).orElseThrow(()->new ResourveNotFound("Agent dosnt exist  with id :"+id));
    }

    public Agent updateAgent(Long id, Agent updatedAgent ){
        Agent agent = agentRepository.findById(id).orElseThrow(()->new ResourveNotFound("Agent dosnt exist  with id :"+id));

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

        return agentRepository.save(agent);
    }

    public Boolean deleteAgent(Long id){
        Agent agent = agentRepository.findById(id).orElseThrow(() -> new ResourveNotFound("Agent doesn't exist with id: " + id));
        agentRepository.delete(agent);

        return Boolean.TRUE;

    }
}
