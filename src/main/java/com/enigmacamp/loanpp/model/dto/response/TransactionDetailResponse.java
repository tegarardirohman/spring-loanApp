package com.enigmacamp.loanpp.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDetailResponse {
    private String id;
    private Long transactionDate;
    private Long nominal;
    private String loanStatus;
    private Long createAt;
    private Long updatedAt;
}
