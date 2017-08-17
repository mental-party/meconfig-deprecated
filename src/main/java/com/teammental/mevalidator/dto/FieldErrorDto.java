package com.teammental.mevalidator.dto;

import org.springframework.validation.FieldError;

public class FieldErrorDto {
  // region fields

  private String fieldName;
  private String message;

  // endregion fields


  // region constructors

  public FieldErrorDto() {
  }

  public FieldErrorDto(String fieldName, String message) {
    this.fieldName = fieldName;
    this.message = message;
  }

  public FieldErrorDto(FieldError fieldError) {
    this.fieldName = fieldError.getField();
    this.message = fieldError.getDefaultMessage();
  }

  // endregion


  // region getter & setters

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  // endregion

  // region methods

  @Override
  public String toString() {
    String str = getFieldName() + ": " + getMessage();
    return str;
  }

  // endregion
}
