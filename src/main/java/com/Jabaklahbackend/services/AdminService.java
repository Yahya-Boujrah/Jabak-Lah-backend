package com.Jabaklahbackend.services;

import com.Jabaklahbackend.email.EmailSender;
import com.Jabaklahbackend.email.EmailUtil;
import com.Jabaklahbackend.entities.Admin;
import com.Jabaklahbackend.entities.Agent;
import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.Role;
import com.Jabaklahbackend.payloads.ChangePasswordRequest;
import com.Jabaklahbackend.repositories.AdminRepo;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import exception.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
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
    private final ClientRepo clientRepo;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGeneratorService passwordGenerator;

    private final EmailSender sender;

    private final EmailUtil emailUtil;

    public List<Admin> findAllAdmins(){
        return adminRepo.findAll();
    }

    public List<Agent> findAllAgents(){
        return agentRepository.findAll();

    }
    public Agent saveAgent(Agent agent){
        agent.setRole(Role.AGENT);

        String newPassword = passwordGenerator.passwordForEmail();

        agent.setPassword(passwordEncoder.encode(newPassword));

        sendEmail(agent.getEmail(), agent.getUsername(), newPassword );
        return agentRepository.save(agent);
    }

    public Agent findAgent(Long id){
        return agentRepository.findById(id).orElseThrow(()->new ResourceNotFound("Agent dosnt exist  with id :"+id));
    }

    public Agent updateAgent(Agent updatedAgent, Long id ){
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
    public Boolean resetPasswordAgent(Long id){
        Agent agent = agentRepository.findById(id).orElseThrow();
        String newPassword = passwordGenerator.passwordForEmail();

        agent.setPassword(passwordEncoder.encode(newPassword));

        agentRepository.save(agent);

        sendEmail(agent.getEmail(), agent.getUsername(), newPassword );

        return Boolean.TRUE;
    }

    public Boolean resetPasswordClient(Long id){
        Client client = clientRepo.findById(id).orElseThrow();
        String newPassword = passwordGenerator.passwordForEmail();

        client.setPassword(passwordEncoder.encode(newPassword));

        clientRepo.save(client);

        sendEmail(client.getEmail(), client.getUsername(), newPassword );

        return Boolean.TRUE;
    }

    @Async
    public void sendEmail(String email, String name, String password){
        this.sender.send(email, emailUtil.buildEmail(name, password));
    }

    public Admin getInfos(){
        String currentUser= (String) SecurityContextHolder.getContext().getAuthentication().getName();
        return adminRepo.findByUsername(currentUser.split(":")[0]).orElseThrow();
    }
}
