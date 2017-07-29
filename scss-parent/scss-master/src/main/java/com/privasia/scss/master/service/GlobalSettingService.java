/**
 * 
 */
package com.privasia.scss.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.repository.GlobalSettingRepository;

/**
 * @author Janaka
 *
 */
@Service("globalSettingService")
public class GlobalSettingService {
	
	
	@Autowired
	private GlobalSettingRepository globalSettingRepository;
	
	@Transactional(value = "transactionManager", propagation=Propagation.REQUIRED, readOnly=true)
	public boolean isCustomCheckBeforeTransaction(){
		return globalSettingRepository.isCustomCheckBeforeTransaction();
		
	}
	
	@Transactional(value = "transactionManager", propagation=Propagation.REQUIRED, readOnly=true)
	public boolean mainGateCustomCheck(){
		return globalSettingRepository.mainGateCustomCheck();
		
	}

}