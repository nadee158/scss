package com.privasia.scss.gateout;

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

import com.privasia.scss.core.config.SCSSEntryPoint;
import com.privasia.scss.cosmos.AS400DBConfig;

/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(
    basePackages = {"com.privasia.scss.cosmos.*", "com.privasia.scss.gateout.*", "com.privasia.scss.core.model"})
@EntityScan(basePackages = {"com.privasia.scss.core.model"})
@PropertySource(value = {"classpath:mongodb.properties", "classpath:cosmos_application.properties",
    "classpath:cosmos_sql-dev.properties"})
@Import({AS400DBConfig.class, SCSSEntryPoint.class})
@EnableJpaRepositories(basePackages = {"com.privasia.scss.core.repository"})
public class GateOutEntryPoint extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(GateOutEntryPoint.class, args);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<GateOutEntryPoint> applicationClass = GateOutEntryPoint.class;

}
