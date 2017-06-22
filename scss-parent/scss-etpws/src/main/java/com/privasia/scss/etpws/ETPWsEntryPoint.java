package com.privasia.scss.etpws;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.privasia.scss.etpws.service.client.ETPWebserviceClient;

/**
 * Janaka Wanigatunga
 *
 */
@Configuration
public class ETPWsEntryPoint {

  @Value("${ws.client.default.uri}")
  private String clientDefaultUri;

  @Value("${ws.context.path}")
  private String wsContextPath;

  @Value("${ws.server.uri}")
  private String wsServerUri;

  @Configuration
  @Profile("dev")
  @PropertySource({"classpath:ws-dev.properties"})
  static class Dev {
  }

  @Configuration
  @Profile("prod")
  @PropertySource({"classpath:ws-prod.properties"})
  static class Prod {
  }

  @Configuration
  @Profile("uat")
  @PropertySource({"classpath:ws-uat.properties"})
  static class Uat {
  }


  // public static void main(String[] args) {
  // SpringApplication.run(ETPWsEntryPoint.class, args);
  // }
  //
  //
  // @Override
  // protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  // return application.sources(applicationClass);
  // }
  //
  // private static Class<ETPWsEntryPoint> applicationClass = ETPWsEntryPoint.class;

  @Bean(name = "marshaller")
  public Jaxb2Marshaller marshaller() throws Exception {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setCheckForXmlRootElement(false);
    marshaller.setContextPath(wsContextPath);
    marshaller.afterPropertiesSet();
    return marshaller;
  }

  @Bean(name = "etpWebserviceClient")
  public ETPWebserviceClient etpWebserviceClient(
      @Qualifier("marshaller") Jaxb2Marshaller marshaller) throws Exception {
    ETPWebserviceClient client = new ETPWebserviceClient(clientDefaultUri, wsServerUri);
    client.setDefaultUri(clientDefaultUri);
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    client.afterPropertiesSet();
    return client;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }


}
