package com.privasia.scss.gateout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.privasia.scss.*"})
@EnableAutoConfiguration
public class GateOutEntryPoint {
  public static void main(String[] args) {
    SpringApplication.run(GateOutEntryPoint.class, args);
  }
}
