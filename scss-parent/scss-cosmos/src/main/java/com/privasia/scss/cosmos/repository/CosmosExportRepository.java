package com.privasia.scss.cosmos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ContainerStatus;

@Repository
public class CosmosExportRepository {

  @Autowired
  @Qualifier("as400JdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Value("${export.ogaBlock}")
  private String queryOGABlock;

  @Value("${export.internalBlock}")
  private String queryInternalBlock;

  @Value("${export.containerStatus}")
  private String queryContainerStatus;

  @Value("${export.containerInfo}")
  private String queryContainerInfo;


  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ExportContainer isOGABlock(ExportContainer exportContainer) {

    String containerNo = StringUtils.upperCase(exportContainer.getContainer().getContainerNumber());
    return jdbcTemplate.queryForObject(queryOGABlock, new Object[] {containerNo},
        (rs, i) -> extractOGABlock(exportContainer, rs));

  }

  private ExportContainer extractOGABlock(ExportContainer exportContainer, ResultSet rs) throws SQLException {

    if (rs.next()) {
      exportContainer.setOgaBlock(true);
    }
    return exportContainer;
  }

  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ExportContainer isInternalBlock(ExportContainer exportContainer, String containerNo) {

    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.queryForObject(queryInternalBlock, new Object[] {containerNo},
        (rs, i) -> extractInternalBlock(exportContainer, rs, i));

  }

  private ExportContainer extractInternalBlock(ExportContainer exportContainer, ResultSet rs, int rowNum)
      throws SQLException {

    if (rs.next()) {
      exportContainer.setInternalBlock(true);
      exportContainer.setInternalBlockDesc(rs.getString("inop30"));
    }
    return exportContainer;
  }

  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public String checkContainerStatus(String containerNo, String timeGateIn) {

    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.queryForObject(queryContainerStatus, new Object[] {containerNo, timeGateIn},
        (rs, i) -> extractContainerStatus(rs));

  }

  private String extractContainerStatus(ResultSet rs) throws SQLException {

    if (rs != null && rs.next()) {
      return rs.getString("hdfs03");
    } else {

      return ContainerStatus.NON_EXECUTE.getValue();
    }

  }

  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ExportContainer fetchContainerInfo(String exportContainerNo) {

    exportContainerNo = StringUtils.upperCase(exportContainerNo);
    return jdbcTemplate.queryForObject(queryContainerInfo, new Object[] {exportContainerNo},
        (rs, i) -> extractContainerInfo(rs, i));

  }

  private ExportContainer extractContainerInfo(ResultSet rs, int rowNum) throws SQLException {

    if (rs.next()) {

    }
    return new ExportContainer();
  }

}
