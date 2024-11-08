package com.example.shoppingmall.exception;

public class OrderCancelException extends RuntimeException{

    public OrderCancelException() {
        super();
    }

    public OrderCancelException(String message) {
        super(message);
    }
}
