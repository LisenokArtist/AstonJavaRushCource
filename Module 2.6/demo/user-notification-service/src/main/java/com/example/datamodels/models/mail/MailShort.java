package com.example.datamodels.models.mail;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Details about a sended mail")
public class MailShort extends RepresentationModel<MailShort> {
    @Schema(description = "Email sended from", example = "example@mail.com")
    private final String from;
    
    @Schema(description = "Email sended to", example = "example@mail.com", required=true)
    private final String to;
    
    @Schema(description = "Title of content", example = "The most important title", required=true)
    private final String subject;
    
    @Schema(description = "Content of mail", example = "The quick brown fox jumps over the lazy dog", required=true)
    private final String text;
}
