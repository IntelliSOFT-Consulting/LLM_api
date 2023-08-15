package com.intellisoft.llm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class NcdMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(columnDefinition = "TEXT")
    String phoneNumber;

    @Column(columnDefinition = "TEXT")
    String searchSubject;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime observedTimeStartUse;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime observedTimeLastUse;

    @Column(columnDefinition = "TEXT")
    Duration durationOfEngagement;

    @Column(columnDefinition = "TEXT")
    String ncdUserMostInterestedIn;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @Column(columnDefinition = "TEXT")
    String durationOfEngagementCsv;
}
