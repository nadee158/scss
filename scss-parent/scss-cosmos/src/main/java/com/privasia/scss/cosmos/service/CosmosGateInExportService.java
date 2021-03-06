package com.privasia.scss.cosmos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.dto.request.CosmosGateInExport;
import com.privasia.scss.cosmos.repository.CosmosExportRepository;
import com.privasia.scss.cosmos.xml.element.service.CSMCTLService;
import com.privasia.scss.cosmos.xml.element.service.GINCNTDRPService;

@Service("cosmosGateInExportService")
public class CosmosGateInExportService {

	private CosmosExportRepository cosmosExportRepository;

	private CSMCTLService csmctlService;

	private GINCNTDRPService gincntdrpService;

	@Autowired
	public void setCosmosExportRepository(CosmosExportRepository cosmosExportRepository) {
		this.cosmosExportRepository = cosmosExportRepository;
	}

	@Autowired
	public void setGincntdrpService(GINCNTDRPService gincntdrpService) {
		this.gincntdrpService = gincntdrpService;
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
				String status = cosmosExportRepository.checkContainerStatus(
						exportContainer.getContainer().getContainerNumber(),
						DateUtil.getFormatteDateTime(timeGateIn, DateUtil.GLOBAL_DATE_PATTERN_YYYYMMDD));
				exportContainer.setRtgExecustionStatus(status);

			});
		}
		return exportContainers;
	}

	@Async
	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Future<Boolean> fetchPrimanyInfoContainerInfo(ExportContainer exportContainer) {
		System.out.println("fetchPrimanyInfoContainerInfo service");
		exportContainer = cosmosExportRepository.fetchContainerPrimanyInfo(exportContainer);
		System.out
				.println("fetchPrimanyInfoContainerInfo service" + exportContainer.getContainer().getContainerNumber());
		cosmosExportRepository.isInternalBlock(exportContainer);
		cosmosExportRepository.isOGABlock(exportContainer);

		return new AsyncResult<Boolean>(true);
	}

	@Async
	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Future<Boolean> fetchSecondaryContainerInfo(ExportContainer exportContainer) {

		exportContainer = cosmosExportRepository.fetchContainerSecondaryInfo(exportContainer);

		return new AsyncResult<Boolean>(true);
	}

	public CosmosGateInExport constructCosmosGateInExport(CosmosCommonValuesDTO commonValuesDTO,
			ExportContainer exportContainer, int index) {
		CosmosGateInExport cosmosGateInExport = new CosmosGateInExport();
		cosmosGateInExport.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO, "CTEDSE"));
		cosmosGateInExport.setGINCNTDRP(gincntdrpService.constructGINCNTDRP(exportContainer));
		cosmosGateInExport.setIndex(index);
		return cosmosGateInExport;
	}

}
