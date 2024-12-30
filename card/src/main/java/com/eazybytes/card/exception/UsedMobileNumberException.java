package com.eazybytes.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsedMobileNumberException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public UsedMobileNumberException(String message) {
        super(String.format("Mobile number %s has been used.", message));
    }
}
