package com.teammental.meconfig.handler.rest;

import com.teammental.meconfig.dto.FieldErrorDto;
import com.teammental.meconfig.dto.ValidationResultDto;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "enable-rest-handler", prefix = "com.teammental.meconfig",
    havingValue = "true", matchIfMissing = true)
@RestControllerAdvice
public class ValidationErrorRestHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ValidationErrorRestHandler.class);

  /**
   * Rest handler for validation errors.
   * @param ex handled exception
   * @return rest result
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> processHandler(MethodArgumentNotValidException ex) {

    BindingResult bindingResult = ex.getBindingResult();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    List<FieldErrorDto> fieldErrorDtos = fieldErrors.stream()
        .map(FieldErrorDto::new)
        .collect(Collectors.toList());

    ValidationResultDto validationResultDto = new ValidationResultDto();
    validationResultDto.setFieldErrors(fieldErrorDtos);

    LOGGER.error("VALIDATION ERROR: " + ex.getMessage());

    return ResponseEntity.badRequest().body(validationResultDto);
  }
}
