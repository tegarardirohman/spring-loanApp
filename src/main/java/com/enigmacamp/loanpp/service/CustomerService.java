package com.enigmacamp.loanpp.service;

import com.enigmacamp.loanpp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanpp.model.dto.response.CustomerResponse;
import com.enigmacamp.loanpp.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer findById(String id);
    List<CustomerResponse> findAll();
    CustomerResponse update(CustomerRequest request);
    void deleteById(String id);
}
