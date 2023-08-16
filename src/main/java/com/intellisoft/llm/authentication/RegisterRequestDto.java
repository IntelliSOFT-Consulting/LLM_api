package com.intellisoft.llm.authentication;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.intellisoft.llm.model.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequestDto {

    private String uniqueID;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String location;
    private String specificLocation;
    private String fullName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date signUpDate;
    private String heardAppFrom;
    private String username;
    private String password;
    private List<Role> roles;
}
