/**
 * 
 */
package com.privasia.opus.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.LogOpusData;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;
import com.privasia.scss.opus.service.OpusRequestResponseService;

/**
 * @author Janaka
 *
 */

@Aspect
@Component
public class LogOpusRequestResponseAspect {
	
	private OpusRequestResponseService opusRequestResponseService;
	
	@Autowired
	public void setOpusRequestResponseService(OpusRequestResponseService opusRequestResponseService) {
		this.opusRequestResponseService = opusRequestResponseService;
	}

	//@Async
	@AfterReturning(pointcut = "@annotation(logOpusData)", returning = "response")
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void logOpusRequestResponse(JoinPoint joinPoint, LogOpusData logOpusData, Object response){
		
		if(joinPoint.getArgs()[1] instanceof OpusRequestResponseDTO){
			
			OpusRequestResponseDTO opusRequestResponseDTO = (OpusRequestResponseDTO) joinPoint.getArgs()[1];
			opusRequestResponseService.saveOpusRequestResponse(opusRequestResponseDTO);
		}
	}
	
	
	@AfterThrowing(pointcut = "@annotation(logOpusData)", throwing="ex")
	public void logOpusRequestResponse2(Exception ex, LogOpusData logOpusData){
		
		
	}
	

}
