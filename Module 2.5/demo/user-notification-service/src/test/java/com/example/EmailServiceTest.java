package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.services.EmailService;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    @InjectMocks
    private EmailService service;

    @Mock
    private JavaMailSender sender;
    
    @Test
    void shouldSendEmail(){
        String to = "username@email.com";
        String subject = "Java spring application test message";
        String text = "This is a test message sended from unit test java application";

        service.sendEmail(to, subject, text);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(sender).send(captor.capture());
        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(to, sentMessage.getTo());
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(text, sentMessage.getText());
    }
}
