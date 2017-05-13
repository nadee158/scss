package com.privasia.scss.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * Janaka Wanigatunga!
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.*"})
@PropertySource(value = {"classpath:application.properties", "classpath:client_application.properties"})
@EnableAutoConfiguration
public class ClientEntryPoint extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(ClientEntryPoint.class, args);
  }


  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<ClientEntryPoint> applicationClass = ClientEntryPoint.class;


}
