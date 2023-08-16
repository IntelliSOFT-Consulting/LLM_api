package com.intellisoft.llm.controller;

import com.intellisoft.llm.bard.BardApiResponseDto;
import com.intellisoft.llm.bard.BardRequestDto;
import com.intellisoft.llm.gpt.request.GptRequestDto;
import com.intellisoft.llm.gpt.request.UpdateMetaDataDto;
import com.intellisoft.llm.gpt.response.GptResponseDto;
import com.intellisoft.llm.model.NcdMetaData;
import com.intellisoft.llm.service.LlmService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.ALL_VALUE;

@Slf4j
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

    @PostMapping(path = "/askGoogle", consumes = ALL_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BardApiResponseDto> askBard(@RequestBody BardRequestDto bardRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(llmService.askGoogleBard(bardRequestDto));
    }

    @Operation(summary = "Update NDC MetdaData")
    @PutMapping(path = "/updateMetaData/{phoneNumber}", consumes = ALL_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NcdMetaData> updateMetaData(@PathVariable String phoneNumber, @RequestBody UpdateMetaDataDto updateMetaDataDto) {
        return ResponseEntity.status(HttpStatus.OK).body(llmService.updateMetaData(phoneNumber, updateMetaDataDto));
    }
}



