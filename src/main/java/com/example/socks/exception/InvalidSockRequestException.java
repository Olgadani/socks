package com.example.socks.exception;

public class InvalidSockRequestException extends RuntimeException{
    public InvalidSockRequestException(String message) {
        super(message);
    }
}
