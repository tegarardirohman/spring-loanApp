package com.enigmacamp.loanpp.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanTypeResponse {
    private String id;
    private String type;
    private Long maxLoan;
}
