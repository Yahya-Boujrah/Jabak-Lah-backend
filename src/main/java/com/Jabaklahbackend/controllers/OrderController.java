package com.Jabaklahbackend.controllers;


import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/get")
    public ResponseEntity<Response> getClientOrders(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("orders retrieved ")
                        .data(Map.of("orders", orderService.getClientOrders()))
                        .build()
        );
    }
    @GetMapping("/allOrders")
    public ResponseEntity<Response> getAgentOrders(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("orders retrieved ")
                        .data(Map.of("orders", orderService.getAgentOrders()))
                        .build()
        );
    }
}
