package com.Jabaklahbackend.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements EmailSender{

    private final static Logger logger = LoggerFactory.getLogger(EmailSenderService.class);


    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void send(String to, String email){

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Here's Your Password ");
            helper.setFrom("Jaback_Lah@mail.com");
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e){

            logger.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");

        }
    }
}

