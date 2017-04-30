package com.privasia.scss.etpws.service.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.privasia.scss.etpws.dto.SolasPassFileDTO;
import com.privasia.scss.etpws.service.BookingStatusType;
import com.privasia.scss.etpws.service.EdoExpiryForLineRequestType;
import com.privasia.scss.etpws.service.EdoExpiryForLineResponseType;
import com.privasia.scss.etpws.service.ObjectFactory;
import com.privasia.scss.etpws.service.UpdateHpatStatusRequestType;
import com.privasia.scss.etpws.service.UpdateHpatStatusResponseType;
import com.privasia.scss.etpws.service.UpdateSolasForScssGateInRequestType;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

@Service(value = "etpWebserviceClient")
public class ETPWebserviceClient extends WebServiceGatewaySupport {

  private static final Logger log = Logger.getLogger(ETPWebserviceClient.class);

  @Value("${ws.server.uri}")
  private String wsServerUri;

  @Value("${ws.client.default.uri}")
  private String clientDefaultUri;

  public EdoExpiryForLineResponseType getEdoExpiryForLine(String lineNo) {
    try {
      ObjectFactory objFac = new ObjectFactory();
      EdoExpiryForLineRequestType reqObj = new EdoExpiryForLineRequestType();
      reqObj.setLine(lineNo);
      JAXBElement<EdoExpiryForLineRequestType> request = objFac.createEdoExpiryForLineRequest(reqObj);

      log.error("Requesting web service for " + lineNo);
      log.error("clientDefaultUri " + clientDefaultUri);
      log.error("wsServerUri " + wsServerUri);

      JAXBElement<EdoExpiryForLineResponseType> response =
          (JAXBElement<EdoExpiryForLineResponseType>) getWebServiceTemplate().marshalSendAndReceive(wsServerUri,
              request, new SoapActionCallback(clientDefaultUri + "/getEdoExpiryForLine"));

      return response.getValue();
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
    return null;
  }

  public void updateHpabStatus(String bookingId) {
    try {
      ObjectFactory objFac = new ObjectFactory();
      UpdateHpatStatusRequestType reqObj = new UpdateHpatStatusRequestType();
      reqObj.setBookingId(bookingId);
      reqObj.setStatus(BookingStatusType.EXE);
      JAXBElement<UpdateHpatStatusRequestType> request = objFac.createUpdateHpatStatusRequest(reqObj);

      log.error("Requesting web service for " + bookingId);
      log.error("clientDefaultUri " + clientDefaultUri);
      log.error("wsServerUri " + wsServerUri);

      JAXBElement<UpdateHpatStatusResponseType> response =
          (JAXBElement<UpdateHpatStatusResponseType>) getWebServiceTemplate().marshalSendAndReceive(wsServerUri,
              request, new SoapActionCallback(clientDefaultUri + "/updateHpatStatus"));
      log.error("Requesting web service for " + response.getValue().getCode());

    } catch (Exception ex) {
      log.error(ex.getMessage());
    }

  }

  public void updateSolasToEtp(SolasPassFileDTO solasPassFileDTO) throws ParseException {

    UpdateSolasForScssGateInRequestType parameters = new UpdateSolasForScssGateInRequestType();
    parameters.setWeighingMethod(solasPassFileDTO.getWeighingMethod());
    parameters.setWeighingStation(solasPassFileDTO.getWeighStation());

    GregorianCalendar c = new GregorianCalendar();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    Date date = formatter.parse(solasPassFileDTO.getGateInOK());
    c.setTime(date);
    XMLGregorianCalendarImpl xmlGrogerianCalendar = new XMLGregorianCalendarImpl(c);
    log.info(xmlGrogerianCalendar.toString());
    System.out
        .println("******************* solasDTO.getGateInOK() *******************  " + solasPassFileDTO.getGateInOK());
    parameters.setVgmDate(xmlGrogerianCalendar);

    parameters.setVgmFileName(solasPassFileDTO.getCertificateNo());
    parameters.setVgmFileDate(solasPassFileDTO.getCertificate());
    parameters.setWithnessName(solasPassFileDTO.getIssueBy());
    parameters.setWithnessNric(solasPassFileDTO.getIssuerNRIC());

    log.info("******************* Update ETP Solas Web Service Start *******************  ");
    System.out.println("******************* Update ETP Solas Web Service Start *******************  ");

    if (StringUtils.isNotBlank(solasPassFileDTO.getSolasDetailC1())) {
      log.info("******************* Update ETP Solas Web Service Start *******************  ");
      System.out.println("******************* Update ETP Solas Web Service Start *******************  ");
      parameters.setSolasDetId(solasPassFileDTO.getSolasDetailC1().toLowerCase());
      parameters.setTerminalVgm(solasPassFileDTO.getTerminalVGMC1());
      parameters.setVgmReferenceNo(solasPassFileDTO.getExportSEQ01());
      if (solasPassFileDTO.getShipperVGMC1() > 0 && !solasPassFileDTO.isC1WithInTolerance()) {
        parameters.setVariance(
            solasPassFileDTO.getCalculatedVarianceC1() == null ? null : solasPassFileDTO.getCalculatedVarianceC1());
      }
      parameters.setIsTolerance(solasPassFileDTO.isC1WithInTolerance());
      parameters.setTerminalMgw(solasPassFileDTO.getMgwC1());
      parameters.setGrossWeight(solasPassFileDTO.getGrossWeightC1());

      today = Calendar.getInstance().getTime();
      solasDTO.setRequestSendTimeC1(formatter.format(today));
      response = etpIntegrationService.getEtpServicePortTypePort().updateSolasForScssGateIn(parameters);



      if ("FAIL".equals(response.getResponseCode())) {
        while (retry <= 1) {
          response = etpIntegrationService.getEtpServicePortTypePort().updateSolasForScssGateIn(parameters);
          retry++;
        }
        log.info("updateSolasETPStatus response code : " + response.getResponseCode() + " for container 01 : "
            + solasDTO.getContainer01No() + " and error message : " + response.getErrorMessage());
        System.out.println("updateSolasETPStatus response code : " + response.getResponseCode() + " for container 01 : "
            + solasDTO.getContainer01No() + " and error message : " + response.getErrorMessage());
      } else {
        log.info("updateSolasETPStatus response code : " + response.getResponseCode() + " for container 01 : "
            + solasDTO.getContainer01No());
        System.out.println("updateSolasETPStatus response code : " + response.getResponseCode() + " for container 01 : "
            + solasDTO.getContainer01No());
      }

      today = Calendar.getInstance().getTime();
      solasDTO.setResponseReceivedTimeC1(formatter.format(today));
      solasDTO.setEtpC1ResponseCode(response.getResponseCode());
      solasDTO.setEtpC1ResponseMessage(response.getErrorMessage());

    }

  }

}
