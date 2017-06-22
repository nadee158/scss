package com.privasia.scss.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


/**
 * Janaka Wanigatunga!
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.*"})
@EnableAutoConfiguration
public class ClientEntryPoint extends SpringBootServletInitializer {

  @Configuration
  @Profile("dev")
  @PropertySource({"classpath:application-dev.properties",
      "classpath:client_application-dev.properties"})
  static class Dev {
  }

  @Configuration
  @Profile("prod")
  @PropertySource({"classpath:application-prod.properties",
      "classpath:client_application-prod.properties"})
  static class Prod {
  }

  @Configuration
  @Profile("uat")
  @PropertySource({"classpath:application-uat.properties",
      "classpath:client_application-uat.properties"})
  static class Uat {
  }

  public static void main(String[] args) {
    SpringApplication.run(ClientEntryPoint.class, args);
  }


  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<ClientEntryPoint> applicationClass = ClientEntryPoint.class;


}
