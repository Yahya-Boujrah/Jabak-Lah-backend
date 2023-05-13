package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Bill;
import com.Jabaklahbackend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
    public Optional<List<Bill>> findByClient(Client client);

}
