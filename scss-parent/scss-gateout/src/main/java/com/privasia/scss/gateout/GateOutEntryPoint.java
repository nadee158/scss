package com.privasia.scss.gateout;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
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

import com.privasia.scss.common.interfaces.ContainerExternalDataService;
import com.privasia.scss.core.config.SCSSEntryPoint;
import com.privasia.scss.cosmos.AS400DBConfig;
import com.privasia.scss.etpws.ETPWsEntryPoint;

/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.privasia.scss.cosmos.*", "com.privasia.scss.gateout.*", "com.privasia.scss.core.*",
    "com.privasia.scss.opus.*", "com.privasia.scss.etpws.*", "com.privasia.scss.common.*",})
@EntityScan(basePackages = {"com.privasia.scss.core.model"})
@PropertySource(value = {"classpath:mongodb.properties", "classpath:cosmos_application.properties",
    "classpath:cosmos_sql-dev.properties", "classpath:opus_application.properties", "classpath:ws.properties", "classpath:cosmos_msg_codes.properties"})
@Import({AS400DBConfig.class, SCSSEntryPoint.class, ETPWsEntryPoint.class})
@EnableJpaRepositories(basePackages = {"com.privasia.scss.core.repository"})
@EnableAsync
public class GateOutEntryPoint extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(GateOutEntryPoint.class, args);
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

  private static Class<GateOutEntryPoint> applicationClass = GateOutEntryPoint.class;

}
