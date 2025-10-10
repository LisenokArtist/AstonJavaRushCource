package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.datamodels.models.mail.MailShort;
import com.example.datamodels.models.user.UserEvent;
import com.example.services.user.UserEventListener;

@Service
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private final UserEventListener listener;


    public EmailService(JavaMailSender mailSender, UserEventListener listener){
        this.mailSender = mailSender;
        this.listener = listener;

        registerEventHandlers();
    }

    
    /**
     * Подписываемся на события, которые
     * генерирует UserEventListener
     */
    private void registerEventHandlers(){
        listener.addActionListener(e -> {
            UserEventListener.Events event = UserEventListener.Events.valueOf(e.getActionCommand());
            switch(event){
                case onUserEvent -> onUserEvent((UserEvent)e.getSource());
            }
        });
    }

    /**
     * Обработчик события onUserEvent
     * @param userEvent событие
     */
    private void onUserEvent(UserEvent userEvent) {
        switch (userEvent.getEventType()) {
            case CREATE -> sendAccountCreatedEmail(userEvent.getUserEmail());
            case DELETE -> sendAccountDeletedEmail(userEvent.getUserEmail());
        }
    }

    /**
     * Отправляет системное сообщение
     * на почту о создании
     * @param to email получателя
     */
    private void sendAccountCreatedEmail(String to) {
        sendEmail(
            null, 
            to, 
            "Успешное создание аккаунта", 
            "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
    }

    /**
     * Отправляет системное сообщение
     * на почту о удалении
     * @param to email получателя
     */
    private void sendAccountDeletedEmail(String to) {
        sendEmail(
            null, 
            to, 
            "Удаление аккаунта", 
            "Здравствуйте! Ваш аккаунт был удалён.");
    }

    /**
     * Отправляет сообщение на email
     * @param mail модель сообщения
     */
    public void sendEmail(MailShort mail){
        this.sendEmail(
            mail.getFrom(), 
            mail.getTo(), 
            mail.getSubject(), 
            mail.getText());
    }

    /**
     * Отправляет сообщение на email
     * @param from от кого
     * @param to куда
     * @param subject тема
     * @param text сообщение
     */
    public void sendEmail(@Nullable String from, String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        if (from != null) message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}