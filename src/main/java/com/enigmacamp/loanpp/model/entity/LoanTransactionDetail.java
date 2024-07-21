package com.enigmacamp.loanpp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "trx_loan_detail")
public class LoanTransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long transactionDate;
    private Double nominal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private LoanTransaction loanTransaction;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "guarantee_picture_id")
    private List<GuaranteePicture> guaranteePicture;

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

    public enum LoanStatus {
        PAID,
        UNPAID
    }
}


