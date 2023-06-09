package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.DeliveryMan;
import com.Jabaklahbackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {

    public Optional<List<Order>> findByClient(Client client);

    public Optional<List<Order>> findByDeliveryman(DeliveryMan deliveryMan);

}
