package com.teammental.meconfig.testapp;

import com.teammental.meconfig.bll.service.BaseCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TestCrudServiceImpl extends BaseCrudServiceImpl<TestDto, Integer>
    implements TestCrudService {

  @Autowired
  private TestRepository testRepository;

  @Override
  protected JpaRepository getRepository() {

    return testRepository;
  }

  @Override
  protected Class<?> getDtoClass() {

    return TestDto.class;
  }

  @Override
  protected Class<?> getEntityClass() {

    return TestEntity.class;
  }
}
