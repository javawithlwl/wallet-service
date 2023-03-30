package com.lwl.wallet.dao.exception;

public class ResourceNotFoundException extends  RuntimeException {
  private String errorCode;

  public ResourceNotFoundException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }
}