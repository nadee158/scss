package com.privasia.scss.cosmos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.cosmos.util.TextString;

@Repository
public class CosmosImportRepository {

	private static final Log log = LogFactory.getLog(CosmosImportRepository.class);

	private JdbcTemplate jdbcTemplate;

	@Value("${import.getContainerInfo}")
	private String queryGetContainerInfo;

	/*@Value("${import.getSealInfo}")
	private String queryGetSealInfo;*/

	@Value("${import.checkLaden}")
	private String queryCheckLaden;

	@Value("${import.isOGABlock}")
	private String queryIsOGABlock;

	@Value("${import.isInternalBlock}")
	private String queryIsInternalBlock;

	@Value("${import.dsoSealNo}")
	private String queryDsoSealNo;
	
	@Autowired
	public void setJdbcTemplate(@Qualifier("as400JdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ImportContainer getContainerInfo(ImportContainer importContainer) {

		String containerNo = StringUtils.upperCase(importContainer.getContainer().getContainerNumber());
		try {
			return jdbcTemplate.queryForObject(queryGetContainerInfo, new Object[] { containerNo },
					(rs, i) -> mapToImportContainer(importContainer, rs));
		} catch (EmptyResultDataAccessException e) {
			log.error("ImportContainer getContainerInfo " + e.getMessage());
			e.printStackTrace();
			throw new BusinessException("FOT/BKG does not exist");
		}
	}

	private ImportContainer mapToImportContainer(ImportContainer importContainer, ResultSet rs){
		
			if(importContainer.getContainer() == null){
				importContainer.setContainer(new CommonContainerDTO());
			}
			try {
				importContainer.setShippingAgent(TextString.format(rs.getString("ORGV05")));
				importContainer.getContainer().setContainerNumber(TextString.format(rs.getString("CNID03")));
				importContainer.setGateInOut(rs.getString("HDTP03"));
				importContainer.setShippingLine(TextString.format(rs.getString("LYND05")));
				importContainer.getContainer().setContainerFullOrEmpty(TextString.format(rs.getString("CNBT03")));
				importContainer.getContainer().setContainerISOCode(TextString.format(rs.getString("CNIS03")));
				importContainer.setOrderFOT(TextString.format(rs.getString("ORRF05")));
				importContainer.setCurrentPosition(TextString.format(rs.getString("PSEX45")));
			} catch (SQLException e) {
				log.error("ImportContainer mapToImportContainer " + e.getMessage());
				e.printStackTrace();
				throw new BusinessException("Error cound while fetching container in cosmos "
						+ importContainer.getContainer().getContainerNumber());
			}
			
		return importContainer;
	}

	/*@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
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

	}*/

	/*@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
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
	}*/

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean isOGABlock(ImportContainer importContainer) {

		String containerNo = StringUtils.upperCase(importContainer.getContainer().getContainerNumber());
		try {
			return jdbcTemplate.queryForObject(queryIsOGABlock, new Object[] { containerNo }, (rs, i) -> extractOGAOrInternalBlock(rs));
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	private boolean extractOGAOrInternalBlock(ResultSet rs){
		return true;
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean isInternalBlock(ImportContainer importContainer) {

		String containerNo = StringUtils.upperCase(importContainer.getContainer().getContainerNumber());
		
		try {
			return jdbcTemplate.queryForObject(queryIsInternalBlock, new Object[] { containerNo }, (rs, i) -> extractOGAOrInternalBlock(rs));
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ImportContainer fetchDsoSealNo(ImportContainer importContainer) {

		String containerNo = StringUtils.upperCase(importContainer.getContainer().getContainerNumber());
		String vesselScn = StringUtils.upperCase(importContainer.getVesselScn());
		return jdbcTemplate.queryForObject(queryDsoSealNo, new Object[] { containerNo, vesselScn },
				(rs, i) -> extractDsoSealNumbers(importContainer, rs, i));

	}

	private ImportContainer extractDsoSealNumbers(ImportContainer importContainer, ResultSet rs, int rowNum)
			throws SQLException {

		String sealOrigin = null;
		String sealType = null;
		if(rs.next()) {
			if (importContainer.getSealAttribute() == null) {
				CommonSealDTO sealAttribute = new CommonSealDTO();
				importContainer.setSealAttribute(sealAttribute);
			}
			
			sealOrigin = StringUtils.trim(rs.getString("SLOR2K"));
			sealType = StringUtils.trim(rs.getString("SLTP2K"));
			log.info("sealOrigin" + sealOrigin);
			log.info("sealType" + sealType);

			if (StringUtils.equalsIgnoreCase("L", sealOrigin) && StringUtils.equalsIgnoreCase("SL", sealType)) {
				importContainer.getSealAttribute().setSeal01Origin(StringUtils.trim(rs.getString("SLOR2K")));
				importContainer.setCosmosSeal01Origin(StringUtils.trim(rs.getString("SLOR2K")));
				importContainer.getSealAttribute().setSeal01Type(StringUtils.trim(rs.getString("SLTP2K")));
				importContainer.setCosmosSeal01Type(StringUtils.trim(rs.getString("SLTP2K")));
				importContainer.getSealAttribute().setSeal01Number(StringUtils.trim(rs.getString("SEAL2K")));
				importContainer.setCosmosSeal01Number(StringUtils.trim(rs.getString("SEAL2K")));
				  
			} else if (StringUtils.equalsIgnoreCase("S", sealOrigin) && StringUtils.equalsIgnoreCase("SL", sealType)) {
				importContainer.getSealAttribute().setSeal02Origin(StringUtils.trim(rs.getString("SLOR2K")));
				importContainer.setCosmosSeal01Origin(StringUtils.trim(rs.getString("SLOR2K")));
				importContainer.getSealAttribute().setSeal02Type(StringUtils.trim(rs.getString("SLTP2K")));
				importContainer.setCosmosSeal02Type(StringUtils.trim(rs.getString("SLTP2K")));
				importContainer.getSealAttribute().setSeal02Number(StringUtils.trim(rs.getString("SEAL2K")));
				importContainer.setCosmosSeal02Number(StringUtils.trim(rs.getString("SEAL2K")));
			}
		}

		return importContainer;
	}

}
