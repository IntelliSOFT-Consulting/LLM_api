package com.intellisoft.llm.service;


import com.intellisoft.llm.authentication.LoginRequestDto;
import com.intellisoft.llm.authentication.RegisterRequestDto;
import com.intellisoft.llm.model.User;

public interface AuthenticationService {
    String login(LoginRequestDto loginRequestDto);

    User register(RegisterRequestDto registerRequestDto);
}
