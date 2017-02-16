/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.OpusGateOutReadRequest;
import com.privasia.scss.opus.dto.OpusGateOutReadResponse;
import com.privasia.scss.opus.dto.OpusImportContainer;

/**
 * @author Janaka
 *
 */

@Service("opusGateOutReadService")
public class OpusGateOutReadService {

  private static final Logger log = LoggerFactory.getLogger(OpusGateOutReadService.class);

  @Value("${gate_out.read.response.url}")
  private String gateOutReadResponseURL;

  public OpusGateOutReadRequest constructOpenGateOutRequest(GateOutRequest gateOutRequest) {

    OpusGateOutReadRequest opusGateOutRequest = new OpusGateOutReadRequest();

    opusGateOutRequest.setContainerNo1ImportCY(gateOutRequest.getImpContainer1());
    opusGateOutRequest.setContainerNo2ImportCY(gateOutRequest.getImpContainer2());
    // 20161130112233 - yyyyMMddHHmmss
    LocalDateTime gateOutDateTime = CommonUtil.getParsedDate(gateOutRequest.getGateOUTDateTime());
    opusGateOutRequest.setGateOUTDateTime(DateUtil.getJsonDateFromDate(gateOutDateTime));
    opusGateOutRequest.setHaulageCode("HAUCD");
    opusGateOutRequest.setLaneNo(gateOutRequest.getLaneNo());
    opusGateOutRequest.setTruckHeadNo(gateOutRequest.getTruckHeadNo());
    opusGateOutRequest.setUserID(gateOutRequest.getUserName());
    return opusGateOutRequest;
  }

  public OpusGateOutReadResponse getGateOutReadResponse(OpusGateOutReadRequest opusGateOutReadRequest) {
    System.out.println("gateOutReadResponseURL " + gateOutReadResponseURL);
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<OpusGateOutReadRequest> request =
        new HttpEntity<OpusGateOutReadRequest>(opusGateOutReadRequest, headers);

    ResponseEntity<OpusGateOutReadResponse> response =
        restTemplate.postForEntity(gateOutReadResponseURL, request, OpusGateOutReadResponse.class);

    log.info(response.toString());
    return response.getBody();
  }

  public GateOutReponse constructGateOutReponse(OpusGateOutReadResponse opusGateOutReadResponse,
      GateOutReponse gateOutReponse) {

    LocalDateTime localDateTime = DateUtil.getLocalDategFromString(opusGateOutReadResponse.getGateOUTDateTime());
    gateOutReponse.setGateOUTDateTime(CommonUtil.getFormatteDate(localDateTime));
    gateOutReponse.setHaulageCode(opusGateOutReadResponse.getHaulageCode());
    gateOutReponse.setLaneNo(opusGateOutReadResponse.getLaneNo());
    gateOutReponse.setTruckHeadNo(opusGateOutReadResponse.getTruckHeadNo());
    gateOutReponse.setTruckPlateNo(opusGateOutReadResponse.getTruckPlateNo());
    return constructImportContainers(opusGateOutReadResponse.getImportContainerListCY(), gateOutReponse);
  }

  private GateOutReponse constructImportContainers(List<OpusImportContainer> importContainerListCY,
      GateOutReponse gateOutReponse) {

    if (!(importContainerListCY == null || importContainerListCY.isEmpty())) {

      importContainerListCY.forEach(opusImportContainer -> {
        Optional<ImportContainer> optinalContainer =
            gateOutReponse.getImportContainers().stream().filter(importContainer -> importContainer.getContainer()
                .getContainerNumber().equals(opusImportContainer.getContainerNo())).findFirst();

        /*
         * ImportContainer importContainer = optinalContainer.orElseThrow(() -> new
         * ResultsNotFoundException(
         * "No Import Containers could be found for the given Cantainer Number from SCSS !"));
         */

        // constructImportContainer(opusImportContainer, importContainer);

      });

      return gateOutReponse;
    }
    return null;
  }


}
