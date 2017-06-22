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
import com.privasia.scss.common.annotation.LogOpusData;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

/**
 * @author Janaka
 *
 */

@Service("opusGateInReadService")
public class OpusGateInReadService {

	private static final Logger log = LoggerFactory.getLogger(OpusGateInReadService.class);

	@Value("${gate_in.read.response.url}")
	private String gateInReadResponseURL;
	
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

	public OpusGateInReadRequest constructOpenGateInRequest(GateInRequest gateInRequest) {

		OpusGateInReadRequest opusGateInRequest = new OpusGateInReadRequest();

		opusGateInRequest.setContainerNo1ImportCY(gateInRequest.getImpContainer1());
		opusGateInRequest.setContainerNo2ImportCY(gateInRequest.getImpContainer2());
		// add export containers also
		opusGateInRequest.setContainerNo1ExportCY(gateInRequest.getExpContainer1());
		opusGateInRequest.setContainerNo2ExportCY(gateInRequest.getExpContainer2());

		// 20161130112233 - yyyyMMddHHmmss
		opusGateInRequest.setGateINDateTime(DateUtil.getJsonDateFromDate(gateInRequest.getGateInDateTime()));
		opusGateInRequest.setHaulageCode(gateInRequest.getHaulageCode());
		opusGateInRequest.setLaneNo(gateInRequest.getLaneNo());
		opusGateInRequest.setTruckHeadNo(gateInRequest.getTruckHeadNo());
		opusGateInRequest.setUserID(gateInRequest.getUserName());
		return opusGateInRequest;
	}

	//@LogOpusData
	public OpusGateInReadResponse getGateInReadResponse(OpusGateInReadRequest opusGateInReadRequest,
			OpusRequestResponseDTO opusRequestResponseDTO) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<OpusGateInReadRequest> request = new HttpEntity<OpusGateInReadRequest>(opusGateInReadRequest,
				headers);

		log.info("OpusGateInReadRequest : -" + (new Gson()).toJson(opusGateInReadRequest));

		opusRequestResponseDTO.setSendTime(LocalDateTime.now());
		ResponseEntity<OpusGateInReadResponse> response = restTemplate.postForEntity(gateInReadResponseURL, request,
				OpusGateInReadResponse.class);

		log.info("RESPONSE FROM OPUS: " + response.toString());

		opusRequestResponseDTO.setResponse(gson.toJson(response.getBody()));
		opusRequestResponseDTO.setReceivedTime(LocalDateTime.now());
		
		opusRequestResponseService.saveOpusRequestResponse(opusRequestResponseDTO);
		
		return response.getBody();
	}

	public GateInResponse constructGateInReponse(OpusGateInReadResponse opusGateInReadResponse,
			GateInResponse gateInResponse) {

		gateInResponse.setUserId(opusGateInReadResponse.getUserID());
		//LocalDateTime localDateTime = DateUtil.getLocalDategFromString(opusGateInReadResponse.getGateInDateTime());

		//gateInReponse.setGateINDateTime(localDateTime);
		gateInResponse.setHaulageCode(opusGateInReadResponse.getHaulageCode());
		gateInResponse.setLaneNo(opusGateInReadResponse.getLaneNo());
		gateInResponse.setTruckHeadNo(opusGateInReadResponse.getTruckHeadNo());
		// already hav import containers and export containers, update them
		List<ExportContainer> updatedExportContainerList = opusDTOConstructService
				.giReadResponseExporterContainerListToExportContainerList(
						opusGateInReadResponse.getExportContainerListCY(), gateInResponse);

		List<ImportContainer> updatedImportContainerList = opusDTOConstructService
				.giReadResponseImportContainerListToImportContainerList(
						opusGateInReadResponse.getImportContainerListCY(), gateInResponse);

		gateInResponse.setExportContainers(updatedExportContainerList);
		gateInResponse.setImportContainers(updatedImportContainerList);
		return gateInResponse;
	}

}
