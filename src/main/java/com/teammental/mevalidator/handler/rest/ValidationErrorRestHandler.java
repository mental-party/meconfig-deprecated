package com.teammental.mevalidator.handler.rest;

import java.util.List;
import java.util.stream.Collectors;

import com.teammental.mevalidator.dto.FieldErrorDto;
import com.teammental.mevalidator.dto.ValidationResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorRestHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ValidationErrorRestHandler.class);

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
