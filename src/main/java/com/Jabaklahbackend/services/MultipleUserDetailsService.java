package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Admin;
import com.Jabaklahbackend.entities.Agent;
import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.repositories.AdminRepo;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class MultipleUserDetailsService implements UserDetailsService {


    private final AgentRepo agentRepo;

    private final AdminRepo adminRepo;

    private final ClientRepo clientRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String arr[] = username.split(":");

        switch (arr[1]){
            case "AGENT" :
                Agent agent = agentRepo.findByUsername(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                return agent;

            case "ADMIN" :
                Admin admin = adminRepo.findByUsername(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                return admin;

            case "CLIENT" :
                Client client = clientRepo.findByPhone(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                return client;
            default:
                throw new UsernameNotFoundException("User not found");
        }
    }
}
