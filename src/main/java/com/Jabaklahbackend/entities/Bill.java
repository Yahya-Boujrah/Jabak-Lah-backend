package com.Jabaklahbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ColumnDefault("0")
    private BigDecimal totalAmount = new BigDecimal(0);
    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean paid = Boolean.FALSE;
    private String verificationCode;
    private LocalDateTime verificationCodeSentAt;
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    @JsonIgnore
    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Debt> debts;
    @PrePersist
    public void setCreationDateTime() {
        this.createdAt = LocalDateTime.now();
    }

}
