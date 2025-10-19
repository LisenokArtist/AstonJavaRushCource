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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
// http://localhost:8081/swagger-ui/index.html
// http://localhost:8081/v3/api-docs
@RestController
@RequestMapping("/api/email")
@Tag(
    name = "Email Controller", 
    description = "Operations related to sending email messages")
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
    @Operation(
        summary = "Sends email",
        description="Sends a message to your email address",
        requestBody=@RequestBody(description = "Short mail model to send", required = true)
    )
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
    @Operation(
        summary = "Sends email",
        description="Sends a message to your email address"
    )
    @GetMapping("/send/")
    public ResponseEntity<EntityModel<MailShort>> send(
        @Parameter(description = "Email sended from", example = "example@mail.com")
        @RequestParam(required=false) String from,
        @Parameter(description = "Email sended to", example = "example@mail.com")
        @RequestParam String to,
        @Parameter(description = "Title of content", example = "The most important title")
        @RequestParam String subject,
        @Parameter(description = "Content of mail", example = "The quick brown fox jumps over the lazy dog")
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
