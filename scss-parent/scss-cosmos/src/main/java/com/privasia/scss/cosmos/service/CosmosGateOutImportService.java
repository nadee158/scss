package com.privasia.scss.cosmos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.repository.CosmosImportRepository;

@Service("cosmosGateOutImportService")
public class CosmosGateOutImportService {

  private CosmosImportRepository cosmosImportRepository;

  @Autowired
  public void setCosmosImportRepository(CosmosImportRepository cosmosImportRepository) {
    this.cosmosImportRepository = cosmosImportRepository;
  }

  public List<ImportContainer> constructGateOutImport(List<ImportContainer> importContainers) {
    if (!(importContainers == null || importContainers.isEmpty())) {
      importContainers.forEach(importContainer -> {
        // check
        cosmosImportRepository.fetchDsoSealNo(importContainer);
      });
    }
    return importContainers;
  }

}
