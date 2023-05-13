package com.Jabaklahbackend.repositories;


import com.Jabaklahbackend.entities.Article;
import com.Jabaklahbackend.entities.Creditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {

    List<Article> findByCreditor(Creditor creditor);

    @Query(value = "SELECT a FROM Article a WHERE a.creditor IN :creditors")
    List<Article> findFilteredArticles( @Param("creditors") List<Creditor> creditors);

}
