package com.Jabaklahbackend.controllers;


import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;


    @GetMapping("/getOrders")
    public ResponseEntity<Response> getOrdersForDG(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("orders", deliveryService.getOrdersForDG()))
                        .message("list of orders for delivery man")
                        .build()
        );
    }
    @GetMapping("/deliveryMen")
    public ResponseEntity<Response> getDeliveryMen(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("deliveryMen", deliveryService.deliveryMen()))
                        .message("list of delivery Men")
                        .build()
        );
    }
    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<Response> updateOrder(@PathVariable Long orderId, @RequestBody String dgId){
        System.out.println("in controller liv");
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("affected" + deliveryService.affectDG2Order( dgId, Long.valueOf(orderId) ) );
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateStatus(@PathVariable Long id, @RequestBody String status){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message(deliveryService.updateOrderStatus(id, status))
                        .build()
        );
    }

    @GetMapping("/getProducts/{id}")
    public ResponseEntity<Response> getProducts(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("products", deliveryService.getProductsByOrder(id)))
                        .message("list of orders for delivery man")
                        .build()
        );
    }

}
