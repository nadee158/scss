/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.DateUtil;
//import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;
import com.privasia.scss.opus.dto.OpusImportContainer;

/**
 * @author Janaka
 *
 */

@Service("opusGateInReadService")
public class OpusGateInReadService {

	private static final Logger log = LoggerFactory.getLogger(OpusGateInReadService.class);

	public OpusGateInReadRequest constructOpenGateInRequest(GateInRequest gateInRequest) {

		OpusGateInReadRequest opusGateInRequest = new OpusGateInReadRequest();

		opusGateInRequest.setContainerNo1ImportCY(gateInRequest.getImpContainer1());
		opusGateInRequest.setContainerNo2ImportCY(gateInRequest.getImpContainer2());
		// 20161130112233 - yyyyMMddHHmmss
		LocalDateTime gateInDateTime = CommonUtil.getParsedDate(gateInRequest.getGateInDateTime());
		opusGateInRequest.setGateINDateTime(DateUtil.getJsonDateFromDate(gateInDateTime));
		opusGateInRequest.setHaulageCode("HAUCD");
		opusGateInRequest.setLaneNo(gateInRequest.getLaneNo());
		opusGateInRequest.setTruckHeadNo(gateInRequest.getTruckHeadNo());
		opusGateInRequest.setUserID(gateInRequest.getUserName());
		return opusGateInRequest;
	}

	public OpusGateInReadResponse getGateInReadResponse(OpusGateInReadRequest opusGateInReadRequest) {

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://172.21.88.65:9014/scss/put/GI_R_01";
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<OpusGateInReadRequest> request = new HttpEntity<OpusGateInReadRequest>(opusGateInReadRequest,
				headers);

		ResponseEntity<OpusGateInReadResponse> response = restTemplate.postForEntity(url, request,
				OpusGateInReadResponse.class);

		log.info(response.toString());
		return response.getBody();
	}

	public GateInReponse constructGateInReponse(OpusGateInReadResponse opusGateInReadResponse,
			GateInReponse gateInReponse) {

		LocalDateTime localDateTime = OpusService.getLocalDategFromString(opusGateInReadResponse.getGateInDateTime());
		gateInReponse.setGateINDateTime(CommonUtil.getFormatteDate(localDateTime));
		gateInReponse.setHaulageCode(opusGateInReadResponse.getHaulageCode());
		gateInReponse.setLaneNo(opusGateInReadResponse.getLaneNo());
		gateInReponse.setTruckHeadNo(opusGateInReadResponse.getTruckHeadNo());
		gateInReponse.setTruckPlateNo(opusGateInReadResponse.getTruckPlateNo());
		return constructImportContainers(opusGateInReadResponse.getImportContainerListCY(), gateInReponse);
	}

	private GateInReponse constructImportContainers(List<OpusImportContainer> importContainerListCY,
			GateInReponse gateInReponse) {

		if (!(importContainerListCY == null || importContainerListCY.isEmpty())) {

			importContainerListCY.forEach(opusImportContainer -> {
				Optional<ImportContainer> optinalContainer = gateInReponse.getImportContainers().stream()
						.filter(importContainer -> importContainer.getContainer().getContainerNumber()
								.equals(opusImportContainer.getContainerNo()))
						.findFirst();

				/*ImportContainer importContainer = optinalContainer.orElseThrow(() -> new ResultsNotFoundException(
						"No Import Containers could be found for the given Cantainer Number from SCSS !"));*/

				//constructImportContainer(opusImportContainer, importContainer);

			});

			return gateInReponse;
		}
		return null;
	}

	private ImportContainer constructImportContainer(OpusImportContainer opusImportContainer,
			ImportContainer container) {

		// not currently available- added to importcontainer
		// private long containerDischargeDateTime;// 20161124162510,
		// private String impCarrierType;// null,
		// private String impCarrier;// null,
		// private String vesselCode;// UANE,
		// private String vesselVoyage;// 003/2016,
		// private String visitId;// 121212,
		// private String vesselScn;// DB0899,
		// private String vesselName;// AL NEFUD,
		// private String vesselATA;// 20161124161800
		// modelMapper.map(opusImportContainer, container);

		CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
		// private double containerHeight;// 8,
		commonContainerDTO.setContainerHeight(Integer.parseInt(opusImportContainer.getContainerHeight()));
		// private double containerSize;// 40,
		commonContainerDTO.setContainerLength(Integer.parseInt(opusImportContainer.getContainerSize()));
		// private String containerFullOrEmpty;// F,
		commonContainerDTO.setContainerFullOrEmpty(opusImportContainer.getContainerFullOrEmpty());
		// private String containerIso;// 4001,
		commonContainerDTO.setContainerISOCode(opusImportContainer.getContainerIso());

		// private String containerInOrOut;// OUT,
		container.setGateInOut(opusImportContainer.getContainerInOrOut());

		// private String impOrderNo;// ORDER0001,
		container.setOrderFOT(opusImportContainer.getImpOrderNo());

		// private String containerShippingLine;// CMA,
		container.setLine(opusImportContainer.getContainerShippingLine());

		// private String containerType;// GE,
		container.setContainerType(opusImportContainer.getContainerType());

		// private String currentYardPosition;// 02S-0102-C-1,
		container.setYardPosition(opusImportContainer.getCurrentYardPosition());

		container.setContainer(commonContainerDTO);

		return container;
	}

}
