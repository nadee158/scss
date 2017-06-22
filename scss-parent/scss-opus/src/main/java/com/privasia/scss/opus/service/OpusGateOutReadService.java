/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.OpusGateOutReadRequest;
import com.privasia.scss.opus.dto.OpusGateOutReadResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

/**
 * @author Janaka
 *
 */

@Service("opusGateOutReadService")
public class OpusGateOutReadService {

	private static final Logger log = LoggerFactory.getLogger(OpusGateOutReadService.class);

	@Value("${gate_out.read.response.url}")
	private String gateOutReadResponseURL;

	private OpusDTOConstructService opusDTOConstructService;

	private OpusRequestResponseService opusRequestResponseService;

	private Gson gson;

	@Autowired
	public void setOpusDTOConstructService(OpusDTOConstructService opusDTOConstructService) {
		this.opusDTOConstructService = opusDTOConstructService;
	}

	@Autowired
	public void setOpusRequestResponseService(OpusRequestResponseService opusRequestResponseService) {
		this.opusRequestResponseService = opusRequestResponseService;
	}

	@Autowired
	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public OpusGateOutReadRequest constructOpenGateOutRequest(GateOutRequest gateOutRequest) {

		OpusGateOutReadRequest opusGateOutRequest = new OpusGateOutReadRequest();

		opusGateOutRequest.setContainerNo1ImportCY(gateOutRequest.getImpContainer1());
		opusGateOutRequest.setContainerNo2ImportCY(gateOutRequest.getImpContainer2());
		opusGateOutRequest.setContainerNo1ExportCY(gateOutRequest.getExpContainer1());
		opusGateOutRequest.setContainerNo2ExportCY(gateOutRequest.getExpContainer2());
		opusGateOutRequest.setContainerNo1ImportWHODD(gateOutRequest.getOddImpContainer1());
		opusGateOutRequest.setContainerNo2ImportWHODD(gateOutRequest.getOddImpContainer2());
		// 20161130112233 - yyyyMMddHHmmss
		opusGateOutRequest.setGateOUTDateTime(DateUtil.getJsonDateFromDate(gateOutRequest.getGateOUTDateTime()));
		opusGateOutRequest.setHaulageCode(gateOutRequest.getHaulageCode());
		opusGateOutRequest.setLaneNo(gateOutRequest.getLaneNo());
		opusGateOutRequest.setTruckHeadNo(gateOutRequest.getTruckHeadNo());
		opusGateOutRequest.setUserID(gateOutRequest.getUserName());
		return opusGateOutRequest;
	}

	public OpusGateOutReadResponse getGateOutReadResponse(OpusGateOutReadRequest opusGateOutReadRequest,
			OpusRequestResponseDTO opusRequestResponseDTO) {
		log.info("gateOutReadResponseURL " + gateOutReadResponseURL);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<OpusGateOutReadRequest> request = new HttpEntity<OpusGateOutReadRequest>(opusGateOutReadRequest,
				headers);
		
		opusRequestResponseDTO.setSendTime(LocalDateTime.now());
		ResponseEntity<OpusGateOutReadResponse> response = restTemplate.postForEntity(gateOutReadResponseURL, request,
				OpusGateOutReadResponse.class);

		log.info("OpusGateOutReadRequest : -" + (new Gson()).toJson(opusGateOutReadRequest));

		opusRequestResponseDTO.setResponse(gson.toJson(response.getBody()));
		opusRequestResponseDTO.setReceivedTime(LocalDateTime.now());

		opusRequestResponseService.saveOpusRequestResponse(opusRequestResponseDTO);

		return response.getBody();
	}

	public GateOutReponse constructGateOutReponse(OpusGateOutReadResponse opusGateOutReadResponse,
			GateOutReponse gateOutReponse) {

		gateOutReponse.setUserID(opusGateOutReadResponse.getUserID());
		//LocalDateTime localDateTime = DateUtil.getLocalDategFromString(opusGateOutReadResponse.getGateOUTDateTime());
		//gateOutReponse.setGateOUTDateTime(localDateTime);
		gateOutReponse.setHaulageCode(opusGateOutReadResponse.getHaulageCode());
		gateOutReponse.setLaneNo(opusGateOutReadResponse.getLaneNo());
		gateOutReponse.setTruckHeadNo(opusGateOutReadResponse.getTruckHeadNo());
		// gateOutReponse.setTruckPlateNo(opusGateOutReadResponse.getTruckPlateNo());
		gateOutReponse
				.setImportContainers(opusDTOConstructService.goReadResponseImportContainerListToImportContainerList(
						opusGateOutReadResponse.getImportContainerListCY(), gateOutReponse.getImportContainers()));
		gateOutReponse
				.setExportContainers(opusDTOConstructService.goReadResponseExportContainerListToExportContainerList(
						opusGateOutReadResponse.getExportContainerListCY(), gateOutReponse.getExportContainers()));
		return gateOutReponse;
	}

}
