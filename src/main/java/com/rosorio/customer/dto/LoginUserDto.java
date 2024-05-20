package com.rosorio.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUserDto {
    @Email(message = "Email must be a valid email address")
    private String email;
    @NotNull(message = "Password must not be null")
    private String password;
}
