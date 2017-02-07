package com.privasia.scss.opus.service;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;

@Service("opusService")
public class OpusService {

  private static final Logger log = LoggerFactory.getLogger(OpusService.class);

  public OpusGateInReadResponse getGIR01Response(OpusGateInReadRequest gir01Request) {

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://172.21.88.65:9014/scss/put/GI_R_01";
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<OpusGateInReadRequest> request = new HttpEntity<OpusGateInReadRequest>(gir01Request, headers);

    ResponseEntity<OpusGateInReadResponse> response = restTemplate.postForEntity(url, request, OpusGateInReadResponse.class);

    log.info(response.toString());
    return response.getBody();
  }


  public OpusGateInWriteResponse getGIW01Response(OpusGateInWriteRequest giW01Request) {

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://172.21.88.65:9014/scss/put/GI_W_01";
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<OpusGateInWriteRequest> request = new HttpEntity<OpusGateInWriteRequest>(giW01Request, headers);

    ResponseEntity<OpusGateInWriteResponse> response = restTemplate.postForEntity(url, request, OpusGateInWriteResponse.class);

    log.info(response.toString());
    return response.getBody();
  }

  public static String getJsonDateFromDate(LocalDateTime localDateTime) {
    try {
      if (!(localDateTime == null)) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return localDateTime.format(dateFormat);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  public static LocalDateTime getLocalDategFromString(String gateINDateTime) {
    try {
      if (StringUtils.isNotEmpty(gateINDateTime)) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(gateINDateTime, dateFormat);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }



}
