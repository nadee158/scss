package com.privasia.scss.etpws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.privasia.scss.etpws.service.client.ETPWebserviceClient;

/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@PropertySource("classpath:ws.properties")
public class ETPWsEntryPoint extends SpringBootServletInitializer {

  @Value("${ws.client.default.uri}")
  private String clientDefaultUri;

  @Value("${ws.context.path}")
  private String wsContextPath;

  public static void main(String[] args) {
    SpringApplication.run(ETPWsEntryPoint.class, args);
  }


  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<ETPWsEntryPoint> applicationClass = ETPWsEntryPoint.class;

  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    System.out.println("wsContextPath :" + wsContextPath);
    marshaller.setCheckForXmlRootElement(false);
    marshaller.setContextPath(wsContextPath);
    return marshaller;
  }

  @Bean
  public ETPWebserviceClient etpWebserviceClient(Jaxb2Marshaller marshaller) {
    System.out.println("clientDefaultUri :" + clientDefaultUri);
    ETPWebserviceClient client = new ETPWebserviceClient();
    client.setDefaultUri(clientDefaultUri);
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }


}
