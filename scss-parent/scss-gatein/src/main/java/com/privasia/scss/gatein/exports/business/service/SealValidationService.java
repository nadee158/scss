/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.ShipSeal;
import com.privasia.scss.core.repository.ShipSealRepository;

/**
 * @author Janaka
 *
 */
@Service("sealValidationService")
public class SealValidationService {
	
	
	private ShipSealRepository shipSealRepository;
	
	@Autowired
	public void setShipSealRepository(ShipSealRepository shipSealRepository) {
		this.shipSealRepository = shipSealRepository;
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
		Optional<Stream<ShipSeal>> optShipSealList = shipSealRepository.findByLineCode(exportContainer.getShippingLine());
		if(optShipSealList.isPresent()){
			Stream<ShipSeal> shipSealStream = optShipSealList.get();
			 return shipSealStream
		            .filter(shipSeal -> (! StringUtils.equalsIgnoreCase("*", shipSeal.getRules())) && 
		            		StringUtils.startsWithIgnoreCase(shipSeal.getRules(), sealDTO.getSeal01Number()))
		            .findFirst().isPresent();
		}else{
			return true;
		}
		
	}

}
