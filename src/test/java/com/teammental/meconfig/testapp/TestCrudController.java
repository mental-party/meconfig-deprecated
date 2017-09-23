package com.teammental.meconfig.testapp;

import com.teammental.meconfig.web.controller.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestCrudController extends BaseCrudController<TestCrudService, TestDto, Integer> {

  @Autowired
  private TestCrudService testCrudService;

  @Override
  protected TestCrudService getBaseCrudService() {

    return testCrudService;
  }

  @Override
  protected String getMappingUrlOfController() {

    return "/";
  }

}
