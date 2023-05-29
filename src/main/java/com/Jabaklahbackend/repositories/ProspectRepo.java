package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProspectRepo extends JpaRepository<Prospect,Long> {

    Optional<Prospect> findByPhone(String phone);

     public Boolean existsByPhone(String phone);
}
