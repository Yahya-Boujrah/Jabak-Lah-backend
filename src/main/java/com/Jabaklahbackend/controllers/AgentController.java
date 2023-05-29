package com.Jabaklahbackend.controllers;


import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.payloads.ChangePasswordRequest;
import com.Jabaklahbackend.payloads.ClientRequest;
import com.Jabaklahbackend.payloads.ProspectRequest;
import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.services.AdminService;
import com.Jabaklahbackend.services.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {
    private final AgentService agentService;
    private final AdminService adminService;

    @GetMapping("/listAgent")
    public ResponseEntity<Response> getAllAgents(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("agents", adminService.findAllAgents()))
                        .message("list of all agents")
                        .build()
        );
    }
    @GetMapping("/listClient")
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

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateClient(@PathVariable Long id ,@RequestBody Client client){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("client", agentService.updateClient(client, id)))
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
    @GetMapping("/listProspect")
    public ResponseEntity<Response> getAllProspects(){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("prospects", agentService.getListProspects()))
                        .message("list of all prospects")
                        .build()
        );
    }

    @PostMapping("/approve")
    public ResponseEntity<Response> saveClient(@RequestBody ProspectRequest prospect){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .data(Map.of("client",agentService.convertProspectToClient(prospect)))
                        .message("Client created")
                        .build()
        );
    }

    @DeleteMapping("/deleteProspect/{id}")
    public ResponseEntity<Response> deleteProspect(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("deleted", agentService.deleteProspect(id)))
                        .message("prospect deleted")
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
                        .data(Map.of("agent",agentService.getInfos()))
                        .build()
        );
    }

    @SneakyThrows
    @PutMapping("/changePassword")
    public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordRequest request){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("changed", agentService.changePassword(request) ))
                        .message("password changed")
                        .build()
        );
    }

    @PutMapping("/resetPasswordClient/{id}")
    public ResponseEntity<Response> resetPasswordClient(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("reset", adminService.resetPasswordClient(id)))
                        .message("password reset successfully")
                        .build()
        );
    }

}
