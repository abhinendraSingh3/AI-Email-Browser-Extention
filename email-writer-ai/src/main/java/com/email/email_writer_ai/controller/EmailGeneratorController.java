package com.email.email_writer_ai.controller;


import com.email.email_writer_ai.dto.EmailRequest;
import com.email.email_writer_ai.service.EmailGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailGeneratorController {

    private final EmailGeneratorService emailGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity <String> generateEmail(@RequestBody EmailRequest emailRequest ) {

        String response=emailGeneratorService.generateEmailReply(emailRequest);

        return ResponseEntity.ok(response);

    }
}
