package com.privasia.scss.gateout;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.privasia.scss.gateout.mongo.repository")
public class MongoConfig extends AbstractMongoConfiguration {

  @Value("${spring.data.mongodb.database}")
  private String databaseName;

  @Value("${spring.data.mongodb.host}")
  private String hostIp;

  @Override
  public Mongo mongo() throws Exception {
    return new MongoClient(hostIp);
  }

  @Override
  protected String getMappingBasePackage() {
    return "com.privasia.scss.gateout.mongo.repository";
  }

  @Override
  protected String getDatabaseName() {
    return databaseName;
  }


  @Bean
  public GridFsTemplate gridFsTemplate() throws Exception {
    return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
  }



}
