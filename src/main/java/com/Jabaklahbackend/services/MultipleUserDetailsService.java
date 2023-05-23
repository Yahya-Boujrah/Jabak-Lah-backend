package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.User;
import com.Jabaklahbackend.repositories.AdminRepo;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import lombok.RequiredArgsConstructor;
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
        System.out.println(username + " load");
        String arr[] = username.split(":");
        User user = new User();
        String role = arr[1];

        switch (role){
            case "CLIENT" :
                System.out.println("client heree");
                user = clientRepo.findByPhone(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                user.setPhone(username);
                break;

            case "AGENT" :
                System.out.println("agent heree");
                user = agentRepo.findByUsername(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                user.setUsername(username);
                break;

            case "ADMIN" :
                System.out.println("admin heree");
                user= adminRepo.findByUsername(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                user.setUsername(username);
                break;

            default:
                throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
