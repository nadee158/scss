/**
 * 
 */
package com.privasia.scss.cosmos.service;

import java.util.Optional;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.service.export.ExportUtilService;
import com.privasia.scss.cosmos.model.ISOCode;
import com.privasia.scss.cosmos.oracle.repository.ISOCodeCosmosRepository;

@Service("cosmosGateInReadService")
public class CosmosGateInReadService {

	private static final Log log = LogFactory.getLog(CosmosGateInReadService.class);

	private CosmosGateInImportService cosmosGateInImportService;

	private ISOCodeCosmosRepository isoCodeCosmosRepository;

	private CosmosGateInExportService cosmosGateInExportService;

	@Autowired
	public void setCosmosGateInImportService(CosmosGateInImportService cosmosGateInImportService) {
		this.cosmosGateInImportService = cosmosGateInImportService;
	}

	@Autowired
	public void setIsoCodeCosmosRepository(ISOCodeCosmosRepository isoCodeCosmosRepository) {
		this.isoCodeCosmosRepository = isoCodeCosmosRepository;
	}

	@Autowired
	public void setCosmosGateInExportService(CosmosGateInExportService cosmosGateInExportService) {
		this.cosmosGateInExportService = cosmosGateInExportService;
	}

	@Async
	public Future<GateInResponse> populateCosmosGateInImport(GateInResponse gateInResponse) {

		if (!(gateInResponse.getImportContainers() == null || gateInResponse.getImportContainers().isEmpty())) {

			gateInResponse.getImportContainers().forEach(importContainer -> {
				System.out.println("Running in populateCosmosGateInImport **************************** "+importContainer.getContainer().getContainerNumber());
				cosmosGateInImportService.fetchContainerInfo(importContainer);

				if (StringUtils.isNotEmpty(importContainer.getContainer().getContainerISOCode())) {
					Optional<ISOCode> optISOCode = isoCodeCosmosRepository
							.findOne(importContainer.getContainer().getContainerISOCode());
					ISOCode iso = optISOCode
							.orElseThrow(() -> new ResultsNotFoundException("Cosmos Iso Code Not found in SCSS "
									+ importContainer.getContainer().getContainerISOCode()));

					importContainer.getContainer().setContainerHeight(iso.getHeight());
					importContainer.setContainerLength(iso.getLength());
					importContainer.getContainer()
							.setContainerSize(ExportUtilService.getStringValueFromInteger(iso.getLength()));
					importContainer.setContainerType(iso.getType());
					importContainer.setTareWeight(iso.getTareWeight());
				}

			});

		}else{
			gateInResponse.setImportContainers(null);
			System.out.println("else part running in populateCosmosGateInImport **************************** ");
		}

		return new AsyncResult<GateInResponse>(gateInResponse);
	}

	@Async
	public Future<GateInResponse> populateCosmosGateInExport(GateInResponse gateInResponse) {

		if (!(gateInResponse.getExportContainers() == null || gateInResponse.getExportContainers().isEmpty())) {

			gateInResponse.getExportContainers().forEach(exportContainer -> {
				System.out.println("Running in populateCosmosGateInExport **************************** "+exportContainer.getContainer().getContainerNumber());
				Future<Boolean> primary = cosmosGateInExportService.fetchPrimanyInfoContainerInfo(exportContainer);
				Future<Boolean> Secondary = cosmosGateInExportService.fetchSecondaryContainerInfo(exportContainer);

				while (primary.isDone() && Secondary.isDone()) {
					if (StringUtils.isNotEmpty(exportContainer.getContainer().getContainerISOCode())) {
						Optional<ISOCode> optISOCode = isoCodeCosmosRepository
								.findOne(exportContainer.getContainer().getContainerISOCode());
						ISOCode iso = optISOCode
								.orElseThrow(() -> new ResultsNotFoundException("Cosmos Iso Code Not found in SCSS "
										+ exportContainer.getContainer().getContainerISOCode()));

						exportContainer.getContainer().setContainerHeight(iso.getHeight());
						// exportContainer.setContainerLength(iso.getLength());
						exportContainer.getContainer()
								.setContainerSize(ExportUtilService.getStringValueFromInteger(iso.getLength()));
						exportContainer.setContainerType(iso.getType());
						exportContainer.setTareWeight(iso.getTareWeight());
					}
				}

			});
		}else{
			gateInResponse.setExportContainers(null);
			System.out.println("else part running in populateCosmosGateInExport **************************** ");
		}

		return new AsyncResult<GateInResponse>(gateInResponse);
	}

}
