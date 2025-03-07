package com.eazybytes.accounts.service.client;
import com.eazybytes.accounts.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loan",fallback = LoanFallback.class)
public interface LoanFeignClient {
    @GetMapping(value = "/api/fetch",consumes = "application/json")
    ResponseEntity<LoanDto> getLoanDetails(@RequestHeader("eazybank-correlation-id") String correlationId,
                                           @RequestParam String mobileNumber);
}
