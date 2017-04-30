/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;

/**
 * @author Janaka
 *
 */
@Service("dgContainerService")
public class DGContainerService {
	
	private WDCGlobalSettingRepository wdcGlobalSettingRepository;
	
	@Autowired
	public void setWdcGlobalSettingRepository(WDCGlobalSettingRepository wdcGlobalSettingRepository) {
		this.wdcGlobalSettingRepository = wdcGlobalSettingRepository;
	}
	
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean allowDGToBeValidate(){
		
		Optional<String> optLPKEDI = wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode("LPK_EDI");
		
		String lpkEDI = optLPKEDI.orElseThrow(() -> new ResultsNotFoundException("Could not Found LPK_EDI ! "));
		
		if(StringUtils.equalsIgnoreCase(lpkEDI, "Y"))
			return true;
		
		
		return false;
	}
	
	public boolean validateDGContainer(ExportContainer exportContainer){
		
		boolean allowToValidate = allowDGToBeValidate();
		
		if(allowToValidate){
			exportContainer.setLpkEdiEnabled(true);
			
			if(isaDGContainer(exportContainer)){
				
			}else{
				return false;
			}
		}
		
		
		return false;
	}
	
	private boolean isaDGContainer(ExportContainer exportContainer){
		
		
		if(StringUtils.isNotEmpty(exportContainer.getImdg()))
			return true;
		
		return false;
	}

}
