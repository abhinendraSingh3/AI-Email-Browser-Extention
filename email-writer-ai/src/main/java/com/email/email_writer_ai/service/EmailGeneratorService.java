package com.email.email_writer_ai.service;
import com.email.email_writer_ai.EmailRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailGeneratorService {

    private final WebClient webClient;


    //need api url
//    @Value is a Spring annotation used to inject values into variables.
//    Most commonly, it is used to read values from application.properties or application.yml.
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    //need api key
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String generateEmailReply(EmailRequest emailRequest){
    //Build prompt
    String prompt=buildPrompt(emailRequest);

    //craft a request--we need the structure like api defined
        Map<String, Object> requestBody=Map.of(
                "contents",new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text",prompt)
                        })
                }
        );

    //do request and get response
        String response=webClient.post()
                .uri(geminiApiUrl+geminiApiKey)
                .

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
