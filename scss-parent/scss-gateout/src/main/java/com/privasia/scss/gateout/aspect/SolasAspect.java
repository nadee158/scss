/**
 * 
 */
package com.privasia.scss.gateout.aspect;

import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.SolasApplicable;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.gateout.service.SolasGateOutService;


/**
 * @author Janaka
 *
 */
@Aspect
@Component
public class SolasAspect {

	private static final Log log = LogFactory.getLog(SolasAspect.class);

	private SolasGateOutService solasGateOutService;

	@Autowired
	public void setSolasGateOutService(SolasGateOutService solasGateOutService) {
		this.solasGateOutService = solasGateOutService;
	}
	
	@Async
	@AfterReturning(pointcut = "@annotation(solasApplicable)", returning = "returnValue")
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void solasApplicable(SolasApplicable solasApplicable, List<Exports> returnValue) {

		log.info("*****************   solasApplicable called *************************");
		
		solasGateOutService.updateSolasInfo(returnValue);

		
	}

}
