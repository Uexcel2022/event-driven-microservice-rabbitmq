package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Schema(
        name = "Loan",
        description = "Schema to hold loan details"
)
@Getter @Setter
public class LoanDto {
    @Schema(
            description = "EazyBank 12 digits loan number", example = "100801363801"
    )
    @Pattern(regexp = "^[1-9][0-9]{11}$",message = "Loan number is invalid, must be 12 digits")
    private String loanNumber;

    @Schema(
            description = "Loan types in EazyBank", example = "home loan"
    )
    @Pattern(regexp = "(home loan|vehicle loan|school loan)",message = "Invalid loan type")
    private String loanType;

    @Schema(
            description = "Customer mobile number",example = "07168163864"
    )
    @Pattern(regexp = "^0[7-9][10][0-9]{8}$",message = "Invalid mobile number")
    private String mobileNumber;
    @Schema(
            description = "Total loan amount",example = "1000.12"
    )
    @Positive(message = "Total loan should be greater than zero")
    private double totalLoan;
    @Schema(
            description = "Amount paid",example = "500"
    )
    @PositiveOrZero(message = "Amount paid should be zero or greater")
    private double amountPaid;
    @Schema(
            description = "Loan outstanding",example = "9000.59"
    )
    @PositiveOrZero(message = "Outstanding loan should be zero or greater")
    private double outstandingAmount;
}
