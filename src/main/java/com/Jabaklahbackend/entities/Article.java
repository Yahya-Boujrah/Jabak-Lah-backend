package com.Jabaklahbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Enumerated(EnumType.STRING)
    private ArticleType type;
    @OneToOne
    @JoinColumn(name = "creditor_id", referencedColumnName = "id")
    private Creditor creditor;
    @JsonIgnore
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<Debt> debts;
}
