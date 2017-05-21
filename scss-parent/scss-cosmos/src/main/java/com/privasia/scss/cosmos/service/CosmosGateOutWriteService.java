/**
 * 
 */
package com.privasia.scss.cosmos.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutImport;

@Service("cosmosGateOutWriteService")
public class CosmosGateOutWriteService {

	private CosmosGateOutImportService cosmosGateOutImportService;
	
	@Autowired
	public void setCosmosGateOutImportService(CosmosGateOutImportService cosmosGateOutImportService) {
		this.cosmosGateOutImportService = cosmosGateOutImportService;
	}
	

	public List<CosmosGateOutImport> constructCosmosGateoutImport(CosmosCommonValuesDTO commonValuesDTO,
			List<ImportContainer> importContainers, int startIndex) {
		
		final List<CosmosGateOutImport> cosmosImportList = new ArrayList<>();
		if (!(importContainers == null || importContainers.isEmpty())) {
			
			for (ImportContainer importContainer : importContainers) {
				
				if(!(StringUtils.equalsIgnoreCase(importContainer.getBaseCommonGateInOutAttribute().getEirStatus(),
						TransactionStatus.APPROVED.getValue())
						&& StringUtils.equalsIgnoreCase(importContainer.getCommonGateInOut().getGateInStatus(),
								TransactionStatus.APPROVED.getValue()))){
					cosmosImportList.add(
							cosmosGateOutImportService.constructCosmosGateOutImport(commonValuesDTO, importContainer, startIndex));
					startIndex++;
				}
				
			}
			
		}

		return cosmosImportList;
	}


	

}
