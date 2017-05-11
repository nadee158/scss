/**
 * 
 */
package com.privasia.scss.gatein.aspect;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.annotation.DontValidateSeal;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.gatein.exports.business.service.SealValidationService;

/**
 * @author Janaka
 *
 */
@Aspect
@Component
public class SealValidationLogAspect {
	
	private static final Log log = LogFactory.getLog(SealValidationLogAspect.class);

	private SealValidationService sealValidationService;
	
	@Autowired
	public void setSealValidationService(SealValidationService sealValidationService) {
		this.sealValidationService = sealValidationService;
	}
	
	@Async
	@AfterReturning(pointcut = "@annotation(dontValidateSeal)")
	public void dontValidateSeal(JoinPoint joinPoint, DontValidateSeal dontValidateSeal) {

		log.info("*****************   dontValidateSeal called *************************");
		
		if(joinPoint.getArgs()[0] instanceof GateInWriteRequest){
			GateInWriteRequest gateInWriteRequest = (GateInWriteRequest) joinPoint.getArgs()[0];
			
			List<ExportContainer> sealValidationContainers = gateInWriteRequest.getExportContainers().stream().filter(exports ->
						 exports.getDontValidateSeal()).collect(Collectors.toList());
			
			if(!(sealValidationContainers == null || sealValidationContainers.isEmpty())){
				
				sealValidationService.saveSealValidationLog(sealValidationContainers);
				
				return;
			}
			
			return;
		}else {
			throw new BusinessException("Invalid agruments for update Dont Validate Seal Log");
		}

	}


	

	

}


