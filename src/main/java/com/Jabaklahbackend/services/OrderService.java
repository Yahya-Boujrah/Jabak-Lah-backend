package com.Jabaklahbackend.services;


import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.Order;
import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final ClientRepo clientRepo;

    private final OrderRepo orderRepo;


    public List<Order> getClientOrders(){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();
        return orderRepo.findByClient(client).orElseThrow();
    }

    public String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }
}
