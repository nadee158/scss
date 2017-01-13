package com.privasia.scss.gateout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.privasia.scss.cosmos.AS400DBConfig;

/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.privasia.scss.cosmos.*", "com.privasia.scss.gateout.*"})
@PropertySource(value = {"classpath:mongodb.properties", "classpath:cosmos_application.properties",
    "classpath:cosmos_sql-dev.properties"})
@Import(AS400DBConfig.class)
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
