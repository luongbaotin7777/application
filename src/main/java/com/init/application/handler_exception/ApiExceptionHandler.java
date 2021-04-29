package com.init.application.handler_exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleAllException(Exception ex) {
        if (ex.getLocalizedMessage().equals("Access is denied")) {
            RestError err = RestError.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .debugMessage(ex.getLocalizedMessage())
                    .exception(ex.getClass().toString())
                    .build();
            return new ResponseEntity(err, HttpStatus.FORBIDDEN);
        } else {
            RestError err = RestError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .debugMessage(ex.getLocalizedMessage())
                    .exception(ex.getClass().toString())
                    .build();
            return new ResponseEntity(err, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(SingleErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity handleValidationException(SingleErrorException ex) {
        return new ResponseEntity(ex.getRestError(), ex.getHttpStatus());
    }

    //Validate Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestError> customValidationErrorHanding(MethodArgumentNotValidException exception) {
        RestError err = RestError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .debugMessage(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage())
                .exception(exception.getClass().toString())
                .errors(exception.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()))
                .build();
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
