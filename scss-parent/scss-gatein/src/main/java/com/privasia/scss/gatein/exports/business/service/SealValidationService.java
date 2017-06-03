/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.SealValidateLog;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.SealValidationLogRepository;
import com.privasia.scss.core.repository.ShipSealRepository;

/**
 * @author Janaka
 *
 */
@Service("sealValidationService")
public class SealValidationService {
	
	
	private ShipSealRepository shipSealRepository;
	
	private ExportsRepository exportsRepository;
	
	private SealValidationLogRepository sealValidationLogRepository;
	
	@Autowired
	public void setShipSealRepository(ShipSealRepository shipSealRepository) {
		this.shipSealRepository = shipSealRepository;
	}
	
	@Autowired
	public void setExportsRepository(ExportsRepository exportsRepository) {
		this.exportsRepository = exportsRepository;
	}

	@Autowired
	public void setSealValidationLogRepository(SealValidationLogRepository sealValidationLogRepository) {
		this.sealValidationLogRepository = sealValidationLogRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean validateSeal(ExportContainer exportContainer){
		
		if(StringUtils.endsWithIgnoreCase(exportContainer.getContainer().getContainerFullOrEmpty(), ContainerFullEmptyType.EMPTY.getValue()))
			return true;
		if(exportContainer.getDontValidateSeal())
			return true;
		CommonSealDTO sealDTO = exportContainer.getSealAttribute();
		if(sealDTO == null ) 
			return true;
		if(StringUtils.isEmpty(sealDTO.getSeal01Number()))
			return true;
		if(StringUtils.isNumeric(sealDTO.getSeal01Number()))
			return true;
		if(StringUtils.isEmpty(exportContainer.getShippingLine()))
			throw new BusinessException("For Container " + exportContainer.getContainer().getContainerNumber() + " Shipping Line not provided");
		
		List<String> ruleList = shipSealRepository.fetchSealRules(exportContainer.getShippingLine());
		
		if(ruleList == null || ruleList.isEmpty()){
			// no shpping line to check rules
			return true;
		}else{
			// need to validate the rule
			
			boolean exist =  ruleList.stream()
		            .filter(rule -> (!StringUtils.equalsIgnoreCase("*", rule)) && 
		            		StringUtils.startsWithIgnoreCase(sealDTO.getSeal01Number(), rule))
		            .findFirst().isPresent();
			if(exist){
				return true;
			}else{
				throw new BusinessException("Container " + exportContainer.getContainer().
								getContainerNumber() + " Seal 1 Prefix Seal No is Invalid");
			}
		}
		
		
	}
	
	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveSealValidationLog(List<ExportContainer> exportContainers){
	
		exportContainers.forEach(container->{
			
			SealValidateLog sealValidationLog = new SealValidateLog();
			sealValidationLog.setShippingLine(container.getShippingLine()); 
			sealValidationLog.setVesselSCN(container.getVesselSCN());
			sealValidationLog.setVesselName(container.getVesselName());
			sealValidationLog.setSealLineCode(container.getSealAttribute().getSeal01Number());
			sealValidationLog.setGateInTime(container.getBaseCommonGateInOutAttribute().getTimeGateIn());
			sealValidationLog.setContNo(container.getContainer().getContainerNumber());
			sealValidationLog.setBookingNo(container.getBookingNo());
			Optional<Exports> optExports = exportsRepository.findOne(container.getExportID());
			Exports exports = optExports.orElseThrow(() -> new ResultsNotFoundException
								("Exports Reference cannot be found to save Seal validation Log !"));
			
			sealValidationLog.setGateInClerk(exports.getBaseCommonGateInOutAttribute().getGateInClerk());
			sealValidationLogRepository.save(sealValidationLog);
			
		});
	}
	

}
