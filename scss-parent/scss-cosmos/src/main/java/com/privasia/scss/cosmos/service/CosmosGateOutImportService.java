package com.privasia.scss.cosmos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.repository.CosmosImportRepository;

@Service("cosmosGateOutImportService")
public class CosmosGateOutImportService {

	private CosmosImportRepository cosmosImportRepository;

	@Autowired
	public void setCosmosImportRepository(CosmosImportRepository cosmosImportRepository) {
		this.cosmosImportRepository = cosmosImportRepository;
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

}
