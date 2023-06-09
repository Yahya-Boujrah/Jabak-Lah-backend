package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.Article;
import com.Jabaklahbackend.entities.Bill;
import com.Jabaklahbackend.entities.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepo extends JpaRepository<Debt, Long> {

    public Optional<List<Debt>> findByArticle(Article article);
    public Optional<List<Debt>> findByBill(Bill bill);

    public Optional<List<Debt>> findByOrderId(Long id);
}
