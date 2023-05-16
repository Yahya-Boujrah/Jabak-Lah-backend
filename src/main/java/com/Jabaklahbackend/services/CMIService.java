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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.Jabaklahbackend.services.BillService.appBill;

@RequiredArgsConstructor
@Service
public class CMIService {

    private final ClientRepo clientRepo;
    private final BillRepo billRepo;
    private final DebtRepo debtRepo;

    private final PasswordGeneratorService passwordGenerator;

    private final SmsService smsService;


    public boolean chargerSolde(BigDecimal amount){
        if(amount == null){
            throw new IllegalStateException("cannot charge null amount");
        }

        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Client client = clientRepo.findByPhone(phone).orElseThrow();
        BigDecimal newBalance = client.getBalance().add(amount);

        client.setBalance(newBalance);

        clientRepo.save(client);
        return Boolean.TRUE;

    }

    public String payBill1(){
       // String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientRepo.findByPhone("0616061968").orElseThrow();

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

        appBill = billRepo.save(
                Bill.builder()
                        .client(client)
                        .paid(Boolean.FALSE)
                        .totalAmount(BigDecimal.ZERO)
                        .build()
        );

        return "Balance updated and bill paid";

    }
public String payBill(){
    String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Client client = clientRepo.findByPhone(phone).orElseThrow();


    if(client.getBalance().compareTo(appBill.getTotalAmount() ) == -1)
        throw new IllegalStateException("Client does not have enough balance");

    String verificationCode = generateVerificationCode(appBill, phone);
    appBill.setVerificationCode(verificationCode);
    appBill.setVerificationCodeSentAt(LocalDateTime.now());

    return "check sms to verify payment";
}

    public String confirmBillPayment(String verificationCode){
        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientRepo.findByPhone(phone).orElseThrow();

        if(client.getBalance().compareTo( appBill.getTotalAmount() ) == -1)
            throw new IllegalStateException("Client does not have enough balance");

        int minutes = (int) ChronoUnit.MINUTES.between(appBill.getVerificationCodeSentAt(), LocalDateTime.now());

        if ( appBill.getVerificationCode() == verificationCode) {
            if (minutes <= 5) {
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

            } else return "verification code expired";
        }
        return "wrong code";

    }

    String generateVerificationCode(Bill bill, String phone){
        String verificationCode = passwordGenerator.passwordForSms();
        String verificationMssg = generateSms(verificationCode, bill);
        smsService.sendSMS(phone, verificationMssg);
        return verificationCode;
    }

    private String generateSms(String verificationCode, Bill bill){
        return verificationCode+ " est le code de confirmation pour votre payement pour " +
                "un montant de "+ bill.getTotalAmount()+
                " MAD.\n" +
                "Ce code expire dans 5 minutes.";
    }
}
