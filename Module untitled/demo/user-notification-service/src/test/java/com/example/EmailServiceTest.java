package com.example;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.example.services.EmailService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import jakarta.mail.MessagingException;

@SpringBootTest
@EmbeddedKafka(partitions = 1)
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
@ActiveProfiles("test") 
public class EmailServiceTest {
    @Value("${spring.mail.username}")
    private String USERNAME;
    @Value("${spring.mail.password}")
    private String PASSWORD;
    @Value("${spring.mail.host}")
    private String HOST;
    @Value("${spring.mail.port}")
    private int PORT;

    @Autowired
    private EmailService service;

    private GreenMail greenMail;

    @BeforeEach
    @SuppressWarnings("unused")
    void beforeEach() {
        ServerSetup setup = new ServerSetup(PORT, HOST, "smtp"); // Example port
        greenMail = new GreenMail(setup);
        greenMail.setUser(USERNAME, PASSWORD);
        greenMail.start();
    }

    @Test
    void shouldSendEmail() throws IOException, MessagingException{
        String from = USERNAME;
        String to = "test@example.com";
        String subject = "Java spring application test message";
        String text = "This is a test message sended from unit test java application";
        
        service.sendEmail(from, to, subject, text);

        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        assertEquals(1, greenMail.getReceivedMessages().length);
        assertEquals(from, greenMail.getReceivedMessages()[0].getFrom()[0].toString());
        assertEquals(subject, greenMail.getReceivedMessages()[0].getSubject());
        assertEquals(text, greenMail.getReceivedMessages()[0].getContent().toString().trim());
    }
}
