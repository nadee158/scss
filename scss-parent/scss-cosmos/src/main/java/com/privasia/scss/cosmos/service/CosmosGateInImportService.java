package com.privasia.scss.cosmos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.dto.request.CosmosGateInImport;
import com.privasia.scss.cosmos.repository.CosmosImportRepository;
import com.privasia.scss.cosmos.xml.element.service.CSMCTLService;
import com.privasia.scss.cosmos.xml.element.service.GINCNTPUPService;

@Service("cosmosGateInImportService")
public class CosmosGateInImportService {

	private CosmosImportRepository cosmosImportRepository;

	private CSMCTLService csmctlService;

	private GINCNTPUPService gincntpupService;

	@Autowired
	public void setGincntpupService(GINCNTPUPService gincntpupService) {
		this.gincntpupService = gincntpupService;
	}

	@Autowired
	public void setCosmosImportRepository(CosmosImportRepository cosmosImportRepository) {
		this.cosmosImportRepository = cosmosImportRepository;
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
	
	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ImportContainer fetchContainerInfo(ImportContainer importContainer) {
		cosmosImportRepository.getContainerInfo(importContainer);
		boolean internalBlock = cosmosImportRepository.isInternalBlock(importContainer);
		boolean ogaBlock = cosmosImportRepository.isOGABlock(importContainer);
		
		if(internalBlock && ogaBlock){
			throw new BusinessException("Container "+importContainer.getContainer().getContainerNumber() +" :Internal & OGA Block!");
		}else if(internalBlock){
			throw new BusinessException("Container "+importContainer.getContainer().getContainerNumber() +" : Internal Block!");
		}else if(ogaBlock){
			throw new BusinessException("Container "+importContainer.getContainer().getContainerNumber() +" : OGA Block!");
		}
		return importContainer;
	}

	public CosmosGateInImport constructCosmosGateInImport(CosmosCommonValuesDTO commonValuesDTO,
			ImportContainer importContainer, int index) {
		CosmosGateInImport cosmosGateInImport = new CosmosGateInImport();
		cosmosGateInImport.setCSMCTL(csmctlService.constructCSMCTLForImport(commonValuesDTO));
		cosmosGateInImport.setGINCNTPUP(gincntpupService.constructGINCNTPUP(importContainer));
		cosmosGateInImport.setIndex(index);
		return cosmosGateInImport;
	}

}
