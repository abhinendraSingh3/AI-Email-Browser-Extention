package com.email.email_writer_ai.service;
import com.email.email_writer_ai.dto.EmailRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import java.lang.String;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailGeneratorService {


    private final WebClient.Builder webClientBuilder;
    //need api url
//    @Value is a Spring annotation used to inject values into variables.Most commonly, it is used to read values from application.properties or application.yml.
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
        String response=webClientBuilder.build().post()
                .uri(geminiApiUrl+geminiApiKey)
                .header("Content-Type","application/json") //application/json literally just means "the data I'm sending is in JSON format
                .bodyValue(requestBody)
                .retrieve()//Go and execute it
                .bodyToMono(String.class)//give me the body as a String
               .block();//block and give me the actual value now

        //return response
        return extractResponseContent(response);

    }
    private String extractResponseContent(String response){
        try{
            ObjectMapper mapper=new ObjectMapper();
            JsonNode rootNode=mapper.readTree(response);//----parses that string into a tree structure in memory,JsonNode rootNode-he starting point of that tree, like the root folder
            return rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

        }
        catch(Exception e){
            return "Error processing request: "+e.getMessage();
        }
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
