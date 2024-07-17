package com.enigmacamp.loanpp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trx_loan_detail")
class LoanTransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long transactionDate;
    private Double nominal;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    @JsonIgnore
    private LoanTransaction loanTransaction;


    private LoanStatus loanStatus; // enum
    private Long createdAt;
    private Long updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}

enum LoanStatus {
    PAID,
    UNPAID
}
