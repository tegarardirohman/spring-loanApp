package com.enigmacamp.loanpp.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApprovalRequest {
    private String loanTransactionId;
    private Long interestRates;
}
