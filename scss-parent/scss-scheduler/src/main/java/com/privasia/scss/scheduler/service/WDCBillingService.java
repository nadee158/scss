/**
 * 
 */
package com.privasia.scss.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.model.WDCTermWeighingDetail;
import com.privasia.scss.core.repository.WDCTermWeighingDetailRepository;
import com.privasia.scss.scheduler.util.AppLogger;

/**
 * @author Janaka
 *
 */
@Service("wdcBillingService")
public class WDCBillingService {
	
	private final static AppLogger logger = AppLogger.getInstance();
	
	private WDCTermWeighingDetailRepository wdcTermWeighingDetailRepository;
	
	@Autowired
	public void setWdcTermWeighingDetailRepository(WDCTermWeighingDetailRepository wdcTermWeighingDetailRepository) {
		this.wdcTermWeighingDetailRepository = wdcTermWeighingDetailRepository;
	}
	
	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveBillingInfo(WDCTermWeighingDetail weighingDetail) {
		WDCTermWeighingDetail persist = wdcTermWeighingDetailRepository.save(weighingDetail);
		logger.info("SAVED BILLING INFO ID : " + persist.getTermWeighingDetailID());

		
	}
	
	

}
