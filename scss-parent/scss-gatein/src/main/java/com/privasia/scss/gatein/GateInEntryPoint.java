package com.privasia.scss.gatein;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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
import com.privasia.scss.cosmos.AS400DBConfig;
import com.privasia.scss.etpws.ETPWsEntryPoint;

/**
 * Janaka Wanigatunga
 *
 */

@Configuration
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.privasia.scss.hdbs.*", "com.privasia.scss.cosmos.*", "com.privasia.scss.hpat.*",
    "com.privasia.scss.gatein.*", "com.privasia.scss.core.*", "com.privasia.scss.opus.*", "com.privasia.scss.etpws.*"})
@EntityScan(basePackages = {"com.privasia.scss.core.model"})
@PropertySource(value = {"classpath:cosmos_application.properties", "classpath:cosmos_sql-dev.properties",
    "classpath:opus_application.properties", "classpath:ws.properties", "classpath:lpkedi.properties",
    "classpath:lpkedi_sql.properties", "classpath:ws.properties"})
@Import({AS400DBConfig.class, SCSSEntryPoint.class, LpkediPersistenceContext.class, ETPWsEntryPoint.class})
@EnableJpaRepositories(basePackages = {"com.privasia.scss.core.repository"})
@EnableAsync
public class GateInEntryPoint extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(GateInEntryPoint.class, args);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<GateInEntryPoint> applicationClass = GateInEntryPoint.class;

}
