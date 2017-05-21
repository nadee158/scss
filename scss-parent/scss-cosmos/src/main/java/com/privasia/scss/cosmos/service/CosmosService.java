package com.privasia.scss.cosmos.service;

import java.time.LocalDate;
import java.time.LocalTime;
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
import com.privasia.scss.cosmos.xml.element.service.GOTTRCINFService;

@Service("cosmos")
public class CosmosService implements OpusCosmosBusinessService {

	private CosmosGateOutImportService cosmosGateOutImportService;

	private CosmosGateOutExportService cosmosGateOutExportService;

	private CosmosGateInWriteService cosmosGateInWriteService;
	
	private CosmosGateOutWriteService cosmosGateOutWriteService;

	private AGSClientService agsClientService;

	private CSMCTLService csmctlService;

	private GINTRCINFService gintrcinfService;

	private GOTTRCINFService gottrcinfService;

	private CosmosResponseService cosmosResponseService;

	@Autowired
	public void setCosmosGateInWriteService(CosmosGateInWriteService cosmosGateInWriteService) {
		this.cosmosGateInWriteService = cosmosGateInWriteService;
	}

	@Autowired
	public void setCosmosGateOutWriteService(CosmosGateOutWriteService cosmosGateOutWriteService) {
		this.cosmosGateOutWriteService = cosmosGateOutWriteService;
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
	public void setGottrcinfService(GOTTRCINFService gottrcinfService) {
		this.gottrcinfService = gottrcinfService;
	}

	@Autowired
	public void setCosmosResponseService(CosmosResponseService cosmosResponseService) {
		this.cosmosResponseService = cosmosResponseService;
	}

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
		
		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());

		CosmosCommonValuesDTO commonValuesDTO = getCommonValues(gateOutWriteRequest);
		commonValuesDTO.setLoginUser(gateOutWriteRequest.getUserName());

		CosmosGateOutWriteRequest cosmosGateOutWriteRequest = new CosmosGateOutWriteRequest();

		int startIndex = 2;
		switch (impExpFlag) {
		case IMPORT:
			// getImpRequestXML
			if (!(gateOutWriteRequest.getImportContainers() == null || gateOutWriteRequest.getImportContainers().isEmpty())) {
				
				List<CosmosGateOutImport> cosmosImportList = cosmosGateOutWriteService.constructCosmosGateoutImport(
						commonValuesDTO, gateOutWriteRequest.getImportContainers(), startIndex);
				if(cosmosImportList.isEmpty())
					return gateOutReponse;
				cosmosGateOutWriteRequest.setImportList(cosmosImportList);
			}
			break;
		case EXPORT:
			cosmosGateOutWriteRequest.setImportList(null);
			break;
		case IMPORT_EXPORT:
			if (!(gateOutWriteRequest.getImportContainers() == null || gateOutWriteRequest.getImportContainers().isEmpty())) {
				
				List<CosmosGateOutImport> cosmosImportList = cosmosGateOutWriteService.constructCosmosGateoutImport(
						commonValuesDTO, gateOutWriteRequest.getImportContainers(), startIndex);
				cosmosGateOutWriteRequest.setImportList(cosmosImportList);
			}
			break;
		default:
			throw new BusinessException("Invalid transaction Type ! " + impExpFlag.name());
		}

		try {
			SGS2CosmosRequest cosmosRequest = new SGS2CosmosRequest();
			cosmosRequest.setIndex(1);
			cosmosRequest.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
			cosmosRequest.setGOTTRCINF(gottrcinfService.constructGOTTRCINF(commonValuesDTO));
			cosmosRequest.setCosmosGateOutWriteRequest(cosmosGateOutWriteRequest);
			cosmosRequest.setGINTRCINF(null);
			cosmosRequest.setCosmosGateInWriteRequest(null);
			SGS2CosmosResponse cosmosResponse = agsClientService.sendToCosmos(cosmosRequest,
					gateOutWriteRequest.getCosmosPort());
			cosmosResponseService.extractCosmosGateOutResponse(cosmosResponse);

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new BusinessException("Error Sending request to cosmos!");
		}

		return gateOutReponse;
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

		CosmosGateInWriteRequest cosmosGateInWriteRequest = new CosmosGateInWriteRequest();

		int startIndex = 2;
		switch (impExpFlag) {
		case IMPORT:
			if (!(gateInWriteRequest.getImportContainers() == null
					|| gateInWriteRequest.getImportContainers().isEmpty())) {
				List<CosmosGateInImport> cosmosImportList = cosmosGateInWriteService.constructCosmosGateInImport(
						commonValuesDTO, gateInWriteRequest.getImportContainers(), startIndex);
				cosmosGateInWriteRequest.setImportList(cosmosImportList);
			}
			cosmosGateInWriteRequest.setExportList(null);
			break;
		case EXPORT:
			if (!(gateInWriteRequest.getExportContainers() == null
					|| gateInWriteRequest.getExportContainers().isEmpty())) {
				List<CosmosGateInExport> cosmosExportList = cosmosGateInWriteService.constructCosmosGateInExport(
						commonValuesDTO, gateInWriteRequest.getExportContainers(), startIndex);
				cosmosGateInWriteRequest.setExportList(cosmosExportList);
			}
			cosmosGateInWriteRequest.setImportList(null);
			break;
		case IMPORT_EXPORT:
			if (!(gateInWriteRequest.getImportContainers() == null
					|| gateInWriteRequest.getImportContainers().isEmpty())) {
				List<CosmosGateInImport> cosmosImportList = cosmosGateInWriteService.constructCosmosGateInImport(
						commonValuesDTO, gateInWriteRequest.getImportContainers(), startIndex);
				cosmosGateInWriteRequest.setImportList(cosmosImportList);
				startIndex += gateInWriteRequest.getImportContainers().size();
			}
			if (!(gateInWriteRequest.getExportContainers() == null
					|| gateInWriteRequest.getExportContainers().isEmpty())) {
				List<CosmosGateInExport> cosmosExportList = cosmosGateInWriteService.constructCosmosGateInExport(
						commonValuesDTO, gateInWriteRequest.getExportContainers(), startIndex);
				cosmosGateInWriteRequest.setExportList(cosmosExportList);
			}
			break;
		default:
			throw new BusinessException("Invalid transaction Type ! " + impExpFlag.name());
		}
		GateInReponse gateInReponse = null;
		try {
			SGS2CosmosRequest cosmosRequest = new SGS2CosmosRequest();
			cosmosRequest.setIndex(1);
			cosmosRequest.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
			cosmosRequest.setGINTRCINF(gintrcinfService.constructGINTRCINF(commonValuesDTO));
			cosmosRequest.setCosmosGateInWriteRequest(cosmosGateInWriteRequest);
			cosmosRequest.setCosmosGateOutWriteRequest(null);
			cosmosRequest.setGOTTRCINF(null);
			SGS2CosmosResponse cosmosResponse = agsClientService.sendToCosmos(cosmosRequest,
					gateInWriteRequest.getCosmosPort());
			gateInReponse = cosmosResponseService.extractCosmosGateInResponse(cosmosResponse, gateInWriteRequest);

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new BusinessException("Error Sending request to cosmos!");
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
