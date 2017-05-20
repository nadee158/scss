/**
 * 
 */
package com.privasia.scss.cosmos.service;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.cosmos.xml.element.GINCNTDRPR;
import com.privasia.scss.cosmos.xml.element.Message;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosResponse;

/**
 * @author Janaka
 *
 */
@Service("cosmosResponseService")
public class CosmosResponseService {
	
	
	public void extractCosmosResponse(SGS2CosmosResponse cosmosResponse, GateInWriteRequest gateInWriteRequest){
		
		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());

		switch (impExpFlag) {
		case IMPORT:
			if (!(gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())) {
				
			}
			break;
		case EXPORT:
			if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
				
			}
			break;
		case IMPORT_EXPORT:
			if (!(gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())) {
				
			}
			if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
				
			}
			break;
		default:
			throw new BusinessException("Invalid transaction Type ! "+impExpFlag.name());
		}
		
		
	}
	
	
	public void extractCosmosResponse(SGS2CosmosResponse cosmosResponse, GateOutWriteRequest gateOutWriteRequest){
		
		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());

		switch (impExpFlag) {
		case IMPORT:
			if (!(gateOutWriteRequest.getImportContainers() == null || gateOutWriteRequest.getImportContainers().isEmpty())) {
				
			}
			break;
		case EXPORT:
			if (!(gateOutWriteRequest.getExportContainers() == null || gateOutWriteRequest.getExportContainers().isEmpty())) {
				
			}
			break;
		case IMPORT_EXPORT:
			if (!(gateOutWriteRequest.getImportContainers() == null || gateOutWriteRequest.getImportContainers().isEmpty())) {
				
			}
			if (!(gateOutWriteRequest.getExportContainers() == null || gateOutWriteRequest.getExportContainers().isEmpty())) {
				
			}
			break;
		default:
			throw new BusinessException("Invalid transaction Type ! "+impExpFlag.name());
		}
		
	}
	
	private List<ImportContainer> setYardPosition(SGS2CosmosResponse cosmosResponse, List<ImportContainer> importContainers){
		
		List<Message> elementList = cosmosResponse.getMessage();
		
		if(elementList == null || elementList.isEmpty())
			throw new BusinessException("Invalid response received from cosmos. No Message elements found! ");
		
		List<GINCNTDRPR> yardPositionlist = elementList.stream().map(Message::getGINCNTDRPR).collect(Collectors.toList());
		
		if(yardPositionlist == null || yardPositionlist.isEmpty())
			throw new BusinessException("Cannot find Yard Position information from cosmos ");
		
		yardPositionlist.stream().forEach(element ->{
			Optional<ImportContainer> optContainer = importContainers.stream().filter(imports -> StringUtils.equalsIgnoreCase(element.getUNITSE(), 
					imports.getContainer().getContainerNumber())).findAny();
			
			ImportContainer importContainer = optContainer.orElseThrow(() -> new BusinessException("Container No "+element.getUNITSE()
						+" not found to populate data"));
			
			//importContainer.setYardBayCode(yardBayCode);
			//importContainer.setYardPosition(yardPosition);
			element.getPKIDSE();
			element.getPSIDSE();
		});
		
		return importContainers;
	}

}
