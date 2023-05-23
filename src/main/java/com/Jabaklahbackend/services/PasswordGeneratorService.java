package com.Jabaklahbackend.services;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordGeneratorService {

    @Value("${password.choices.8}")
    private String alphaNumeric;

    @Value("${password.choices.5}")
    private String numeric;

    public String passwordForEmail(){

        String password = "";

        for (int i = 0; i < 8; i++){
            password += alphaNumeric.charAt(new Random().nextInt(alphaNumeric.length()));
        }

        return password;
    }

    public String passwordForSms(){

        String password = "";

        for (int i = 0; i < 5; i++){
            password += numeric.charAt(new Random().nextInt(numeric.length()));
        }

        return password;
    }

}
