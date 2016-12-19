package com.privasia.scss.cosmos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.SealInfo;
import com.privasia.scss.cosmos.util.TextString;

@Repository
public class CosmosImportRepository {

	@Autowired
	@Qualifier("as400JdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Value("${import.getContainerInfo}")
	private String queryGetContainerInfo;

	@Value("${import.getSealInfo}")
	private String queryGetSealInfo;

	@Value("${import.checkLaden}")
	private String queryCheckLaden;

	@Value("${import.isOGABlock}")
	private String queryIsOGABlock;

	@Value("${import.isInternalBlock}")
	private String queryIsInternalBlock;

	@Transactional(readOnly = true)
	public ImportContainer getContainerInfo(ImportContainer importContainer, String containerNo) {

		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.queryForObject(queryGetContainerInfo, new Object[] { containerNo },
				(rs, i) -> mapToImportContainer(importContainer, rs, i));
	}

	private ImportContainer mapToImportContainer(ImportContainer importContainer, ResultSet rs, int rowNum)
			throws SQLException {
		if (rs != null) {
			importContainer.setAgentCode(TextString.format(rs.getString("orgv05")));
			importContainer.setContainerNumber(TextString.format(rs.getString("cnid03")));
			importContainer.setInOrOut(rs.getString("hdtp03"));
			importContainer.setLine(TextString.format(rs.getString("lynd05")));
			importContainer.setFullOrEmpty(TextString.format(rs.getString("cnbt03")));
			importContainer.setIsoCode(TextString.format(rs.getString("cnis03")));
			importContainer.setOrderFOT(TextString.format(rs.getString("orrf05")));
			importContainer.setCurrentPosition(TextString.format(rs.getString("psex45")));
			importContainer.setHandlingID(TextString.format(rs.getString("hdid10")));
		} else {
			importContainer.setFOTBKGFlag(false);
		}
		return importContainer;
	}

	@Transactional(readOnly = true)
	public Optional<List<SealInfo>> getSealInfo(String handingID) {

		handingID = StringUtils.upperCase(handingID);
		return jdbcTemplate.query(queryGetSealInfo, new Object[] { handingID }, this::extractSealInfo);

	}

	private Optional<List<SealInfo>> extractSealInfo(ResultSet rs) throws SQLException, DataAccessException {

		SealInfo sealInfo = null;
		List<SealInfo> sealInfoList = null;
		if (rs != null) {
			sealInfoList = new ArrayList<>();
			while (rs.next()) {
				sealInfo = new SealInfo();
				sealInfo.setSealOrigin(TextString.format(rs.getString("slor2k")));
				sealInfo.setSealType(TextString.format(rs.getString("sltp2k")));
				sealInfo.setSealNo(TextString.format(rs.getString("seal2k")));
				sealInfoList.add(sealInfo);
			}
		}

		return Optional.of(sealInfoList);

	}

	@Transactional(readOnly = true)
	public boolean checkLaden(String containerNo) {

		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.query(queryGetSealInfo, new Object[] { containerNo }, this::verifyLaden);

	}

	private boolean verifyLaden(ResultSet rs) throws SQLException {

		if (rs.next()) {
			if (rs.getString("cnbt03") != null && rs.getString("cnbt03").equals("E")) {
				// return GatePassErrMsg.GATE_PASS_OK;
				return true;
			}
		}
		// return GatePassErrMsg.GATE_PASS_NO_PREARRIVAL;
		return false;
	}

	@Transactional(readOnly = true)
	public boolean isOGABlock(String containerNo) {

		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.query(queryIsOGABlock, new Object[] { containerNo }, this::extractOGAOrInternalBlock);

	}

	private boolean extractOGAOrInternalBlock(ResultSet rs) throws SQLException {

		if (rs.next()) {
			return true;
		}

		return false;
	}

	@Transactional(readOnly = true)
	public boolean isInternalBlock(String containerNo) {

		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.query(queryIsInternalBlock, new Object[] { containerNo }, this::extractOGAOrInternalBlock);

	}

}
