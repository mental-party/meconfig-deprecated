package com.teammental.meconfig.handler.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "enable-rest-handler", prefix = "com.teammental.meconfig",
    havingValue = "true", matchIfMissing = true)
@RestControllerAdvice
public class InvalidDataAccessApiUsageExceptionRestHandler {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(InvalidDataAccessApiUsageExceptionRestHandler.class);

  /**
   * Handler method for InvalidDataAccessApiUsageException type.
   * @param ex Auto-catched exception
   * @return HttpStatus=403
   */
  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  public ResponseEntity processHandler(InvalidDataAccessApiUsageException ex) {
    LOGGER.debug("InvalidDataAccessApiUsageException handler process: " + ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
  }
}
