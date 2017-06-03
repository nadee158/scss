/**
 * 
 */
package com.privasia.scss.gateout.aspect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.SolasApplicable;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.SolasPassFileDTO;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.gateout.service.SolasGateOutService;

/**
 * @author Janaka
 *
 */
@Aspect
@Component
public class SolasAspect {

	private static final Log log = LogFactory.getLog(SolasAspect.class);

	@Value("${async.wait.time}")
	private long asyncWaitTime;

	private SolasGateOutService solasGateOutService;

	@Autowired
	public void setSolasGateOutService(SolasGateOutService solasGateOutService) {
		this.solasGateOutService = solasGateOutService;
	}

	@Async
	@AfterReturning(pointcut = "@annotation(solasApplicable)")
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void solasApplicable(JoinPoint joinPoint, SolasApplicable solasApplicable) {
		
		System.out.println("solasApplicable method start first ********************** ");
		log.info("*****************   solasApplicable called *************************");

		if (joinPoint.getArgs()[0] instanceof GateOutWriteRequest) {
			List<Long> expIDList = new ArrayList<Long>();
			GateOutWriteRequest gateOutWriteRequest = (GateOutWriteRequest) joinPoint.getArgs()[0];
			
			gateOutWriteRequest.getExportContainers().forEach(container ->{
				log.info("Adding export ID ************"+container.getExportID());
						expIDList.add(container.getExportID());
			});
			log.info("Calling  generateSolasCertificateInfo method************");
			SolasPassFileDTO solasPassFileDTO = solasGateOutService.generateSolasCertificateInfo(expIDList);
			solasGateOutService.updateSolasInfo(expIDList, solasPassFileDTO);
			
			return;
		} else {
			throw new BusinessException("Invalid agruments for update Solas");
		}

	}

}
