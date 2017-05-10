package com.privasia.scss.gatein.lpkedi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;

@Repository("lpkediRepository")
public class LPKEDIRepository {

  private JdbcTemplate lpkediJdbcTemplate;

  @Value("${export.findLPKEDITDigiMessage}")
  private String findLPKEDITDigiMessage;

  @Autowired
  public void setLpkediJdbcTemplate(@Qualifier("lpkediJdbcTemplate") final JdbcTemplate lpkediJdbcTemplate) {
    this.lpkediJdbcTemplate = lpkediJdbcTemplate;
  }

  @Transactional(value = "lpkediTransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ExportContainer findLPKEDITDigiMessage(ExportContainer exportContainer)
      throws SQLException, SQLTimeoutException {

    String contNo = StringUtils.upperCase(exportContainer.getContainer().getContainerNumber());
    String contNoWithSpace = "";

    if (contNo.length() > 4) {
      String contNoSplitFirst = StringUtils.substring(contNo, 0, 4);
      String contNoSplitSecond = StringUtils.substring(contNo, 4);
      contNoWithSpace = contNoSplitFirst + " " + contNoSplitSecond;
    }

    String vessleSCN = StringUtils.upperCase(exportContainer.getVesselSCN());


    return lpkediJdbcTemplate.queryForObject(findLPKEDITDigiMessage, new Object[] {contNoWithSpace, contNo, vessleSCN},
        (rs, i) -> extractLPKEDITDigiMessage(exportContainer, rs, i));

  }


  private ExportContainer extractLPKEDITDigiMessage(ExportContainer exportContainer, ResultSet rs, int rowNum)
      throws SQLException, SQLTimeoutException {

    if (rs.next()) {
      exportContainer.setLpkApproval(rs.getString("KPA_APPROVAL"));
      exportContainer.setHdlGoodsCode(rs.getString("GOODS_HDL_CODE"));
      // f.setGoodsHdlDescC1(rs.getString("GOODS_HDL_DESC"));
      exportContainer.setHdlGoodsDescription(rs.getString("HANDLING_DESC"));
      exportContainer.setDgDescription(rs.getString("DG_DESC"));
      exportContainer.setLpkClass(rs.getString("KPA_CLASS"));
    }
    return exportContainer;
  }



}
