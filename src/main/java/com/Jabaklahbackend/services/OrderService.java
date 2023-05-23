package com.Jabaklahbackend.services;


import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.Order;
import com.Jabaklahbackend.entities.OrderItem;
import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class OrderService {


    private final ClientRepo clientRepo;

    private final OrderRepo orderRepo;

    public String placeOrder(List<OrderItem> orderItems){

        Order order = new Order();

        orderItems.forEach(item -> order.add(item));

        String trackingNumber = generateOrderTrackingNumber();

        order.setOrderTrackingNumber(trackingNumber);

//        String phone = SecurityContextHolder.getContext().getAuthentication().getName();

        order.setClient(clientRepo.findByPhone("0629974866").orElseThrow());

        orderRepo.save(order);

        return "order saved with Number : " + trackingNumber;
    }



    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }
}
