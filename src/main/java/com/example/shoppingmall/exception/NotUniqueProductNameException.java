package com.example.shoppingmall.exception;

public class NotUniqueProductNameException extends RuntimeException{

    public NotUniqueProductNameException() {
        super();
    }

    public NotUniqueProductNameException(String message) {
        super(message);
    }
}
