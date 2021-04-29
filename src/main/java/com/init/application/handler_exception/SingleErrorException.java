package com.init.application.handler_exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleErrorException extends RuntimeException {
    private RestError restError;
    private HttpStatus httpStatus;
}
