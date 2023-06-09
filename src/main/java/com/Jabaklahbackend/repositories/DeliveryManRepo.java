package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.DeliveryMan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryManRepo extends JpaRepository<DeliveryMan, Long> {

    public Optional<DeliveryMan> findByUsername(String username);
}
