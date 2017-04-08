/**
 * 
 */
package com.privasia.scss.gatein;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@EnableJpaRepositories(basePackages = "com.privasia.scss.gatein.lpkedi",
    entityManagerFactoryRef = "lpkediEntityManagerFactory", transactionManagerRef = "lpkediTransactionManager")
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
public class LpkediPersistenceContext {



  @Bean
  public AuditorAware<Long> auditorProvider() {
    return new UserIDAuditorAwareService();
  }

  @Bean
  public DateTimeProvider dateTimeProvider(CurrentDateTimeService currentDateTimeService) {
    return new AuditingDateTimeProvider(currentDateTimeService);
  }

  @Bean(name = "lpkediDataSource", destroyMethod = "close")
  @ConfigurationProperties(prefix = "lpkedi")
  public DataSource lpkediDataSource(Environment env) {

    HikariConfig dataSourceConfig = new HikariConfig();
    dataSourceConfig.setDriverClassName(env.getRequiredProperty("lpkedi.spring.datasource.driver"));
    dataSourceConfig.setJdbcUrl(env.getRequiredProperty("lpkedi.spring.datasource.url"));
    dataSourceConfig.setUsername(env.getRequiredProperty("lpkedi.spring.datasource.username"));
    dataSourceConfig.setPassword(env.getRequiredProperty("lpkedi.spring.datasource.password"));
    dataSourceConfig.setPoolName("scss_lpkedi");

    dataSourceConfig.setConnectionTestQuery(env.getRequiredProperty("lpkedi.spring.datasource.validationQuery"));

    dataSourceConfig
        .setConnectionTimeout(Long.parseLong(env.getRequiredProperty("lpkedi.spring.datasource.connectionTimeout")));
    dataSourceConfig.setIdleTimeout(Long.parseLong(env.getRequiredProperty("lpkedi.spring.datasource.idleTimeout")));
    dataSourceConfig.setInitializationFailFast(false);
    dataSourceConfig.setLeakDetectionThreshold(
        Long.parseLong(env.getRequiredProperty("lpkedi.spring.datasource.leakDetection.threshold")));
    dataSourceConfig
        .setMaximumPoolSize(Integer.parseInt(env.getRequiredProperty("lpkedi.spring.datasource.maximumPoolSize")));
    dataSourceConfig.setMaxLifetime(Integer.parseInt(env.getRequiredProperty("lpkedi.spring.datasource.maxLifetime")));
    dataSourceConfig.setMinimumIdle(Integer.parseInt(env.getRequiredProperty("lpkedi.spring.datasource.minimumIdle")));


    dataSourceConfig.addDataSourceProperty("prepStmtCacheSize",
        Integer.parseInt(env.getRequiredProperty("lpkedi.spring.datasource.prepStmtCacheSize")));
    dataSourceConfig.addDataSourceProperty("prepStmtCacheSqlLimit",
        Integer.parseInt(env.getRequiredProperty("lpkedi.spring.datasource.prepStmtCacheSqlLimit")));
    dataSourceConfig.addDataSourceProperty("useServerPrepStmts",
        Boolean.parseBoolean(env.getRequiredProperty("lpkedi.spring.datasource.cachePrepStmts")));


    return new HikariDataSource(dataSourceConfig);

  }

  @Bean(name = "lpkediEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean lpkediEntityManagerFactory(DataSource lpkediDataSource,
      Environment env) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(lpkediDataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("com.privasia.scss.core.model",
        "org.springframework.data.jpa.convert.threeten");

    Properties jpaProperties = new Properties();

    // Configures the used database dialect. This allows Hibernate to create SQL
    // that is optimized for the used database.
    jpaProperties.put("hibernate.dialect", env.getRequiredProperty("lpkedi.spring.jpa.properties.hibernate.dialect"));

    // Specifies the action that is invoked to the database when the Hibernate
    // SessionFactory is created or closed.
    // jpaProperties.put("hibernate.hbm2ddl.auto",
    // env.getRequiredProperty("lpkedi.spring.jpa.hibernate.ddl-auto"));

    // Configures the naming strategy that is used when Hibernate creates
    // new database objects and schema elements
    jpaProperties.put("hibernate.ejb.naming_strategy",
        env.getRequiredProperty("lpkedi.spring.jpa.hibernate.naming-strategy"));

    // If the value of this property is true, Hibernate writes all SQL
    // statements to the console.
    jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("lpkedi.spring.jpa.show-sql"));

    // If the value of this property is true, Hibernate will format the SQL
    // that is written to the console.
    jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("lpkedi.spring.jpa.format_sql"));

    entityManagerFactoryBean.setJpaProperties(jpaProperties);

    return entityManagerFactoryBean;
  }

  @Bean(name = "lpkediTransactionManager")
  public JpaTransactionManager lpkediTransactionManager(EntityManagerFactory lpkediEntityManagerFactory) {
    JpaTransactionManager lpkediTransactionManager = new JpaTransactionManager();
    lpkediTransactionManager.setEntityManagerFactory(lpkediEntityManagerFactory);
    return lpkediTransactionManager;
  }



}
