package com.example.shoppingmall.exception;

public class NotUniqueUserIdException extends RuntimeException{

    public NotUniqueUserIdException() {
        super();
    }

    public NotUniqueUserIdException(String message) {
        super(message);
    }
}
