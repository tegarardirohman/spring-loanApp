package com.enigmacamp.loanpp.service.impl;

import com.enigmacamp.loanpp.model.dto.request.ApprovalRequest;
import com.enigmacamp.loanpp.model.dto.request.TransactionRequest;
import com.enigmacamp.loanpp.model.dto.response.LoanTransactionResponse;
import com.enigmacamp.loanpp.model.dto.response.TransactionDetailResponse;
import com.enigmacamp.loanpp.model.entity.*;
import com.enigmacamp.loanpp.repository.*;
import com.enigmacamp.loanpp.service.CustomerService;
import com.enigmacamp.loanpp.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final LoanTransactionRepository loanTransactionRepository;
    private final CustomerRepository customerRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final InstalmentTypeRepository instalmentTypeRepository;
    private final GuaranteePictureRepository guaranteePictureRepository;
    private final LoanTransactionDetailRepository loanTransactionDetailRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public LoanTransactionResponse createTransaction(TransactionRequest request) {
        Customer customer = customerRepository.findById(request.getCustomer().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        LoanType loanType = loanTypeRepository.findById(request.getLoanType().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan type not found"));
        InstalmentType instalmentType = instalmentTypeRepository.findById(request.getInstalmentType().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instalment type not found"));

        LoanTransaction loanTransaction = LoanTransaction.builder()
                .loanType(loanType)
                .instalmentType(instalmentType)
                .customer(customer)
                .loanTransactionDetails(request.getLoanTransactionDetails())
                .nominal(request.getNominal())
                .build();

//         save to loan details
        if (request.getLoanTransactionDetails() != null && !request.getLoanTransactionDetails().isEmpty()) {
            loanTransaction.setLoanTransactionDetails(request.getLoanTransactionDetails());
//
            List<LoanTransactionDetail> loanTransactionDetails = loanTransaction.getLoanTransactionDetails().stream().toList();
            loanTransactionDetailRepository.saveAllAndFlush(loanTransactionDetails);
        }


        return convertToTransactionDetailResponse(loanTransactionRepository.saveAndFlush(loanTransaction));
    }

    @Override
    public LoanTransactionResponse getTransactionById(String id) {
        LoanTransaction loan = loanTransactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan transaction not found"));

        return convertToTransactionDetailResponse(loan);
    }

    private Double getNominalWithInterest(Double nominal, Double interestRate) {

        return nominal + (nominal * interestRate / 100);
    }

    @Override
    public void approveTransaction(String adminId, ApprovalRequest approvalRequest) {
        User user = userRepository.findById(adminId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (user.getRoles().stream().map(Role::getRole).toList().contains(Role.ERole.ROLE_ADMIN.toString()) ) {
            LoanTransaction loanTransaction = loanTransactionRepository.findById(approvalRequest.getLoanTransactionId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

            // put
            loanTransaction.setApprovalStatus(LoanTransaction.ApprovalStatus.APPROVED);
            loanTransaction.setApprovedAt(System.currentTimeMillis());
            loanTransaction.setApprovedBy(user.getEmail());


            // create loan transaction details
            LoanTransactionDetail loanTransactionDetail = LoanTransactionDetail.builder()
                    .transactionDate(System.currentTimeMillis())
                    .nominal(getNominalWithInterest(loanTransaction.getNominal(), Double.valueOf(approvalRequest.getInterestRates())))
                    .loanStatus(LoanTransactionDetail.LoanStatus.UNPAID)
                    .build();

            List<LoanTransactionDetail> loanTransactionDetails = new ArrayList<>(loanTransaction.getLoanTransactionDetails().stream().toList());
            loanTransactionDetails.add(loanTransactionDetail);
            loanTransaction.setLoanTransactionDetails(loanTransactionDetails);

            loanTransactionDetailRepository.saveAllAndFlush(loanTransactionDetails);
            convertToTransactionDetailResponse(loanTransactionRepository.saveAndFlush(loanTransaction));

        } else {
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Admins can approve transactions");
        }
    }

    public String getFileExtension(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        return StringUtils.getFilenameExtension(fileName);
    }


    @Transactional
    public String storeFile(MultipartFile file, String id) throws IOException {

        String idFileName = id+"."+getFileExtension(file);

        Path fileLocation = Path.of("assets/images/guaranteeImages");

        if (!Files.exists(fileLocation)) {
            Files.createDirectories(fileLocation);
        }

        try {
            Path targetLocation = fileLocation.resolve(idFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation.toString();

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file", e);
        }
    }

    @Override
    public void payInstalment(String transactionId, MultipartFile guaranteeImage) {
        LoanTransactionDetail loanTransactionDetail = loanTransactionDetailRepository.findById(transactionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        String filename;
        try {
            filename = storeFile(guaranteeImage, transactionId);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file", e);
        }

        GuaranteePicture guaranteePicture = GuaranteePicture.builder()
                .size(guaranteeImage.getSize())
                .path(filename)
                .size(guaranteeImage.getSize())
                .name(filename)
                .contentType(getFileExtension(guaranteeImage))
                .build();

        List<GuaranteePicture> guaranteePictures = new ArrayList<>();
        guaranteePictures.add(guaranteePicture);

        loanTransactionDetail.setGuaranteePicture(guaranteePictures);

        // save
        guaranteePictureRepository.saveAllAndFlush(guaranteePictures);
        loanTransactionDetailRepository.saveAndFlush(loanTransactionDetail);
    }


    private LoanTransactionResponse convertToTransactionDetailResponse(LoanTransaction loanTrans) {
        return LoanTransactionResponse.builder()
                .id(loanTrans.getId())
                .loanTypeId(loanTrans.getLoanType().getId())
                .instalmentTypeId(loanTrans.getInstalmentType().getId())
                .customerId(loanTrans.getCustomer().getId())
                .nominal(loanTrans.getNominal().longValue())
                .approveAt(loanTrans.getApprovedAt())
                .approveBy(loanTrans.getApprovedBy())
                .approvalStatus(loanTrans.getApprovalStatus().toString())
                .transactionDetailResponses(loanTrans.getLoanTransactionDetails().stream().map(this::convertToTransactionDetailResponse).toList())
                .createAt(loanTrans.getCreatedAt())
                .updatedAt(loanTrans.getUpdatedAt())
                .build();
    }

    private TransactionDetailResponse convertToTransactionDetailResponse(LoanTransactionDetail detail) {
        return TransactionDetailResponse.builder()
                .id(detail.getId())
                .transactionDate(detail.getTransactionDate())
                .nominal(detail.getNominal().longValue())
                .loanStatus(String.valueOf(detail.getLoanStatus()))
                .createAt(detail.getCreatedAt())
                .updatedAt(detail.getUpdatedAt())
                .build();
    }
}
