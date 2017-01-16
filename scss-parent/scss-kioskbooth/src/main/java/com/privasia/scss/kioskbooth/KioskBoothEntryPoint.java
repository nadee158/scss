package com.privasia.scss.kioskbooth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.*"})
@EnableAutoConfiguration
public class KioskBoothEntryPoint extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(KioskBoothEntryPoint.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<KioskBoothEntryPoint> applicationClass = KioskBoothEntryPoint.class;


}
