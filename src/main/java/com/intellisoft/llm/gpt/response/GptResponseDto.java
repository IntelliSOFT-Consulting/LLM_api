package com.intellisoft.llm.gpt.response;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class GptResponseDto implements Serializable {
    private String id;
    private String object;
    private String model;
    private Long created;
    private List<Choice> choices;
    private UsageInfo usage;

    @Data
    public static class UsageInfo implements Serializable {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }
}




