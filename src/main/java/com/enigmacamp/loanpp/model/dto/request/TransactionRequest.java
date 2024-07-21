package com.enigmacamp.loanpp.model.dto.request;

import com.enigmacamp.loanpp.model.entity.Customer;
import com.enigmacamp.loanpp.model.entity.InstalmentType;
import com.enigmacamp.loanpp.model.entity.LoanTransactionDetail;
import com.enigmacamp.loanpp.model.entity.LoanType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionRequest {
    private LoanType loanType;
    private InstalmentType instalmentType;
    private Customer customer;
    private Double nominal;
    private List<LoanTransactionDetail> loanTransactionDetails;

}
