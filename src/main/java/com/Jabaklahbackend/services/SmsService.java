package com.Jabaklahbackend.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

//    @Value("${twilio.account.SID}")
    private String SID;

//    @Value("${twilio.account.TOKEN}")
    private String TOKEN;


    public String sendSMS(String phone, String content){
        Twilio.init(SID,TOKEN);
        Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber(""), content).create();

        return "message sent successfully";
    }


}
