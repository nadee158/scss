package com.privasia.scss.core.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.privasia.scss.core.util.constant.Profiles;
import com.privasia.scss.core.util.service.CurrentDateTimeService;

@SpringBootApplication
@Import({PersistenceContext.class})
public class SCSSEntryPoint {
	
	@Profile(Profiles.APPLICATION)
	@Bean
	CurrentDateTimeService getCurrentDateTimeService(){
		return new CurrentDateTimeService();
	}

	public static void main(String[] args) {
		SpringApplication.run(SCSSEntryPoint.class, args);
	}
}
