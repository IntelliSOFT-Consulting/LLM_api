package com.intellisoft.llm.service;


import com.intellisoft.llm.bard.BardApiResponseDto;
import com.intellisoft.llm.bard.BardRequestDto;
import com.intellisoft.llm.gpt.request.Message;
import com.intellisoft.llm.gpt.request.UpdateMetaDataDto;
import com.intellisoft.llm.gpt.request.GptRequestDto;
import com.intellisoft.llm.gpt.request.ChatGptRequest;
import com.intellisoft.llm.gpt.response.GptResponseDto;
import com.intellisoft.llm.model.NcdMetaData;
import com.intellisoft.llm.model.User;
import com.intellisoft.llm.repository.NcdMetaDataRepository;
import com.intellisoft.llm.service.summary.SummaryService;
import com.intellisoft.llm.util.AppConstants;
import com.intellisoft.llm.util.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LlmServiceImpl implements LlmService {
    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private String apiKey;

    @Autowired
    NcdMetaDataRepository ncdMetaDataRepository;

    @Autowired
    SummaryService summaryService;


    public GptResponseDto askChatGpt(GptRequestDto gptRequestDto) {

        // extract raw searched Question::

        String extractedContents = "";

        List<Message> messages = gptRequestDto.getMessages();

        for (Message message : messages) {
            extractedContents += message.getContent() + "\n";
        }

        // store metadata from user:

        NcdMetaData ncdMetaData = new NcdMetaData();

        ncdMetaData.setPhoneNumber(gptRequestDto.getPhoneNumber());
        ncdMetaData.setSearchSubject(gptRequestDto.getSearchSubject());
        ncdMetaData.setContentSearched(extractedContents);

        ncdMetaDataRepository.save(ncdMetaData);

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
        headers.add(AppConstants.AUTHORIZATION, AppConstants.BEARER + (AppConstants.API_KEY1+AppConstants.API_KEY2+AppConstants.API_KEY3));
        return new HttpEntity<>(chatRequest, headers);
    }

    public GptResponseDto getResponse(HttpEntity<ChatGptRequest> chatRequestHttpEntity) {
        ResponseEntity<GptResponseDto> responseEntity = restTemplate.postForEntity(
                AppConstants.URL,
                chatRequestHttpEntity,
                GptResponseDto.class);
        return responseEntity.getBody();
    }

    @Override
    public NcdMetaData updateMetaData(String phoneNumber, UpdateMetaDataDto updateMetaDataDto) {

        Optional<NcdMetaData> ncdMetaData = ncdMetaDataRepository.findMostRecentByPhoneNumberAndSubject(phoneNumber, updateMetaDataDto.getSearchSubject());

        if (ncdMetaData.isEmpty()) {
            throw new ResourceNotFoundException("Metadata info not found with phone number: " + phoneNumber);
        }

        NcdMetaData foundMetaData = ncdMetaData.get();

        // Parse the old durationOfEngagement value to get the old Duration
        Duration oldDuration = foundMetaData.getDurationOfEngagement();

        if (oldDuration == null) {
            oldDuration = Duration.ZERO;
        }

        // Parse the new duration string from updateMetaDataDto
        String newDurationString = updateMetaDataDto.getDurationOfEngagement(); // Replace with the received new value
        Duration newDuration = parseDurationString(newDurationString);

        // Calculate the new total duration by adding the old and new durations
        Duration totalDuration = oldDuration.plus(newDuration);

        // Convert the new total duration to a string representation for logging to CSV summary file
        long totalHours = totalDuration.toHours();
        long totalMinutes = totalDuration.toMinutesPart();
        String totalDurationString = totalHours + " hour" + (totalHours != 1 ? "s" : "") + " " + totalMinutes + " minute" + (totalMinutes != 1 ? "s" : "");

        // Update the durationOfEngagement field with the new total duration
        foundMetaData.setDurationOfEngagement(totalDuration);

        //set other fields from the updateMetaDataDto
        foundMetaData.setObservedTimeLastUse(updateMetaDataDto.getObservedTimeLastUse());
        foundMetaData.setObservedTimeStartUse(updateMetaDataDto.getObservedTimeStartUse());

        //get count of ncd most searched by the user:
        int ncdUserMostInterestedIn = ncdMetaDataRepository.countByPhoneNumberAndSearchSubject(phoneNumber, updateMetaDataDto.getSearchSubject());

        //add ncd data to CSV Summary:

        NcdMetaData summaryData = new NcdMetaData();

        summaryData.setId(foundMetaData.getId());
        summaryData.setPhoneNumber(foundMetaData.getPhoneNumber());
        summaryData.setSearchSubject(foundMetaData.getSearchSubject());
        summaryData.setObservedTimeStartUse(updateMetaDataDto.getObservedTimeStartUse());
        summaryData.setObservedTimeLastUse(updateMetaDataDto.getObservedTimeLastUse());
        summaryData.setDurationOfEngagementCsv(totalDurationString);
        summaryData.setNcdUserMostInterestedIn(String.valueOf(ncdUserMostInterestedIn));
        summaryData.setContentSearched(foundMetaData.getContentSearched());

        summaryService.logMetaData(summaryData);

        // Save the updated entity
        return ncdMetaDataRepository.save(foundMetaData);
    }

    private Duration parseDurationString(String durationString) {
        String[] parts = durationString.split("\\s+");
        if (parts.length != 4 || !"hour".equals(parts[1]) || !"minutes".equals(parts[3])) {
            throw new IllegalArgumentException("Invalid duration format: " + durationString);
        }

        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[2]);
            return Duration.ofHours(hours).plusMinutes(minutes);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid duration format: " + durationString);
        }
    }

    @Override
    public BardApiResponseDto askGoogleBard(BardRequestDto bardRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BardRequestDto> request = new HttpEntity<>(bardRequestDto, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BardApiResponseDto> response = restTemplate.postForEntity(AppConstants.BARD_URL, request, BardApiResponseDto.class);

        return response.getBody();
    }




}






