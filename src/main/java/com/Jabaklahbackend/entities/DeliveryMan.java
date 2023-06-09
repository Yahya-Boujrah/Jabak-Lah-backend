package com.Jabaklahbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class DeliveryMan extends User{

    @JsonIgnore
    @OneToMany(mappedBy = "deliveryman", fetch = FetchType.LAZY)
    List<Order> orders;

}
