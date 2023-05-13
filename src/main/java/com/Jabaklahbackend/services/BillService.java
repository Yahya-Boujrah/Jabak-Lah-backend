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

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepo billRepo;
    private final ClientRepo clientRepo;
    private final DebtRepo debtRepo;

    public static Bill appBill;

    public Bill createBill(){
//        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(phone);
        Client client = clientRepo.findByPhone("0629974866").orElseThrow();

        appBill = billRepo.save(
                Bill.builder()
                        .client(client)
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
        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(phone);
        Client client = clientRepo.findByPhone(phone).orElseThrow();

        List<Bill> bills = billRepo.findByClient(client).orElseThrow();
        return bills;
    }
}
