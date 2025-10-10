package com.example.datamodels.models.mail;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailShort extends RepresentationModel<MailShort> {
    private final String from;
    private final String to;
    private final String subject;
    private final String text;
}
