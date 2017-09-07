package com.teammental.meconfig.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationResultDto {

  private List<FieldErrorDto> fieldErrors;
  private List<GlobalErrorDto> globalErrors;

  public List<FieldErrorDto> getFieldErrors() {
    return fieldErrors;
  }

  public ValidationResultDto() {
    fieldErrors = new ArrayList<>();
    globalErrors = new ArrayList<>();
  }

  public void setFieldErrors(List<FieldErrorDto> fieldErrors) {
    if (fieldErrors != null) {
      this.fieldErrors = fieldErrors;
    }
  }

  public void addFieldError(String fieldName, String message) {
    FieldErrorDto fieldErrorDto = new FieldErrorDto(fieldName, message);
    fieldErrors.add(fieldErrorDto);
  }

  public void addFieldError(FieldErrorDto fieldErrorDto) {
    if (fieldErrorDto != null) {
      fieldErrors.add(fieldErrorDto);
    }
  }

  public List<GlobalErrorDto> getGlobalErrors() {
    return globalErrors;
  }

  public void setGlobalErrors(List<GlobalErrorDto> globalErrors) {
    if (globalErrors != null) {
      this.globalErrors = globalErrors;
    }
  }

  public void addGlobalError(String message) {
    GlobalErrorDto globalErrorDto = new GlobalErrorDto();
    globalErrorDto.setMessage(message);
    globalErrors.add(globalErrorDto);
  }

  public void addGlobalError(GlobalErrorDto globalErrorDto) {
    if(globalErrorDto != null) {
      globalErrors.add(globalErrorDto);
    }
  }
}
