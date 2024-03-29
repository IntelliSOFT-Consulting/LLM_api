package com.intellisoft.llm.service;

import com.intellisoft.llm.authentication.LoginRequestDto;
import com.intellisoft.llm.authentication.ProfileUpdateRequestDto;
import com.intellisoft.llm.authentication.RegisterRequestDto;
import com.intellisoft.llm.model.Role;
import com.intellisoft.llm.model.User;
import com.intellisoft.llm.repository.RoleRepository;
import com.intellisoft.llm.repository.UserRepository;
import com.intellisoft.llm.util.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements com.intellisoft.llm.service.AuthenticationService {

    private final UserRepository userRepository;
    private final com.intellisoft.llm.service.JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationServiceImpl(
            UserRepository userRepository,
            com.intellisoft.llm.service.JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    @Override
    public User login(LoginRequestDto loginRequestDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());

        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, loginRequestDto.getPassword(), userDetails.getAuthorities()
        );

        String token = jwtService.generateToken(authenticationToken);

        if (token.isEmpty()) {
            throw new RuntimeException("Token generation failed");
        }

        Optional<User> optionalUser = userRepository.findByUsername(loginRequestDto.getUsername());

        User foundUser = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        foundUser.setToken(token);

        return foundUser;
    }

    @Override
    public User register(RegisterRequestDto registerRequestDto) {

        Optional<User> existingUser = userRepository.findByUsername(registerRequestDto.getUsername());

        if (existingUser.isPresent()) {
            throw new UsernameAlreadyExistsException("Username is already taken");
        }

        List<Role> roles = registerRequestDto.getRoles().stream()
                .map(role -> roleRepository.save(role))
                .collect(Collectors.toList());

        User user = User.builder()
                .uniqueId(registerRequestDto.getUniqueID())
                .dob(registerRequestDto.getDob())
                .gender(registerRequestDto.getGender())
                .contact(registerRequestDto.getPhoneNumber())
                .heardAppFrom(registerRequestDto.getHeardAppFrom())
                .username(registerRequestDto.getUsername())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .roles(roles)
                .location(registerRequestDto.getLocation())
                .specificLocation(registerRequestDto.getSpecificLocation())
                .build();

        userRepository.save(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                registerRequestDto.getUsername(), registerRequestDto.getPassword()
        );
        String token = jwtService.generateToken(authenticationToken);

        user.setToken(token);
        return user;
    }

    @Override
    public String reset(String username, String newPassword) {
        // Update the user's password
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User foundUser = user.get();

        foundUser.setPassword(newPassword);
        userRepository.save(foundUser);

        return "Password reset successful for user: " + username;
    }

    @Override
    public User updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto) {
        Optional<User> user = userRepository.findByContact(profileUpdateRequestDto.getPhoneNumber());

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User foundUser = user.get();

        foundUser.setDob(profileUpdateRequestDto.getDob());
        foundUser.setGender(profileUpdateRequestDto.getGender());
        foundUser.setContact(profileUpdateRequestDto.getContact());
        foundUser.setHeardAppFrom(profileUpdateRequestDto.getHeardAppFrom());

        userRepository.save(foundUser);

        return foundUser;
    }
}
