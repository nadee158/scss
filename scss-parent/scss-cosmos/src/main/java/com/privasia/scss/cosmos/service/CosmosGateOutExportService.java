package com.privasia.scss.cosmos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutExport;
import com.privasia.scss.cosmos.repository.CosmosExportRepository;

@Service("cosmosGateOutExportService")
public class CosmosGateOutExportService {

	private CosmosExportRepository cosmosExportRepository;

	@Autowired
	public void setCosmosExportRepository(CosmosExportRepository cosmosExportRepository) {
		this.cosmosExportRepository = cosmosExportRepository;
	}


	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExportContainer> setContainerStatus(List<ExportContainer> exportContainers) {
		if (!(exportContainers == null || exportContainers.isEmpty())) {
			exportContainers.forEach(exportContainer -> {
				LocalDateTime timeGateIn = exportContainer.getBaseCommonGateInOutAttribute().getTimeGateIn();
				String status = cosmosExportRepository.checkContainerStatus(exportContainer.getContainer().getContainerNumber(), 
							DateUtil.getFormatteDateTime(timeGateIn, DateUtil.GLOBAL_DATE_PATTERN_YYYYMMDD));
				exportContainer.setRtgExecustionStatus(status);
				
			});
		}
		return exportContainers;
	}
	
	public void constructCosmosGateOutWriteRequest(){
		
		CosmosGateOutExport gateOutExport = new CosmosGateOutExport();
		gateOutExport.setIndex(1);
		
	}

	
}
