package com.teammental.meconfig.handler.rest;

import com.teammental.meconfig.exception.entity.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "enable-entity-exceptions-handler",
    prefix = "com.teammental.meconfig",
    havingValue = "true",
    matchIfMissing = true)
@RestControllerAdvice
public class EntityNotFoundExceptionRestHandler {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(EntityNotFoundException.class);

  /**
   * Handler method for EntityNotFoundException type.
   * @param ex Auto-catched exception
   * @return HttpStatus=404
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity processHandler(EntityNotFoundException ex) {
    LOGGER.debug("EntityNotFoundException handler process: " + ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
