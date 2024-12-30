package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardFallback implements CardFeignClient{
    @Override
    public ResponseEntity<CardDto> fetchCard(String correlationId, String mobileNumber) {
        return null;
    }
}
