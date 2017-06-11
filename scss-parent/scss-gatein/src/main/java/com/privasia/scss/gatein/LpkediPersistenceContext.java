/**
 * 
 */
package com.privasia.scss.gatein;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Janaka
 *
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "com.privasia.scss.gatein.lpkedi.repository",
    transactionManagerRef = "lpkediTransactionManager")
public class LpkediPersistenceContext {



  @Bean(name = "lpkediJdbcTemplate")
  public JdbcTemplate jdbcTemplate(@Qualifier("lpkediDataSource") DataSource lpkediDataSource) {
    return new JdbcTemplate(lpkediDataSource);
  }

  @Bean(name = "lpkediDataSource", destroyMethod = "close")
  @ConfigurationProperties(prefix = "lpkedi")
  public DataSource lpkediDataSource(Environment env) {

    HikariConfig dataSourceConfig = new HikariConfig();
    dataSourceConfig.setDriverClassName(env.getRequiredProperty("lpkedi.spring.datasource.driver"));
    dataSourceConfig.setJdbcUrl(env.getRequiredProperty("lpkedi.spring.datasource.url"));
    dataSourceConfig.setUsername(env.getRequiredProperty("lpkedi.spring.datasource.username"));
    dataSourceConfig.setPassword(env.getRequiredProperty("lpkedi.spring.datasource.password"));
    dataSourceConfig.setPoolName(env.getRequiredProperty("lpkedi.spring.datasource.poolname"));

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



  @Bean(name = "lpkediTransactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("lpkediDataSource") DataSource lpkediDataSource) {
    return new DataSourceTransactionManager(lpkediDataSource);
  }



}
