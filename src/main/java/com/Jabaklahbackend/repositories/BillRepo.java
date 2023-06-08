package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Bill;
import com.Jabaklahbackend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
    public Optional<List<Bill>> findByClient(Client client);

    @Query(value = "SELECT * FROM bill WHERE paid = false ", nativeQuery = true)
    public Optional<List<Bill>> findByPaid();

}
