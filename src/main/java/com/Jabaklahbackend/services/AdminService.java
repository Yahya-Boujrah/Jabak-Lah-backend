package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Admin;
import com.Jabaklahbackend.entities.Agent;
import com.Jabaklahbackend.entities.Role;
import com.Jabaklahbackend.payloads.ChangePasswordRequest;
import com.Jabaklahbackend.repositories.AdminRepo;
import com.Jabaklahbackend.repositories.AgentRepo;
import exception.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AgentRepo agentRepository;
    private final AdminRepo adminRepo;

    private final PasswordEncoder passwordEncoder;
    public List<Admin> findAllAdmins(){
        return adminRepo.findAll();
    }

    public List<Agent> findAllAgents(){
        return agentRepository.findAll();

    }
    public Agent saveAgent(Agent agent){

        if (agentRepository.existsByUsername(agent.getUsername())) throw new IllegalStateException("agent already exists");

        agent.setPassword(new BCryptPasswordEncoder().encode("123"));
        agent.setRole(Role.AGENT);
        return agentRepository.save(agent);
    }

    public Agent findAgent(Long id){
        return agentRepository.findById(id).orElseThrow(()->new ResourceNotFound("Agent dosnt exist  with id :"+id));
    }

    public Agent updateAgent(Agent updatedAgent, Long id ){
        System.out.println("id  "+ updatedAgent.getId());
        System.out.println("id  "+ id);
        Agent agent = agentRepository.findById(id).orElseThrow(()->new ResourceNotFound("Agent dosnt exist  with id :"+id));

        agent.setFirstName(updatedAgent.getFirstName());
        agent.setLastName(updatedAgent.getLastName());
        agent.setBirthDate(updatedAgent.getBirthDate());
        agent.setUsername(updatedAgent.getUsername());

        agent.setAddress(updatedAgent.getAddress());
        agent.setEmail(updatedAgent.getEmail());
        agent.setPhone(updatedAgent.getPhone());
        agent.setCin(updatedAgent.getCin());
        agent.setPatentNumber(updatedAgent.getPatentNumber());
        agent.setImmatricule(updatedAgent.getImmatricule());
        System.out.println("id  "+ agent.getId());

        return agentRepository.save(agent);
    }

    public Boolean deleteAgent(Long id){
        Agent agent = agentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Agent doesn't exist with id: " + id));
        agentRepository.delete(agent);

        return Boolean.TRUE;

    }
    public Boolean changePassword(ChangePasswordRequest request)throws Exception{
        Admin admin  = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!passwordEncoder.matches(request.getOldPassword(), admin.getPassword())){
             new IllegalStateException("bad password");
        };
        String newPassEncoded = passwordEncoder.encode(request.getNewPassword());
        admin.setPassword(newPassEncoded);
        admin.setUsername(admin.getUsername().split(":")[0]);
        admin.setPasswordChanged(Boolean.TRUE);
        adminRepo.save(admin);

        return Boolean.TRUE;
    }
}
