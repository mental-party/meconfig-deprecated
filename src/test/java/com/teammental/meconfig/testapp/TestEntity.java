package com.teammental.meconfig.testapp;

import com.teammental.memapper.util.StringUtil;

import java.util.Random;

import javax.persistence.Entity;

@Entity
public class TestEntity {
  private Integer id;
  private String name;

  public Integer getId() {

    return id;
  }

  public void setId(Integer id) {

    this.id = id;
  }

  public String getName() {

    return name;
  }

  public void setName(String name) {

    this.name = name;
  }

  /**
   * Generates a random TestEntity object.
   * @return testEntity
   */
  public static TestEntity buildRandom() {
    Random random = new Random();
    TestEntity testEntity = new TestEntity();
    testEntity.setId(random.nextInt());
    testEntity.setName(StringUtil.generateRandomString(10));
    return testEntity;
  }
}
