package com.intellisoft.llm.controller;

import com.intellisoft.llm.gpt.request.GptRequestDto;
import com.intellisoft.llm.gpt.response.GptResponseDto;
import com.intellisoft.llm.service.LlmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.ALL_VALUE;

@RestController
@RequestMapping("/api/llm")
@RequiredArgsConstructor
public class LlmController {
    @Autowired
    LlmService llmService;

    @PostMapping(path = "/askChatGpt", consumes = ALL_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GptResponseDto> askGpt(@RequestBody GptRequestDto gptRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(llmService.askChatGpt(gptRequestDto));
    }

}



