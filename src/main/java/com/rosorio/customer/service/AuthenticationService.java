package com.rosorio.customer.service;

import com.rosorio.customer.config.security.JwtService;
import com.rosorio.customer.dto.LoginUserDto;
import com.rosorio.customer.dto.RegisterUserDto;
import com.rosorio.customer.persistence.entities.User;
import com.rosorio.customer.persistence.repositories.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService( UserRepository userRepository, AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder, JwtService jwtService ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User signup(RegisterUserDto registerUserDto) {
        User userExists = userRepository.findByEmail(registerUserDto.getEmail())
                .orElse(null);
        if (userExists != null) {
            throw new IllegalArgumentException("User already exists with email: " + registerUserDto.getEmail());
        }

        if (StringUtils.isBlank(registerUserDto.getId())) {
            userExists = userRepository.findById(registerUserDto.getId())
                    .orElse(null);
            if (userExists != null)
                throw new IllegalArgumentException("User already exists with id: " + registerUserDto.getId());
        }

        User user = User.builder()
                .id(registerUserDto.getId())
                .fullName(registerUserDto.getFullName())
                .email(registerUserDto.getEmail())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .authorities(registerUserDto.getAuthorities())
                .build();

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getId(),
                        input.getPassword()
                )
        );
        return user;
    }

    public String generateJWTToken(User user) {
        return jwtService.generateToken(user);
    }

    public void createDefaultAdminUser(String defaultAdminEmail, String defaultAdminPassword,
                                       List<String> authorityList) {
      userRepository.findByEmail(defaultAdminEmail)
              .ifPresent( userRepository::delete );

        signup(RegisterUserDto.builder()
                .email(defaultAdminEmail)
                .password(defaultAdminPassword)
                .fullName("Admin")
                .authorities(authorityList)
                .build());
    }
}
