package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.constant.APIUrl;
import com.enigmacamp.loanpp.model.dto.response.AvatarResponse;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.service.FileStorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class FileStorageController {
    private final FileStorageService fileStorageService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    @GetMapping("/{customerId}/avatar")
    public ResponseEntity<byte[]> getImage(@PathVariable String customerId) {

        return fileStorageService.getImage(customerId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    @PostMapping("/{customerId}/upload/avatar")
    public ResponseEntity<CommonResponse<AvatarResponse>> uploadAvatar(@RequestParam("avatar") MultipartFile avatar, @PathVariable String customerId) {
        String fileName = fileStorageService.storeFile(avatar, customerId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APIUrl.CUSTOMER_API + "/")
                .path(customerId)
                .path("/avatar")
                .toUriString();

        AvatarResponse avatarResponse = AvatarResponse.builder()
                .url(fileDownloadUri)
                .name(fileName)
                .build();

        CommonResponse<AvatarResponse> commonResponse = CommonResponse.<AvatarResponse>builder()
                .message("File uploaded successfully")
                .data(Optional.of(avatarResponse))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
}
