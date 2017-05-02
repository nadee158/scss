/**
 * 
 */
package com.privasia.scss.gateout.aspect;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.SolasApplicable;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.gateout.service.SolasGateOutService;

import net.sf.jasperreports.engine.JRException;

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

	@AfterReturning(pointcut="@annotation(solasApplicable)", returning="returnValue")
	public void solasApplicable(SolasApplicable solasApplicable, List<Exports> returnValue) {
		
		log.info("*****************   solasApplicable called *************************");
		System.out.println("*****************   solasApplicable *************************");
		
		System.out.println("****** RETURN VALUES EMPTY  "+returnValue.isEmpty());
		
		try {
			Future<Boolean> isSolasApplicable = solasGateOutService.isSolasApplicable(returnValue);
			
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			System.out.println("*****************  &&&&&&&&&&&&&&& *************************");
			
			
			boolean results = isSolasApplicable.get();
			System.out.println("*****************  &&&&&&&&&&&&&&& ************************* "+results);
		} catch (JRException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
