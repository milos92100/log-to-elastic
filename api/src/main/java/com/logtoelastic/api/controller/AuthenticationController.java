package com.logtoelastic.api.controller;

import com.logtoelastic.api.request.AuthenticationRequest;
import com.logtoelastic.api.response.ApiError;
import com.logtoelastic.api.response.ApiResponse;
import com.logtoelastic.api.service.LoginService;
import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.logtoelastic.domain.authentication.AuthenticationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AuthenticationController {
    private static final Logger logger = LogManager.getLogger(GreetingController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping("/authentication")
    public ApiResponse authenticate(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResult result = loginService.login(
                new AuthenticationCredentials(request.getUsername(), request.getUsername())
        );

        logger.info("try login with user: {}, and pass: {}", request.getUsername(), request.getPassword());
        return ApiResponse.failure(List.of(new ApiError("TEST", "A test")));
    }
}
