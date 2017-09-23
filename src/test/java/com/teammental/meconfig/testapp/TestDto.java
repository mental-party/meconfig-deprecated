package com.teammental.meconfig.testapp;

import com.teammental.meconfig.dto.IdDto;
import com.teammental.memapper.util.StringUtil;
import java.util.Random;

public class TestDto implements IdDto<Integer> {

  private Integer id;
  private String name;

  @Override
  public Integer getId() {

    return id;
  }

  @Override
  public void setId(Integer id) {

    this.id = id;
  }

  public String getName() {

    return name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public static TestDto buildRandom() {

    Random random = new Random();
    TestDto testDto = new TestDto();
    testDto.setId(random.nextInt());
    testDto.setName(StringUtil.generateRandomString(10));
    return testDto;
  }

  @Override
  public String toString() {

    return "[Id=" + getId() + ", Name=" + getName() + "]";
  }
}
