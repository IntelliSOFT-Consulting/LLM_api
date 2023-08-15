package com.intellisoft.llm.service.summary;

import com.intellisoft.llm.model.NcdMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryService {
    Logger ncdMetaDataLogger = LoggerFactory.getLogger("ncdMetaData");

    public void logMetaData(NcdMetaData ncdMetaData) {
        String logEntry = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s",
                LocalDateTime.now(),
                "MetaData id", ncdMetaData.getId(),
                "searchSubject", ncdMetaData.getSearchSubject(),
                "Observed Time Of Start Use", ncdMetaData.getObservedTimeStartUse(),
                "Observed Time Last Use", ncdMetaData.getObservedTimeLastUse(),
                "Duration Of Engagement", ncdMetaData.getDurationOfEngagement(),
                "Ncd User Most Interested In", ncdMetaData.getNcdUserMostInterestedIn(),
                "User Contact", ncdMetaData.getPhoneNumber());

        ncdMetaDataLogger.info(logEntry);
    }


}

