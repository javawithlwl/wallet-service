package com.lwl.wallet.controller.advice;

import com.lwl.wallet.dao.exception.ResourceNotFoundException;
import com.lwl.wallet.dao.exception.UserAlreadyExistsException;
import com.lwl.wallet.dao.exception.WalletAlreadyExistsException;
import com.lwl.wallet.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value
      = { UserAlreadyExistsException.class, WalletAlreadyExistsException.class})
  protected ResponseEntity<ErrorResponse> handleConflict(
      RuntimeException ex, HttpServletRequest request) {
      ErrorResponse errorResponse = ErrorResponse.builder()
          .message(ex.getMessage())
          .path(request.getRequestURI())
          .httpStatus(HttpStatus.CONFLICT)
          .dateTime(LocalDateTime.now())
          .build();
    return ResponseEntity.badRequest().body(errorResponse);
  }
  @ExceptionHandler(value
      = {ResourceNotFoundException.class})
  protected ResponseEntity<ErrorResponse> noFoundHandler(
      RuntimeException ex, HttpServletRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .httpStatus(HttpStatus.NOT_FOUND)
        .dateTime(LocalDateTime.now())
        .build();
    return ResponseEntity.badRequest().body(errorResponse);
  }
}
