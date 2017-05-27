/**
 * 
 */
package com.privasia.scss.core.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(basePackages = "com.privasia.scss.core.repository", entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager")
@EntityScan(basePackages = {"com.privasia.scss.core.model"})
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
public class PersistenceContext {



  @Bean
  public AuditorAware<Long> auditorProvider() {
    return new UserIDAuditorAwareService();
  }

  @Bean
  public DateTimeProvider dateTimeProvider(CurrentDateTimeService currentDateTimeService) {
    return new AuditingDateTimeProvider(currentDateTimeService);
  }

  @Primary
  @Bean(name = "dataSource", destroyMethod = "close")
  public DataSource dataSource(Environment env) {

    HikariConfig dataSourceConfig = new HikariConfig();
    dataSourceConfig.setDriverClassName(env.getRequiredProperty("spring.datasource.driver"));
    dataSourceConfig.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
    dataSourceConfig.setUsername(env.getRequiredProperty("spring.datasource.username"));
    dataSourceConfig.setPassword(env.getRequiredProperty("spring.datasource.password"));
    dataSourceConfig.setPoolName("scss");

    dataSourceConfig.setConnectionTestQuery(env.getRequiredProperty("spr.datasource.validationQuery"));

    dataSourceConfig.setConnectionTimeout(Long.parseLong(env.getRequiredProperty("spr.datasource.connectionTimeout")));
    dataSourceConfig.setIdleTimeout(Long.parseLong(env.getRequiredProperty("spr.datasource.idleTimeout")));
    dataSourceConfig.setInitializationFailFast(false);
    dataSourceConfig
        .setLeakDetectionThreshold(Long.parseLong(env.getRequiredProperty("spr.datasource.leakDetection.threshold")));
    dataSourceConfig.setMaximumPoolSize(Integer.parseInt(env.getRequiredProperty("spr.datasource.maximumPoolSize")));
    dataSourceConfig.setMaxLifetime(Integer.parseInt(env.getRequiredProperty("spr.datasource.maxLifetime")));
    dataSourceConfig.setMinimumIdle(Integer.parseInt(env.getRequiredProperty("spr.datasource.minimumIdle")));


    dataSourceConfig.addDataSourceProperty("prepStmtCacheSize",
        Integer.parseInt(env.getRequiredProperty("spr.dataSource.prepStmtCacheSize")));
    dataSourceConfig.addDataSourceProperty("prepStmtCacheSqlLimit",
        Integer.parseInt(env.getRequiredProperty("spr.dataSource.prepStmtCacheSqlLimit")));
    dataSourceConfig.addDataSourceProperty("useServerPrepStmts",
        Boolean.parseBoolean(env.getRequiredProperty("spr.dataSource.cachePrepStmts")));


    return new HikariDataSource(dataSourceConfig);

  }

  @Primary
  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource,
      Environment env) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("com.privasia.scss.core.model",
        "org.springframework.data.jpa.convert.threeten");

    Properties jpaProperties = new Properties();

    // Configures the used database dialect. This allows Hibernate to create SQL
    // that is optimized for the used database.
    jpaProperties.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));

    // Specifies the action that is invoked to the database when the Hibernate
    // SessionFactory is created or closed.
    // jpaProperties.put("hibernate.hbm2ddl.auto",
    // env.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));

    // Configures the naming strategy that is used when Hibernate creates
    // new database objects and schema elements
    jpaProperties.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("spring.jpa.hibernate.naming-strategy"));

    // If the value of this property is true, Hibernate writes all SQL
    // statements to the console.
    jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.show-sql"));

    // If the value of this property is true, Hibernate will format the SQL
    // that is written to the console.
    jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("spring.jpa.format_sql"));
    
    jpaProperties.put("hibernate.enable_lazy_load_no_trans", env.getRequiredProperty("spring.jpa.properties.hibernate.enable_lazy_load_no_trans"));
    
    jpaProperties.put("hibernate.open-in-view", env.getRequiredProperty("spring.jpa.open-in-view"));

    entityManagerFactoryBean.setJpaProperties(jpaProperties);

    return entityManagerFactoryBean;
  }

  @Bean(name = "transactionManager")
  public JpaTransactionManager transactionManager(
      @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }



}
