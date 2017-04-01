package com.privasia.scss.cosmos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.SealInfo;
import com.privasia.scss.cosmos.util.TextString;

@Repository
public class CosmosImportRepository {

  private static final Log log = LogFactory.getLog(CosmosImportRepository.class);

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

  @Value("${import.dsoSealNo}")
  private String queryDsoSealNo;

  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ImportContainer getContainerInfo(ImportContainer importContainer, String containerNo) {

    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.queryForObject(queryGetContainerInfo, new Object[] {containerNo},
        (rs, i) -> mapToImportContainer(importContainer, rs, i));
  }

  private ImportContainer mapToImportContainer(ImportContainer importContainer, ResultSet rs, int rowNum)
      throws SQLException {
    if (rs != null) {
      importContainer.setShippingAgent(TextString.format(rs.getString("orgv05")));
      importContainer.getContainer().setContainerNumber(TextString.format(rs.getString("cnid03")));
      importContainer.setGateInOut(rs.getString("hdtp03"));
      importContainer.setShippingLine(TextString.format(rs.getString("lynd05")));
      importContainer.getContainer().setContainerFullOrEmpty(TextString.format(rs.getString("cnbt03")));
      if (importContainer.getContainer() == null) {
        importContainer.setContainer(new CommonContainerDTO());
      }
      importContainer.getContainer().setContainerISOCode(TextString.format(rs.getString("cnis03")));
      importContainer.setOrderFOT(TextString.format(rs.getString("orrf05")));
      importContainer.setCurrentPosition(TextString.format(rs.getString("psex45")));
      importContainer.setHandlingID(Long.parseLong(TextString.format(rs.getString("hdid10"))));
    } else {
      importContainer.setFOTBKGFlag(false);
    }
    return importContainer;
  }

  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public Optional<List<SealInfo>> getSealInfo(String handingID) {

    handingID = StringUtils.upperCase(handingID);
    return jdbcTemplate.query(queryGetSealInfo, new Object[] {handingID}, this::extractSealInfo);

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

  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public boolean checkLaden(String containerNo) {

    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.query(queryGetSealInfo, new Object[] {containerNo}, this::verifyLaden);

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

  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public boolean isOGABlock(String containerNo) {

    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.query(queryIsOGABlock, new Object[] {containerNo}, this::extractOGAOrInternalBlock);

  }

  private boolean extractOGAOrInternalBlock(ResultSet rs) throws SQLException {

    if (rs.next()) {
      return true;
    }

    return false;
  }

  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public boolean isInternalBlock(String containerNo) {

    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.query(queryIsInternalBlock, new Object[] {containerNo}, this::extractOGAOrInternalBlock);

  }

  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public CommonSealDTO fetchDsoSealNo(String containerNo, String vesselScn) {

    containerNo = StringUtils.upperCase(containerNo);
    vesselScn = StringUtils.upperCase(vesselScn);
    return jdbcTemplate.queryForObject(queryDsoSealNo, new Object[] {containerNo, vesselScn},
        (rs, i) -> extractDsoSealNumbers(rs, i));

  }

  private CommonSealDTO extractDsoSealNumbers(ResultSet rs, int rowNum) throws SQLException {

    CommonSealDTO commonSealDTO = null;
    String sealOrigin = null;
    String sealType = null;
    while (rs.next()) {
      commonSealDTO = new CommonSealDTO();
      sealOrigin = StringUtils.trim(rs.getString("SLOR2K"));
      sealType = StringUtils.trim(rs.getString("SLTP2K"));
      log.info("sealOrigin" + sealOrigin);
      log.info("sealType" + sealType);

      if (StringUtils.equalsIgnoreCase("L", sealOrigin) && StringUtils.equalsIgnoreCase("SL", sealType)) {
        commonSealDTO.setSeal01Origin(StringUtils.trim(rs.getString("SLOR2K")));
        commonSealDTO.setSeal01Type(StringUtils.trim(rs.getString("SLTP2K")));
        commonSealDTO.setSeal01Number(StringUtils.trim(rs.getString("SEAL2K")));

      } else if (StringUtils.equalsIgnoreCase("S", sealOrigin) && StringUtils.equalsIgnoreCase("SL", sealType)) {
        commonSealDTO.setSeal02Origin(StringUtils.trim(rs.getString("SLOR2K")));
        commonSealDTO.setSeal02Type(StringUtils.trim(rs.getString("SLTP2K")));
        commonSealDTO.setSeal02Number(StringUtils.trim(rs.getString("SEAL2K")));
      }
    }

    return commonSealDTO;
  }

}
