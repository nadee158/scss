package com.privasia.scss.hpat.etp.service.client;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.privasia.scss.hpat.etp.service.EdoExpiryForLineRequestType;
import com.privasia.scss.hpat.etp.service.EdoExpiryForLineResponseType;

public class ETPWebserviceClient extends WebServiceGatewaySupport {

  private static final Logger log = Logger.getLogger(ETPWebserviceClient.class);

  @Value("${ws.server.uri}")
  private String wsServerUri;

  @Value("${ws.client.default.uri}")
  private String clientDefaultUri;

  public EdoExpiryForLineResponseType getEdoExpiryForLine(String lineNo) {

    EdoExpiryForLineRequestType request = new EdoExpiryForLineRequestType();
    request.setLine(lineNo);

    log.error("Requesting web service for " + lineNo);
    log.error("clientDefaultUri " + clientDefaultUri);
    log.error("wsServerUri " + wsServerUri);

    EdoExpiryForLineResponseType response = (EdoExpiryForLineResponseType) getWebServiceTemplate()
        .marshalSendAndReceive(wsServerUri, request, new SoapActionCallback(clientDefaultUri + "/getEdoExpiryForLine"));

    return response;
  }

}
