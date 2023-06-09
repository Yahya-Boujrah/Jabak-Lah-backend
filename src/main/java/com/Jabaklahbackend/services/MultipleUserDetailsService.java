package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.User;
import com.Jabaklahbackend.repositories.AdminRepo;
import com.Jabaklahbackend.repositories.AgentRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.DeliveryManRepo;
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
    private final DeliveryManRepo deliveryManRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username + " load");
        String arr[] = username.split(":");
        User user = new User();
        String role = arr[1];

        switch (role){
            case "CLIENT" :
                System.out.println("client here");
                user = clientRepo.findByPhone(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                user.setPhone(username);
                break;

            case "AGENT" :
                System.out.println("agent here");
                user = agentRepo.findByUsername(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                user.setUsername(username);
                break;

            case "ADMIN" :
                System.out.println("admin here");
                user= adminRepo.findByUsername(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                user.setUsername(username);
                break;

            case "LIVREUR" :
                System.out.println("livreur here");
                user= deliveryManRepo.findByUsername(arr[0]).orElseThrow( () -> new UsernameNotFoundException("User not found"));
                System.out.println("livreur found");
                user.setUsername(username);
                break;

            default:
                throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
