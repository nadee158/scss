package com.privasia.scss.gateout;

import java.util.Collections;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.service.WDCGlobalSettingService;

@Configuration
public class EmailConfig {

  @Value("${mail.host}")
  private String host;

  @Value("${mail.port}")
  private Integer port;

  @Value("${mail.authenticateUserId}")
  private String authenticateUserId;

  @Value("${mail.authenticatePwd}")
  private String authenticatePassword;

  @Value("${mail.ssLFactory}")
  private String sslFactory;


  private WDCGlobalSettingService wdcGlobalSettingService;

  @Autowired
  public void setWdcGlobalSettingService(WDCGlobalSettingService wdcGlobalSettingService) {
    this.wdcGlobalSettingService = wdcGlobalSettingService;
  }


  public static void main(String[] args) {
    SpringApplication.run(EmailConfig.class, args);
  }


  @Bean(name = "emailTemplateEngine")
  public TemplateEngine getEmailTemplateEngine() {
    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    // Resolver for HTML emails
    templateEngine.addTemplateResolver(htmlTemplateResolver());
    // Resolver for TEXT emails
    templateEngine.addTemplateResolver(textTemplateResolver());
    return templateEngine;
  }


  private ITemplateResolver htmlTemplateResolver() {
    final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setOrder(Integer.valueOf(1));
    templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML5");
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setCacheable(false);
    return templateResolver;
  }



  private ITemplateResolver textTemplateResolver() {
    final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setOrder(Integer.valueOf(2));
    templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".txt");
    templateResolver.setTemplateMode("TEXT");
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setCacheable(false);
    return templateResolver;
  }


  @Bean
  public Filter characterEncodingFilter() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(true);
    return characterEncodingFilter;
  }

  @Bean(name = "javaMailSender")
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    javaMailSender.setHost(host);
    javaMailSender.setPort(port);

    host = wdcGlobalSettingService.getWDCGlobalSetting(ApplicationConstants.SMTP_HOST);
    if (StringUtils.isNotEmpty(host)) {
      javaMailSender.setHost(host);
    }

    javaMailSender.setUsername(authenticateUserId);
    javaMailSender.setPassword(authenticatePassword);
    javaMailSender.setDefaultEncoding("UTF-8");

    Properties prop = javaMailSender.getJavaMailProperties();

    prop.put("javax.net.debug", "ssl,handshake");
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.debug", "true");

    prop.put("mail.smtp.auth", "true");
    prop.put("mail.pop3.socketFactory.class", sslFactory);
    prop.put("mail.smtp.socketFactory.class", sslFactory);

    return javaMailSender;
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
