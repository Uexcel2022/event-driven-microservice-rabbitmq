package com.eazybytes.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Schema(
        name = "Account",
        description = "Schema to hold Customer's Account information"
)
@Setter @Getter
public class AccountDto {
    @Schema(
            description = "Account number of EazyBank Account", example = "1147578903"
    )
    @Pattern(regexp = "[1-9]{10}", message = "Account number is 10 digits required field")
    private Long accountNumber;
    @Schema(
            description = "The Account types available in EazyBank", example = "savings,current,investment"
    )
    @Pattern(regexp = "(savings|current|investment)", message = "Account types: savings,current or investment")
    private String accountType;
    @NotEmpty(message = "Branch address can not be null or empty")
    @Schema(
            description = "Branch address of EazyBank", example="23 Owolowo way, Ikeja"
    )
    private String branchAddress;
}
