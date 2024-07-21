package com.enigmacamp.loanpp.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LoanTransactionResponse {
    private String id;
    private String loanTypeId;
    private String instalmentTypeId;
    private String customerId;
    private Long nominal;
    private Long approveAt;
    private String approveBy;
    private String approvalStatus;
    private List<TransactionDetailResponse> transactionDetailResponses;
    private Long createAt;
    private Long updatedAt;

}
