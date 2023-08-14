package com.intellisoft.llm.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfileUpdateRequestDto {
    private Integer age;
    private String gender;
    private String contact;
    private String heardAppFrom;
    private String phoneNumber;

}
