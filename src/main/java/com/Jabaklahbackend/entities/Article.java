package com.Jabaklahbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
//    private String description;
//    private Boolean paid = false;
//    private BigDecimal amount;
//    private LocalDateTime createdAt;
//    @PrePersist
//    public void setCreationDateTime() {
//        this.createdAt = LocalDateTime.now();
//    }

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "creditor_id", referencedColumnName = "id")
    private Creditor creditor;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<Debt> debts;
}
