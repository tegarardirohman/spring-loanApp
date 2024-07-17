package com.enigmacamp.loanpp.service.impl;

import com.enigmacamp.loanpp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanpp.model.dto.response.CustomerResponse;
import com.enigmacamp.loanpp.model.entity.AppUser;
import com.enigmacamp.loanpp.model.entity.Customer;
import com.enigmacamp.loanpp.model.entity.User;
import com.enigmacamp.loanpp.repository.CustomerRepository;
import com.enigmacamp.loanpp.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customer = customerRepository.findAll();
        List<CustomerResponse> customerResponses = new ArrayList<>();

        for (Customer c : customer) {
            CustomerResponse customerResponse = CustomerResponse.builder()
                    .id(c.getId())
                    .firstName(c.getFirstName())
                    .lastName(c.getLastName())
                    .dateOfBirth(c.getDateOfBirth())
                    .phone(c.getPhone())
                    .status(c.getStatus())
                    .build();

            customerResponses.add(customerResponse);
        }

        return customerResponses;
    }

    @Override
    @Transactional
    public CustomerResponse update(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setPhone(request.getPhone());
        customer.setStatus(request.getStatus());
        customer = customerRepository.saveAndFlush(customer);

        return convertToCustomerResponse(customer);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        customerRepository.delete(customer);
    }


    private CustomerResponse convertToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .dateOfBirth(customer.getDateOfBirth())
                .status(customer.getStatus())
                .build();
    }


}
