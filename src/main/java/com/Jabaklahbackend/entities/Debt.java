package com.Jabaklahbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    private Boolean paid = false;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    @PrePersist
    public void setCreationDateTime() {
        this.createdAt = LocalDateTime.now();
    }
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "creditor_id", referencedColumnName = "id")
    private Creditor creditor;

    @ManyToOne
    @JoinColumn(name = "article_id" , nullable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}