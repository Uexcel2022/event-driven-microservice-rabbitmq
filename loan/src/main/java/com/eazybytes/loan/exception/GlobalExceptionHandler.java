package com.eazybytes.loan.exception;

import com.eazybytes.loan.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Nullable
    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                 HttpStatusCode status, WebRequest request) {
        Map<String, String> validatedErrors = new LinkedHashMap<>();
        List<FieldError> errorList = ex.getBindingResult().getFieldErrors();

        errorList.forEach(FieldError -> {
            String fieldName = FieldError.getField();
            String message = FieldError.getDefaultMessage();
            validatedErrors.put(fieldName, message);
        });

        return new ResponseEntity<>(validatedErrors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MobileNumberExistException.class)
    public ResponseEntity<ErrorResponseDto> handleMobileNumberExistException(
            final MobileNumberExistException e, final WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(request.getDescription(false),
                        HttpStatus.BAD_REQUEST, e.getMessage(), getDate()
                ));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            final ResourceNotFoundException e, final WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(request.getDescription(false),
                        HttpStatus.NOT_FOUND, e.getMessage(), getDate()
                ));
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidLoanNumberException(
            final InvalidArgumentException e, final WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(request.getDescription(false),
                        HttpStatus.BAD_REQUEST, e.getMessage(), getDate()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleExceptions(
            final Exception e, final WebRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(request.getDescription(false),
                        HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), getDate()
                ));
    }

    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        return sdf.format(new Date());
    }


}
