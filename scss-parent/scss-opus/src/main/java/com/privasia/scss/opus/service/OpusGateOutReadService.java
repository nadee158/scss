/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.concurrent.Future;

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
import com.privasia.scss.common.util.CommonUtil;
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

  private OpusService opusService;

  private OpusRequestResponseService opusRequestResponseService;

  private Gson gson;

  @Autowired
  public void setOpusService(OpusService opusService) {
    this.opusService = opusService;
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
    // 20161130112233 - yyyyMMddHHmmss
    LocalDateTime gateOutDateTime = CommonUtil.getParsedDate(gateOutRequest.getGateOUTDateTime());
    opusGateOutRequest.setGateOUTDateTime(DateUtil.getJsonDateFromDate(gateOutDateTime));
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

    HttpEntity<OpusGateOutReadRequest> request =
        new HttpEntity<OpusGateOutReadRequest>(opusGateOutReadRequest, headers);
    // save in to db
    Future<Long> future = opusRequestResponseService.saveOpusRequest(opusRequestResponseDTO);
    ResponseEntity<OpusGateOutReadResponse> response =
        restTemplate.postForEntity(gateOutReadResponseURL, request, OpusGateOutReadResponse.class);

    log.info(response.toString());

    // update to db
    // while(future.isDone()){
    // opusRequestResponseDTO.setResponse(gson.toJson(response.getBody()));
    // opusRequestResponseService.updateOpusResponse(opusRequestResponseDTO, future);
    // }
    //
    return response.getBody();
  }

  public GateOutReponse constructGateOutReponse(OpusGateOutReadResponse opusGateOutReadResponse,
      GateOutReponse gateOutReponse) {

    gateOutReponse.setUserID(opusGateOutReadResponse.getUserID());
    LocalDateTime localDateTime = DateUtil.getLocalDategFromString(opusGateOutReadResponse.getGateOUTDateTime());
    gateOutReponse.setGateOUTDateTime(CommonUtil.getFormatteDate(localDateTime));
    gateOutReponse.setHaulageCode(opusGateOutReadResponse.getHaulageCode());
    gateOutReponse.setLaneNo(opusGateOutReadResponse.getLaneNo());
    gateOutReponse.setTruckHeadNo(opusGateOutReadResponse.getTruckHeadNo());
    // gateOutReponse.setTruckPlateNo(opusGateOutReadResponse.getTruckPlateNo());
    gateOutReponse.setImportContainers(opusService.goReadResponseImportContainerListToImportContainerList(
        opusGateOutReadResponse.getImportContainerListCY(), gateOutReponse.getImportContainers()));
    gateOutReponse.setExportContainers(opusService.goReadResponseExportContainerListToExportContainerList(
        opusGateOutReadResponse.getExportContainerListCY(), gateOutReponse.getExportContainers()));
    return gateOutReponse;
  }

}
