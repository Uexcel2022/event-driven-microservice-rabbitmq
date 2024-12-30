package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card",fallback = CardFallback.class)
public interface CardFeignClient {
    @GetMapping(value = "/api/fetch",consumes = "application/json")
    ResponseEntity<CardDto> fetchCard(@RequestHeader("eazybank-correlation-id") String correlationId,
                                      @RequestParam String mobileNumber);
}
