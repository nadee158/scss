/**
 * 
 */
package com.privasia.scss.cosmos;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Janaka
 *
 */
@Configuration
@EnableAutoConfiguration
@PropertySource(value = { "classpath:cosmos_application.properties" })
public class AS400DBConfig {
	
	@Bean(name = "as400Db", destroyMethod = "close")
	@ConfigurationProperties(prefix = "spring.ds_as400")
	public DataSource as400DataSource(Environment env) {

		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(env.getRequiredProperty("spring.ds_as400.driverClassName"));
		dataSourceConfig.setJdbcUrl(env.getRequiredProperty("spring.ds_as400.url"));
		dataSourceConfig.setUsername(env.getRequiredProperty("spring.ds_as400.username"));
		dataSourceConfig.setPassword(env.getRequiredProperty("spring.ds_as400.password"));
		dataSourceConfig.setPoolName(env.getRequiredProperty("spring.ds_as400.connectionPool"));
		dataSourceConfig.setConnectionTestQuery(env.getRequiredProperty("spring.ds_as400.connectionTestQuery"));
		return new HikariDataSource(dataSourceConfig);
		

	}
	
	@Bean(name = "as400JdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("as400Db") DataSource as400Db) {
		return new JdbcTemplate(as400Db);
	}
	
	@Bean(name= "as400TransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("as400Db") DataSource as400Db) {
		return new DataSourceTransactionManager(as400Db);
	}

}
