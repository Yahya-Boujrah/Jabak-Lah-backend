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

public class Creditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CreditorType type;
    @PrePersist
    public void setCreationDateTime() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setChangeDateTime() {
        this.updatedAt = LocalDateTime.now();
    }
}
