package com.teammental.meconfig.exception.entity;

public class EntityDeleteException extends Exception
{
  public EntityDeleteException() {
    super();
  }

  public EntityDeleteException(String message) {
    super(message);
  }

  public EntityDeleteException(String message, Exception cause) {
    super(message, cause);
  }
}
