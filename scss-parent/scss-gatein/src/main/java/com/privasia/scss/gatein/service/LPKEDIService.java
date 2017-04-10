package com.privasia.scss.gatein.service;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.gatein.lpkedi.repository.LPKEDIRepository;

@Service("lpkediService")
@Transactional
public class LPKEDIService {

  @Autowired
  private LPKEDIRepository lpkediRepository;

  public ExportContainer findLPKEDITDigiMessage(ExportContainer exportContainer) {
    try {
      exportContainer = lpkediRepository.findLPKEDITDigiMessage(exportContainer);
    } catch (SQLTimeoutException e) {
      e.printStackTrace();
      throw new BusinessException(
          "Unable to access DG container approval record <Timeout>, please proceed with manual validation.<br/>");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new BusinessException(
          "Unable to access DG container approval record, please proceed with manual validation. <br/>");
    }
    return exportContainer;
  }

}