package com.privasia.scss.gateout;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.privasia.scss.gateout.mongo.repository")
public class MongoConfig extends AbstractMongoConfiguration {

  @Value("${collection.solas}")
  private String solasCollection;

  @Value("${collection.pdf}")
  private String pdfCollection;

  @Value("${collection.zip}")
  private String zipCollection;

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


  @Bean(name = "pdfFileGridFsTemplate", autowire = Autowire.BY_NAME)
  public GridFsTemplate pdfFileGridFsTemplate() throws Exception {
    System.out.println("pdfCollection " + pdfCollection);
    return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter(), pdfCollection);
  }

  @Bean(name = "zipFileGridFsTemplate", autowire = Autowire.BY_NAME)
  public GridFsTemplate zipFileGridFsTemplate() throws Exception {
    System.out.println("zipCollection " + zipCollection);
    return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter(), zipCollection);
  }

  @Bean(name = "solasCertificateGridFsTemplate", autowire = Autowire.BY_NAME)
  public GridFsTemplate solasCertificateGridFsTemplate() throws Exception {
    System.out.println("solasCollection " + solasCollection);
    return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter(), solasCollection);
  }

  @Primary
  @Bean(name = "gridFsTemplate", autowire = Autowire.BY_NAME)
  public GridFsTemplate gridFsTemplate() throws Exception {
    return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
  }



}
