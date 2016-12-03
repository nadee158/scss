/**
 * 
 */
package com.privasia.scss.core.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.repository.ODDRepository;

/**
 * @author Janaka
 *
 */

@Service("commonODDService")
public class CommonODDService {
	
	private ODDRepository oddRepository;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly = true)
	public boolean isPlateNumberInUse(String plateNumber){
		
		int count = oddRepository.countByPMPlateNoAndOddStatus(plateNumber, TransactionStatus.INPROGRESS);
		
		if(count>0)
			return true;
		
		return false;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly = true)
	public boolean isHeadNumberInUse(String headNumber){
		
		int count = oddRepository.countByPMHeadNoAndOddStatus(headNumber, TransactionStatus.INPROGRESS);
		
		if(count>0)
			return true;
		
		return false;
	}
	
	
	@Autowired
	public void setOddRepository(ODDRepository oddRepository) {
		this.oddRepository = oddRepository;
	}
	
	

}
