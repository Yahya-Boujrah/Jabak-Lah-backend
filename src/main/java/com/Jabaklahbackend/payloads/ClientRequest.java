package com.Jabaklahbackend.payloads;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClientRequest {
    private String firstName;
    private String lastName;
    private String role;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String address;
    private String email;
    private String phone;
    private String cin;
    private BigDecimal balance;
}
