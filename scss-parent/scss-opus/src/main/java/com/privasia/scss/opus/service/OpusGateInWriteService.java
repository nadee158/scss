/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.List;

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
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.GIWriteRequestExportContainer;
import com.privasia.scss.opus.dto.GIWriteRequestImportContainer;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

/**
 * @author Janaka
 *
 */

@Service("opusGateInWriteService")
public class OpusGateInWriteService {

	private static final Logger log = LoggerFactory.getLogger(OpusGateInWriteService.class);

	@Value("${gate_in.write.response.url}")
	private String gateInWriteResponseURL;

	private OpusRequestResponseService opusRequestResponseService;

	private OpusDTOConstructService opusDTOConstructService;

	private Gson gson;

	@Autowired
	public void setGson(Gson gson) {
		this.gson = gson;
	}

	@Autowired
	public void setOpusDTOConstructService(OpusDTOConstructService opusDTOConstructService) {
		this.opusDTOConstructService = opusDTOConstructService;
	}

	@Autowired
	public void setOpusRequestResponseService(OpusRequestResponseService opusRequestResponseService) {
		this.opusRequestResponseService = opusRequestResponseService;
	}

	// @LogOpusData
	public OpusGateInWriteResponse getGateInWriteResponse(OpusGateInWriteRequest opusGateInWriteRequest,
			OpusRequestResponseDTO opusRequestResponseDTO) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<OpusGateInWriteRequest> request = new HttpEntity<OpusGateInWriteRequest>(opusGateInWriteRequest,
				headers);

		log.info("OpusGateInWriteRequest : -" + (new Gson()).toJson(opusGateInWriteRequest));

		opusRequestResponseDTO.setSendTime(LocalDateTime.now());
		ResponseEntity<OpusGateInWriteResponse> response = restTemplate.postForEntity(gateInWriteResponseURL, request,
				OpusGateInWriteResponse.class);

		log.info("RESPONSE FROM OPUS: " + response.toString());

		opusRequestResponseDTO.setResponse(gson.toJson(response.getBody()));
		opusRequestResponseDTO.setReceivedTime(LocalDateTime.now());

		opusRequestResponseService.saveOpusRequestResponse(opusRequestResponseDTO);

		return response.getBody();
	}

	public GateInResponse constructGateInReponse(OpusGateInWriteResponse opusGateInWriteResponse,
			GateInResponse gateInResponse) {

		// LocalDateTime localDateTime =
		// DateUtil.getLocalDategFromString(opusGateInWriteResponse.getGateINDateTime());
		// gateInReponse.setGateINDateTime(localDateTime);
		gateInResponse.setHaulageCode(opusGateInWriteResponse.getHaulageCode());
		gateInResponse.setLaneNo(opusGateInWriteResponse.getLaneNo());
		gateInResponse.setTruckHeadNo(opusGateInWriteResponse.getTruckHeadNo());
		gateInResponse.setTruckPlateNo(opusGateInWriteResponse.getTruckPlateNo());
		gateInResponse.setExportContainers(
				opusDTOConstructService.giWriteResponseExportContainerListToExportContainerList(opusGateInWriteResponse,
						gateInResponse.getExportContainers()));
		gateInResponse.setImportContainers(
				opusDTOConstructService.giWriteResponseImportContainerListToImportContainerList(opusGateInWriteResponse,
						gateInResponse.getImportContainers()));
		gateInResponse.setCallCardNo(opusGateInWriteResponse.getCallCardNo());
		gateInResponse.setManualPlanIndicator(false);
		return gateInResponse;
	}

	public OpusGateInWriteRequest constructOpusGateInWriteRequest(GateInWriteRequest gateInWriteRequest) {
		OpusGateInWriteRequest opusGateInWriteRequest = new OpusGateInWriteRequest();
	
		List<GIWriteRequestExportContainer> exportContainerListCY = opusDTOConstructService
				.exportContainerListToGIWriteRequestExportContainerList(gateInWriteRequest);
		List<GIWriteRequestImportContainer> importContainerListCY = opusDTOConstructService
				.importContainerListToGIWriteRequestImportContainerList(gateInWriteRequest);

		opusGateInWriteRequest.setGateINDateTime(DateUtil.getJsonDateFromDate(gateInWriteRequest.getGateInDateTime()));
		opusGateInWriteRequest.setHaulageCode(gateInWriteRequest.getHaulageCode());
		opusGateInWriteRequest.setLaneNo(gateInWriteRequest.getLaneNo());
		opusGateInWriteRequest.setTruckHeadNo(gateInWriteRequest.getTruckHeadNo());
		opusGateInWriteRequest.setUserID(gateInWriteRequest.getUserName());
		opusGateInWriteRequest.setExportContainerListCY(exportContainerListCY);
		opusGateInWriteRequest.setImportContainerListCY(importContainerListCY);
		opusGateInWriteRequest.setTrailerNo(gateInWriteRequest.getTrailerNo());
		opusGateInWriteRequest.setTrailerWeight(Integer.toString(gateInWriteRequest.getTrailerWeight()));
		opusGateInWriteRequest.setTruckPlateNo(gateInWriteRequest.getTruckPlateNo());
		opusGateInWriteRequest.setTruckWeight(Integer.toString(gateInWriteRequest.getTruckWeight()));
		
		
		return opusGateInWriteRequest;
	}

}
