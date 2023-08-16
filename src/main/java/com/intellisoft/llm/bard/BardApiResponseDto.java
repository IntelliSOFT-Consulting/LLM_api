package com.intellisoft.llm.bard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class BardApiResponseDto implements Serializable{
    @JsonProperty("choices")
    private List<ChoiceDto> choiceList;

    @JsonProperty("content")
    private String content;

    @JsonProperty("conversation_id")
    private String conversationId;

    @JsonProperty("factuality_queries")
    private List<String> factualityQueries;

    @JsonProperty("images")
    private List<String> images;

    @JsonProperty("links")
    private List<String> links;

    @JsonProperty("program_lang")
    private String programLang;

    @JsonProperty("response_id")
    private String responseId;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("text_query")
    private List<JsonNode> textQuery;

    @Data
    public static class ChoiceDto implements Serializable{
        @JsonProperty("content")
        private List<String> content;
        @JsonProperty("id")
        private String id;
    }
}
