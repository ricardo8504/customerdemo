package com.rosorio.customer.rs;

import com.rosorio.customer.config.security.JwtService;
import com.rosorio.customer.dto.LoginResponse;
import com.rosorio.customer.dto.LoginUserDto;
import com.rosorio.customer.dto.RegisterUserDto;
import com.rosorio.customer.persistence.entities.User;
import com.rosorio.customer.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    public AuthenticationRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return getLoginResponseEntity(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        return getLoginResponseEntity(authenticatedUser);
    }

    private ResponseEntity<LoginResponse> getLoginResponseEntity(User authenticatedUser) {
        String jwtToken = authenticationService.generateJWTToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .build();
        return ResponseEntity.ok(loginResponse);
    }
}
