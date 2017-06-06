/**
 * 
 */
package com.privasia.scss.cosmos.service;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.AGSMessageStatus;
import com.privasia.scss.cosmos.model.AGSLog;
import com.privasia.scss.cosmos.oracle.repository.AGSLogRepository;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosRequest;

/**
 * @author Janaka
 *
 */
@Service("agsLogService")
public class AGSLogService {
	
	private AGSLogRepository agsLogRepository;
	
	@Autowired
	public void setAgsLogRepository(AGSLogRepository agsLogRepository) {
		this.agsLogRepository = agsLogRepository;
	}


	@Transactional(value = "cosmosOracleTransactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void saveAGSLog(SGS2CosmosRequest request, String response, int portNo) throws JAXBException{
		System.out.println("*****************   saveAGSLog ******************** ");
		AGSLog agsRequest = new AGSLog();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(request.getClass());
		
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 

		java.io.StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(request, sw);
		agsRequest.setXmlData(sw.toString());
		agsRequest.setPortNumber(portNo);
		agsRequest.setSendRCV(AGSMessageStatus.SEND);
		System.out.println("*****************   before saveAGSLog ******************** ");
		agsLogRepository.save(agsRequest);
		System.out.println("*****************   saveAGSLog request ******************** "+agsRequest.getAgsMessageId());
		
		AGSLog agsResponse = new AGSLog();
		agsResponse.setXmlData(response);
		agsResponse.setPortNumber(portNo);
		agsResponse.setSendRCV(AGSMessageStatus.RECEIVE);
		agsLogRepository.save(agsResponse);
		System.out.println("*****************   saveAGSLog response ******************** "+agsResponse.getAgsMessageId());
		
		
	}

}
