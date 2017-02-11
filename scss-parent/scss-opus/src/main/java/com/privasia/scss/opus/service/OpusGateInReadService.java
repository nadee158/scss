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

import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.DateUtil;
// import com.privasia.scss.core.exception.ResultsNotFoundException;
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

  @Value("${gate_in.read.response.url}")
  private String gateInReadResponseURL;

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
        Optional<ImportContainer> optinalContainer =
            gateInReponse.getImportContainers().stream().filter(importContainer -> importContainer.getContainer()
                .getContainerNumber().equals(opusImportContainer.getContainerNo())).findFirst();

        /*
         * ImportContainer importContainer = optinalContainer.orElseThrow(() -> new
         * ResultsNotFoundException(
         * "No Import Containers could be found for the given Cantainer Number from SCSS !"));
         */

        // constructImportContainer(opusImportContainer, importContainer);

      });

      return gateInReponse;
    }
    return null;
  }


}
