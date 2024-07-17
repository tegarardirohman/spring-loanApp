package com.enigmacamp.loanpp.model.entity;


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
@Table(name = "t_instalment_type")
class InstalmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "instalment_type")
    private EInstalmentType instalmentType;
}

enum EInstalmentType {
    ONE_MONTH,
    THREE_MONTHS,
    SIXTH_MONTHS,
    NINE_MONTHS,
    TWELVE_MONTHS
}
