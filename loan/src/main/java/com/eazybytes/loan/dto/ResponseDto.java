package com.eazybytes.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Schema(
        name = "Responses",
        description = "Hold response code and message"
)
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    @Schema(
            description = "Responses codes",example = "200,417,201"
    )
    private String statusCode;
    @Schema(
            description = "Responses messages", example = "...Successfully, ...Fail"
    )
    private String statusMessage;
}
