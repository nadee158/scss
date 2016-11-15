package com.privasia.scss.cosmos.repository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.SealInfo;
import com.privasia.scss.cosmos.rowmappers.ImportContainerRowMapper;
import com.privasia.scss.cosmos.rowmappers.ImportSealRowMapper;



@Repository
public class ImportSealRepository {

  @Autowired
  @Qualifier("as400JdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Value("${cosmos.getImportSealInfo}")
  private String queryGetImportSealInfo;

 
  public Optional<List<SealInfo>> getImportSealInfo(String handingID) {
    
	handingID = StringUtils.upperCase(handingID);
    return jdbcTemplate.query(queryGetImportSealInfo, new Object[] {handingID}, new ImportSealRowMapper());
  }

}
