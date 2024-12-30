package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
@Schema(
        name = "Response",
        description = "Schema to hold response information"

)
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
