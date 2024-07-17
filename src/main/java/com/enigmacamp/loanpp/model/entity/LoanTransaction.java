package com.enigmacamp.loanpp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trx_loan")
class LoanTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;

    @OneToOne
    @JoinColumn(name = "instalment_type_id")
    private InstalmentType instalmentType;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double nominal;
    private Long approvedAt;
    private String approvedBy;
    private ApprovalStatus approvalStatus; // enum

    @OneToMany(mappedBy = "loanTransaction")
    private List<LoanTransactionDetail> loanTransactionDetails;

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

enum ApprovalStatus {
    APPROVED,
    REJECTED
}