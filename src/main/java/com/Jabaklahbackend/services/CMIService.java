package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Bill;
import com.Jabaklahbackend.entities.Client;
import com.Jabaklahbackend.entities.Debt;
import com.Jabaklahbackend.repositories.BillRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.DebtRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.Jabaklahbackend.services.BillService.appBill;

@RequiredArgsConstructor
@Service
public class CMIService {

    private final ClientRepo clientRepo;
    private final BillRepo billRepo;
    private final DebtRepo debtRepo;

    public boolean chargeSolde(String phone, BigDecimal amount){
        if(amount == null){
            throw new IllegalStateException("cannot charge null amount");
        }

        Client client = clientRepo.findByPhone(phone).orElseThrow();
        BigDecimal newBalance = client.getBalance().add(amount);

        client.setBalance(newBalance);

        clientRepo.save(client);
        return Boolean.TRUE;
    }

    public String payBill(){
        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientRepo.findByPhone(phone).orElseThrow();

        if(client.getBalance().compareTo( appBill.getTotalAmount() ) == -1)
            throw new IllegalStateException("Client do not have enough balance");

        BigDecimal newBalance = client.getBalance().subtract(appBill.getTotalAmount());

        client.setBalance(newBalance);

        appBill.setPaid(Boolean.TRUE);

        billRepo.save(appBill);

        List<Debt> debts = debtRepo.findByBill(appBill).orElseThrow();

        List<Debt> mappedDebts =  debts.stream()
                .map(debt -> {
                    debt.setPaid(Boolean.TRUE);
                    return debt;
                })
                .collect(Collectors.toList());

        debtRepo.saveAll(mappedDebts);
        clientRepo.save(client);

        return "Balance updated and bill paid";

    }

}
