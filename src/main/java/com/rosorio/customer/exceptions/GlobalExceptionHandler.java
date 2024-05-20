package com.rosorio.customer.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String DESCRIPTION_FIELD_NAME = "description";

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    exception.getMessage());
            errorDetail.setProperty(DESCRIPTION_FIELD_NAME, "The username or password is incorrect");
        } else if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()),
                    exception.getMessage());
            errorDetail.setProperty(DESCRIPTION_FIELD_NAME, "The account is locked");
        } else if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    exception.getMessage());
            errorDetail.setProperty(DESCRIPTION_FIELD_NAME, "You are not authorized to access this resource");
        } else if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()),
                    exception.getMessage());
            errorDetail.setProperty(DESCRIPTION_FIELD_NAME, "The JWT signature is invalid");
        } else if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION_FIELD_NAME, "The JWT token has expired");
        } else if (exception instanceof MethodArgumentNotValidException methodException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), "");errorDetail.setProperty(DESCRIPTION_FIELD_NAME, Optional.of(methodException)
                            .map(MethodArgumentNotValidException::getBindingResult)
                            .map(Errors::getAllErrors)
                            .map(errors -> errors.stream()
                                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                    .reduce((s, s2) -> s + ", " + s2)
                                    .orElse("Invalid request"))
                            .orElse("Invalid request"));
        } else if (exception instanceof IllegalArgumentException illegalArgumentException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION_FIELD_NAME, illegalArgumentException.getMessage());
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception.getMessage());
            errorDetail.setProperty(DESCRIPTION_FIELD_NAME, "Unknown internal server error.");
        }

        return errorDetail;
    }
}
