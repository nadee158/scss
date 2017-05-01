/**
 * 
 */
package com.privasia.scss.gateout.aspect;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.SolasApplicable;
import com.privasia.scss.core.model.Exports;

/**
 * @author Janaka
 *
 */
@Component
@Aspect
public class SolasAspect {

	private static final Log log = LogFactory.getLog(SolasAspect.class);
	
	@AfterReturning(pointcut="@annotation(solasApplicable)", returning="exportsList2")
	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void solasApplicable(JoinPoint joinPoint, SolasApplicable solasApplicable, List<Exports> exportsList2) {
		
		
		for (Exports exports : exportsList2) {
			System.out.println("****** "+exports.getExportID());
		}

		System.out.println("*****************   solasApplicable *************************");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("Method returned value is : " + joinPoint.getArgs()[0]);
		System.out.println("******");

		if (joinPoint.getArgs()[0] instanceof List) {
			@SuppressWarnings("unchecked")
			List<Exports> exportsList = (List<Exports>) joinPoint.getArgs()[0];
			exportsList.forEach(exp ->{
				
				System.out.println("****** "+exp.getExportID());
				
			});
			
		} 
		
		for(Object obj : joinPoint.getArgs()){
			log.info("Method returned value is : " + obj);
		}

		log.info("*****************   solasApplicable called *************************");
		log.info("hijacked : " + joinPoint.getSignature().getName());
		log.info("Method returned value is : " + joinPoint.getArgs()[0]);
		log.info("******");

	}

}
