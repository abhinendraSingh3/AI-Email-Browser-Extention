package com.email.email_writer_ai.service;

import com.email.email_writer_ai.EmailRequest;
import org.springframework.stereotype.Service;

@Service
public class EmailGeneratorService {

    public String generateEmailReply(EmailRequest emailRequest){
    //Build prompt
    Sting Prompt=buildPrompt(emailRequest);

    //craft a request--we need the structure like api defined

    //do request and get response
    //return response


    }

    private String buildPrompt(EmailRequest emailRequest){
        StringBuilder prompt=new StringBuilder();
        prompt.append("Generate a professional email reply for the following email content. Please don't generate a subject line");
        if(emailRequest.getTone()!=null && !emailRequest.getTone().isEmpty() ){
            prompt.append("Use a ").append(emailRequest.getTone()).append("tone.");
        }
        prompt.append("\nOriginal Email\n").append(emailRequest.getEmailContent());
        return prompt.toString();


    }


}
