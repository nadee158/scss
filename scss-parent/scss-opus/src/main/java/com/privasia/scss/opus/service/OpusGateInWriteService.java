/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.GIReadResponseExporterContainer;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.dto.GIReadResponseImportContainer;

/**
 * @author Janaka
 *
 */

@Service("opusGateInWriteService")
public class OpusGateInWriteService {

  private static final Logger log = LoggerFactory.getLogger(OpusGateInWriteService.class);

  @Value("${gate_in.write.response.url}")
  private String gateInWriteResponseURL;

  private OpusService opusService;

  @Autowired
  public void setOpusService(OpusService opusService) {
    this.opusService = opusService;
  }

  public OpusGateInWriteResponse getGateInWriteResponse(OpusGateInWriteRequest opusGateInWriteRequest) {
    System.out.println("gateInWriteResponseURL " + gateInWriteResponseURL);
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<OpusGateInWriteRequest> request =
        new HttpEntity<OpusGateInWriteRequest>(opusGateInWriteRequest, headers);

    ResponseEntity<OpusGateInWriteResponse> response =
        restTemplate.postForEntity(gateInWriteResponseURL, request, OpusGateInWriteResponse.class);

    log.info(response.toString());
    return response.getBody();
  }

  public GateInReponse constructGateInReponse(OpusGateInWriteResponse opusGateInWriteResponse,
      GateInReponse gateInReponse) {
    LocalDateTime localDateTime = DateUtil.getLocalDategFromString(opusGateInWriteResponse.getGateINDateTime());
    gateInReponse.setGateINDateTime(CommonUtil.getFormatteDate(localDateTime));
    gateInReponse.setHaulageCode(opusGateInWriteResponse.getHaulageCode());
    gateInReponse.setLaneNo(opusGateInWriteResponse.getLaneNo());
    gateInReponse.setTruckHeadNo(opusGateInWriteResponse.getTruckHeadNo());
    gateInReponse.setTruckPlateNo(opusGateInWriteResponse.getTruckPlateNo());
    gateInReponse.setExportContainers(opusService
        .constructExportContainersFromOpusExportContainers(opusGateInWriteResponse.getExportContainerListCY()));
    gateInReponse.setImportContainers(opusService
        .constructImportContainersFromOpusImportContainers(opusGateInWriteResponse.getImportContainerListCY()));
    gateInReponse.setCallCardNo(opusGateInWriteResponse.getCallCardNo());
    return gateInReponse;
  }

  public OpusGateInWriteRequest constructOpusGateInWriteRequest(GateInWriteRequest gateInWriteRequest) {
    OpusGateInWriteRequest opusGateInWriteRequest = new OpusGateInWriteRequest();

    List<GIReadResponseExporterContainer> exportContainerListCY =
        OpusService.constructOpusExporterContainersFromExporterContainers(gateInWriteRequest.getExportContainers());
    List<GIReadResponseImportContainer> importContainerListCY =
        OpusService.constructOpusImportContainersFromImportContainers(gateInWriteRequest.getImportContainers());

    LocalDateTime gateInDateTime = CommonUtil.getParsedDate(gateInWriteRequest.getGateInDateTime());
    opusGateInWriteRequest.setGateINDateTime(DateUtil.getJsonDateFromDate(gateInDateTime));
    opusGateInWriteRequest.setHaulageCode("HAN");
    opusGateInWriteRequest.setLaneNo(gateInWriteRequest.getLaneNo());
    opusGateInWriteRequest.setTruckHeadNo(gateInWriteRequest.getTruckHeadNo());
    opusGateInWriteRequest.setUserID(gateInWriteRequest.getUserName());
    opusGateInWriteRequest.setExportContainerListCY(exportContainerListCY);
    opusGateInWriteRequest.setImportContainerListCY(importContainerListCY);
    opusGateInWriteRequest.setTrailerNo(gateInWriteRequest.getTrailerNo());
    opusGateInWriteRequest.setTrailerWeight(Integer.toString(gateInWriteRequest.getTrailerWeight()));
    opusGateInWriteRequest.setTruckPlateNo(gateInWriteRequest.getTruckPlateNo());
    opusGateInWriteRequest.setTruckWeight(Integer.toString(gateInWriteRequest.getTruckWeight()));
    opusGateInWriteRequest.setImportContainerListCFS(new ArrayList<>());
    opusGateInWriteRequest.setExportContainerListCFS(new ArrayList<>());
    return opusGateInWriteRequest;
  }

}
