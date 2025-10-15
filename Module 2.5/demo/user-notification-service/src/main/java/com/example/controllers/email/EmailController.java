package com.example.controllers.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.datamodels.models.mail.MailShort;
import com.example.services.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private final EmailService service;

    
    public EmailController(EmailService service){
        this.service = service;
    }


    @GetMapping("/send")
    public ResponseEntity<String> send(@RequestBody MailShort body){
        this.service.sendEmail(body);
        return new ResponseEntity<>("Mail has been sended!", HttpStatus.OK);
    }
}
