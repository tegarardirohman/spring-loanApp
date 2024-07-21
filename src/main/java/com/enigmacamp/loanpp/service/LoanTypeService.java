package com.enigmacamp.loanpp.service;

import com.enigmacamp.loanpp.model.dto.request.LoanTypeRequest;
import com.enigmacamp.loanpp.model.dto.response.LoanTypeResponse;
import com.enigmacamp.loanpp.model.entity.LoanType;

import java.util.List;

public interface LoanTypeService {
    LoanTypeResponse save(LoanTypeRequest request);
    LoanTypeResponse getById(String id);
    List<LoanTypeResponse> findAll();
    LoanTypeResponse update(LoanTypeRequest request);
    void delete(String id);
}
