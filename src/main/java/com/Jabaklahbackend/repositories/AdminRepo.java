package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);
}
