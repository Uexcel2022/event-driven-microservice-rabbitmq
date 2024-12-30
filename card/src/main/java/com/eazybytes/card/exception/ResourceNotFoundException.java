package com.eazybytes.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(
            String resourceName, String filedName ,String fieldValue) {
        super(String.format(
                "%s not found for the given input data %s: %s", resourceName, filedName, fieldValue));
    }
}
