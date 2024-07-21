package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.model.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.dto.response.InstalmentTypeRespone;
import com.enigmacamp.loanpp.service.InstalmentTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instalment-types")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class InstalmentTypeController {
    private final InstalmentTypeService instalmentTypeService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')")
    ResponseEntity<CommonResponse<InstalmentTypeRespone>> createInstalmentType(@Validated @RequestBody InstalmentTypeRequest request) {
        InstalmentTypeRespone instalmentTypeRespone = instalmentTypeService.createInstalmentType(request);

        CommonResponse<InstalmentTypeRespone> commonResponse = CommonResponse.<InstalmentTypeRespone>builder()
                .message("Successfully created instalment type")
                .data(Optional.ofNullable(instalmentTypeRespone))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);

    }

    @GetMapping("/{id}")
    ResponseEntity<CommonResponse<InstalmentTypeRespone>> getInstalmentTypes(@PathVariable String id) {
        InstalmentTypeRespone instalmentTypeRespone = instalmentTypeService.getInstalmentTypeById(id);

        CommonResponse<InstalmentTypeRespone> commonResponse = CommonResponse.<InstalmentTypeRespone>builder()
                .message("Successfully retrieved instalment type")
                .data(Optional.ofNullable(instalmentTypeRespone))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping()
    ResponseEntity<CommonResponse<List<InstalmentTypeRespone>>> getAllInstalmentTypes() {
        List<InstalmentTypeRespone> instalmentTypeRespones = instalmentTypeService.getAllInstalment();

        CommonResponse<List<InstalmentTypeRespone>> commonResponse = CommonResponse.<List<InstalmentTypeRespone>>builder()
                .message("Successfully retrieved instalment type")
                .data(Optional.ofNullable(instalmentTypeRespones))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }


    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')")
    ResponseEntity<CommonResponse<InstalmentTypeRespone>> updateInstalmentType(@Validated @RequestBody InstalmentTypeRequest request) {
        InstalmentTypeRespone instalmentTypeRespone = instalmentTypeService.updateInstalmentType(request);

        CommonResponse<InstalmentTypeRespone> commonResponse = CommonResponse.<InstalmentTypeRespone>builder()
                .message("Successfully updated instalment type")
                .data(Optional.ofNullable(instalmentTypeRespone))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')")
    ResponseEntity<CommonResponse<String>> updateInstalmentType(@Validated @PathVariable String id) {
        instalmentTypeService.deleteInstalmentType(id);

        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .message("Successfully deleted instalment type")
                .data(Optional.empty())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);

    }


}
