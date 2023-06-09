package com.Jabaklahbackend.services;


import com.Jabaklahbackend.entities.*;
import com.Jabaklahbackend.repositories.DebtRepo;
import com.Jabaklahbackend.repositories.DeliveryManRepo;
import com.Jabaklahbackend.repositories.OrderRepo;
import com.Jabaklahbackend.repositories.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private static DebtRepo debtRepo;

    private static ProductRepo productRepo;

    private static OrderRepo orderRepo;

    private static DeliveryManRepo deliveryManRepo;

    public List<Order> getOrdersForDG(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DeliveryMan deliveryMan = deliveryManRepo.findByUsername(username.split(":")[0]).orElseThrow();
        return orderRepo.findByDeliveryman(deliveryMan).orElseThrow();
    }


    public String updateOrderStatus(Long id, String status){
        Order order = orderRepo.findById(id).orElseThrow();
        order.setStatus(OrderStatus.valueOf(status));
        orderRepo.save(order);
        return "order changed successfully";
    }

    public List<Product> getProductsByOrder(Long id){
        List<Debt> debts = debtRepo.findByOrderId(id).orElseThrow();
        List<Long> productIds = debts.stream().map(debt -> {
            return debt.getProduct().getId();
        }).collect(Collectors.toList());


        return productRepo.findByDebtIds(productIds).orElseThrow();
    }
}
