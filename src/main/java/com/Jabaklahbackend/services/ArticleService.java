package com.Jabaklahbackend.services;


import com.Jabaklahbackend.entities.Article;
import com.Jabaklahbackend.entities.Creditor;
import com.Jabaklahbackend.repositories.ArticleRepo;
import com.Jabaklahbackend.repositories.CreditorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final CreditorRepo creditorRepo;

    private final ArticleRepo articleRepo;

    public List<Article> listArticles(Long id){
        return articleRepo.findByCreditor((Creditor) creditorRepo.findById(id).orElseThrow());
    }

}
