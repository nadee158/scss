package com.privasia.scss.hpat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.privasia.scss.hpat.etp.service.client.ETPWebserviceClient;

/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.*"})
@EnableAutoConfiguration
public class HPATEntryPoint extends SpringBootServletInitializer {

  @Value("${ws.client.default.uri}")
  private String clientDefaultUri;

  @Value("${ws.context.path}")
  private String wsContextPath;

  public static void main(String[] args) {
    SpringApplication.run(HPATEntryPoint.class, args);
  }


  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<HPATEntryPoint> applicationClass = HPATEntryPoint.class;

  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    System.out.println("wsContextPath :" + wsContextPath);
    marshaller.setContextPath(wsContextPath);
    return marshaller;
  }

  @Bean
  public ETPWebserviceClient weatherClient(Jaxb2Marshaller marshaller) {
    System.out.println("clientDefaultUri :" + clientDefaultUri);
    ETPWebserviceClient client = new ETPWebserviceClient();
    client.setDefaultUri(clientDefaultUri);
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }


}
