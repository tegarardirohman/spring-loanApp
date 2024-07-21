package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.constant.APIUrl;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.dto.response.UserResponse;
import com.enigmacamp.loanpp.model.entity.AppUser;
import com.enigmacamp.loanpp.model.entity.User;
import com.enigmacamp.loanpp.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable("id") String id) {
        UserResponse user = userService.findById(id);

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .message("Success")
                .data(Optional.of(user))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
