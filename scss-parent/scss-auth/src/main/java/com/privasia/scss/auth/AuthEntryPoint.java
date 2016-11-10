package com.privasia.scss.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.*"})
@EnableAutoConfiguration
public class AuthEntryPoint extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(AuthEntryPoint.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<AuthEntryPoint> applicationClass = AuthEntryPoint.class;


}
