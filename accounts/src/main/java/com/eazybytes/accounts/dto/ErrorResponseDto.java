package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Setter @Getter @AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    @Schema(
            description = "API path invoked when the error occurred" ,example = "uri=/api/fetch"
    )
    private String apiPath;
    @Schema(
            description = "Response codes" ,example = "400,500,404"
    )
    private HttpStatus errorCode;
    @Schema(
            description = "Error massages", example = "Required parameter 'mobileNumber' is not present."
    )
    private String errorMessage;

    private String errorTime;
}
