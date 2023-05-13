package com.Jabaklahbackend.controllers;


import com.Jabaklahbackend.entities.Debt;
import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.services.ArticleService;
import com.Jabaklahbackend.services.BillService;
import com.Jabaklahbackend.services.CreditorService;
import com.Jabaklahbackend.services.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.Jabaklahbackend.services.BillService.appBill;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/client")
public class ClientController {

    private final CreditorService creditorService;

    private final ArticleService articleService;

    private final DebtService debtService;

    private final BillService billService;


    @GetMapping("/creditors")
    public ResponseEntity<Response> getCreditors(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Creditors Retrieved")
                        .data(Map.of("creditors", creditorService.listCreditors()))
                        .build()
        );
    }

    @GetMapping("/creditors/{id}/articles")
    public ResponseEntity<Response> getArticlesByCreditor(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Articles Retrieved for " + id)
                        .data(Map.of("articles", articleService.listArticles(id)))
                        .build()
        );
    }

    @PostMapping("/articles/debts/generate")
    public ResponseEntity<Response> generateDebts(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .message("Debts generated")
                        .data(Map.of("Debts", debtService.generateDueDebts()))
                        .build()
        );
    }
    @GetMapping("/articles/{id}/debts")
    public ResponseEntity<Response> getDebtsByArticle(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Debts Retrieved for " + id)
                        .data(Map.of("Debts", debtService.listDebts(id)))
                        .build()
        );
    }
    @PostMapping("/articles/debts/save")
    public ResponseEntity<Response> saveDebt(@RequestBody Debt debt){

        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .message("Debt saved ")
                        .data(Map.of("Debt", debtService.createDebt(debt)))
                        .build()
        );
    }

    @PostMapping("/bill/generate")
    public ResponseEntity<Response> generateBill(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .message("First Bill Created ")
                        .data(Map.of("Bill", billService.createBill()))
                        .build()
        );
    }

    @PutMapping("/bind/{id}")
    public ResponseEntity<Response> bindDebtToBill(@PathVariable Long id){

        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Debt binded to bill " + appBill.getId())
                        .data(Map.of("Debt", debtService.bindToBill(id)))
                        .build()
        );

    }

}
