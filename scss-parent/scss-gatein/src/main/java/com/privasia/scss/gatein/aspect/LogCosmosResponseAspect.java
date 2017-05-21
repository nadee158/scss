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

import com.privasia.scss.common.annotation.ISaDG;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.gatein.exports.business.service.DGContainerService;

/**
 * @author Janaka
 *
 */
@Aspect
@Component
public class LogCosmosResponseAspect {
	
	private static final Log log = LogFactory.getLog(LogCosmosResponseAspect.class);

	private DGContainerService dgContainerService;
	

	@Autowired
	public void setDgContainerService(DGContainerService dgContainerService) {
		this.dgContainerService = dgContainerService;
	}
	
	@Async
	@AfterReturning(pointcut = "@annotation(isaDG)")
	public void isADGContainer(JoinPoint joinPoint, ISaDG isaDG) {

		log.info("*****************   isADGContainer called *************************");
		
		if(joinPoint.getArgs()[0] instanceof GateInWriteRequest){
			GateInWriteRequest gateInWriteRequest = (GateInWriteRequest) joinPoint.getArgs()[0];
			
			List<ExportContainer> dgContainers = gateInWriteRequest.getExportContainers().stream().filter(exports ->
						StringUtils.isNotEmpty(exports.getImdg()) && exports.isDontValidateDg()).collect(Collectors.toList());
			
			if(!(dgContainers == null || dgContainers.isEmpty())){
				
				dgContainerService.saveDGValidationLog(dgContainers);
				
				return;
			}
			
			return;
		}else {
			throw new BusinessException("Invalid agruments for update DG Validation Log");
		}

	}

	

}


