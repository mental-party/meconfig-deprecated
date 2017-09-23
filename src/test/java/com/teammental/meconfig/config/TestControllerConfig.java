package com.teammental.meconfig.config;

import com.teammental.meconfig.handler.rest.EntityDeleteExceptionRestHandler;
import com.teammental.meconfig.handler.rest.EntityInsertExceptionRestHandler;
import com.teammental.meconfig.handler.rest.EntityNotFoundExceptionRestHandler;
import com.teammental.meconfig.handler.rest.EntityUpdateExceptionRestHandler;
import com.teammental.meconfig.handler.rest.InvalidDataAccessApiUsageExceptionRestHandler;
import com.teammental.meconfig.handler.rest.ValidationErrorRestHandler;
import com.teammental.meconfig.testapp.TestCrudService;
import com.teammental.meconfig.testapp.TestCrudServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({EntityNotFoundExceptionRestHandler.class,
    EntityInsertExceptionRestHandler.class,
    ValidationErrorRestHandler.class,
    EntityUpdateExceptionRestHandler.class,
    EntityDeleteExceptionRestHandler.class,
    InvalidDataAccessApiUsageExceptionRestHandler.class})
public class TestControllerConfig {

  @Bean
  public TestCrudService testCrudService() {
    return Mockito.mock(TestCrudService.class);
  }

}
