package com.quantacomit.practical_test.exception;


import com.quantacomit.practical_test.DTO.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(CustomException ex){
        ErrorResponse errorResponseDto = new ErrorResponse();
        errorResponseDto.setDateTime(new Date());
        errorResponseDto.setStatusCode(400);
        errorResponseDto.setMessage(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), errorResponseDto.getMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleRuntimeException() {
        ErrorResponse errorResponseDto = new ErrorResponse();
        errorResponseDto.setDateTime(new Date());
        errorResponseDto.setStatusCode(500);
        errorResponseDto.setMessage("something went wrong !");
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponseDto.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Validation Failed!", errors), HttpStatus.BAD_REQUEST);
    }


}
