package com.eazybytes.card.controller;

import com.eazybytes.card.constants.CardConstants;
import com.eazybytes.card.dto.CardDto;
import com.eazybytes.card.dto.CardContactInfoDto;
import com.eazybytes.card.dto.ErrorResponseDto;
import com.eazybytes.card.dto.ResponseDto;
import com.eazybytes.card.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Tag(
        name = "EazyBank CRUD REST APIs ",
        description =  "CRUD REST APIs in EazyBank to CREATE,FETCH,UPDATE AND DELETE card details"
)


@RestController
//@AllArgsConstructor
@Validated
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Value("${build.version}")
    private String buildVersion;

    private final ICardService iCardService;
    private  final CardContactInfoDto contactConfigDto;
    private  final Environment env;

    public CardController(ICardService iCardService, CardContactInfoDto contactConfigDto, Environment env) {
        this.iCardService = iCardService;
        this.contactConfigDto = contactConfigDto;
        this.env = env;
    }

    @Operation(
            summary = "REST API To Create Card Details",
            description = "REST API to create card in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = CardConstants.STATUS_201_desc,
                            content = @Content(
                               schema = @Schema(implementation = ResponseDto.class)
                            )

                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = CardConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )

                    )

            }


    )

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCardDetails(@RequestParam String mobileNumber) {
        iCardService.validateMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iCardService.createCard(mobileNumber));
    }


    @Operation(
            summary = "REST API to fetch card Details",
            description = "REST API for fetching card details in EazyBank",
             responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = CardConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(implementation = CardDto.class)
                            )
                    ),
                     @ApiResponse(
                             responseCode = "404",
                             description = CardConstants.STATUS_404_desc,
                             content = @Content(
                                     schema = @Schema(implementation = ErrorResponseDto.class)
                             )
                     ),
                     @ApiResponse(
                             responseCode = "500",
                             description = CardConstants.STATUS_500_desc,
                             content = @Content(
                                     schema = @Schema(implementation = ErrorResponseDto.class)
                             )

                     )
             }
    )

    @GetMapping("/fetch")
    public ResponseEntity<CardDto> fetchCard(@RequestHeader("eazybank-correlation-id") String correlationId,
                                             @RequestParam String mobileNumber) {
        iCardService.validateMobileNumber(mobileNumber);
        logger.info("getCardDetails method starts");
        CardDto cardDto = iCardService.getCardDetails(mobileNumber);
//        logger.debug("eazybank-correlation-id found: {}", correlationId);
        logger.info("getCardDetails method ends");
        return   ResponseEntity.ok().body(cardDto);
    }

    @Operation(
            summary = "REST API To Update Card Details",
            description = "REST API for updating card details in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = CardConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDto.class)
                            )

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = CardConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )

                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = CardConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),


                    @ApiResponse(
                            responseCode = "417",
                            description = CardConstants.STATUS_417_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDto.class)
                            )

                    ),
            }

    )

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody CardDto cardDto) {
        boolean success = iCardService.updateCardDetails(cardDto);
        if (success) {
            return ResponseEntity.ok().body(new ResponseDto(
                    CardConstants.STATUS_CODE_200,CardConstants.MESSAGE_200_UPDATE));
        }else {
            return ResponseEntity.ok().body(new ResponseDto(
                    CardConstants.STATUS_CODE_417,CardConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "REST API To Delete Card Details",
            description = "REST API for delete card details in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = CardConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDto.class)
                            )

                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = CardConstants.STATUS_417_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDto.class)
                            )


                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = CardConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = CardConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )

                    )
            }

    )

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam String mobileNumber) {
        iCardService.validateMobileNumber(mobileNumber);
        boolean success = iCardService.deleteCardDetails(mobileNumber);
        if (success) {
           return ResponseEntity.ok().body(new ResponseDto(
                   CardConstants.STATUS_CODE_200,CardConstants.MESSAGE_200_Delete));
        }else {
            return ResponseEntity.ok().body(new ResponseDto(
                    CardConstants.STATUS_CODE_417,CardConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "REST API to fetch buildVersion information",
            description = "REST API for fetching application buildVersion details in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = CardConstants.STATUS_200_desc
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = CardConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )

                    )
            }
    )

    @GetMapping("build-info")
    public ResponseEntity<Map<String,String>> getBuildVersion() {
        Map<String,String> buildInfo = new LinkedHashMap<>();
        buildInfo.put("Name","Eazy Bank Card Microservice");
        buildInfo.put("version", buildVersion);
        buildInfo.put("Build Date", "2024-10-20");
        return ResponseEntity.ok().body(buildInfo);
    }


    @Operation(
            summary = "REST API to fetch JDK Version",
            description = "REST API for fetching application JDK information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = CardConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(
                                            example = "JDK: version"
                                    )
                            )

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = CardConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = CardConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )

                    )
            }
    )


    @GetMapping("java-info")
    public ResponseEntity<Map<String,String>> getEnvironment() {
        Map<String,String> envInfo = new  LinkedHashMap<>();
        envInfo.put("JDK",env.getProperty("java.version"));
        return ResponseEntity.ok().body(envInfo);
    }


    @Operation(
            summary = "REST API to fetch contact",
            description = "REST API for fetching contact details in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = CardConstants.STATUS_200_desc
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = CardConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = CardConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )

                    )
            }
    )

    @GetMapping("contact-info")
    public ResponseEntity<CardContactInfoDto> getContactsDetails() {
        return ResponseEntity.ok().body(contactConfigDto);
    }
}
