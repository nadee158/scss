package com.privasia.scss.gatein.lpkedi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

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

import com.privasia.scss.common.dto.ExportContainer;

@Repository("lpkediRepository")
public class LPKEDIRepository {
	
	private static final Log log = LogFactory.getLog(LPKEDIRepository.class);

  private JdbcTemplate lpkediJdbcTemplate;

  @Value("${export.findLPKEDITDigiMessage}")
  private String findLPKEDITDigiMessage;

  @Autowired
  public void setLpkediJdbcTemplate(@Qualifier("lpkediJdbcTemplate") final JdbcTemplate lpkediJdbcTemplate) {
    this.lpkediJdbcTemplate = lpkediJdbcTemplate;
  }

  @Transactional(value = "lpkediTransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ExportContainer findLPKEDITDigiMessage(ExportContainer exportContainer) {

    String contNo = StringUtils.upperCase(exportContainer.getContainer().getContainerNumber());
    String contNoWithSpace = "";

    if (contNo.length() > 4) {
      String contNoSplitFirst = StringUtils.substring(contNo, 0, 4);
      String contNoSplitSecond = StringUtils.substring(contNo, 4);
      contNoWithSpace = contNoSplitFirst + " " + contNoSplitSecond;
    }

    String vessleSCN = StringUtils.upperCase(exportContainer.getVesselSCN());
    
    try {
    	return lpkediJdbcTemplate.queryForObject(findLPKEDITDigiMessage, new Object[] {contNoWithSpace, contNo, vessleSCN},
    	        (rs, i) -> extractLPKEDITDigiMessage(exportContainer, rs, i));
	} catch (EmptyResultDataAccessException e) {
		return exportContainer;
	}

  }


  private ExportContainer extractLPKEDITDigiMessage(ExportContainer exportContainer, ResultSet rs, int rowNum) {

      try {
		exportContainer.setLpkApproval(rs.getString("KPA_APPROVAL"));
		exportContainer.setHdlGoodsCode(rs.getString("GOODS_HDL_CODE"));
	    exportContainer.setHdlGoodsDescription(rs.getString("HANDLING_DESC"));
	    exportContainer.setDgDescription(rs.getString("DG_DESC"));
	    exportContainer.setLpkClass(rs.getString("KPA_CLASS"));
	} catch (SQLTimeoutException e) {
		log.error("ExportContainer extractLPKEDITDigiMessage " + e.getMessage());
		e.printStackTrace();
		exportContainer.setDgMessage("Unable to access DG container approval record <Timeout>, please proceed with manual validation.");
	} catch (SQLException e) {
		log.error("ExportContainer extractLPKEDITDigiMessage " + e.getMessage());
		exportContainer.setDgMessage("Unable to access DG container approval record, please proceed with manual validation. ");
	}
    return exportContainer;
  }



}
