package com.privasia.scss.cosmos.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.rowmappers.ImportContainerRowMapper;



@Repository
public class ImportContainerRepository {

  @Autowired
  @Qualifier("as400JdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Value("${cosmos.getImportContainerInfo}")
  private String queryGetImportContainerInfo;

  @Transactional(readOnly = true)
  public List<ImportContainer> getImportContainerInfo(ImportContainer importContainer, String containerNo) {
    
    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.query(queryGetImportContainerInfo, new Object[] {containerNo}, new ImportContainerRowMapper(importContainer));
  }

}
