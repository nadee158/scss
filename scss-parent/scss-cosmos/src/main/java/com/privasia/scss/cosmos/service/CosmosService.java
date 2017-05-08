package com.privasia.scss.cosmos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.interfaces.OpusCosmosBusinessService;

@Service("cosmosService")
public class CosmosService implements OpusCosmosBusinessService {

	private CosmosGateOutImportService cosmosGateOutImportService;
	
	private CosmosGateOutExportService cosmosGateOutExportService;

	@Autowired
	public void setCosmosGateOutImportService(CosmosGateOutImportService cosmosGateOutImportService) {
		this.cosmosGateOutImportService = cosmosGateOutImportService;
	}
	
	@Autowired
	public void setCosmosGateOutExportService(CosmosGateOutExportService cosmosGateOutExportService) {
		this.cosmosGateOutExportService = cosmosGateOutExportService;
	}

	@Override
	public GateInReponse sendGateInRequest(GateInWriteRequest gateInWriteRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GateOutReponse sendGateOutReadRequest(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {
		// TODO Auto-generated method stub
		// gateOutReponse-transaction type - switch
		
		TransactionType trxType = TransactionType.fromCode(gateOutReponse.getTransactionType());
		
		switch (trxType) {
		case IMPORT:
			if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
				List<ImportContainer> updatedImportList = cosmosGateOutImportService.fetchCosmosSeal(gateOutReponse.getImportContainers());
				gateOutReponse.setImportContainers(updatedImportList);
			}
			break;
		case EXPORT:
			if (!(gateOutReponse.getExportContainers() == null || gateOutReponse.getExportContainers().isEmpty())) {
				List<ExportContainer> updatedExportList = cosmosGateOutExportService.setContainerStatus(gateOutReponse.getExportContainers());
				gateOutReponse.setExportContainers(updatedExportList);
			}
			break;
		case IMPORT_EXPORT:
			if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
				List<ImportContainer> updatedImportList = cosmosGateOutImportService.fetchCosmosSeal(gateOutReponse.getImportContainers());
				gateOutReponse.setImportContainers(updatedImportList);
			}
			if (!(gateOutReponse.getExportContainers() == null || gateOutReponse.getExportContainers().isEmpty())) {
				List<ExportContainer> updatedExportList = cosmosGateOutExportService.setContainerStatus(gateOutReponse.getExportContainers());
				gateOutReponse.setExportContainers(updatedExportList);
			}
			break;

		default:
			break;
		}
		
		
		return gateOutReponse;
	}

	@Override
	public GateOutReponse sendGateOutWriteRequest(GateOutWriteRequest gateOutWriteRequest,
			GateOutReponse gateOutReponse) {
		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());
		
		
		switch (impExpFlag) {
		case IMPORT:
			
			break;
		case EXPORT:
			
			break;
			
		case IMPORT_EXPORT:
			
			break;

		default:
			break;
		}
		
		return null;
	}

}