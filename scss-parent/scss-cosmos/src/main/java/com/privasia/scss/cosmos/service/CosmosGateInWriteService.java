/**
 * 
 */
package com.privasia.scss.cosmos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.dto.request.CosmosGateInExport;
import com.privasia.scss.cosmos.dto.request.CosmosGateInImport;
import com.privasia.scss.cosmos.dto.request.CosmosGateInWriteRequest;
import com.privasia.scss.cosmos.xml.element.service.CSMCTLService;
import com.privasia.scss.cosmos.xml.element.service.GINTRCINFService;

@Service("cosmosGateInWriteService")
public class CosmosGateInWriteService {

	private CSMCTLService csmctlService;

	private GINTRCINFService gintrcinfService;

	private CosmosGateInImportService cosmosGateInImportService;
	
	private CosmosGateInExportService cosmosGateInExportService;

	@Autowired
	public void setCsmctlService(CSMCTLService csmctlService) {
		this.csmctlService = csmctlService;
	}

	@Autowired
	public void setGintrcinfService(GINTRCINFService gintrcinfService) {
		this.gintrcinfService = gintrcinfService;
	}

	@Autowired
	public void setCosmosGateInImportService(CosmosGateInImportService cosmosGateInImportService) {
		this.cosmosGateInImportService = cosmosGateInImportService;
	}
	
	@Autowired
	public void setCosmosGateInExportService(CosmosGateInExportService cosmosGateInExportService) {
		this.cosmosGateInExportService = cosmosGateInExportService;
	}

	public CosmosGateInWriteRequest constructCosmosGateInWriteRequest(CosmosCommonValuesDTO commonValuesDTO) {

		CosmosGateInWriteRequest cosmosGateInWriteRequest = new CosmosGateInWriteRequest();
		cosmosGateInWriteRequest.setIndex(1); 
		cosmosGateInWriteRequest.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
		cosmosGateInWriteRequest.setGINTRCINF(gintrcinfService.constructGINTRCINF(commonValuesDTO));

		return cosmosGateInWriteRequest;
	}

	public List<CosmosGateInImport> constructCosmosGateInImport(CosmosCommonValuesDTO commonValuesDTO,
			List<ImportContainer> importContainers, int startIndex) {
		
		final List<CosmosGateInImport> cosmosImportList = new ArrayList<>();
		if (!(importContainers == null || importContainers.isEmpty())) {
			
			for (ImportContainer importContainer : importContainers) {
				cosmosImportList.add(
						cosmosGateInImportService.constructCosmosGateInImport(commonValuesDTO, importContainer, startIndex));
				startIndex++;
			}
			
		}

		return cosmosImportList;
	}
	
	public List<CosmosGateInExport> constructCosmosGateInExport(CosmosCommonValuesDTO commonValuesDTO,
			List<ExportContainer> exportContainers, int startIndex) {
		
		final List<CosmosGateInExport> cosmosExportList = new ArrayList<>();
		if (!(exportContainers == null || exportContainers.isEmpty())) {
			
			for (ExportContainer exportContainer : exportContainers) {
				cosmosExportList.add(
						cosmosGateInExportService.constructCosmosGateInExport(commonValuesDTO, exportContainer, startIndex));
				startIndex++;
			}
			
		}

		return cosmosExportList;
	}

}
