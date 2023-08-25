package com.intellisoft.llm.service.summary;

import com.intellisoft.llm.model.NcdMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class SummaryService {
    private static final String CSV_FILE_PATH = "summaries/meta_data.csv";

    public void logMetaData(NcdMetaData ncdMetaData) {
        try (FileWriter csvWriter = new FileWriter(CSV_FILE_PATH, true)) {
            File file = new File(CSV_FILE_PATH);

            // Check if the file is empty or doesn't exist, and add column labels if necessary
            if (file.length() == 0) {
                csvWriter.append("ID,PhoneNumber,SearchSubject,ObservedTimeStartUse,ObservedTimeLastUse,DurationOfEngagement,NcdUserMostInterestedIn,contentSearched\n");
            }

            // Build the CSV row string
            String csvRow = String.format("%s,%s,%s,%s,%s,%s,%s,,%s",
                    ncdMetaData.getId(), ncdMetaData.getPhoneNumber(), ncdMetaData.getSearchSubject(),
                    ncdMetaData.getObservedTimeStartUse(), ncdMetaData.getObservedTimeLastUse(),
                    ncdMetaData.getDurationOfEngagementCsv(), ncdMetaData.getNcdUserMostInterestedIn(), ncdMetaData.getContentSearched());

            // Write the CSV row to the file
            csvWriter.append(csvRow).append("\n");

            // Flush and close the writer
            csvWriter.flush();
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}
