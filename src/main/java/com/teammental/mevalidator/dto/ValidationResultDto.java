package com.teammental.mevalidator.dto;

import java.util.List;

public class ValidationResultDto {

  private List<FieldErrorDto> fieldErrors;
  private List<GlobalErrorDto> globalErrors;

  public List<FieldErrorDto> getFieldErrors() {
    return fieldErrors;
  }

  public void setFieldErrors(List<FieldErrorDto> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }

  public List<GlobalErrorDto> getGlobalErrors() {
    return globalErrors;
  }

  public void setGlobalErrors(List<GlobalErrorDto> globalErrors) {
    this.globalErrors = globalErrors;
  }
}
