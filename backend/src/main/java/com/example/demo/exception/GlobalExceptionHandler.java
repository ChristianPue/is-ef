package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BusinessRuleException.class)
  public ProblemDetail handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setProperty("title", ex.getMessage());
    pd.setProperty("timestamp", LocalDateTime.now());
    pd.setProperty("details", "BusinessRuleException");
    return pd;
  }

  @ExceptionHandler(DuplicatedResourceException.class)
  public ProblemDetail handleDuplicatedResourceException(DuplicatedResourceException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
    pd.setProperty("title", ex.getMessage());
    pd.setProperty("timestamp", LocalDateTime.now());
    pd.setProperty("details", "DuplicatedResourceException");
    return pd;
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
    pd.setProperty("title", ex.getMessage());
    pd.setProperty("timestamp", LocalDateTime.now());
    pd.setProperty("details", "ResourceNotFoundException");
    return pd;
  }
}
