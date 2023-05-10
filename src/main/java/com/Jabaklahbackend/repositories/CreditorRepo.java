package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Creditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditorRepo extends JpaRepository {
    public Optional<Creditor> findByName();

}
