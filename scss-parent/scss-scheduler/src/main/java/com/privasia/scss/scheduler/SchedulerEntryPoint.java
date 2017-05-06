package com.privasia.scss.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import com.privasia.scss.core.config.SCSSEntryPoint;

/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.*"})
@EnableAutoConfiguration
@PropertySource(value = {"classpath:scheduler_application.properties", "classpath:quartz.properties"})
@Import({SCSSEntryPoint.class})
@EntityScan(basePackages = {"com.privasia.scss.core.model"})
@EnableJpaRepositories(basePackages = {"com.privasia.scss.core.repository"})
@EnableAsync
public class SchedulerEntryPoint extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(SchedulerEntryPoint.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<SchedulerEntryPoint> applicationClass = SchedulerEntryPoint.class;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
