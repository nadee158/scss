/**
 * 
 */
package com.privasia.scss.cosmos.aspect;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.LogCosmosData;
import com.privasia.scss.cosmos.service.AGSLogService;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosRequest;

/**
 * @author Janaka
 *
 */
@Aspect
@Component
public class LogCosmosDataAspect {

	private static final Log log = LogFactory.getLog(LogCosmosDataAspect.class);
	
	private AGSLogService agsLogService;
	
	@Autowired
	public void setAgsLogService(AGSLogService agsLogService) {
		this.agsLogService = agsLogService;
	}



	//@Async
	@AfterReturning(pointcut = "@annotation(logCosmosData)", returning = "response")
	@Transactional(value = "cosmosOracleTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void logCosmosData(JoinPoint joinPoint, LogCosmosData logCosmosData, Object response) {

		System.out.println("*****************   logCosmosData called *************************");
		
		try{
			
			if(joinPoint.getArgs()[0] instanceof SGS2CosmosRequest && 
					response instanceof String &&
					joinPoint.getArgs()[1] instanceof Integer){
				SGS2CosmosRequest request = (SGS2CosmosRequest) joinPoint.getArgs()[0];
				String responseXML = (String) response;
				System.out.println("*****************   before called saveAGSLog  *************************");
				agsLogService.saveAGSLog(request, responseXML,  (int) joinPoint.getArgs()[1]);
				
			}

		}catch(JAXBException e){
			log.error("ERROR In logCosmosData "+e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		

	}

}
