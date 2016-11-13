package com.privasia.scss.etpws.service.client;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.privasia.scss.etpws.service.EdoExpiryForLineRequestType;
import com.privasia.scss.etpws.service.EdoExpiryForLineResponseType;
import com.privasia.scss.etpws.service.ObjectFactory;


public class ETPWebserviceClient extends WebServiceGatewaySupport {

  private static final Logger log = Logger.getLogger(ETPWebserviceClient.class);

  @Value("${ws.server.uri}")
  private String wsServerUri;

  @Value("${ws.client.default.uri}")
  private String clientDefaultUri;

  public EdoExpiryForLineResponseType getEdoExpiryForLine(String lineNo) {

    ObjectFactory objFac = new ObjectFactory();
    EdoExpiryForLineRequestType reqObj = new EdoExpiryForLineRequestType();
    reqObj.setLine(lineNo);
    JAXBElement<EdoExpiryForLineRequestType> request = objFac.createEdoExpiryForLineRequest(reqObj);


    log.error("Requesting web service for " + lineNo);
    log.error("clientDefaultUri " + clientDefaultUri);
    log.error("wsServerUri " + wsServerUri);


    JAXBElement<EdoExpiryForLineResponseType> response =
        (JAXBElement<EdoExpiryForLineResponseType>) getWebServiceTemplate().marshalSendAndReceive(wsServerUri, request,
            new SoapActionCallback(clientDefaultUri + "/getEdoExpiryForLine"));

    return response.getValue();
  }

}
