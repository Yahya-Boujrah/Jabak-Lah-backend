package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepo extends JpaRepository <Client,Long> {
    public Optional<Client> findByPhone(String phone);

    public Boolean existsByPhone(String phone);
}
