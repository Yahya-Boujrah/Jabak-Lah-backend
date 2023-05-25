package com.Jabaklahbackend.controllers;


import com.Jabaklahbackend.payloads.PaymentInfo;
import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.services.CMIService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/cmi")
public class CMIController {

    private final CMIService cmiService;

    @PostMapping("/charger")
    public ResponseEntity<Response> chargerSolde(@RequestBody BigDecimal amount){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("chargement effectue avec succees de " + amount)
                        .data(Map.of("transaction", cmiService.chargerSolde(amount)))
                        .build()
        );
    }


    @PostMapping("/payment-intent")
    public ResponseEntity<Response> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {

        String paymentIntent = cmiService.createPaymentIntent(paymentInfo).toJson();

        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("paymentIntent",paymentIntent))
                        .build()
        );
    }

    @PostMapping("/payment")
    public ResponseEntity<Response> payBill(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message(cmiService.payBill1())
                        .build()
        );
    }
}
