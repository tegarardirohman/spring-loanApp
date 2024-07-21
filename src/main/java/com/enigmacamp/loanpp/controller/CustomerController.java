package com.enigmacamp.loanpp.controller;

import com.enigmacamp.loanpp.constant.APIUrl;
import com.enigmacamp.loanpp.model.dto.request.CustomerRequest;
import com.enigmacamp.loanpp.model.dto.response.AvatarResponse;
import com.enigmacamp.loanpp.model.dto.response.CommonResponse;
import com.enigmacamp.loanpp.model.dto.response.CustomerResponse;
import com.enigmacamp.loanpp.model.entity.Customer;
import com.enigmacamp.loanpp.service.CustomerService;
import com.enigmacamp.loanpp.service.FileStorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable("id") String id) {
        CustomerResponse customer = customerService.findById(id);

        CommonResponse<CustomerResponse > response = CommonResponse.<CustomerResponse>builder()
                .message("Success")
                .data(Optional.of(customer))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer() {
        List<CustomerResponse> customers = customerService.findAll();

        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .message("Success")
                .data(Optional.of(customers))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.update(customerRequest);

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("Successfully update customer")
                .data(Optional.of(customerResponse))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_CUSTOMER')")
    public ResponseEntity<CommonResponse> deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteById(id);

        CommonResponse<Object> response = CommonResponse.builder()
                .message("Customer deleted")
                .data(Optional.empty())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
