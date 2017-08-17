package com.teammental.mevalidator.dto;


import java.util.List;

public class ValidationResultDto {

  private List<FieldErrorDto> fieldErrors;

  public List<FieldErrorDto> getFieldErrors() {
    return fieldErrors;
  }

  public void setFieldErrors(List<FieldErrorDto> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }
}
