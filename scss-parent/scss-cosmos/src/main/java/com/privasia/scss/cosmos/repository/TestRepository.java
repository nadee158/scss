package com.privasia.scss.cosmos.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.cosmos.dto.TestDto;
import com.privasia.scss.cosmos.rowmappers.TestRowMapper;



@Repository
public class TestRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private Environment env;

  @Value("${testquery:FAILED}")
  private String testText;

  @Transactional(readOnly = true)
  public List<TestDto> testQuery(String containerNo) {
    System.out.println("CAME HERE TestRepository :" + containerNo);
    String sql = env.getProperty("testquery");
    System.out.println("sql ## :" + sql);
    System.out.println("testText %% :" + testText);
    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.query(sql, new Object[] {containerNo}, new TestRowMapper());
  }

}
