package com.teammental.meconfig.handler.rest;

import com.teammental.meconfig.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "enable-not-found-handler", prefix = "com.teammental.meconfig",
    havingValue = "true", matchIfMissing = true)
@RestControllerAdvice
public class NotFoundRestHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundException.class);

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void processHandler(NotFoundException ex) {
    LOGGER.error("NOT FOUND ERROR: " + ex.getMessage());
  }
}
