package com.teammental.meconfig.config;

import com.teammental.meconfig.handler.rest.EntityDeleteExceptionRestHandler;
import com.teammental.meconfig.handler.rest.EntityInsertExceptionRestHandler;
import com.teammental.meconfig.handler.rest.EntityNotFoundExceptionRestHandler;
import com.teammental.meconfig.handler.rest.EntityUpdateExceptionRestHandler;
import com.teammental.meconfig.handler.rest.InvalidDataAccessApiUsageExceptionRestHandler;
import com.teammental.meconfig.handler.rest.ValidationErrorRestHandler;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Configuration
public class TestControllerConfig {

  /**
   * ExceptionHandlerExceptionResolver which contains rest handlers
   * and is used in Controller test methods.
   * @return ExceptionHandlerExceptionResolver object
   */
  @Bean
  public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {

    final StaticApplicationContext applicationContext = new StaticApplicationContext();

    applicationContext.registerBeanDefinition("entityDeleteExceptionRestHandler",
        new RootBeanDefinition(EntityDeleteExceptionRestHandler.class, null, null));

    applicationContext.registerBeanDefinition("entityInsertExceptionRestHandler",
        new RootBeanDefinition(EntityInsertExceptionRestHandler.class, null, null));

    applicationContext.registerBeanDefinition("entityUpdateExceptionRestHandler",
        new RootBeanDefinition(EntityUpdateExceptionRestHandler.class, null, null));

    applicationContext.registerBeanDefinition("entityNotFoundExceptionRestHandler",
        new RootBeanDefinition(EntityNotFoundExceptionRestHandler.class, null, null));

    applicationContext.registerBeanDefinition("validationErrorRestHandler",
        new RootBeanDefinition(ValidationErrorRestHandler.class, null, null));

    applicationContext.registerBeanDefinition("invalidDataAccessApiUsageExceptionRestHandler",
        new RootBeanDefinition(InvalidDataAccessApiUsageExceptionRestHandler.class, null, null));


    final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver =
        new ExceptionHandlerExceptionResolver();

    exceptionHandlerExceptionResolver.setApplicationContext(applicationContext);

    exceptionHandlerExceptionResolver.afterPropertiesSet();

    return exceptionHandlerExceptionResolver;
  }

  public static final String URL = "/testdtos";
}
