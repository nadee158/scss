package com.privasia.scss.etpws.service.client;

import java.rmi.RemoteException;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.privasia.scss.etpws.service.BookingStatusType;
import com.privasia.scss.etpws.service.EdoExpiryForLineRequestType;
import com.privasia.scss.etpws.service.EdoExpiryForLineResponseType;
import com.privasia.scss.etpws.service.EtpProcessExceptionType;
import com.privasia.scss.etpws.service.ObjectFactory;
import com.privasia.scss.etpws.service.UpdateHpatStatusRequestType;
import com.privasia.scss.etpws.service.UpdateHpatStatusResponseType;

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

			JAXBElement<EdoExpiryForLineResponseType> response = (JAXBElement<EdoExpiryForLineResponseType>) getWebServiceTemplate()
					.marshalSendAndReceive(wsServerUri, request,
							new SoapActionCallback(clientDefaultUri + "/getEdoExpiryForLine"));

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

			JAXBElement<UpdateHpatStatusResponseType> response = (JAXBElement<UpdateHpatStatusResponseType>) getWebServiceTemplate()
					.marshalSendAndReceive(wsServerUri, request,
							new SoapActionCallback(clientDefaultUri + "/updateHpatStatus"));
			log.error("Requesting web service for " + response.getValue().getCode());

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}

	}

}
