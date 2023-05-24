package com.Jabaklahbackend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice;

    private String status;

    private String orderTrackingNumber;
    @CreationTimestamp
    private Date dateCreated;

    @UpdateTimestamp
    private Date lastUpdated;


    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;


    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Debt> debts;

    public void setDebts(List<Debt> debts) {
        debts.forEach(debt -> debt.setOrder(this));
        this.debts = debts;
    }

}
