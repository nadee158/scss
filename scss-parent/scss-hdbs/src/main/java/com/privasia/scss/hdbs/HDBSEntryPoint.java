package com.privasia.scss.hdbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;



/**
 * Janaka Wanigatunga
 *
 */

@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.*"})
@EnableAutoConfiguration
@EnableAsync
public class HDBSEntryPoint extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(HDBSEntryPoint.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<HDBSEntryPoint> applicationClass = HDBSEntryPoint.class;

}
