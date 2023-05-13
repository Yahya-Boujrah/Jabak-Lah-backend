package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Admin;
import com.Jabaklahbackend.entities.Agent;
import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.repositories.AdminRepo;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class MultipleUserDetailsService implements UserDetailsService {


    @Autowired
    private AgentRepo agentRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String arr[] = username.split(":");

        System.out.println(arr[0]);
        System.out.println(arr[1]);

        switch (arr[1]){
            case "AGENT" :
                System.out.println("here 1");
                System.out.println(agentRepo.findByUsername("MouadRabihi").orElseThrow().getUsername());
                System.out.println("here 2");
                Agent agent = agentRepo.findByUsername(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                System.out.println("here 3");
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
