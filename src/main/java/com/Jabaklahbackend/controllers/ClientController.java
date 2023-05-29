package com.Jabaklahbackend.controllers;


import com.Jabaklahbackend.entities.Debt;
import com.Jabaklahbackend.entities.Product;
import com.Jabaklahbackend.payloads.ChangePasswordRequest;
import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.services.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    private final ClientService clientService;
    private final OrderService orderService;

    @GetMapping("/creditors")
    public ResponseEntity<Response> getCreditors(){
        //System.out.println("inside controller");
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
                        .data(Map.of("debts", debtService.listDebts(id)))
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
                        .data(Map.of("debt", debtService.createDebt(debt)))
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
                        .data(Map.of("bill", billService.createBill()))
                        .build()
        );
    }
    @PutMapping("/bind/{debtId}")
    public ResponseEntity<Response> bindDebtToBill(@PathVariable("debtId") List<Long> debtIds){

        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Debt binded to bill " + appBill.getId())
                        .data(Map.of("debt", debtService.bindToBill(debtIds)))
                        .build()
        );
    }
    @GetMapping("/bill/debts")
    public  ResponseEntity<Response> findBillDebts(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Debts of bill retrieved")
                        .data(Map.of("debts", billService.findBillDebts(appBill.getId())))
                        .build()
        );
    }
    @GetMapping("/history")
    public ResponseEntity<Response> getHistory(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("History for user " )
                        .data(Map.of("bills", billService.getBillsHistory()))
                        .build()
        );
    }
    @DeleteMapping("/bill/delete")
    public ResponseEntity<Response> deleteBill(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Bill deleted "+ billService.deleteBill(appBill.getId()))
                        .build()
        );
    }


    @PostMapping("/addProduct")
    public ResponseEntity<Response> addOrderToBill(@RequestBody Product product){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("product added to bill")
                        .data(Map.of("debt", debtService.createDebt(product)))
                        .build()
        );
    }

    @GetMapping("/infos")
    public ResponseEntity<Response> getInfos(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("infos of user" )
                        .data(Map.of("client",clientService.getInfos()))
                        .build()
        );
    }

    @SneakyThrows
    @PutMapping("/changePassword")
    public ResponseEntity<Response> changePassword(@RequestBody String password){

        System.out.println(password);

        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("changed", clientService.changePassword(password) ))
                        .message("password changed")
                        .build()
        );
    }
}
