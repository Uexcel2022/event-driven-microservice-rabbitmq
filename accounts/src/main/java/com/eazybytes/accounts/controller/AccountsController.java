package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
        name = "CRUD REST APIs FOR EazyBank",
        description = "CRUD REST APIs in EazyBank to CREAT, RETRIEVE, UPDATE AND DELETE Account details"
)
@RestController
//@AllArgsConstructor
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @Value("${build.version}")
    private String buildVersion;


    private final Environment env;
    private final IAccountService iAccountService;
    private final AccountsContactInfoDto accountsContactInfoDto;

    public AccountsController(Environment env, IAccountService iAccountService, AccountsContactInfoDto accountsContactInfoDto) {
        this.env = env;
        this.iAccountService = iAccountService;
        this.accountsContactInfoDto = accountsContactInfoDto;
    }

    @Operation(
            summary = "REST API To Create Account ",
            description = "REST API to create new Customer and Account details inside EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = AccountConstants.STATUS_201_desc
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = AccountConstants.STATUS_500_desc ,
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )

    @PostMapping(path = "/create-account")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){

        iAccountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Details REST API",
            description = "REST API to fetch Customer and Account details  inside EazyBank",
            responses = {
                    @ApiResponse(
                    responseCode = "200",
                    description = AccountConstants.STATUS_200_desc
                ),
                    @ApiResponse(
                            responseCode = "404",
                            description = AccountConstants.STATUS_404_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                        responseCode = "500",
                        description = AccountConstants.STATUS_500_desc,
                        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                        )
                )
        }
    )

    @GetMapping("/fetch-customer")
    public ResponseEntity<CustomerDto> fetchCustomer(@RequestHeader("eazybank-correlation-id") String correlationId,
                                                     @RequestParam("mobileNumber") String mobileNumber){
        iAccountService.validateMobileNumber(mobileNumber);
        logger.info("getCustomerByMobileNumber details method starts");
        CustomerDto customerDto = iAccountService.getCustomerByMobileNumber(mobileNumber);
//        logger.debug("eazybank-correlation-id found: {}", correlationId);
        logger.info("getCustomerByMobileNumber details method ends");
        return ResponseEntity.ok().body(customerDto);
    }

    @Operation(
            summary = "Update Details REST API",
            description = "REST API to update Customer and Account details  inside EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = AccountConstants.STATUS_200_desc
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = AccountConstants.STATUS_404_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = AccountConstants.STATUS_500_desc,

                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = AccountConstants.STATUS_417_desc
                    )
            }

    )
    @PutMapping("/update-account")
    public  ResponseEntity<ResponseDto> updateBankAccount(@Valid @RequestBody CustomerDto customerDto) {

        boolean isUpdated = iAccountService.updateCustomer(customerDto);

        if (isUpdated) {
            return ResponseEntity.ok()
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_Update));
        }
    }
    @Operation(
            summary = "Delete Details REST API",
            description = "REST API to delete Customer and Account details  inside EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = AccountConstants.STATUS_200_desc
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = AccountConstants.STATUS_404_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = AccountConstants.STATUS_500_desc,
                            content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                      )
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = AccountConstants.STATUS_417_desc
                    )
            }

    )
    @DeleteMapping("/delete-account")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam("mobileNumber") String mobileNumber){
        iAccountService.validateMobileNumber(mobileNumber);
            boolean isDeleted = iAccountService.deleteCustomer(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok()
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_Delete));
        }

    }

    @Operation(
            summary = "REST API to Fetch Build information",
            description = "REST API to fetch application buildVersion details  inside EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = AccountConstants.STATUS_200_desc
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = AccountConstants.STATUS_404_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = AccountConstants.STATUS_500_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/build-info")
    @RateLimiter(name = "getBuildVersion",fallbackMethod = "getBuildVersionFallBack")
    public ResponseEntity<Map<String,String>> getBuildVersion(){
        Map<String,String> versionInfo = new LinkedHashMap<>();
        versionInfo.put("Name","Eazy Bank Account Microservice");
        versionInfo.put("version", buildVersion);
        versionInfo.put("Build Date", "2024-10-20");
        return ResponseEntity.ok(versionInfo);
    }

    public ResponseEntity<Map<String,String>> getBuildVersionFallBack(Throwable throwable){
        Map<String,String> versionInfo = new LinkedHashMap<>();
        versionInfo.put("Name","Eazy Bank Account Microservice");
        versionInfo.put("version", "****");
        versionInfo.put("Build Date", "****");
        return ResponseEntity.ok(versionInfo);
    }



    @Operation(
            summary = "REST API to Fetch JDK version",
            description = "REST API to fetch application JDK information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = AccountConstants.STATUS_200_desc,
                            content = @Content(
                                    schema = @Schema(
                                            example = "JDK: version"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = AccountConstants.STATUS_404_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = AccountConstants.STATUS_500_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @Retry(name = "getJavaVersionInfo",fallbackMethod = "getJavaVersionInfoFallback")
    @GetMapping("/java-version")
    public ResponseEntity<Map<String,String>> getJavaVersionInfo(){
        Map<String,String> javaInfo = new  LinkedHashMap<>();
        javaInfo.put("JDK",env.getProperty("java.version"));
        logger.debug("invoked getJavaVersionInfo");
        return ResponseEntity.ok(javaInfo);
    }

    public ResponseEntity<Map<String,String>> getJavaVersionInfoFallback(Throwable e){
        Map<String,String> javaInfo = new  LinkedHashMap<>();
        javaInfo.put("JDK","17.*.*");
        logger.debug("invoked getJavaVersionInfoFallback");
        return ResponseEntity.ok(javaInfo);
    }



    @Operation(
            summary = "REST API to Fetch Contact",
            description = "REST API to fetch  contact details in EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = AccountConstants.STATUS_200_desc
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = AccountConstants.STATUS_404_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = AccountConstants.STATUS_500_desc,
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity.ok(accountsContactInfoDto);
    }


}
