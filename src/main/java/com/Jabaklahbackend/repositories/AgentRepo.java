package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepo extends JpaRepository<Agent,Long> {
    Optional<Agent> findByUsername(String username);

    public Boolean existsByUsername(String Username);
}
