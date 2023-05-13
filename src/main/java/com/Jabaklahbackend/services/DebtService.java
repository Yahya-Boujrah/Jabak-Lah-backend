package com.Jabaklahbackend.services;


import com.Jabaklahbackend.entities.*;
import com.Jabaklahbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.Jabaklahbackend.services.BillService.appBill;

@Service
@RequiredArgsConstructor
public class DebtService {

    private final ClientRepo clientRepo;

    private final CreditorRepo creditorRepo;

    private final ArticleRepo articleRepo;

    private final DebtRepo debtRepo;


    private final BillRepo billRepo;


    public List<Debt> listDebts(Long id){
        return debtRepo.findByArticle(articleRepo.findById(id).orElseThrow()).orElseThrow();
    }

    public Debt createDebt(Debt debt){
        return debtRepo.save(debt);
    }


    public Debt bindToBill(Long id){

        Debt debt = debtRepo.findById(id).orElseThrow();

        debt.setBill(appBill);

        return debtRepo.save(debt);

    }


    public List<Debt> generateDueDebts() {

        List<Debt> debts = new ArrayList<>();

        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(phone);
        Client client = clientRepo.findByPhone(phone).orElseThrow();

//        List<Creditor> creditors = creditorRepo.findAll()
//                .stream()
//                .filter(creditor -> creditor.getType() != CreditorType.CHARITY).collect(Collectors.toList());

        List<Creditor> creditors = creditorRepo.findFilteredCreditors();

        List<Article> articles = articleRepo.findFilteredArticles(creditors).stream().filter(article ->
                article.getType() != ArticleType.ARTICLE_RECHARGE && article.getType() != ArticleType.CHARITY
        ).collect(Collectors.toList());


        for (Article article : articles){
            int decider = new Random().nextInt(350);
            if (decider > 50 && decider <= 250){
                Debt debt = Debt.builder()
                        .client(client)
                        .creditor(article.getCreditor())
                        .type(DebtType.DEBT)
                        .article(article)
                        .amount(new BigDecimal(decider))
                        .name(article.getName())
                        .description("This is a debt for " + article.getName())
                        .build();
                debts.add(debt);
            }
            else if (decider > 250){
                Debt debt = Debt.builder()
                        .client(client)
                        .creditor(article.getCreditor())
                        .type(DebtType.DEBT)
                        .article(article)
                        .amount(new BigDecimal(decider - 50))
                        .name(article.getName())
                        .description("This is a debt for " + article.getName())
                        .build();

                Debt penalty = Debt.builder()
                        .client(client)
                        .creditor(article.getCreditor())
                        .type(DebtType.PENALTY)
                        .article(article)
                        .amount(new BigDecimal(decider - 220))
                        .name(article.getName())
                        .description("This is a penalty for " + article.getName())
                        .build();

                debts.addAll(Arrays.asList(debt, penalty));
            }
        }

        debtRepo.saveAll(debts);
        return debts;

    }

}