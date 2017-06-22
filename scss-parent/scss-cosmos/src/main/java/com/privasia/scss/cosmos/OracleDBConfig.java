/**
 * 
 */
package com.privasia.scss.cosmos;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.privasia.scss.cosmos.util.AuditingDateTimeProvider;
import com.privasia.scss.cosmos.util.CurrentDateTimeService;
import com.privasia.scss.cosmos.util.UserIDAuditorAwareService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Janaka
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(dateTimeProviderRef = "cosmosOracleDateTimeProvider")
@EnableJpaRepositories(basePackages = "com.privasia.scss.cosmos.oracle.repository",
    entityManagerFactoryRef = "cosmosOracleEntityManagerFactory",
    transactionManagerRef = "cosmosOracleTransactionManager")
@EntityScan("com.privasia.scss.cosmos.model")
@EnableAutoConfiguration(
    exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
public class OracleDBConfig {

  @Configuration
  @Profile("dev")
  @PropertySource({"classpath:cosmos_application-dev.properties"})
  static class Dev {
  }

  @Configuration
  @Profile("prod")
  @PropertySource({"classpath:cosmos_application-prod.properties"})
  static class Prod {
  }

  @Configuration
  @Profile("uat")
  @PropertySource({"classpath:cosmos_application-uat.properties"})
  static class Uat {
  }



  @Bean
  public AuditorAware<Long> auditorProvider() {
    return new UserIDAuditorAwareService();
  }

  @Bean(name = "cosmosOracleDateTimeProvider")
  public DateTimeProvider cosmosOracleDateTimeProvider(
      @Qualifier("cosmosCurrentDateTimeService") CurrentDateTimeService currentDateTimeService) {
    return new AuditingDateTimeProvider(currentDateTimeService);
  }


  @Bean(name = "cosmosOracleDataSource", destroyMethod = "close")
  public DataSource cosmosOracleDataSource(Environment env) {

    HikariConfig dataSourceConfig = new HikariConfig();
    dataSourceConfig.setDriverClassName(env.getRequiredProperty("spring.ds_ora.datasource.driver"));
    dataSourceConfig.setJdbcUrl(env.getRequiredProperty("spring.ds_ora.datasource.url"));
    dataSourceConfig.setUsername(env.getRequiredProperty("spring.ds_ora.datasource.username"));
    dataSourceConfig.setPassword(env.getRequiredProperty("spring.ds_ora.datasource.password"));
    dataSourceConfig.setPoolName(env.getRequiredProperty("spring.ds_ora.datasource.poolname"));


    dataSourceConfig.setConnectionTestQuery(
        env.getRequiredProperty("spring.ds_ora.datasource.validationQuery"));

    dataSourceConfig.setConnectionTimeout(
        Long.parseLong(env.getRequiredProperty("spring.ds_ora.datasource.connectionTimeout")));
    dataSourceConfig.setIdleTimeout(
        Long.parseLong(env.getRequiredProperty("spring.ds_ora.datasource.idleTimeout")));
    dataSourceConfig.setInitializationFailFast(false);
    dataSourceConfig.setLeakDetectionThreshold(Long
        .parseLong(env.getRequiredProperty("spring.ds_ora.datasource.leakDetection.threshold")));
    dataSourceConfig.setMaximumPoolSize(
        Integer.parseInt(env.getRequiredProperty("spring.ds_ora.datasource.maximumPoolSize")));
    dataSourceConfig.setMaxLifetime(
        Integer.parseInt(env.getRequiredProperty("spring.ds_ora.datasource.maxLifetime")));
    dataSourceConfig.setMinimumIdle(
        Integer.parseInt(env.getRequiredProperty("spring.ds_ora.datasource.minimumIdle")));


    dataSourceConfig.addDataSourceProperty("prepStmtCacheSize",
        Integer.parseInt(env.getRequiredProperty("spring.ds_ora.dataSource.prepStmtCacheSize")));
    dataSourceConfig.addDataSourceProperty("prepStmtCacheSqlLimit", Integer
        .parseInt(env.getRequiredProperty("spring.ds_ora.dataSource.prepStmtCacheSqlLimit")));
    dataSourceConfig.addDataSourceProperty("useServerPrepStmts",
        Boolean.parseBoolean(env.getRequiredProperty("spring.ds_ora.dataSource.cachePrepStmts")));


    return new HikariDataSource(dataSourceConfig);

  }


  @Bean(name = "cosmosOracleEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean cosmosOracleEntityManagerFactory(
      @Qualifier("cosmosOracleDataSource") DataSource dataSource, Environment env) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("com.privasia.scss.cosmos.model",
        "org.springframework.data.jpa.convert.threeten");

    Properties jpaProperties = new Properties();

    // Configures the used database dialect. This allows Hibernate to create SQL
    // that is optimized for the used database.
    jpaProperties.put("hibernate.dialect",
        env.getRequiredProperty("spring.ds_ora.jpa.properties.hibernate.dialect"));

    // Specifies the action that is invoked to the database when the Hibernate
    // SessionFactory is created or closed.
    // jpaProperties.put("hibernate.hbm2ddl.auto",
    // env.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));

    // Configures the naming strategy that is used when Hibernate creates
    // new database objects and schema elements
    jpaProperties.put("hibernate.ejb.naming_strategy",
        env.getRequiredProperty("spring.ds_ora.jpa.hibernate.naming-strategy"));

    // If the value of this property is true, Hibernate writes all SQL
    // statements to the console.
    jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("spring.ds_ora.jpa.show-sql"));

    // If the value of this property is true, Hibernate will format the SQL
    // that is written to the console.
    jpaProperties.put("hibernate.format_sql",
        env.getRequiredProperty("spring.ds_ora.jpa.format_sql"));

    entityManagerFactoryBean.setJpaProperties(jpaProperties);

    return entityManagerFactoryBean;
  }

  @Bean(name = "cosmosOracleTransactionManager")
  public JpaTransactionManager cosmosOracleTransactionManager(
      @Qualifier("cosmosOracleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }



}
