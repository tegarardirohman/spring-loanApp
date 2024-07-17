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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class CustomerController {
    private final CustomerService customerService;
    private final FileStorageService fileStorageService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<Customer>> getCustomerById(@PathVariable("id") String id) {
        Customer customer = customerService.findById(id);

        CommonResponse<Customer> response = CommonResponse.<Customer>builder()
                .message("Success")
                .data(Optional.of(customer))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping()
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer() {
        List<CustomerResponse> customers = customerService.findAll();

        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .message("Success")
                .data(Optional.of(customers))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping()
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.update(customerRequest);

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("Sucessfully update customer")
                .data(Optional.of(customerResponse))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteById(id);

        CommonResponse<Object> response = CommonResponse.builder()
                .message("Customer deleted")
                .data(Optional.empty())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{customerId}/avatar")
    public ResponseEntity<byte[]> getImage(@PathVariable String customerId) {

        return fileStorageService.getImage(customerId);
    }

    @PostMapping("/{customerId}/upload/avatar")
    public ResponseEntity<CommonResponse<AvatarResponse>> uploadAvatar(@RequestParam("avatar") MultipartFile avatar, @PathVariable String customerId) {
        String userId = customerId;
        String fileName = fileStorageService.storeFile(avatar, userId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APIUrl.CUSTOMER_API)
                .path(customerId)
                .path("/avatar/")
                .toUriString();

        AvatarResponse avatarResponse = AvatarResponse.builder()
                .url(fileDownloadUri)
                .name(fileName)
                .build();

        CommonResponse<AvatarResponse> commonResponse = CommonResponse.<AvatarResponse>builder()
                .message("File uploaded successfully")
                .data(Optional.of(avatarResponse))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }



}
