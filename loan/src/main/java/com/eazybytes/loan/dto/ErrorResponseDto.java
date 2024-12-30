package com.eazybytes.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
@Schema(
        name = "ErrorResponses"
)
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
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
