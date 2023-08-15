package com.intellisoft.llm.gpt.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GptRequestDto implements Serializable {
    private String phoneNumber;
    private String searchSubject;
    private List<Message> messages;
}

