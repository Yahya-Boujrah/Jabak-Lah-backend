package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.*;
import com.Jabaklahbackend.payloads.PaymentInfo;
import com.Jabaklahbackend.repositories.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.Jabaklahbackend.services.BillService.appBill;

@Service
@RequiredArgsConstructor
public class CMIService {
    private final ClientRepo clientRepo;

    private final BillRepo billRepo;

    private final DebtRepo debtRepo;

    private  final PasswordGeneratorService passwordGenerator;

    private final SmsService smsService;

    private final OrderService orderService;

    private final OrderRepo orderRepo;

    private final ProductRepo productRepo;


    @Value("${stripe.key.secret}")
    private String secretKey;

    public Client chargerSolde(Long id, BigDecimal amount){
        if(amount == null){
            throw new IllegalStateException("cannot charge null amount");
        }
        Client client = clientRepo.findById(id).orElseThrow();
        BigDecimal newBalance = client.getBalance().add(amount);

        client.setBalance(newBalance);

        return clientRepo.save(client);
    }

    public Boolean chargerSolde(BigDecimal amount){
        if(amount == null){
            throw new IllegalStateException("cannot charge null amount");
        }

        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();
        BigDecimal newBalance = client.getBalance().add(amount);

        client.setBalance(newBalance);

        clientRepo.save(client);
        return Boolean.TRUE;

    }

    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

        Stripe.apiKey = secretKey;

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "JabakLah purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

public String payBill(){
    System.out.println("in paybill service");
    String phone = (String) SecurityContextHolder.getContext().getAuthentication().getName();

    System.out.println(phone.split(":")[0]);

    Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();


    if(client.getBalance().compareTo(appBill.getTotalAmount() ) == -1)
        throw new IllegalStateException("Client does not have enough balance");

    String verificationCode = generateVerificationCode(appBill, client.getPhone());
    System.out.println(verificationCode);
    appBill.setVerificationCode(verificationCode);
    appBill.setVerificationCodeSentAt(LocalDateTime.now());

    return "check sms to verify payment";
}

public String confirmBillPayment(String verificationCode){
        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepo.findByPhone(phone.split(":")[0]).orElseThrow();
        if(client.getBalance().compareTo( appBill.getTotalAmount() ) == -1)
            throw new IllegalStateException("Client does not have enough balance");

        int minutes = (int) ChronoUnit.MINUTES.between(appBill.getVerificationCodeSentAt(), LocalDateTime.now());
    System.out.println(appBill.getVerificationCode());
    System.out.println(verificationCode);
    System.out.println(minutes);
        if ( appBill.getVerificationCode().equals(verificationCode)) {
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
                appBill = billRepo.save(
                        Bill.builder()
                                .client(client)
                                .paid(Boolean.FALSE)
                                .totalAmount(BigDecimal.ZERO)
                                .build()
                );
                List<Debt> productDebts = mappedDebts.stream().filter(debt -> debt.getType() == DebtType.PRODUCT).collect(Collectors.toList());
                BigDecimal total = new BigDecimal(0);
                List<Long> productIds = new ArrayList<>();
                for(Debt debt : productDebts){
                    total = total.add(debt.getAmount());
                    productIds.add(debt.getProduct().getId());
                }
                List<Product> products = productRepo.findByDebtIds(productIds).orElseThrow();

                products.stream().forEach(product -> {
                    int unit = product.getUnitsInStock();
                    unit = unit - 1;
                    product.setUnitsInStock(unit);
                });

                Order order = new Order();
                order.setDebts(productDebts);
                order.setTotalPrice(total);
                order.setOrderTrackingNumber(orderService.generateOrderTrackingNumber());
                order.setClient(client);
                orderRepo.save(order);
                productRepo.saveAll(products);
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
