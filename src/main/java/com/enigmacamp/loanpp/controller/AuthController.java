package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.constant.APIUrl;
import com.enigmacamp.loanpp.model.dto.request.AuthRequest;
import com.enigmacamp.loanpp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.dto.response.AuthResponse;
import com.enigmacamp.loanpp.model.entity.Role;
import com.enigmacamp.loanpp.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(APIUrl.AUTH_API)
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class AuthController {
    public final AuthService authService;

    @PostMapping("/signup/admin")
    public ResponseEntity<CommonResponse<AuthResponse>> register(@Validated @RequestBody AuthRequest request) {
        Role.ERole role = Role.ERole.ROLE_ADMIN;

        AuthResponse authResponse = authService.register(request, role);

        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .message("Register Sucess")
                .data(Optional.of(authResponse))
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<AuthResponse>> login(@Validated @RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.login(request);

        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .message("Login Success")
                .data(Optional.of(authResponse))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/signup/customer")
    public ResponseEntity<CommonResponse<AuthResponse>> registerCustomer(@Validated @RequestBody CustomerRequest request) {
        Role.ERole role = Role.ERole.ROLE_CUSTOMER;

        AuthResponse authResponse = authService.register(request, role);

        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .message("Register Sucess")
                .data(Optional.of(authResponse))
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



}
