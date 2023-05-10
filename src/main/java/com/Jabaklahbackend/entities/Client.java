package com.Jabaklahbackend.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client extends User{
    private String address;
    private String email;
    private String phone;
    private String cin;
    private BigDecimal balance = new BigDecimal(0);

}
