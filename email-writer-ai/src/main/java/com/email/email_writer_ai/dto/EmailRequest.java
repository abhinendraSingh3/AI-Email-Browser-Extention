package com.email.email_writer_ai.dto;

import lombok.Data;

@Data //this will help to create getter setter constructor etc
public class EmailRequest {

    private String emailContent;
    private String tone;
}
