package com.example.shoppingmall.exception;

public class NotUniqueStockException extends RuntimeException{

    public NotUniqueStockException() {
        super();
    }

    public NotUniqueStockException(String message) {
        super(message);
    }
}
