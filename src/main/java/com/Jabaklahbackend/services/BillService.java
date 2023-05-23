package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.*;
import com.Jabaklahbackend.repositories.BillRepo;
import com.Jabaklahbackend.repositories.ClientRepo;
import com.Jabaklahbackend.repositories.DebtRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepo billRepo;
    private final ClientRepo clientRepo;
    private final DebtRepo debtRepo;

    public static Bill appBill;

    public Bill createBill(){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();

        appBill = billRepo.save(
                Bill.builder()
                        .client(client)
                        .paid(Boolean.FALSE)
                        .totalAmount(BigDecimal.ZERO)
                        .build()
        );

        return appBill;
    }

    public List<Debt> findBillDebts(Long id){
        Bill bill = billRepo.findById(id).orElseThrow();
        List<Debt> debts = debtRepo.findByBill(bill).orElseThrow();

        return debts;
    }
    public List<Bill> getBillsHistory(){
        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();

        List<Bill> bills = billRepo.findByClient(client).orElseThrow();

        bills = bills.stream().filter(bill ->
                bill.getPaid() != Boolean.FALSE).collect(Collectors.toList());
        return bills;
    }

    public String deleteBill(Long id){
        Bill bill = billRepo.findById(id).orElseThrow();

        if(bill.getPaid() == Boolean.TRUE){
            return null;
        }
        List<Debt> debts = debtRepo.findAll().stream().filter(debt ->
                debt.getPaid() != Boolean.TRUE
            ).collect(Collectors.toList());


        debtRepo.deleteAll(debts);
        billRepo.delete(bill);

        return "Bill deleted ";
    }


}
