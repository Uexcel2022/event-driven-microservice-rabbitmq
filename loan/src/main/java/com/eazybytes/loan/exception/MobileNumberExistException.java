package com.eazybytes.loan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MobileNumberExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public MobileNumberExistException(String message) {
        super(String.format("Mobile number %s has been used.", message));
    }

}
