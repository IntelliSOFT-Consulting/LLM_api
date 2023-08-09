package com.intellisoft.llm.gpt.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GptRequestDto implements Serializable {
    private List<Message> messages;
}

