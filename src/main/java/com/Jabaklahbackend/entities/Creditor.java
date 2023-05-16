package com.Jabaklahbackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
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
    @Enumerated(EnumType.STRING)
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
