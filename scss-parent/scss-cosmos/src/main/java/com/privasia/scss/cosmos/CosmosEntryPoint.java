package com.privasia.scss.cosmos;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
// @PropertySources({@PropertySource("classpath:application.properties"),
// @PropertySource("classpath:sqlqueries.properties")})
// @PropertySource("classpath:sqlqueries.properties")
public class CosmosEntryPoint {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }


}
