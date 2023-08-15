package com.intellisoft.llm.gpt.request;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UpdateMetaDataDto implements Serializable {
    private String searchSubject;
    private LocalDateTime observedTimeStartUse;
    private LocalDateTime observedTimeLastUse;
    private String durationOfEngagement;
}