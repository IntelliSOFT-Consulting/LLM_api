package com.intellisoft.llm.service;


import com.intellisoft.llm.gpt.request.GptRequestDto;
import com.intellisoft.llm.gpt.response.GptResponseDto;

public interface LlmService {

    GptResponseDto askChatGpt(GptRequestDto gptRequestDto);
}
