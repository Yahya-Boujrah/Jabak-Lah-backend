package com.Jabaklahbackend.controllers;



import com.Jabaklahbackend.entities.Agent;
import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.payloads.ClientRequest;
import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.services.AdminService;
import com.Jabaklahbackend.services.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AgentService agentService;

    @GetMapping("/admins")
    public ResponseEntity<Response> findAllAdmins(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("admins", adminService.findAllAdmins()))
                        .message("list of all admins")
                        .build()
        );
    }

    @GetMapping("/agents")
    public ResponseEntity<Response> findAllAgents(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("agents", adminService.findAllAgents()))
                        .message("list of all agents")
                        .build()
        );
    }
    @PostMapping("/saveAgent")
    public ResponseEntity<Response> saveAgent(@Validated @RequestBody Agent agent){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .data(Map.of("agent", adminService.saveAgent(agent)))
                        .message("Agent created")
                        .build()
        );
    }

    @GetMapping("/agent/{id}")
    public ResponseEntity <Response> getAgentById( @PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("agent", adminService.findAgent(id)))
                        .message("Agent created")
                        .build()
        );
    }
    @PutMapping("/updateAgent")
    public ResponseEntity<Response> updateAgent(@RequestBody Agent updatedAgent) {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("agent", adminService.updateAgent(updatedAgent)))
                        .message("Agent updated")
                        .build()
        );
    }
    @DeleteMapping("/deletAgent/{id}")
    public ResponseEntity<Response> deleteAgent(@PathVariable long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("deleted", adminService.deleteAgent(id)))
                        .message("Agent deleted")
                        .build()
        );

    }

    @PostMapping("/saveClient")
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
    @GetMapping("/clients")
    public ResponseEntity<Response> getAllClients(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
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

    @PutMapping("/updateClient")
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
    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<Response> deleteClient(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("deleted", agentService.deleteClient(id)))
                        .message("client deleted")
                        .build()
        );
    }

}


