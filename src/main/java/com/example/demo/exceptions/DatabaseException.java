package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class DatabaseException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private final HttpStatus statusCode;

    public DatabaseException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode(){ return this.statusCode; }
}
