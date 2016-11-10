package com.privasia.scss.core.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.privasia.scss.core.util.constant.Profiles;
import com.privasia.scss.core.util.service.CurrentDateTimeService;

@SpringBootApplication
@Import({PersistenceContext.class})
public class SCSSEntryPoint {

  @Profile(Profiles.APPLICATION)
  @Bean
  CurrentDateTimeService getCurrentDateTimeService() {
    return new CurrentDateTimeService();
  }

  public static void main(String[] args) {
    SpringApplication.run(SCSSEntryPoint.class, args);
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
