/**
 * 
 */
package com.privasia.scss.core.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.privasia.scss.core.util.service.AuditingDateTimeProvider;
import com.privasia.scss.core.util.service.CurrentDateTimeService;
import com.privasia.scss.core.util.service.UserIDAuditorAwareService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Janaka
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(basePackages = {"com.privasia.scss"})
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
public class PersistenceContext {



  @Bean
  AuditorAware<Long> auditorProvider() {
    return new UserIDAuditorAwareService();
  }

  @Bean
  DateTimeProvider dateTimeProvider(CurrentDateTimeService currentDateTimeService) {
    return new AuditingDateTimeProvider(currentDateTimeService);
  }

  @Bean(destroyMethod = "close")
  DataSource dataSource(Environment env)  {
	  
		HikariConfig dataSourceConfig = new HikariConfig();
	    dataSourceConfig.setDriverClassName(env.getRequiredProperty("spring.datasource.driver"));
	    dataSourceConfig.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
	    dataSourceConfig.setUsername(env.getRequiredProperty("spring.datasource.username"));
	    dataSourceConfig.setPassword(env.getRequiredProperty("spring.datasource.password"));
	    dataSourceConfig.setPoolName("scss");
	    return new HikariDataSource(dataSourceConfig);
    
  }

  @Bean
  LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("com.privasia.scss.core.model", "org.springframework.data.jpa.convert.threeten");

    Properties jpaProperties = new Properties();

    // Configures the used database dialect. This allows Hibernate to create SQL
    // that is optimized for the used database.
    jpaProperties.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));

    // Specifies the action that is invoked to the database when the Hibernate
    // SessionFactory is created or closed.
    //jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));

    // Configures the naming strategy that is used when Hibernate creates
    // new database objects and schema elements
    jpaProperties.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("spring.jpa.hibernate.naming-strategy"));

    // If the value of this property is true, Hibernate writes all SQL
    // statements to the console.
    jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.show-sql"));

    // If the value of this property is true, Hibernate will format the SQL
    // that is written to the console.
    jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("spring.jpa.format_sql"));

    entityManagerFactoryBean.setJpaProperties(jpaProperties);

    return entityManagerFactoryBean;
  }

  
  JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }



}
