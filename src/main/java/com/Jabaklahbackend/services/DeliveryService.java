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

    private final DebtRepo debtRepo;

    private final ProductRepo productRepo;

    private final OrderRepo orderRepo;

    private final DeliveryManRepo deliveryManRepo;

    public List<DeliveryMan> deliveryMen(){
        return deliveryManRepo.findAll();
    }
    public Boolean affectDG2Order(Long dgId, Long orderId){
        DeliveryMan dg = deliveryManRepo.findById(dgId).orElseThrow();
        Order order = orderRepo.findById(orderId).orElseThrow();

        order.setDeliveryman(dg);
        //dg.setOrders(List.of(order));


        //deliveryManRepo.save(dg);
        orderRepo.save(order);

        return Boolean.TRUE;
    }

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
