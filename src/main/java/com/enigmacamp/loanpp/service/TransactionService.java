package com.enigmacamp.loanpp.service;

import com.enigmacamp.loanpp.model.dto.request.ApprovalRequest;
import com.enigmacamp.loanpp.model.dto.request.TransactionRequest;
import com.enigmacamp.loanpp.model.dto.response.LoanTransactionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface TransactionService {
    LoanTransactionResponse createTransaction(TransactionRequest transactionRequest);
    LoanTransactionResponse getTransactionById(String id);
    void approveTransaction(String adminId, ApprovalRequest approvalRequest);
    void payInstalment(String transactionId, MultipartFile guaranteeImage);

}
