package com.lwl.wallet.dao.exception;

public class WalletAlreadyExistsException extends  RuntimeException{

        private String errorCode;
        public WalletAlreadyExistsException(String message,String errorCode){
                super(message);
                this.errorCode = errorCode;
        }
}
