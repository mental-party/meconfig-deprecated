package com.teammental.meconfig.exception.entity;

public class EntityUpdateException extends Exception {
  public EntityUpdateException() {
    super();
  }

  public EntityUpdateException(String message) {
    super(message);
  }

  public EntityUpdateException(String message, Exception cause) {
    super(message, cause);
  }
}
