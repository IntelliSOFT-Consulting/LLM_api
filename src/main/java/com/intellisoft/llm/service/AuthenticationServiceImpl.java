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
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationServiceImpl(
            UserRepository userRepository,
            JwtService jwtService,
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
    public String login(LoginRequestDto loginRequestDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());

        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, loginRequestDto.getPassword(), userDetails.getAuthorities()
        );
//        authenticationManager.authenticate(authenticationToken);

        return jwtService.generateToken(authenticationToken);
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
                .age(registerRequestDto.getAge())
                .gender(registerRequestDto.getGender())
                .contact(registerRequestDto.getPhoneNumber())
                .heardAppFrom(registerRequestDto.getHeardAppFrom())
                .username(registerRequestDto.getUsername())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .roles(roles)
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
        Optional<User> user = userRepository.findByPhoneNumber(profileUpdateRequestDto.getPhoneNumber());

        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        User foundUser = user.get();

        foundUser.setAge(profileUpdateRequestDto.getAge());
        foundUser.setGender(profileUpdateRequestDto.getGender());
        foundUser.setContact(profileUpdateRequestDto.getContact());
        foundUser.setHeardAppFrom(profileUpdateRequestDto.getHeardAppFrom());

        userRepository.save(foundUser);

        return foundUser;
    }
}
