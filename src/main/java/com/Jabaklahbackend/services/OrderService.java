package com.Jabaklahbackend.services;


import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class OrderService {

    public String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }
}
