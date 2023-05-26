package com.Jabaklahbackend.payloads;

import com.Jabaklahbackend.entities.AccountType;
import com.Jabaklahbackend.entities.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClientRequest {
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String username;
    private Date birthDate;
    private String address;
    private String email;
    private String phone;
    private String cin;
    private BigDecimal balance;
    private AccountType accountType;
}
