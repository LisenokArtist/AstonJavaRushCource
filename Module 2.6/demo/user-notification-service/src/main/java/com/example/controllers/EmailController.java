package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.datamodels.models.mail.MailShort;
import com.example.services.EmailService;
// http://localhost:8080/swagger-ui/index.html
// http://localhost:8080/v3/api-docs
@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private final EmailService service;

    
    public EmailController(EmailService service){
        this.service = service;
    }


    /**
     * Отправляет сообщение на почту
     * @param body Тело сообщения
     * @return Сообщение о статусе отправке сообщения
     */
    @GetMapping("/send/{body}")
    public ResponseEntity<EntityModel<MailShort>> send(@PathVariable MailShort body){
        this.service.sendEmail(body);
        
        EntityModel<MailShort> model = EntityModel.of(body,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class)
                .send(body))
                .withSelfRel());

        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    /**
     * Отправляет сообщение на почту
     * @param from отправитель
     * @param to получатель
     * @param subject тема
     * @param text сообщение
     * @return Сообщение о статусе отправке сообщения
     */
    @GetMapping("/send/")
    public ResponseEntity<EntityModel<MailShort>> send(
        @RequestParam String from,
        @RequestParam String to,
        @RequestParam String subject,
        @RequestParam String text
    ){
        MailShort body = new MailShort(from, to, subject, text);
        this.service.sendEmail(body);

        EntityModel<MailShort> model = EntityModel.of(body,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class)
                .send(from, to, subject, text))
                .withSelfRel());

        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }
}
