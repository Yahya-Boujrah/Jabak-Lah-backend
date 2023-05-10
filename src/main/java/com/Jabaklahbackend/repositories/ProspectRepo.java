package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProspectRepo extends JpaRepository<Prospect,Long> {

}
