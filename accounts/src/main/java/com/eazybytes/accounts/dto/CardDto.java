package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(
        name = "Card",
        description = "Schema to hold card information"
)
@Getter @Setter @ToString
public class CardDto {
    @Schema(
          description = "Card number of EazyBank", example = "100672009814"
    )
    @Pattern(regexp = "^[1-9][0-9]{11}$",message = "Invalid card number, must be 12 digits.")
    private String cardNumber;
    @Schema(
            description = "Mobile number of customer", example = "07009081070"
    )
    @Pattern(regexp = "^0[7-9][01][0-9]{8}$",message = "Invalid mobile number example: 07009081070")
    private String mobileNumber;
    @Schema(
            description = "Card type in EazyBank", example = "credit card"
    )
    @Pattern(regexp = "(^credit card|debit card|loan card$)",message = "Unacceptable card type")
    private String cardType;

    @Schema(
            description = "Card fund limit in EazyBank", example = "10000.91"
    )
    @PositiveOrZero(message = "Total limit should be zero or greater")
    private double totalLimit;
    @Schema(
            description = "The amount customer has used from the card", example = "500.21"
    )
    @PositiveOrZero(message = "Amount used should be zero or greater")
    private double amountUsed;
    @Schema(
            description = "Available amount on the card", example = "1230.31"
    )
    @PositiveOrZero(message = "Available amount should be zero or greater")
    private double availableAmount;
}
