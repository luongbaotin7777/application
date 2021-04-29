package com.init.application.handler_exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestError {
    private HttpStatus status;

    private String message;

    private String debugMessage;

    private String exception;

    private String resource;

    private List<String> errors;
}
