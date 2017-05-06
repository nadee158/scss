package com.privasia.scss.cosmos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ContainerStatus;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.cosmos.util.TextString;

@Repository
public class CosmosExportRepository {

	private JdbcTemplate jdbcTemplate;

	@Value("${export.ogaBlock}")
	private String queryOGABlock;

	@Value("${export.internalBlock}")
	private String queryInternalBlock;

	@Value("${export.containerStatus}")
	private String queryContainerStatus;

	@Value("${export.containerPrimaryInfo}")
	private String queryContainerInfo;

	@Autowired
	public void setJdbcTemplate(@Qualifier("as400JdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer isOGABlock(ExportContainer exportContainer, String containerNo) {

		return jdbcTemplate.queryForObject(queryOGABlock, new Object[] { containerNo },
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

		return jdbcTemplate.queryForObject(queryInternalBlock, new Object[] { containerNo },
				(rs, i) -> extractInternalBlock(exportContainer, rs, i));

	}

	private ExportContainer extractInternalBlock(ExportContainer exportContainer, ResultSet rs, int rowNum)
			throws SQLException {

		if (rs.next()) {
			exportContainer.setInternalBlock(true);
			exportContainer.setInternalBlockDesc(rs.getString("INOP30"));
		}
		return exportContainer;
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public String checkContainerStatus(String containerNo, String timeGateIn) {

		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.queryForObject(queryContainerStatus, new Object[] { containerNo, timeGateIn },
				(rs, i) -> extractContainerStatus(rs));

	}

	private String extractContainerStatus(ResultSet rs) throws SQLException {

		if (rs != null && rs.next()) {
			return rs.getString("HDFS03");
		} else {
			return ContainerStatus.NON_EXECUTE.getValue();
		}

	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer fetchContainerInfo(String exportContainerNo) {

		exportContainerNo = StringUtils.upperCase(exportContainerNo);
		Optional<ExportContainer> optExportContainer = jdbcTemplate.queryForObject(queryContainerInfo,
				new Object[] { exportContainerNo }, (rs, i) -> extractContainerInfo(rs, i));
		
		if(!optExportContainer.isPresent())
			optExportContainer = Optional.of(new ExportContainer());
		
		ExportContainer exportContainer = optExportContainer.get();
		optExportContainer.get().getContainer().setContainerNumber(exportContainerNo);
		isInternalBlock(exportContainer, exportContainerNo);
		isOGABlock(exportContainer, exportContainerNo);
		
		return exportContainer;

	}

	private Optional<ExportContainer> extractContainerInfo(ResultSet rs, int rowNum) throws SQLException {
		
		ExportContainer exportContainer = null;
		int rowcount = 0;
		if (rs.last()) {
			rowcount = rs.getRow();
			rs.beforeFirst(); 
		}

		if (rs.next()) {

			exportContainer = new ExportContainer();
			CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
			exportContainer.setContainer(commonContainerDTO);
			exportContainer.setTotalBooking(rowcount);

			exportContainer.setBookingNoExist(true);
			exportContainer.setBookingNo(rs.getString("ORRF05"));
			exportContainer.getContainer().setContainerFullOrEmpty(rs.getString("CNBT03"));
			exportContainer.setShippingLine(rs.getString("LYND05"));
			exportContainer.setShippingAgent(rs.getString("ORGV05"));

			exportContainer.setVesselCode(rs.getString("VMID01"));
			exportContainer.setVesselVoyageIN(rs.getString("RSIN01"));
			exportContainer.setVesselVoyageOUT(rs.getString("RSUT01"));
			exportContainer.setVesselVisitID(rs.getString("BZID01"));
			exportContainer.setVesselSCN(StringUtils.trim(rs.getString("REGN01")));
			exportContainer.setVesselName(StringUtils.trim(rs.getString("MVVA47")));
			exportContainer.setVesselStatus(rs.getString("BZFS01"));

			exportContainer.setVesselETADate(DateUtil.getLocalDategFromString(
					rs.getString("ETAD01") + TextString.padding(rs.getString("ETAT01"), 6, '0', true)));
			
		}
		
		return Optional.ofNullable(exportContainer);
	}

}
