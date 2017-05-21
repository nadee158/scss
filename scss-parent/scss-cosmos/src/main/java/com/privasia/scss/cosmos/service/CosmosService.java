package com.privasia.scss.cosmos.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.GateWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.interfaces.OpusCosmosBusinessService;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.dto.request.CosmosGateInExport;
import com.privasia.scss.cosmos.dto.request.CosmosGateInImport;
import com.privasia.scss.cosmos.dto.request.CosmosGateInWriteRequest;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutImport;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutWriteRequest;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosRequest;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosResponse;
import com.privasia.scss.cosmos.xml.element.service.CSMCTLService;
import com.privasia.scss.cosmos.xml.element.service.GINTRCINFService;
import com.privasia.scss.cosmos.xml.element.service.GateOutMessageService;

@Service("cosmos")
public class CosmosService implements OpusCosmosBusinessService {

	private CosmosGateOutImportService cosmosGateOutImportService;

	private CosmosGateOutExportService cosmosGateOutExportService;

	private GateOutMessageService gateOutMessageService;

	private CosmosGateInWriteService cosmosGateInWriteService;
	
	private AGSClientService agsClientService;
	
	private CSMCTLService csmctlService;
	
	private GINTRCINFService gintrcinfService;
	
	private CosmosResponseService cosmosResponseService;

	@Autowired
	public void setCosmosGateInWriteService(CosmosGateInWriteService cosmosGateInWriteService) {
		this.cosmosGateInWriteService = cosmosGateInWriteService;
	}

	@Autowired
	public void setGateOutMessageService(GateOutMessageService gateOutMessageService) {
		this.gateOutMessageService = gateOutMessageService;
	}

	@Autowired
	public void setCosmosGateOutImportService(CosmosGateOutImportService cosmosGateOutImportService) {
		this.cosmosGateOutImportService = cosmosGateOutImportService;
	}

	@Autowired
	public void setCosmosGateOutExportService(CosmosGateOutExportService cosmosGateOutExportService) {
		this.cosmosGateOutExportService = cosmosGateOutExportService;
	}
	
	@Autowired
	public void setAgsClientService(AGSClientService agsClientService) {
		this.agsClientService = agsClientService;
	}
	
	@Autowired
	public void setCsmctlService(CSMCTLService csmctlService) {
		this.csmctlService = csmctlService;
	}
	
	@Autowired
	public void setGintrcinfService(GINTRCINFService gintrcinfService) {
		this.gintrcinfService = gintrcinfService;
	}
	
	@Autowired
	public void setCosmosResponseService(CosmosResponseService cosmosResponseService) {
		this.cosmosResponseService = cosmosResponseService;
	}

	private int index = 0;

	@Override
	public GateOutReponse sendGateOutReadRequest(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {
		// TODO Auto-generated method stub
		// gateOutReponse-transaction type - switch

		TransactionType trxType = TransactionType.fromCode(gateOutReponse.getTransactionType());

		switch (trxType) {
		case IMPORT:
			if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
				List<ImportContainer> updatedImportList = cosmosGateOutImportService
						.fetchCosmosSeal(gateOutReponse.getImportContainers());
				gateOutReponse.setImportContainers(updatedImportList);
			}
			break;
		case EXPORT:
			if (!(gateOutReponse.getExportContainers() == null || gateOutReponse.getExportContainers().isEmpty())) {
				List<ExportContainer> updatedExportList = cosmosGateOutExportService
						.setContainerStatus(gateOutReponse.getExportContainers());
				gateOutReponse.setExportContainers(updatedExportList);
			}
			break;
		case IMPORT_EXPORT:
			if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
				List<ImportContainer> updatedImportList = cosmosGateOutImportService
						.fetchCosmosSeal(gateOutReponse.getImportContainers());
				gateOutReponse.setImportContainers(updatedImportList);
			}
			if (!(gateOutReponse.getExportContainers() == null || gateOutReponse.getExportContainers().isEmpty())) {
				List<ExportContainer> updatedExportList = cosmosGateOutExportService
						.setContainerStatus(gateOutReponse.getExportContainers());
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
		index = 0;
		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());

		CosmosCommonValuesDTO commonValuesDTO = getCommonValues(gateOutWriteRequest);
		commonValuesDTO.setLoginUser(gateOutWriteRequest.getUserName());

		CosmosGateOutWriteRequest cosmosGateOutWriteRequest = new CosmosGateOutWriteRequest();
		index++;
		cosmosGateOutWriteRequest
				.setGateOutMessage(gateOutMessageService.constructGateOutMessage(commonValuesDTO, index));

		switch (impExpFlag) {
		case IMPORT:
			index++;
			// getImpRequestXML
			if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
				cosmosGateOutWriteRequest.setImportList(new ArrayList<CosmosGateOutImport>());
				gateOutReponse.getImportContainers().forEach(importContainer -> {
					cosmosGateOutWriteRequest.getImportList().add(cosmosGateOutImportService
							.constructCosmosGateOutImport(commonValuesDTO, importContainer, index));
				});
			}
			break;
		case EXPORT:
			index++;
			// getExpRequestXML - CosmosGateOutExport
			cosmosGateOutWriteRequest
					.setExport(cosmosGateOutExportService.constructCosmosGateOutExport(commonValuesDTO, index));
			break;
		case IMPORT_EXPORT:
			index++;
			cosmosGateOutWriteRequest
					.setExport(cosmosGateOutExportService.constructCosmosGateOutExport(commonValuesDTO, index));
			// gateoutxmlrequest
			// getImpExpRequestXML
			if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
				cosmosGateOutWriteRequest.setImportList(new ArrayList<CosmosGateOutImport>());
				gateOutReponse.getImportContainers().forEach(importContainer -> {
					cosmosGateOutWriteRequest.getImportList().add(cosmosGateOutImportService
							.constructCosmosGateOutImport(commonValuesDTO, importContainer, index));
				});
			}
			break;
		default:
			break;
		}

		return null;
	}

	@Override
	public GateInReponse sendGateInReadRequest(GateInRequest gateInRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GateInReponse sendGateInWriteRequest(GateInWriteRequest gateInWriteRequest) {
		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());

		CosmosCommonValuesDTO commonValuesDTO = getCommonValues(gateInWriteRequest);
		commonValuesDTO.setLoginUser(gateInWriteRequest.getUserName());

		CosmosGateInWriteRequest cosmosGateInWriteRequest = cosmosGateInWriteService.constructCosmosGateInWriteRequest(commonValuesDTO);
		SGS2CosmosRequest cosmosRequest = new SGS2CosmosRequest();
		int startIndex = 2;
		switch (impExpFlag) {
		case IMPORT:
			if (!(gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())) {
				List<CosmosGateInImport> cosmosImportList = cosmosGateInWriteService.constructCosmosGateInImport(commonValuesDTO, 
						gateInWriteRequest.getImportContainers(), startIndex);
				cosmosGateInWriteRequest.setImportList(cosmosImportList);
			}
			break;
		case EXPORT:
			if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
				List<CosmosGateInExport> cosmosExportList = cosmosGateInWriteService.constructCosmosGateInExport(commonValuesDTO, gateInWriteRequest.getExportContainers(), startIndex);
				cosmosGateInWriteRequest.setExportList(cosmosExportList);
			}
			break;
		case IMPORT_EXPORT:
			if (!(gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())) {
				List<CosmosGateInImport> cosmosImportList = cosmosGateInWriteService.constructCosmosGateInImport(commonValuesDTO, gateInWriteRequest.getImportContainers(), startIndex);
				cosmosGateInWriteRequest.setImportList(cosmosImportList);
				startIndex += gateInWriteRequest.getImportContainers().size();
			}
			if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
				List<CosmosGateInExport> cosmosExportList = cosmosGateInWriteService.constructCosmosGateInExport(commonValuesDTO, gateInWriteRequest.getExportContainers(), startIndex);
				cosmosGateInWriteRequest.setExportList(cosmosExportList);
			}
			break;
		default:
			throw new BusinessException("Invalid transaction Type ! "+impExpFlag.name());
		}
		GateInReponse gateInReponse = null;
		try {
			cosmosRequest.setIndex(0);
			cosmosRequest.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
			cosmosRequest.setGINTRCINF(gintrcinfService.constructGINTRCINF(commonValuesDTO));
			cosmosRequest.setCosmosGateInWriteRequest(cosmosGateInWriteRequest);
			cosmosRequest.setCosmosGateOutWriteRequest(null);
			SGS2CosmosResponse cosmosResponse = agsClientService.sendToCosmos(cosmosRequest, gateInWriteRequest.getCosmosPort());
			gateInReponse = cosmosResponseService.extractCosmosGateInResponse(cosmosResponse, gateInWriteRequest);
		
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gateInReponse;
	}

	private CosmosCommonValuesDTO getCommonValues(GateWriteRequest gateWriteRequest) {
		CosmosCommonValuesDTO cosmosCommonValuesDTO = new CosmosCommonValuesDTO();
		cosmosCommonValuesDTO.setMsgUniqueId(System.currentTimeMillis() + "");
		cosmosCommonValuesDTO.setDate(DateUtil.getFormatteDate(LocalDate.now(), "yyyyMMdd"));
		cosmosCommonValuesDTO.setTime(DateUtil.getFormatteTime(LocalTime.now(), "HHmmss"));
		cosmosCommonValuesDTO.setLaneNo(StringUtils.upperCase(gateWriteRequest.getLaneNo()));
		cosmosCommonValuesDTO.setTruckNo(StringUtils.upperCase(gateWriteRequest.getTruckHeadNo()));
		cosmosCommonValuesDTO.setCompCode(StringUtils.upperCase(gateWriteRequest.getHaulageCode()));
		return cosmosCommonValuesDTO;
	}

}
