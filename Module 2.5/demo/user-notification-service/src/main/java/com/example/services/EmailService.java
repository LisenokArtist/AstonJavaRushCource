package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendAccountCreatedEmail(String to) {
        sendEmail(to, 
        "Успешное создание аккаунта", 
        "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
    }

    public void sendAccountDeletedEmail(String to) {
        sendEmail(to, 
        "Удаление аккаунта", 
        "Здравствуйте! Ваш аккаунт был удалён.");
    }

    public void sendEmail(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
