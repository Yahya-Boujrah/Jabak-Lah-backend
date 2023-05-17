package com.Jabaklahbackend.controllers;


import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.payloads.ClientRequest;
import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.services.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {
    private final AgentService agentService;

    @PostMapping("/addClient")
    public ResponseEntity<Response> createClient(@RequestBody ClientRequest client){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .data(Map.of("client",agentService.saveClient(client)))
                        .message("Client created")
                        .build()
        );
    }
    @GetMapping("/listClient")
    public ResponseEntity<Response> getAllClients(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .data(Map.of("clients", agentService.getListClient()))
                        .message("list of all clients")
                        .build()
        );
    }

    @GetMapping("/client/{phone}")
    public ResponseEntity<Response> getClient(@PathVariable String phone){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("client", agentService.getClientByPhone(phone)))
                        .message("get client by phone")
                        .build()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateClient(@RequestBody Client client){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("client", agentService.updateClient(client)))
                        .message("client updated")
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteClient(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("client", agentService.deleteClient(id)))
                        .message("client deleted")
                        .build()
        );
    }
}
