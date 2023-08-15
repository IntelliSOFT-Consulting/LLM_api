package com.intellisoft.llm.repository;

import com.intellisoft.llm.model.NcdMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NcdMetaDataRepository extends JpaRepository<NcdMetaData, Long> {
    NcdMetaData findByPhoneNumberAndSearchSubject(String phoneNumber, String searchSubject);

    @Query("SELECT n FROM NcdMetaData n WHERE n.phoneNumber = ?1 AND n.searchSubject = ?2 ORDER BY n.createdAt DESC LIMIT 1")
    Optional<NcdMetaData> findMostRecentByPhoneNumberAndSubject(String phoneNumber, String searchSubject);

    int countByPhoneNumberAndSearchSubject(String phoneNumber, String searchSubject);

}