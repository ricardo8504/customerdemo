package com.rosorio.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rosorio.customer.config.security.Authority;
import com.rosorio.customer.config.validators.ValidEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
@Jacksonized
public class RegisterUserDto {
    private String id;

    @JsonProperty("email")
    @Email(message = "Email must be a valid email address")
    private String email;

    @JsonProperty("password")
    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @JsonProperty("fullName")
    @NotNull(message = "Fullname must not be null")
    private String fullName;

    @JsonProperty("authorities")
    @NotEmpty(message = "Authorities must not be empty")
    @ValidEnum(enumClass = Authority.class, message = "Invalid authorities")
    private List<String> authorities;
}
