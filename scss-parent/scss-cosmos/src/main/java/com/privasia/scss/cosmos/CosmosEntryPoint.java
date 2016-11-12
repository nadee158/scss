package com.privasia.scss.cosmos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;


/**
 * Janaka Wanigatunga
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.cosmos.*", "com.privasia.scss.gateout.*"})
@EnableAutoConfiguration
@EnableTransactionManagement
public class CosmosEntryPoint extends SpringBootServletInitializer {

  // sql = "SELECT cnid03" + ", hdtp03" + ", cnbt03" + ", lynd05" + ", orgv05" + ", cnis03" + ",
  // orrf05"
  // + ", psex45" + ", hdid10" + " FROM PCTCSE.cthndl5" + ", PCTCSE.ctlthd2" + ", PCTCSE.ctordru"
  // + ", PSPACEE.spcinfi" + " WHERE hdtm03='WPT1'" + " AND cnid03=" + SQL.format(containerNo)
  // //+ ", PSPACEE.spcinfi" + " WHERE hdtm03='WPT1'" + " AND cnid03 LIKE " + "'%" + containerNo +
  // "%'"
  // + " AND hdtp03='OUT'" + " AND lttp10='ORD'" + " AND hdid10 = hdid03" + " AND orid10 = orid05"
  // + " AND lhfs10='RGS'" + " AND (ortp05='FOT'" + " OR ortp05 = 'BKG'" + " OR ortp05 = 'CNA' )" +
  // " AND trmc45='WPT1'"
  // + " AND cnid45=cnid03" + " AND psex45 IS NOT NULL";

  public static void main(String[] args) {
    SpringApplication.run(CosmosEntryPoint.class, args);
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<CosmosEntryPoint> applicationClass = CosmosEntryPoint.class;



}
