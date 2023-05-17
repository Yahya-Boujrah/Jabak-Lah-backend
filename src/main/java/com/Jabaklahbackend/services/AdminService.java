package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Admin;
import com.Jabaklahbackend.entities.Agent;
import com.Jabaklahbackend.entities.Role;
import com.Jabaklahbackend.repositories.AdminRepo;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import exeption.ResourveNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AgentRepo agentRepository;
    private final AdminRepo adminRepo;

    public List<Admin> findAllAdmins(){
        return adminRepo.findAll();
    }

    public List<Agent> findAllAgents(){
        return agentRepository.findAll();

    }
    public Agent saveAgent(Agent agent){
        agent.setPassword(new BCryptPasswordEncoder().encode("123"));
        agent.setRole(Role.AGENT);
        return agentRepository.save(agent);
    }

    public Agent findAgent(Long id){
        return agentRepository.findById(id).orElseThrow(()->new ResourveNotFound("Agent dosnt exist  with id :"+id));
    }

    public Agent updateAgent(Agent updatedAgent ){
//        Agent agent = agentRepository.findById(id).orElseThrow(()->new ResourveNotFound("Agent dosnt exist  with id :"+id));
//
//        agent.setFirstName(updatedAgent.getFirstName());
//        agent.setLastName(updatedAgent.getLastName());
//        agent.setBirthDate(updatedAgent.getBirthDate());
//        agent.setRole(updatedAgent.getRole());
//        agent.setUsername(updatedAgent.getUsername());
//        agent.setPassword(updatedAgent.getPassword());
//
//        agent.setAddress(updatedAgent.getAddress());
//        agent.setEmail(updatedAgent.getEmail());
//        agent.setPhone(updatedAgent.getPhone());
//        agent.setCin(updatedAgent.getCin());
//        agent.setPatentNumber(updatedAgent.getPatentNumber());
//        agent.setImmatricule(updatedAgent.getImmatricule());

        return agentRepository.save(updatedAgent);
    }

    public Boolean deleteAgent(Long id){
        Agent agent = agentRepository.findById(id).orElseThrow(() -> new ResourveNotFound("Agent doesn't exist with id: " + id));
        agentRepository.delete(agent);

        return Boolean.TRUE;

    }
}
