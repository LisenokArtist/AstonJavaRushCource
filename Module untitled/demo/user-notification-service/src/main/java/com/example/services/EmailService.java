package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.datamodels.models.mail.MailShort;

@Service
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendAccountCreatedEmail(String to) {
        sendEmail(
            null, 
            to, 
            "Успешное создание аккаунта", 
            "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
    }

    public void sendAccountDeletedEmail(String to) {
        sendEmail(
            null, 
            to, 
            "Удаление аккаунта", 
            "Здравствуйте! Ваш аккаунт был удалён.");
    }

    public void sendEmail(MailShort mail){
        this.sendEmail(
            mail.getFrom(), 
            mail.getTo(), 
            mail.getSubject(), 
            mail.getText());
    }

    public void sendEmail(@Nullable String from, String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        if (from != null) message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
