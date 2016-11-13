package com.privasia.scss.hpat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.*"})
@EnableAutoConfiguration
public class HPATEntryPoint extends SpringBootServletInitializer {


  public static void main(String[] args) {
    SpringApplication.run(HPATEntryPoint.class, args);
  }


  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<HPATEntryPoint> applicationClass = HPATEntryPoint.class;


  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }


}
