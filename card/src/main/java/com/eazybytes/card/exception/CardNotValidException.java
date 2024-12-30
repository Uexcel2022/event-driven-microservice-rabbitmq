package com.eazybytes.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CardNotValidException extends RuntimeException {
    public CardNotValidException(String message) {
        super(message);
    }
}
