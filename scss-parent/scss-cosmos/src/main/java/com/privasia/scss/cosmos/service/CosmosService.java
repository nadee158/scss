package com.privasia.scss.cosmos.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ContainerValidationInfo;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInResponse;
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
import com.privasia.scss.cosmos.dto.request.CosmosGateOutExport;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutImport;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutWriteRequest;
import com.privasia.scss.cosmos.repository.CosmosODDRepository;
import com.privasia.scss.cosmos.xml.element.RequestMessage;
import com.privasia.scss.cosmos.xml.element.service.MessageService;

@Service("cosmos")
public class CosmosService implements OpusCosmosBusinessService {

	private static final Logger log = LoggerFactory.getLogger(CosmosService.class);

	@Value("${async.wait.time}")
	private long asyncWaitTime;

	private CosmosGateInReadService cosmosGateInReadService;

	private CosmosGateOutImportService cosmosGateOutImportService;

	private CosmosGateOutExportService cosmosGateOutExportService;

	private CosmosGateInWriteService cosmosGateInWriteService;

	private CosmosGateOutWriteService cosmosGateOutWriteService;

	private AGSClientService agsClientService;

	private CosmosResponseService cosmosResponseService;

	private CosmosODDRepository cosmosODDRepository;

	private MessageService messageService;

	@Autowired
	public void setCosmosGateInReadService(CosmosGateInReadService cosmosGateInReadService) {
		this.cosmosGateInReadService = cosmosGateInReadService;
	}

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
	public void setCosmosResponseService(CosmosResponseService cosmosResponseService) {
		this.cosmosResponseService = cosmosResponseService;
	}

	@Autowired
	public void setCosmosODDRepository(CosmosODDRepository cosmosODDRepository) {
		this.cosmosODDRepository = cosmosODDRepository;
	}

	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
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
			if (!(gateOutWriteRequest.getImportContainers() == null
					|| gateOutWriteRequest.getImportContainers().isEmpty())) {

				List<CosmosGateOutImport> cosmosImportList = cosmosGateOutWriteService.constructCosmosGateOutImport(
						commonValuesDTO, gateOutWriteRequest.getImportContainers(), startIndex);
				if (cosmosImportList.isEmpty())
					return gateOutReponse;
				cosmosGateOutWriteRequest.setImportList(cosmosImportList);
			}
			break;
		case EXPORT:
			cosmosGateOutWriteRequest.setImportList(null);
			cosmosGateOutWriteRequest.setExport(null);
			break;
		case IMPORT_EXPORT:
			if (!(gateOutWriteRequest.getImportContainers() == null
					|| gateOutWriteRequest.getImportContainers().isEmpty())) {

				List<CosmosGateOutImport> cosmosImportList = cosmosGateOutWriteService.constructCosmosGateOutImport(
						commonValuesDTO, gateOutWriteRequest.getImportContainers(), startIndex);
				cosmosGateOutWriteRequest.setImportList(cosmosImportList);
				startIndex += gateOutWriteRequest.getImportContainers().size();
			}
			if (!(gateOutWriteRequest.getExportContainers() == null
					|| gateOutWriteRequest.getExportContainers().isEmpty())) {
				Optional<CosmosGateOutExport> cosmosGateOutExportOpt = cosmosGateOutWriteService
						.constructCosmosGateOutExport(commonValuesDTO, gateOutWriteRequest.getExportContainers(),
								startIndex);
				if (cosmosGateOutExportOpt.isPresent()) {
					cosmosGateOutWriteRequest.setExport(cosmosGateOutExportOpt.get());
				}
			}
			break;
		default:
			throw new BusinessException("Invalid transaction Type ! " + impExpFlag.name());
		}

		try {
			RequestMessage requestMessage = messageService.constructGateInRootMessage(commonValuesDTO);
			cosmosGateOutWriteRequest.setMessage(requestMessage);

			String cosmosResponse = agsClientService.sendToCosmos(cosmosGateOutWriteRequest,
					gateOutWriteRequest.getCosmosPort());
			cosmosResponseService.extractCosmosGateOutResponse(cosmosResponse);

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new BusinessException("Error Sending request to cosmos!");
		}

		return gateOutReponse;
	}

	@Override
	public GateInResponse sendGateInReadRequest(GateInRequest gateInRequest, GateInResponse gateInResponse) {

		long start = System.currentTimeMillis();

		Future<GateInResponse> importResponse = cosmosGateInReadService.populateCosmosGateInImport(gateInResponse);

		if (gateInResponse.getExportContainers() == null || gateInResponse.getExportContainers().isEmpty()) {

			List<ExportContainer> exportContainers = new ArrayList<>();
			gateInResponse.setExportContainers(exportContainers);
			ExportContainer exportContainer = null;
			if (StringUtils.isNotEmpty(gateInRequest.getExpContainer1())) {
				exportContainer = new ExportContainer();
				exportContainer.setContainer(new CommonContainerDTO());
				exportContainer.getContainer()
						.setContainerNumber(StringUtils.upperCase(gateInRequest.getExpContainer1()));
				gateInResponse.getExportContainers().add(exportContainer);
			}

			if (StringUtils.isNotEmpty(gateInRequest.getExpContainer2())) {
				exportContainer = new ExportContainer();
				exportContainer.setContainer(new CommonContainerDTO());
				exportContainer.getContainer()
						.setContainerNumber(StringUtils.upperCase(gateInRequest.getExpContainer2()));
				gateInResponse.getExportContainers().add(exportContainer);
			}

		}

		Future<GateInResponse> exportResponse = cosmosGateInReadService.populateCosmosGateInExport(gateInResponse);

		while (true) {
			// System.out.println("inside the loop populateCosmosGateInExport");
			if (importResponse.isDone() && exportResponse.isDone()) {
				System.out.println("now done populateCosmosGateInExport");
				try {
					gateInResponse.setImportContainers(importResponse.get().getImportContainers());
					gateInResponse.setExportContainers(exportResponse.get().getExportContainers());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					log.error("Error Occured while retrieve data data from cosmos");
					log.error(e.getMessage());
				}
				break;
			} else {
				// System.out.println("not done yet
				// populateCosmosGateInExport");
			}

			try {
				System.out.println("waiting populateCosmosGateInExport " + asyncWaitTime);
				Thread.sleep(5);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				break;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("time : " + (end - start) / 1000.0 + "sec");
		return gateInResponse;
	}

	@Override
	public GateInResponse sendGateInWriteRequest(GateInWriteRequest gateInWriteRequest) {
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
		GateInResponse gateInReponse = null;
		try {
			RequestMessage requestMessage = messageService.constructGateInRootMessage(commonValuesDTO);
			cosmosGateInWriteRequest.setMessage(requestMessage);

			String cosmosResponse = agsClientService.sendToCosmos(cosmosGateInWriteRequest,
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

	@Override
	public ContainerValidationInfo sendODDContainerValidationRequest(GateOutRequest gateOutRequest) {

		ContainerValidationInfo validationInfo = new ContainerValidationInfo();
		validationInfo.setContainerNo1(gateOutRequest.getOddImpContainer1());
		validationInfo
				.setContainerNo1Status(cosmosODDRepository.validateODDContainer(gateOutRequest.getOddImpContainer1()));

		if (StringUtils.isNotEmpty(gateOutRequest.getOddImpContainer2())) {
			validationInfo.setContainerNo2(gateOutRequest.getOddImpContainer2());
			validationInfo.setContainerNo2Status(
					cosmosODDRepository.validateODDContainer(gateOutRequest.getOddImpContainer2()));
		}
		return validationInfo;
	}

}
