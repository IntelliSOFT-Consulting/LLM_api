package com.intellisoft.llm.bard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class BardRequestDto implements Serializable {
    @JsonProperty("input_text")
    private String inputText;
}

