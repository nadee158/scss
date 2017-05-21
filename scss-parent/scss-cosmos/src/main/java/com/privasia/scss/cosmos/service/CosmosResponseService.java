/**
 * 
 */
package com.privasia.scss.cosmos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.cosmos.util.COSMOSMessageCode;
import com.privasia.scss.cosmos.xml.element.CSMCTL;
import com.privasia.scss.cosmos.xml.element.GINCNTDRPR;
import com.privasia.scss.cosmos.xml.element.GINCNTPUPR;
import com.privasia.scss.cosmos.xml.element.GINTRCINFR;
import com.privasia.scss.cosmos.xml.element.Message;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosResponse;

/**
 * @author Janaka
 *
 */
@Service("cosmosResponseService")
public class CosmosResponseService {

	private COSMOSMessageCode cosmosMessageCode;

	@Autowired
	public void setCosmosMessageCode(COSMOSMessageCode cosmosMessageCode) {
		this.cosmosMessageCode = cosmosMessageCode;
	}

	public GateInReponse extractCosmosGateInResponse(SGS2CosmosResponse cosmosResponse,
			GateInWriteRequest gateInWriteRequest) {

		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());
		GateInReponse gateInReponse = new GateInReponse();
		gateInReponse.setImportContainers(gateInWriteRequest.getImportContainers());
		gateInReponse.setExportContainers(gateInWriteRequest.getExportContainers());

		String errorCode = getCosmosError(cosmosResponse);

		if (StringUtils.isNotEmpty(errorCode)) {

			if (StringUtils.equalsIgnoreCase(errorCode, "INF0016")) {

				// set manual plan indicator
				setManualPlanIndicatorForExports(gateInReponse);
				setCallCardCode(cosmosResponse, gateInReponse);
				gateInReponse.setManualPlanIndicator(true);
				return gateInReponse;

			} else {
				// read the error
				String messageDescription = cosmosMessageCode.getMessageFromCode(errorCode);
				throw new BusinessException(messageDescription);
			}
		} else {
			setCallCardCode(cosmosResponse, gateInReponse);

			switch (impExpFlag) {
			case IMPORT:
				if (!(gateInReponse.getImportContainers() == null || gateInReponse.getImportContainers().isEmpty())) {
					setImportYardPositionAndBayCode(cosmosResponse, gateInReponse.getImportContainers());

				}
				break;
			case EXPORT:
				if (!(gateInReponse.getExportContainers() == null || gateInReponse.getExportContainers().isEmpty())) {
					setExportYardPositionAndBayCode(cosmosResponse, gateInReponse.getExportContainers());

				}
				break;
			case IMPORT_EXPORT:
				if (!(gateInReponse.getImportContainers() == null || gateInReponse.getImportContainers().isEmpty())) {
					setImportYardPositionAndBayCode(cosmosResponse, gateInReponse.getImportContainers());

				}
				if (!(gateInReponse.getExportContainers() == null || gateInReponse.getExportContainers().isEmpty())) {
					setExportYardPositionAndBayCode(cosmosResponse, gateInReponse.getExportContainers());

				}
				break;
			default:
				throw new BusinessException("Invalid transaction Type ! " + impExpFlag.name());
			}
		}

		return gateInReponse;

	}

	public void extractCosmosGateOutResponse(SGS2CosmosResponse cosmosResponse) {

		String errorCode = getCosmosError(cosmosResponse);

		if (StringUtils.isNotEmpty(errorCode)) {

			// read the error
			String messageDescription = cosmosMessageCode.getMessageFromCode(errorCode);
			throw new BusinessException(messageDescription);

		}

	}

	private List<ImportContainer> setImportYardPositionAndBayCode(SGS2CosmosResponse cosmosResponse,
			List<ImportContainer> importContainers) {

		List<Message> elementList = cosmosResponse.getMessage();

		if (elementList == null || elementList.isEmpty())
			throw new BusinessException("Invalid response received from cosmos. No Message elements found! ");

		List<GINCNTPUPR> yardPositionlist = elementList.stream().map(Message::getGINCNTPUPR)
				.collect(Collectors.toList());

		if (yardPositionlist == null || yardPositionlist.isEmpty())
			throw new BusinessException("Cannot find Yard Position information from cosmos ");

		if (!(importContainers == null || importContainers.isEmpty())) {

			importContainers.stream().forEach(importContainer -> {
				Optional<GINCNTPUPR> optElement = yardPositionlist.stream().filter(element -> StringUtils
						.equalsIgnoreCase(element.getUNITSE(), importContainer.getContainer().getContainerNumber()))
						.findAny();

				GINCNTPUPR gincntpupr = optElement.orElseThrow(() -> new BusinessException("Container No "
						+ importContainer.getContainer().getContainerNumber() + " not found to populate data"));

				importContainer.setYardBayCode(gincntpupr.getPKIDSE().trim());
				importContainer.setYardPosition(gincntpupr.getPSIDSE().trim());

			});
		}

		return importContainers;
	}

	private List<ExportContainer> setExportYardPositionAndBayCode(SGS2CosmosResponse cosmosResponse,
			List<ExportContainer> exportContainers) {

		List<Message> elementList = cosmosResponse.getMessage();

		if (elementList == null || elementList.isEmpty())
			throw new BusinessException("Invalid response received from cosmos. No Message elements found! ");

		List<GINCNTDRPR> yardPositionlist = elementList.stream().map(Message::getGINCNTDRPR)
				.collect(Collectors.toList());

		if (yardPositionlist == null || yardPositionlist.isEmpty())
			throw new BusinessException("Cannot find Yard Position information from cosmos ");

		if (!(exportContainers == null || exportContainers.isEmpty())) {

			exportContainers.stream().forEach(exportContainer -> {
				Optional<GINCNTDRPR> optElement = yardPositionlist.stream().filter(element -> StringUtils
						.equalsIgnoreCase(element.getUNITSE(), exportContainer.getContainer().getContainerNumber()))
						.findAny();

				GINCNTDRPR gincntdrpr = optElement.orElseThrow(() -> new BusinessException("Container No "
						+ exportContainer.getContainer().getContainerNumber() + " not found to populate data"));

				exportContainer.setYardBayCode(gincntdrpr.getPKIDSE().trim());
				exportContainer.setYardPosition(gincntdrpr.getPSIDSE().trim());

			});
		}

		return exportContainers;
	}

	private GateInReponse setCallCardCode(SGS2CosmosResponse cosmosResponse, GateInReponse gateInReponse) {

		List<Message> elementList = cosmosResponse.getMessage();

		if (elementList == null || elementList.isEmpty())
			throw new BusinessException("Invalid response received from cosmos. No Message elements found! ");

		List<GINTRCINFR> callCardlist = elementList.stream().map(Message::getGINTRCINFR).collect(Collectors.toList());

		if (callCardlist == null || callCardlist.isEmpty())
			throw new BusinessException("Cannot find Call card code information from cosmos ");

		Optional<GINTRCINFR> optElement = callCardlist.stream().findAny();

		GINTRCINFR gintrcinfr = optElement.get();

		if (!(gateInReponse.getImportContainers() == null || gateInReponse.getImportContainers().isEmpty())) {

			gateInReponse.getImportContainers().stream().forEach(importContainer -> {
				importContainer.setCallCard(Long.parseLong(gintrcinfr.getBZKNSC().trim()));

			});
		}

		if (!(gateInReponse.getExportContainers() == null || gateInReponse.getExportContainers().isEmpty())) {

			gateInReponse.getExportContainers().stream().forEach(exportContainer -> {
				exportContainer.setCallCard(Long.parseLong(gintrcinfr.getBZKNSC().trim()));

			});
		}

		return gateInReponse;
	}

	private String getCosmosError(SGS2CosmosResponse cosmosResponse) {

		List<Message> elementList = cosmosResponse.getMessage();

		if (elementList == null || elementList.isEmpty())
			throw new BusinessException("Invalid response received from cosmos. No Message elements found! ");

		List<CSMCTL> csmctlList = elementList.stream().map(Message::getCSMCTL).collect(Collectors.toList());

		if (csmctlList == null || csmctlList.isEmpty())
			throw new BusinessException("Invalid cosmos response. No CSMCTL elements");

		Optional<CSMCTL> optElement = csmctlList.stream().findFirst();

		CSMCTL csmctl = optElement
				.orElseThrow(() -> new BusinessException("Invalid cosmos response. No CSMCTL elements"));

		if (csmctl.getERRI() == null || StringUtils.isEmpty(csmctl.getERRI())) {
			return null;
		} else {
			return csmctl.getERRI().trim();

		}

	}

	private GateInReponse setManualPlanIndicatorForExports(GateInReponse gateInReponse) {

		if (!(gateInReponse.getExportContainers() == null || gateInReponse.getExportContainers().isEmpty())) {

			gateInReponse.getExportContainers().stream().forEach(exportContainer -> {
				exportContainer.setManualPlanIndicator(true);

			});
		}

		return gateInReponse;
	}

}
