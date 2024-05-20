package com.rosorio.customer.config.global;

import com.rosorio.customer.config.security.Authority;
import com.rosorio.customer.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    @Value("${default.admin.email}")
    private String defaultAdminEmail;
    private final AuthenticationService authenticationService;

    public ApplicationStartup(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        authenticationService.createDefaultAdminUser(defaultAdminEmail, defaultAdminPassword,
                List.of(Authority.CREATE_USER.name(), Authority.READ_USER.name(), Authority.READ_ALL_USERS.name()));
        log.info("Default admin user created with email: {} and password: {}", defaultAdminEmail, defaultAdminPassword);
    }
}
