package com.eazybytes.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
@Getter @Setter
public class CustomerDto {
     @Schema(
             description = "Name of customer",example = "John Don Williams"
     )
    @Pattern(regexp = "[A-Za-z]{3,10} ?[a-zA-Z]{3,10} ?[a-zA-z]{0,10}",
            message = "Requires minimum of 2 names with at least 3 alphabet characters each")
    private String name;

    @Schema(
            description = "Customer email",example = "example@gmail.com"
    )
    @Email(message = "Not a valid email pattern")
    private String email;

    @Pattern(regexp = "0[7-9][01][0-9]{8}", message = "Not a valid Nigeria mobile number")
    private String mobileNumber;

    @Schema(
            name = "account"
    )
    private AccountDto accountDto;
}

