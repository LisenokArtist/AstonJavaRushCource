package com.example.datamodels.models.mail;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailShort {
    private final @Nullable String from;
    private final String to;
    private final String subject;
    private final String text;
}
