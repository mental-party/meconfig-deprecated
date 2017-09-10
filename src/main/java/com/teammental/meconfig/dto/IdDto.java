package com.teammental.meconfig.dto;

import java.io.Serializable;

public interface IdDto<T extends Serializable> extends Dto {
  T getId();
  void setId(T id);
}
