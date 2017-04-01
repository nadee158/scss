/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.GIReadResponseExporterContainer;
import com.privasia.scss.opus.dto.GIReadResponseImportContainer;
// import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;

/**
 * @author Janaka
 *
 */

@Service("opusGateInReadService")
public class OpusGateInReadService {

  private static final Logger log = LoggerFactory.getLogger(OpusGateInReadService.class);

  @Value("${gate_in.read.response.url}")
  private String gateInReadResponseURL;

  private OpusService opusService;

  @Autowired
  public void setOpusService(OpusService opusService) {
    this.opusService = opusService;
  }

  public OpusGateInReadRequest constructOpenGateInRequest(GateInRequest gateInRequest) {

    OpusGateInReadRequest opusGateInRequest = new OpusGateInReadRequest();

    opusGateInRequest.setContainerNo1ImportCY(gateInRequest.getImpContainer1());
    opusGateInRequest.setContainerNo2ImportCY(gateInRequest.getImpContainer2());
    // add export containers also
    opusGateInRequest.setContainerNo1ExportCY(gateInRequest.getExpContainer1());
    opusGateInRequest.setContainerNo2ExportCY(gateInRequest.getExpContainer2());

    // 20161130112233 - yyyyMMddHHmmss
    LocalDateTime gateInDateTime = CommonUtil.getParsedDate(gateInRequest.getGateInDateTime());
    opusGateInRequest.setGateINDateTime(DateUtil.getJsonDateFromDate(gateInDateTime));
    opusGateInRequest.setHaulageCode(gateInRequest.getHaulageCode());
    opusGateInRequest.setLaneNo(gateInRequest.getLaneNo());
    opusGateInRequest.setTruckHeadNo(gateInRequest.getTruckHeadNo());
    opusGateInRequest.setUserID(gateInRequest.getUserName());
    return opusGateInRequest;
  }

  public OpusGateInReadResponse getGateInReadResponse(OpusGateInReadRequest opusGateInReadRequest) {
    System.out.println("gateInReadResponseURL " + gateInReadResponseURL);
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<OpusGateInReadRequest> request = new HttpEntity<OpusGateInReadRequest>(opusGateInReadRequest, headers);

    ResponseEntity<OpusGateInReadResponse> response =
        restTemplate.postForEntity(gateInReadResponseURL, request, OpusGateInReadResponse.class);

    log.info(response.toString());
    return response.getBody();
  }

  public GateInReponse constructGateInReponse(OpusGateInReadResponse opusGateInReadResponse,
      GateInReponse gateInReponse) {

    gateInReponse.setUserId(opusGateInReadResponse.getUserID());
    LocalDateTime localDateTime = DateUtil.getLocalDategFromString(opusGateInReadResponse.getGateInDateTime());

    gateInReponse.setGateINDateTime(CommonUtil.getFormatteDate(localDateTime));
    gateInReponse.setHaulageCode(opusGateInReadResponse.getHaulageCode());
    gateInReponse.setLaneNo(opusGateInReadResponse.getLaneNo());
    gateInReponse.setTruckHeadNo(opusGateInReadResponse.getTruckHeadNo());
    gateInReponse = constructExportContainers(opusGateInReadResponse.getExportContainerListCY(), gateInReponse);
    gateInReponse = constructImportContainers(opusGateInReadResponse.getImportContainerListCY(), gateInReponse);
    return gateInReponse;
  }

  private GateInReponse constructExportContainers(List<GIReadResponseExporterContainer> exportContainerListCY,
      GateInReponse gateInReponse) {

    if (!(exportContainerListCY == null || exportContainerListCY.isEmpty())) {

      exportContainerListCY.forEach(opusExportContainer -> {
        ExportContainer exportContainer =
            opusService.giReadResponseExporterContainerToExportContainer(opusExportContainer);
        gateInReponse.getExportContainers().add(exportContainer);

      });
    }
    return gateInReponse;
  }

  private GateInReponse constructImportContainers(List<GIReadResponseImportContainer> importContainerListCY,
      GateInReponse gateInReponse) {

    if (!(importContainerListCY == null || importContainerListCY.isEmpty())) {

      importContainerListCY.forEach(opusImportContainer -> {
        gateInReponse.getImportContainers().forEach(container -> {
          if (StringUtils.equals(container.getContainer().getContainerNumber(), opusImportContainer.getContainerNo())) {
            ImportContainer importContainer =
                opusService.giReadResponseImportContainerToImportContainer(opusImportContainer);
            gateInReponse.getImportContainers().add(importContainer);
          }
        });

      });
    }
    return gateInReponse;
  }

}
