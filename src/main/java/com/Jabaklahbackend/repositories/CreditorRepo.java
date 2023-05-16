package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Article;
import com.Jabaklahbackend.entities.Creditor;
import com.Jabaklahbackend.entities.CreditorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditorRepo extends JpaRepository<Creditor,Long> {
    public Optional<Creditor> findByName(String name);
}
