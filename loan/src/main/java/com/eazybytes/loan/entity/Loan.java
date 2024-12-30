package com.eazybytes.loan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Loan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;
    @Column(updatable = false)
    private String loanNumber;
    private String loanType;
    private String mobileNumber;
    private double totalLoan;
    private double amountPaid;
    private double outstandingAmount;
}


