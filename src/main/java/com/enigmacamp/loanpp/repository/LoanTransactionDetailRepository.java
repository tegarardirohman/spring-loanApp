package com.enigmacamp.loanpp.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import com.enigmacamp.loanpp.model.entity.LoanTransactionDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTransactionDetailRepository extends JpaRepository<LoanTransactionDetail, String> {
    LoanTransactionDetail findByLoanTransactionId(String loanTransactionId);
}
