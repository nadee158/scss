/**
 * 
 */
package com.privasia.scss.cosmos.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.service.export.ExportUtilService;
import com.privasia.scss.cosmos.model.ISOCode;
import com.privasia.scss.cosmos.repository.ISOCodeCosmosRepository;

@Service("cosmosGateInReadService")
public class CosmosGateInReadService {

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

	public List<ImportContainer> populateCosmosGateInImport(List<ImportContainer> importContainers) {

		if (!(importContainers == null || importContainers.isEmpty())) {

			importContainers.forEach(importContainer -> {
				cosmosGateInImportService.fetchContainerInfo(importContainer);
				if (StringUtils.isNotEmpty(importContainer.getContainer().getContainerISOCode())) {
					Optional<ISOCode> optISOCode = isoCodeCosmosRepository
							.findOne(importContainer.getContainer().getContainerISOCode());
					ISOCode iso = optISOCode.orElseThrow(() -> new ResultsNotFoundException("Cosmos Iso Code Not found in SCSS "
							+ importContainer.getContainer().getContainerISOCode()));
					
					importContainer.getContainer().setContainerHeight(iso.getHeight());
					importContainer.setContainerLength(iso.getLength());
					importContainer.getContainer().setContainerSize(ExportUtilService.getStringValueFromInteger(iso.getLength()));
					importContainer.setContainerType(iso.getType());
					importContainer.setTareWeight(iso.getTareWeight());
				}

			});

		}

		return importContainers;
	}
	
	public List<ExportContainer> populateCosmosGateInExport(List<ExportContainer> exportContainers) {

		if (!(exportContainers == null || exportContainers.isEmpty())) {

			exportContainers.forEach(exportContainer -> {
				cosmosGateInExportService.fetchContainerPrimaryInfo(exportContainer);
				
				if (StringUtils.isNotEmpty(exportContainer.getContainer().getContainerISOCode())) {
					Optional<ISOCode> optISOCode = isoCodeCosmosRepository
							.findOne(exportContainer.getContainer().getContainerISOCode());
					ISOCode iso = optISOCode.orElseThrow(() -> new ResultsNotFoundException("Cosmos Iso Code Not found in SCSS "
							+ exportContainer.getContainer().getContainerISOCode()));
					
					exportContainer.getContainer().setContainerHeight(iso.getHeight());
					//exportContainer.setContainerLength(iso.getLength());
					exportContainer.getContainer().setContainerSize(ExportUtilService.getStringValueFromInteger(iso.getLength()));
					exportContainer.setContainerType(iso.getType());
					exportContainer.setTareWeight(iso.getTareWeight());
				}

			});

		}

		return exportContainers;
	}

	
	
}
