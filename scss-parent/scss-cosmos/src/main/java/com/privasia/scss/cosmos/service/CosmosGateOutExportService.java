package com.privasia.scss.cosmos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutExport;
import com.privasia.scss.cosmos.repository.CosmosExportRepository;
import com.privasia.scss.cosmos.xml.element.service.CSMCTLService;
import com.privasia.scss.cosmos.xml.element.service.GOTTRCINFService;

@Service("cosmosGateOutExportService")
public class CosmosGateOutExportService {

  private CosmosExportRepository cosmosExportRepository;

  private CSMCTLService csmctlService;

  private GOTTRCINFService gottrcinfService;

  @Autowired
  public void setCosmosExportRepository(CosmosExportRepository cosmosExportRepository) {
    this.cosmosExportRepository = cosmosExportRepository;
  }

  @Autowired
  public void setGottrcinfService(GOTTRCINFService gottrcinfService) {
    this.gottrcinfService = gottrcinfService;
  }

  @Autowired
  public void setCsmctlService(CSMCTLService csmctlService) {
    this.csmctlService = csmctlService;
  }


  @Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<ExportContainer> setContainerStatus(List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      exportContainers.forEach(exportContainer -> {
        LocalDateTime timeGateIn = exportContainer.getBaseCommonGateInOutAttribute().getTimeGateIn();
        String status = cosmosExportRepository.checkContainerStatus(exportContainer.getContainer().getContainerNumber(),
            DateUtil.getFormatteDateTime(timeGateIn, DateUtil.GLOBAL_DATE_PATTERN_yyyyMMdd));
        exportContainer.setRtgExecustionStatus(status);

      });
    }
    return exportContainers;
  }

  public CosmosGateOutExport constructCosmosGateOutExport(CosmosCommonValuesDTO commonValuesDTO, int index) {
    CosmosGateOutExport cosmosGateOutExport = new CosmosGateOutExport();
    cosmosGateOutExport.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
    cosmosGateOutExport.setGOTTRCINF(gottrcinfService.constructGOTTRCINF(commonValuesDTO));
    cosmosGateOutExport.setIndex(index);
    return cosmosGateOutExport;
  }



//@formatter:off
//  + "<Message Index=\"1\">\n"
//      + "<CSMCTL>\n"
//      + "</CSMCTL>\n"
//      + "<GOTTRCINF>\n" // For Gate In Truck Information
//      + "</GOTTRCINF>\n"
//      + "</Message>\n"
//@formatter:on


}
