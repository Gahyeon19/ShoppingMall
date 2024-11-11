package com.example.shoppingmall.exception;

public class StockNotExistException extends RuntimeException{

    public StockNotExistException() {
        super();
    }

    public StockNotExistException(String message) {
        super(message);
    }
}
