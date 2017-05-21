package com.privasia.scss.cosmos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutImport;
import com.privasia.scss.cosmos.repository.CosmosImportRepository;
import com.privasia.scss.cosmos.xml.element.service.CSMCTLService;
import com.privasia.scss.cosmos.xml.element.service.GOTCNTINFService;
import com.privasia.scss.cosmos.xml.element.service.GOTTRCINFService;

@Service("cosmosGateOutImportService")
public class CosmosGateOutImportService {

  private CosmosImportRepository cosmosImportRepository;

  private CSMCTLService csmctlService;

  private GOTTRCINFService gottrcinfService;

  private GOTCNTINFService gotcntinfService;

  @Autowired
  public void setGotcntinfService(GOTCNTINFService gotcntinfService) {
    this.gotcntinfService = gotcntinfService;
  }

  @Autowired
  public void setCosmosImportRepository(CosmosImportRepository cosmosImportRepository) {
    this.cosmosImportRepository = cosmosImportRepository;
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
  public List<ImportContainer> fetchCosmosSeal(List<ImportContainer> importContainers) {
    if (!(importContainers == null || importContainers.isEmpty())) {
      importContainers.forEach(importContainer -> {
        cosmosImportRepository.fetchDsoSealNo(importContainer);
      });
    }
    return importContainers;
  }

  public CosmosGateOutImport constructCosmosGateOutImport(CosmosCommonValuesDTO commonValuesDTO,
      ImportContainer importContainer, int index) {
    CosmosGateOutImport cosmosGateOutImport = new CosmosGateOutImport();
    cosmosGateOutImport.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
    cosmosGateOutImport.setGOTCNTINF(gotcntinfService.constructGOTCNTINF(importContainer));
    cosmosGateOutImport.setIndex(index);
    return cosmosGateOutImport;
  }

}
