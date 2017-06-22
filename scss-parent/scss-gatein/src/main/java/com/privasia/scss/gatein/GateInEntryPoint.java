package com.privasia.scss.gatein;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import com.privasia.scss.common.interfaces.ContainerExternalDataService;
import com.privasia.scss.core.config.SCSSEntryPoint;
import com.privasia.scss.cosmos.AS400DBConfig;
import com.privasia.scss.cosmos.OracleDBConfig;
import com.privasia.scss.etpws.ETPWsEntryPoint;

/**
 * Janaka Wanigatunga
 *
 */

@Configuration
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.privasia.scss.hdbs.*", "com.privasia.scss.cosmos.*",
    "com.privasia.scss.hpat.*", "com.privasia.scss.gatein.*", "com.privasia.scss.core.*",
    "com.privasia.scss.common.*", "com.privasia.scss.opus.*", "com.privasia.scss.etpws.*"})
@Import({AS400DBConfig.class, OracleDBConfig.class, SCSSEntryPoint.class,
    LpkediPersistenceContext.class, ETPWsEntryPoint.class, EmailConfig.class})
@EnableAsync
public class GateInEntryPoint extends SpringBootServletInitializer {


  @Configuration
  @Profile("dev")
  @PropertySource({"classpath:cosmos_application-dev.properties",
      "classpath:cosmos_sql-dev.properties", "classpath:opus_application-dev.properties",
      "classpath:ws-dev.properties", "classpath:lpkedi-dev.properties",
      "classpath:lpkedi_sql.properties", "classpath:cosmos_msg_codes.properties"})
  static class Dev {
  }

  @Configuration
  @Profile("prod")
  @PropertySource({"classpath:cosmos_application-prod.properties",
      "classpath:cosmos_sql-prod.properties", "classpath:opus_application-prod.properties",
      "classpath:ws-prod.properties", "classpath:lpkedi-prod.properties",
      "classpath:lpkedi_sql.properties", "classpath:cosmos_msg_codes.properties"})
  static class Prod {
  }

  @Configuration
  @Profile("uat")
  @PropertySource({"classpath:cosmos_application-uat.properties",
      "classpath:cosmos_sql-uat.properties", "classpath:opus_application-uat.properties",
      "classpath:ws-uat.properties", "classpath:lpkedi-uat.properties",
      "classpath:lpkedi_sql.properties", "classpath:cosmos_msg_codes.properties"})
  static class Uat {
  }

  public static void main(String[] args) {
    SpringApplication.run(GateInEntryPoint.class, args);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public ServiceLocatorFactoryBean containerExternalDataServiceServiceLocatorFactoryBean() {

    ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
    serviceLocatorFactoryBean.setServiceLocatorInterface(ContainerExternalDataService.class);
    return serviceLocatorFactoryBean;
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<GateInEntryPoint> applicationClass = GateInEntryPoint.class;

}
