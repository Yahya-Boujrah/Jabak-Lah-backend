package com.Jabaklahbackend.services;


import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
public class SmsService {

    @Value("${vonage.api.key}")
    private String key;

    @Value("${vonage.api.secret}")
    private String secret;


    public String sendSMS(String phone, String content){


        VonageClient vonageClient = VonageClient.builder()
                .apiKey(key)
                .apiSecret(secret)
                .build();

        phone = "+212" + phone.substring(1);

        TextMessage message = new TextMessage("JABAK LAH", phone, content);

        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(message);

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK)
        {
            System.out.println("message sent successfully to " + phone);
            return "message sent successfully";
        }
        else
        {
            throw new IllegalStateException("problem while sending message ");
        }

    }


}
