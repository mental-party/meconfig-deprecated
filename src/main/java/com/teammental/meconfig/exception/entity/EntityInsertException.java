package com.teammental.meconfig.exception.entity;

public class EntityInsertException extends Exception {
  public EntityInsertException() {
    super();
  }

  public EntityInsertException(String message) {
    super(message);
  }

  public EntityInsertException(String message, Exception cause) {
    super(message, cause);
  }
}
