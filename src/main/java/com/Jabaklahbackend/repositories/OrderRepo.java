package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
