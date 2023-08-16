package com.intellisoft.llm.controller;


import com.intellisoft.llm.authentication.LoginRequestDto;
import com.intellisoft.llm.authentication.ProfileUpdateRequestDto;
import com.intellisoft.llm.authentication.RegisterRequestDto;
import com.intellisoft.llm.model.User;
import com.intellisoft.llm.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(path = "/login", consumes = ALL_VALUE)
    public ResponseEntity<User> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(loginRequestDto));
    }

    @PostMapping(path = "/register", consumes = ALL_VALUE)
    public ResponseEntity<User> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(registerRequestDto));
    }

    @PostMapping(path = "/reset", consumes = ALL_VALUE)
    public ResponseEntity<String> reset(@RequestParam String username, @RequestParam String newPassword) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.reset(username, newPassword));
    }

    @PostMapping(path = "/updateProfile", consumes = ALL_VALUE)
    public ResponseEntity<User> updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.updateProfile(profileUpdateRequestDto));
    }

}
