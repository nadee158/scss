/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.GOWriteRequestExportContainer;
import com.privasia.scss.opus.dto.GOWriteRequestImportContainer;
import com.privasia.scss.opus.dto.OpusGateOutWriteRequest;
import com.privasia.scss.opus.dto.OpusGateOutWriteResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

/**
 * @author Janaka
 *
 */

@Service("opusGateOutWriteService")
public class OpusGateOutWriteService {

	private static final Logger log = LoggerFactory.getLogger(OpusGateOutWriteService.class);

	@Value("${gate_out.write.response.url}")
	private String gateOutWriteResponseURL;

	private OpusRequestResponseService opusRequestResponseService;

	private Gson gson;

	private OpusDTOConstructService opusDTOConstructService;

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

	public OpusGateOutWriteResponse getGateOutWriteResponse(OpusGateOutWriteRequest opusGateOutWriteRequest,
			OpusRequestResponseDTO opusRequestResponseDTO) {
		log.info("gateOutWriteResponseURL " + gateOutWriteResponseURL);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		log.info("OpusGateOutWriteResponse : -" + (new Gson()).toJson(opusGateOutWriteRequest));

		HttpEntity<OpusGateOutWriteRequest> request = new HttpEntity<OpusGateOutWriteRequest>(opusGateOutWriteRequest,
				headers);

		opusRequestResponseDTO.setSendTime(LocalDateTime.now());
		ResponseEntity<OpusGateOutWriteResponse> response = restTemplate.postForEntity(gateOutWriteResponseURL, request,
				OpusGateOutWriteResponse.class);

		log.info("RESPONSE FROM OPUS: " + response.toString());

		opusRequestResponseDTO.setResponse(gson.toJson(response.getBody()));
		opusRequestResponseDTO.setReceivedTime(LocalDateTime.now());
		opusRequestResponseService.saveOpusRequestResponse(opusRequestResponseDTO);
		
		return response.getBody();
	}

	public GateOutReponse constructGateOutReponse(OpusGateOutWriteResponse opusGateOutWriteResponse,
			GateOutReponse gateOutReponse) {
		// LocalDateTime localDateTime =
		// DateUtil.getLocalDategFromString(opusGateOutWriteResponse.getGateINDateTime());
		// gateOutReponse.setGateOUTDateTime(localDateTime);
		gateOutReponse.setHaulageCode(opusGateOutWriteResponse.getHaulageCode());
		gateOutReponse.setLaneNo(opusGateOutWriteResponse.getLaneNo());
		gateOutReponse.setTruckHeadNo(opusGateOutWriteResponse.getTruckHeadNo());
		gateOutReponse.setTruckPlateNo(opusGateOutWriteResponse.getTruckPlateNo());
		gateOutReponse
				.setExportContainers(opusDTOConstructService.goWriteRequestExportContainerListToExportContainerList(
						opusGateOutWriteResponse.getExportContainerListCY()));
		gateOutReponse
				.setImportContainers(opusDTOConstructService.goWriteRequestImportContainerListToImportContainerList(
						opusGateOutWriteResponse.getImportContainerListCY()));
		return gateOutReponse;
	}

	public Optional<OpusGateOutWriteRequest> constructOpusGateOutWriteRequest(GateOutWriteRequest gateOutWriteRequest) {
		OpusGateOutWriteRequest opusGateOutWriteRequest = null;

		List<GOWriteRequestExportContainer> exportContainerListCY = opusDTOConstructService
				.exportContainerListToGOWriteRequestExportContainerList(gateOutWriteRequest.getExportContainers());
		List<GOWriteRequestImportContainer> importContainerListCY = opusDTOConstructService
				.importContainerListToGOWriteRequestImportContainerList(gateOutWriteRequest.getImportContainers());

		if ((exportContainerListCY != null && exportContainerListCY.isEmpty()) && 
				(importContainerListCY != null && importContainerListCY.isEmpty())) {
			return Optional.ofNullable(opusGateOutWriteRequest);
		} else {
			opusGateOutWriteRequest = new OpusGateOutWriteRequest();
			opusGateOutWriteRequest
					.setGateOUTDateTime(DateUtil.getJsonDateFromDate(gateOutWriteRequest.getGateOUTDateTime()));
			opusGateOutWriteRequest.setHaulageCode(gateOutWriteRequest.getHaulageCode());
			opusGateOutWriteRequest.setLaneNo(gateOutWriteRequest.getLaneNo());
			opusGateOutWriteRequest.setTruckHeadNo(gateOutWriteRequest.getTruckHeadNo());
			opusGateOutWriteRequest.setTruckPlateNo(gateOutWriteRequest.getTruckPlateNo());
			opusGateOutWriteRequest.setUserID(gateOutWriteRequest.getUserName());
			opusGateOutWriteRequest.setExportContainerListCY(exportContainerListCY);
			opusGateOutWriteRequest.setImportContainerListCY(importContainerListCY);
			return Optional.of(opusGateOutWriteRequest);
		}

	}

}
