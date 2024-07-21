package com.enigmacamp.loanpp.service.impl;

import com.enigmacamp.loanpp.model.dto.request.LoanTypeRequest;
import com.enigmacamp.loanpp.model.dto.response.LoanTypeResponse;
import com.enigmacamp.loanpp.model.entity.LoanType;
import com.enigmacamp.loanpp.repository.LoanTypeRepository;
import com.enigmacamp.loanpp.service.LoanTypeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;

    @Override
    @Transactional
    public LoanTypeResponse save(LoanTypeRequest request) {
        LoanType loanType = LoanType.builder()
                .type(request.getType())
                .maxLoan(request.getMaxLoan())
                .build();

        return convertToLoanTypeResponse(loanTypeRepository.saveAndFlush(loanType));
    }

    @Override
    @Transactional
    public LoanTypeResponse getById(String id) {
        return convertToLoanTypeResponse(loanTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Type not found")));
    }

    @Override
    @Transactional
    public List<LoanTypeResponse> findAll() {
        return loanTypeRepository.findAll().stream().map(this::convertToLoanTypeResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoanTypeResponse update(LoanTypeRequest request) {
        LoanType loanType = loanTypeRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Type not found"));
        loanType.setType(request.getType());
        loanType.setMaxLoan(request.getMaxLoan());
        return convertToLoanTypeResponse(loanTypeRepository.saveAndFlush(loanType));
    }

    @Override
    @Transactional
    public void delete(String id) {
        LoanType loanType = loanTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Type not found"));
        loanTypeRepository.delete(loanType);
    }

    LoanTypeResponse convertToLoanTypeResponse(LoanType loanType) {
        return LoanTypeResponse.builder()
                .id(loanType.getId())
                .type(loanType.getType())
                .maxLoan(loanType.getMaxLoan().longValue())
                .build();
    }
}
