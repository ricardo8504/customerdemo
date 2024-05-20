package com.rosorio.customer.config.security;

import com.rosorio.customer.persistence.entities.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private static final long DEFAULT_TOKEN_EXPIRATION_TIME = 5000L;
    private static final String JWT_SECRET_1 = "fb64218a0b2fd3647b3933f630c604979b9889fbbc7e1f2f4f4a009ca5bf4855";
    private static final String JWT_SECRET_2 = "862c4eeb85392bfb7912df0a2f30a7c298c2c113c4a17dd37839470c1a0b6faa";

    @InjectMocks
    JwtService jwtService;

    @Mock
    JwtServiceConfig jwtServiceConfig;

    @BeforeEach
    void setUp() {
        when(jwtServiceConfig.getJwtExpiration()).thenReturn(DEFAULT_TOKEN_EXPIRATION_TIME);
        when(jwtServiceConfig.getSecretKey()).thenReturn(JWT_SECRET_1);
    }

    @Test
    void testGenerateToken() {
        User user = User.builder()
                .id("1")
                .build();
        String token = jwtService.generateToken(user);
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void testTokenIsExpired() {
        when(jwtServiceConfig.getJwtExpiration()).thenReturn(0L);
        User user = User.builder()
                .id("1")
                .build();
        String token = jwtService.generateToken(user);
        assertNotNull(token);
        ExpiredJwtException exception = assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenValid(token, user));
        assertTrue(exception.getMessage().contains("JWT expired"));
    }

    @Test
    void testTokenIsInvalid_UserDoesNotMatch() {
        User user = User.builder()
                .id("1")
                .build();
        String token = jwtService.generateToken(user);
        assertNotNull(token);
        assertFalse(jwtService.isTokenValid(token, User.builder().id("2").build()));
    }

    @Test
    void testTokenInvalidSignature() {
        User user = User.builder()
                .id("1")
                .build();
        String token = jwtService.generateToken(user);
        assertNotNull(token);
        when(jwtServiceConfig.getSecretKey()).thenReturn(JWT_SECRET_2);
        SignatureException exception = assertThrows(SignatureException.class, () -> jwtService.isTokenValid(token, user));
        assertTrue(exception.getMessage().contains("JWT signature does not match"));
    }

}
