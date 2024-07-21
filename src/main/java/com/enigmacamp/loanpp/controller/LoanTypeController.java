package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.model.dto.request.LoanTypeRequest;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.dto.response.LoanTypeResponse;
import com.enigmacamp.loanpp.model.entity.LoanType;
import com.enigmacamp.loanpp.service.LoanTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/loan-types")
@SecurityRequirement(name = "Bearer Authentication")
public class LoanTypeController {
    private final LoanTypeService loanTypeService;

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<LoanTypeResponse>> createLoanType(@Validated @RequestBody LoanTypeRequest request) {
        LoanTypeResponse loanType = loanTypeService.save(request);

        CommonResponse<LoanTypeResponse> response = CommonResponse.<LoanTypeResponse>builder()
                .message("Successfully added loan type")
                .data(Optional.of(loanType))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<LoanTypeResponse>> getLoanTypeById(@PathVariable String id) {
        LoanTypeResponse loanTypeResponse = loanTypeService.getById(id);

        CommonResponse<LoanTypeResponse> response = CommonResponse.<LoanTypeResponse>builder()
                .message("Loan type found")
                .data(Optional.of(loanTypeResponse))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<List<LoanTypeResponse>>> getAllLoanTypes() {
        List<LoanTypeResponse> loanTypeResponses = loanTypeService.findAll();

        CommonResponse<List<LoanTypeResponse>> response = CommonResponse.<List<LoanTypeResponse>>builder()
                .message("All loan types found")
                .data(Optional.of(loanTypeResponses))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<LoanTypeResponse>> updateLoanType(@Validated @RequestBody LoanTypeRequest request) {
        LoanTypeResponse loanTypeResponse = loanTypeService.update(request);

        CommonResponse<LoanTypeResponse> response = CommonResponse.<LoanTypeResponse>builder()
                .message("Successfully Updated loan type")
                .data(Optional.of(loanTypeResponse))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<String>> deleteLoanType(@RequestParam String id) {
        loanTypeService.delete(id);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .message("Successfully deleted loan type")
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
