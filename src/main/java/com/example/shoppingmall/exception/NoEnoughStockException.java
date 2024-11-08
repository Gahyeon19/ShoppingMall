package com.example.shoppingmall.exception;

public class NoEnoughStockException extends RuntimeException{

    public NoEnoughStockException() {
        super();
    }

    public NoEnoughStockException(String message) {
        super(message);
    }
}
