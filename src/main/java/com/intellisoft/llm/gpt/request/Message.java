package com.intellisoft.llm.gpt.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class Message implements Serializable {
    private String role;
    private String content;
}