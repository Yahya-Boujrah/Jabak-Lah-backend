package com.Jabaklahbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Prospect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;

    private String cin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void setCreationDateTime() {
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void setChangeDateTime() {
        this.updatedAt = LocalDateTime.now();
    }
}
