package com.lwl.wallet.dao.exception;

public class UserAlreadyExistsException extends  RuntimeException {
    private String errorCode;
    public UserAlreadyExistsException(String message,String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
