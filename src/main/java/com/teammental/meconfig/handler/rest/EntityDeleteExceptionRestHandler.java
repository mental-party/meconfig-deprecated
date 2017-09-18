package com.teammental.meconfig.handler.rest;

import com.teammental.meconfig.exception.entity.EntityDeleteException;
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
public class EntityDeleteExceptionRestHandler {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(EntityDeleteExceptionRestHandler.class);

  /**
   * Handler method for EntityDeleteException type.
   * @param ex Auto-catched exception
   * @return HttpStatus=403
   */
  @ExceptionHandler(EntityDeleteException.class)
  public ResponseEntity processHandler(EntityDeleteException ex) {
    LOGGER.debug("EntityDeleteException handler process: " + ex.getMessage());

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
  }
}
