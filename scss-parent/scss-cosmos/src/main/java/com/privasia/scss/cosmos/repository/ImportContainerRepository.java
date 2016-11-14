package com.privasia.scss.cosmos.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.cosmos.dto.TestDto;
import com.privasia.scss.cosmos.rowmappers.TestRowMapper;



@Repository
public class ImportContainerRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Value("${cosmos.getImportContainerInfo}")
  private String queryGetImportContainerInfo;

  @Transactional(readOnly = true)
  public List<TestDto> getImportContainerInfo(String containerNo) {
    
    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.query(queryGetImportContainerInfo, new Object[] {containerNo}, new TestRowMapper());
  }

}
