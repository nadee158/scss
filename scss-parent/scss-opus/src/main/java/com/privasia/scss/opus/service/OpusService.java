package com.privasia.scss.opus.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.privasia.scss.opus.dto.GIR01Response;

@Service("opusService")
public class OpusService {

  private static final Logger log = LoggerFactory.getLogger(OpusService.class);

  public GIR01Response getGIR01Response() {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://172.21.88.65:9014/scss/put/GI_R_01";
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("userID", "USER01");
    map.add("laneNo", "LNO01");
    map.add("haulageCode", "HAUCD");
    map.add("truckHeadNo", "TRUCK");
    map.add("gateINDateTime", "20161130112233");
    map.add("containerNo1ExportCY", "AZHA0000001");
    map.add("containerNo2ExportCY", "CCMO1000031");
    map.add("containerNo1ImportCY", "QASS1234566");
    map.add("containerNo2ImportCY", "EPLA0000002");
    map.add("containerNo1ExportCFS", "");
    map.add("containerNo2ExportCFS", "");
    map.add("containerNo1ImportCFS", "");
    map.add("containerNo2ImportCFS", "");

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

    ResponseEntity<GIR01Response> response = restTemplate.postForEntity(url, request, GIR01Response.class);

    log.info(response.toString());
    return response.getBody();
  }

}
