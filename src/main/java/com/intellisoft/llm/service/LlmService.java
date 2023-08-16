package com.intellisoft.llm.service;


import com.intellisoft.llm.bard.BardApiResponseDto;
import com.intellisoft.llm.bard.BardRequestDto;
import com.intellisoft.llm.gpt.request.GptRequestDto;
import com.intellisoft.llm.gpt.request.UpdateMetaDataDto;
import com.intellisoft.llm.gpt.response.GptResponseDto;
import com.intellisoft.llm.model.NcdMetaData;

public interface LlmService {

    GptResponseDto askChatGpt(GptRequestDto gptRequestDto);

    NcdMetaData updateMetaData(String phoneNumber, UpdateMetaDataDto updateMetaDataDto);

    BardApiResponseDto askGoogleBard(BardRequestDto bardRequestDto);
}
