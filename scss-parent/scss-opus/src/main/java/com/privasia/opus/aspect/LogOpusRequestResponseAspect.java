/**
 * 
 */
package com.privasia.opus.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
@Component
@Aspect
public class LogOpusRequestResponseAspect {
	
	private OpusRequestResponseService opusRequestResponseService;
	
	@Autowired
	public void setOpusRequestResponseService(OpusRequestResponseService opusRequestResponseService) {
		this.opusRequestResponseService = opusRequestResponseService;
	}

	
	@Before("@annotation(logOpusData) && args(opusRequestResponseDTO)")
	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void logOpusRequestBefore(LogOpusData logOpusData, OpusRequestResponseDTO opusRequestResponseDTO){
		
		opusRequestResponseService.saveOpusRequest(opusRequestResponseDTO);
	}
	
	@After("@annotation(logOpusData) && args(opusRequestResponseDTO)")
	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void logOpusResponseAfter(LogOpusData logOpusData, OpusRequestResponseDTO opusRequestResponseDTO){
		
		//opusRequestResponseService.updateOpusResponse(opusRequestResponseDTO, null);
	}

}
