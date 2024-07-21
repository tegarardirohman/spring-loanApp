package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.model.dto.request.ApprovalRequest;
import com.enigmacamp.loanpp.model.dto.request.TransactionRequest;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.dto.response.LoanTransactionResponse;
import com.enigmacamp.loanpp.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.enigmacamp.loanpp.model.entity.LoanTransaction;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> createTransaction(@Validated @RequestBody TransactionRequest transaction) {
        LoanTransactionResponse loanTransaction = transactionService.createTransaction(transaction);

        CommonResponse<LoanTransactionResponse> commonResponse = CommonResponse.<LoanTransactionResponse>builder()
                .message("Successfully create new Loan Transaction")
                .data(Optional.of(loanTransaction))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> getTransaction(@PathVariable String id) {
        LoanTransactionResponse loanTransaction = transactionService.getTransactionById(id);

        CommonResponse<LoanTransactionResponse> commonResponse = CommonResponse.<LoanTransactionResponse>builder()
                .message("Transaction found")
                .data(Optional.of(loanTransaction))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping("/{adminId}/approve")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<LoanTransactionResponse>> approveTransaction(@PathVariable String adminId, @RequestBody ApprovalRequest approvalRequest) {
        transactionService.approveTransaction(adminId, approvalRequest);

        CommonResponse<LoanTransactionResponse> commonResponse = CommonResponse.<LoanTransactionResponse>builder()
                .message("Successfully Approve Loan")
                .data(Optional.empty())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping("/{trxDetailId}/pay")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<String>> payTransaction(@PathVariable String trxDetailId, @RequestBody MultipartFile file) {
        transactionService.payInstalment(trxDetailId, file);

        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .message("Successfully pay transaction")
                .data(Optional.empty())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }



}
