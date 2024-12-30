package com.eazybytes.loan.controller;

import com.eazybytes.loan.constants.LoanConstants;
import com.eazybytes.loan.dto.ErrorResponseDto;
import com.eazybytes.loan.dto.LoanContactInfoDto;
import com.eazybytes.loan.dto.LoanDto;
import com.eazybytes.loan.dto.ResponseDto;
import com.eazybytes.loan.service.ILoanService;
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
        name = "EazyBank Loan Microservice CRUD Operation APIs",
        description = "use to CREATE,FETCH,UPDATE AND DELETE loan details"
)


@RestController
//@AllArgsConstructor
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LoanController {
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private final ILoanService iLoanService;
    private final LoanContactInfoDto loanContactInfoDto;
    private final Environment env;

    @Value("${build.version}")
    private String buildVersion;

    public LoanController(ILoanService iLoanService, LoanContactInfoDto loanContactInfoDto, Environment env) {
        this.iLoanService = iLoanService;
        this.loanContactInfoDto = loanContactInfoDto;
        this.env = env;
    }

    @Operation(
            summary = "REST API to Create Loan Details",
            description = "REST API use to create loan details in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = LoanConstants.STATUS_201_desc,
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDto.class)
                            )

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = LoanConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )

            }
    )

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoanDetails(@RequestParam String mobileNumber){
        iLoanService.validateMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iLoanService.createLoanDetails(mobileNumber));
    }
    @Operation(
            summary = "REST API to Fetch Loan Details",
            description = "REST API use to fetch loan details in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = LoanConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = LoanDto.class
                                    )
                            )

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = LoanConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = LoanConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    )
            }
    )


    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> getLoanDetails(@RequestHeader("eazybank-correlation-id") String correlationId,
                                                  @RequestParam String mobileNumber) {
        iLoanService.validateMobileNumber(mobileNumber);
        logger.info("fetchLoanDetails method starts");
        LoanDto loanDto = iLoanService.fetchLoanDetails(mobileNumber);
//        logger.debug("eazybank-correlation-id found: {}", correlationId);
        logger.info("fetchLoanDetails method ends");
        return ResponseEntity.ok().body(loanDto);
    }

    @Operation(
            summary = "REST API to Update Loan Details",
            description = "REST API use to create loan details in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = LoanConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseDto.class
                                    )
                            )

                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = LoanConstants.STATUS_417_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseDto.class
                                    )
                            )

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = LoanConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = LoanConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    )
            }
    )

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto loanDto){
        boolean success = iLoanService.updateLoanDetails(loanDto);
        if(success){
            return ResponseEntity.ok()
                    .body(new ResponseDto(
                            LoanConstants.STATUS_CODE_200,LoanConstants.MESSAGE_200_UPDATE));
        }else {
            return ResponseEntity.ok()
                    .body(new ResponseDto(
                            LoanConstants.STATUS_CODE_417,LoanConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "REST API to Delete Loan Details",
            description = "REST API use to create loan details in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = LoanConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseDto.class
                                    )
                            )

                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = LoanConstants.STATUS_417_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponseDto.class
                                    )
                            )

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = LoanConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = LoanConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    )
            }

    )

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam String mobileNumber){
        iLoanService.validateMobileNumber(mobileNumber);
        boolean success = iLoanService.deleteLoanDetails(mobileNumber);
        if(success){
            return ResponseEntity.ok()
                    .body(new ResponseDto(
                            LoanConstants.STATUS_CODE_200,LoanConstants.MESSAGE_200_DELETE));
        }else {
            return ResponseEntity.ok()
                    .body(new ResponseDto(
                            LoanConstants.STATUS_CODE_417,LoanConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "REST API to Fetch Build Version",
            description = "REST API use to fetch application buildVersion information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = LoanConstants.STATUS_200_desc

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = LoanConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = LoanConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    )
            }
    )

    @GetMapping("/build-info")
    public  ResponseEntity<Map<String,String>> getBuildInfo(){
        Map<String,String> versionInfo = new LinkedHashMap<>();
        versionInfo.put("Name","Eazy Bank Loan Microservice");
        versionInfo.put("version", buildVersion);
        versionInfo.put("Build Date", "2024-10-20");
        return ResponseEntity.ok(versionInfo);
    }

    @Operation(
            summary = "REST API to Fetch JDK Version",
            description = "REST API use to fetch application JDK information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = LoanConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(
                                            example = "JDK: version"
                                    )
                    )

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = LoanConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = LoanConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    )
            }
    )

    @GetMapping("/java-info")
    public  ResponseEntity<Map<String,String>> getJavaVersionInfo(){
        Map<String,String> javaInfo = new  LinkedHashMap<>();
        javaInfo.put("JDK",env.getProperty("java.version"));
        return ResponseEntity.ok(javaInfo);
    }

    @Operation(
            summary = "REST API to Fetch Contact",
            description = "REST API use to fetch contact information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = LoanConstants.STATUS_200_desc

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = LoanConstants.STATUS_404_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = LoanConstants.STATUS_500_desc,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )

                    )
            }
    )

    @GetMapping("/contact-info")
    public  ResponseEntity<LoanContactInfoDto> getContactInfo(){
        return ResponseEntity.ok(loanContactInfoDto);
    }




}
