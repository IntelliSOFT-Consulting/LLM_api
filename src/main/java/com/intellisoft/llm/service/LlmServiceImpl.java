package com.intellisoft.llm.service;


import com.intellisoft.llm.gpt.request.GptRequestDto;
import com.intellisoft.llm.gpt.request.ChatGptRequest;
import com.intellisoft.llm.gpt.response.GptResponseDto;
import com.intellisoft.llm.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LlmServiceImpl implements LlmService {
    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private String apiKey;


    public GptResponseDto askChatGpt(GptRequestDto gptRequestDto) {

        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequest(
                                AppConstants.MODEL,
                                gptRequestDto.getMessages(),
                                AppConstants.TEMPERATURE,
                                AppConstants.MAX_TOKEN,
                                AppConstants.TOP_P)));
    }

    public HttpEntity<ChatGptRequest> buildHttpEntity(ChatGptRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(AppConstants.MEDIA_TYPE));
        headers.add(AppConstants.AUTHORIZATION, AppConstants.BEARER + apiKey);
        return new HttpEntity<>(chatRequest, headers);
    }

    public GptResponseDto getResponse(HttpEntity<ChatGptRequest> chatRequestHttpEntity) {
        ResponseEntity<GptResponseDto> responseEntity = restTemplate.postForEntity(
                AppConstants.URL,
                chatRequestHttpEntity,
                GptResponseDto.class);
        return responseEntity.getBody();
    }
}






