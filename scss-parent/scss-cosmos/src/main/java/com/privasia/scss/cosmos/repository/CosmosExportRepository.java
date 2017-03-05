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

	@Value("${export.isOGABlock}")
	private String queryIsOGABlock;

	@Value("${export.isInternalBlock}")
	private String queryIsInternalBlock;
	
	@Value("${export.containerStatus}")
	private String queryContainerStatus;

	@Transactional(readOnly = true)
	public boolean isOGABlock(String containerNo) {

		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.query(queryIsOGABlock, new Object[] { containerNo }, this::extractOGABlock);

	}

	private boolean extractOGABlock(ResultSet rs) throws SQLException {

		if (rs.next()) {
			return true;
		}
		return false;
	}

	@Transactional(readOnly = true)
	public ExportContainer isInternalBlock(ExportContainer exportContainer, String containerNo) {

		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.queryForObject(queryIsInternalBlock, new Object[] { containerNo },
				(rs, i) -> extractInternalBlock(exportContainer, rs, i));

	}

	private ExportContainer extractInternalBlock(ExportContainer exportContainer, ResultSet rs, int rowNum)
			throws SQLException {

		if (rs.next()) {
			exportContainer.setInternalBlock(true);
			exportContainer.setInternalBlockDesc(rs.getString("inop30"));
		} else {
			exportContainer.setInternalBlock(true);
		}

		return exportContainer;
	}
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public String checkContainerStatus(String containerNo, String timeGateIn) {

		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.queryForObject(queryContainerStatus, new Object[] { containerNo, timeGateIn },
				(rs, i) -> extractContainerStatus(rs));

	}

	private String extractContainerStatus(ResultSet rs) throws SQLException {

		if (rs != null && rs.next()) {
  		  return rs.getString("hdfs03");
  	  	} else {
  	  		
  	  	return ContainerStatus.NON_EXECUTE.getValue();
  	  	}

	}

}
