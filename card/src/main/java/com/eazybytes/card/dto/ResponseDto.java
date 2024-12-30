package com.eazybytes.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(
        name="Response"
)
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
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
