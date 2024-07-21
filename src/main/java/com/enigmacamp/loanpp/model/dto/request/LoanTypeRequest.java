package com.enigmacamp.loanpp.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanTypeRequest {
    private String id;
    private String type;
    private Double maxLoan;
}
