package com.privasia.scss.etpws.service.client;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.privasia.scss.etpws.dto.SolasETPDTO;
import com.privasia.scss.etpws.service.BookingStatusType;
import com.privasia.scss.etpws.service.EdoExpiryForLineRequestType;
import com.privasia.scss.etpws.service.EdoExpiryForLineResponseType;
import com.privasia.scss.etpws.service.ObjectFactory;
import com.privasia.scss.etpws.service.UpdateHpatStatusRequestType;
import com.privasia.scss.etpws.service.UpdateHpatStatusResponseType;
import com.privasia.scss.etpws.service.UpdateSolasForScssGateInRequestType;
import com.privasia.scss.etpws.service.UpdateSolasForScssGateInResponseType;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class ETPWebserviceClient extends WebServiceGatewaySupport {

  private static final Logger log = Logger.getLogger(ETPWebserviceClient.class);

  public static final String WEB_SERVICE_DATE_PATTERN = "dd-MM-yyyy HH:mm";

  private String wsServerUri;

  private String clientDefaultUri;

  public ETPWebserviceClient(String clientDefaultUri, String wsServerUri) {
    this.clientDefaultUri = clientDefaultUri;
    this.wsServerUri = wsServerUri;
  }

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
  public void updateHpabRejectStatus(String bookingId) {
	    try {
	      ObjectFactory objFac = new ObjectFactory();
	      UpdateHpatStatusRequestType reqObj = new UpdateHpatStatusRequestType();
	      reqObj.setBookingId(bookingId);
	      reqObj.setStatus(BookingStatusType.REJ);
	      JAXBElement<UpdateHpatStatusRequestType> request = objFac.createUpdateHpatStatusRequest(reqObj);

	      log.info("Requesting web service for " + bookingId);
	      log.info("Requesting web service status for " + BookingStatusType.REJ.value());
	      log.info("clientDefaultUri " + clientDefaultUri);
	      log.info("wsServerUri " + wsServerUri);

	      JAXBElement<UpdateHpatStatusResponseType> response =
	          (JAXBElement<UpdateHpatStatusResponseType>) getWebServiceTemplate().marshalSendAndReceive(wsServerUri,
	              request, new SoapActionCallback(clientDefaultUri + "/updateHpatStatus"));
	      log.error("Requesting web service for " + response.getValue().getCode());
	      
	    } catch (Exception ex) {
	      log.error(ex.getMessage());
	    }

	  }

  public List<SolasETPDTO> updateSolasToEtp(List<SolasETPDTO> solasETPDTOs) {

    if (!(solasETPDTOs == null || solasETPDTOs.isEmpty())) {

      ObjectFactory objFac = new ObjectFactory();

      SimpleDateFormat formatter = new SimpleDateFormat(WEB_SERVICE_DATE_PATTERN);

      solasETPDTOs.forEach(solasETPDTO -> {

        UpdateSolasForScssGateInResponseType response = null;
        UpdateSolasForScssGateInRequestType parameters = new UpdateSolasForScssGateInRequestType();
        int retry = 0;

        // set header values and container 01 values
        parameters.setWeighingMethod(solasETPDTO.getWeighingMethod());
        parameters.setWeighingStation(solasETPDTO.getWeighStation());
        GregorianCalendar c = new GregorianCalendar();
        Date date = Date.from(solasETPDTO.getGateInOK().atZone(ZoneId.systemDefault()).toInstant());
        c.setTime(date);
        XMLGregorianCalendarImpl xmlGrogerianCalendar = new XMLGregorianCalendarImpl(c);
        log.info(xmlGrogerianCalendar.toString());
        parameters.setVgmDate(xmlGrogerianCalendar);

        parameters.setVgmFileName(solasETPDTO.getCertificateNo());
        parameters.setVgmFileDate(solasETPDTO.getCertificate());
        parameters.setWithnessName(solasETPDTO.getIssueBy());
        parameters.setWithnessNric(solasETPDTO.getIssuerNRIC());

        if (StringUtils.isNotBlank(solasETPDTO.getSolasDetail())) {
          log.info("******************* Update ETP Solas Web Service Start *******************  ");
          parameters.setSolasDetId(solasETPDTO.getSolasDetail().toLowerCase());
          parameters.setTerminalVgm(solasETPDTO.getTerminalVGM());
          parameters.setVgmReferenceNo(solasETPDTO.getExportSEQ());
          if (solasETPDTO.getShipperVGM() > 0 && !solasETPDTO.isWithInTolerance()) {
            parameters
                .setVariance(solasETPDTO.getCalculatedVariance() == null ? null : solasETPDTO.getCalculatedVariance());
          }
          parameters.setIsTolerance(solasETPDTO.isWithInTolerance());
          parameters.setTerminalMgw(solasETPDTO.getMgw());
          parameters.setGrossWeight(solasETPDTO.getGrossWeight());

          log.info("******************* Update ETP Solas Web Service Start *******************  ");

          Date today = Calendar.getInstance().getTime();
          solasETPDTO.setRequestSendTime(formatter.format(today));

          JAXBElement<UpdateSolasForScssGateInRequestType> request =
              objFac.createUpdateSolasForScssGateInRequest(parameters);

          JAXBElement<UpdateSolasForScssGateInResponseType> jaxBRresponse =
              (JAXBElement<UpdateSolasForScssGateInResponseType>) getWebServiceTemplate().marshalSendAndReceive(
                  wsServerUri, request, new SoapActionCallback(clientDefaultUri + "/updateSolasForScssGateIn"));

          response = jaxBRresponse.getValue();
          LocalDateTime now = LocalDateTime.now();

          if (StringUtils.equalsIgnoreCase("FAIL", response.getResponseCode())) {
            while (retry <= 1) {
              // response =
              // etpIntegrationService.getEtpServicePortTypePort().updateSolasForScssGateIn(parameters);

              jaxBRresponse =
                  (JAXBElement<UpdateSolasForScssGateInResponseType>) getWebServiceTemplate().marshalSendAndReceive(
                      wsServerUri, request, new SoapActionCallback(clientDefaultUri + "/updateSolasForScssGateIn"));

              response = jaxBRresponse.getValue();
              now = LocalDateTime.now();
              retry++;
            }
            log.info("updateSolasETPStatus response code : " + response.getResponseCode() + " for container 01 : "
                + solasETPDTO.getContainerNo() + " and error message : " + response.getErrorMessage());
          } else {
            log.info("updateSolasETPStatus response code : " + response.getResponseCode() + " for container 01 : "
                + solasETPDTO.getContainerNo());
          }
          solasETPDTO.setResponseReceivedTime(now);
          solasETPDTO.setEtpResponseCode(response.getResponseCode());
          solasETPDTO.setEtpResponseMessage(response.getErrorMessage());
        }

      });

    }

    return solasETPDTOs;
  }

}
