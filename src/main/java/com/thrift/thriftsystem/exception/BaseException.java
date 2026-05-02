package com.thrift.thriftsystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public BaseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
