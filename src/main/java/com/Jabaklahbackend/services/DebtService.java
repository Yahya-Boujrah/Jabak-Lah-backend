package com.Jabaklahbackend.services;


import com.Jabaklahbackend.entities.*;
import com.Jabaklahbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
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
        return debtRepo.findByArticle(articleRepo.findById(id).orElseThrow()).orElseThrow().stream().filter(debt -> {
           return debt.getPaid() == Boolean.FALSE;
        }).collect(Collectors.toList());
    }

    public Debt createDebt(Debt debt){
        Debt newDebt = debtRepo.save(debt);
        bindToBill(debt);
        return newDebt;
    }


    public Debt createDebt(Product product){

        String phone = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();

        Debt debt = new Debt();
        debt.setProduct(product);
        debt.setName(product.getName());
        debt.setDescription("debt for product " + product.getName());
        debt.setClient(client);
        debt.setAmount(product.getUnitPrice());
        debt.setType(DebtType.PRODUCT);
        bindToBill(debt);

        return debtRepo.save(debt);
    }


    public Debt bindToBill(Debt debt){
        debt.setBill(appBill);
        appBill.setTotalAmount(appBill.getTotalAmount().add(debt.getAmount()));
        billRepo.save(appBill);
        return debtRepo.save(debt);
    }

    public List<Debt> bindToBill(List<Long> ids){

        List<Debt> debts = debtRepo.findAllById(ids);

        debts.forEach(debt ->{
            debt.setBill(appBill);
            appBill.setTotalAmount(appBill.getTotalAmount().add(debt.getAmount()));

        });

        billRepo.save(appBill);
        return debtRepo.saveAll(debts);
    }

    @Async
    public List<Debt> generateDueDebts() {

        List<Debt> debts = new ArrayList<>();

        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("phone number  " + phone);
        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();

        List<Creditor> creditors = creditorRepo.findAll();

        List<Creditor> filteredCreditors = creditors.stream()
                .filter(creditor ->
                        creditor.getType() != CreditorType.CHARITY
                ).collect(Collectors.toList());


        List<Article> articles = articleRepo.findFilteredArticles(filteredCreditors).stream().filter(article ->
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
                        .paid(Boolean.FALSE)
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
                        .paid(Boolean.FALSE)
                        .build();

                Debt penalty = Debt.builder()
                        .client(client)
                        .creditor(article.getCreditor())
                        .type(DebtType.PENALTY)
                        .article(article)
                        .amount(new BigDecimal(decider - 250))
                        .name(article.getName())
                        .description("This is a penalty for " + article.getName())
                        .paid(Boolean.FALSE)
                        .build();

                debts.addAll(Arrays.asList(debt, penalty));
            }
        }

        debtRepo.saveAll(debts);
        return debts;

    }

}
