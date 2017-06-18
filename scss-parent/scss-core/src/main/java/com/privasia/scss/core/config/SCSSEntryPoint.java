package com.privasia.scss.core.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.privasia.scss.core.util.service.CurrentDateTimeService;

@Configuration
@Import({PersistenceContext.class, ModelMapConfig.class})
@EnableAsync
public class SCSSEntryPoint {

  private static Logger logger = Logger.getLogger(SCSSEntryPoint.class.getName());

  @Value("${current.active.profile.check.value}")
  private String currentActiveProfile;

  @Bean
  CurrentDateTimeService getCurrentDateTimeService() {
    System.out.println("currentActiveProfile " + currentActiveProfile);
    logger.error("currentActiveProfile " + currentActiveProfile);
    return new CurrentDateTimeService();
  }


  @Bean
  public Filter characterEncodingFilter() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(true);
    return characterEncodingFilter;
  }

  @Bean
  public ErrorPageFilter errorPageFilter() {
    return new ErrorPageFilter();
  }

  @Bean
  public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(filter);
    filterRegistrationBean.setEnabled(false);
    return filterRegistrationBean;
  }

  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    factory.setMaxFileSize("128000KB");
    factory.setMaxRequestSize("128000KB");
    return factory.createMultipartConfig();
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

}
