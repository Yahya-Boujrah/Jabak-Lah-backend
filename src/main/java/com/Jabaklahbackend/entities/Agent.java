package com.Jabaklahbackend.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Agent extends User{
    private String address;
    private String email;
    private String phone;
    private String cin;
    private String patentNumber;
    private String immatricule;

}
