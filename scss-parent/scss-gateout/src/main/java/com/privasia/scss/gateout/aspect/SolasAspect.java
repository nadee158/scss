/**
 * 
 */
package com.privasia.scss.gateout.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;



/**
 * @author Janaka
 *
 */
@Aspect
@Component
public class SolasAspect {

	/*private static final Log log = LogFactory.getLog(SolasAspect.class);

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

		
	}*/

}
