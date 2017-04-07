/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.DamageCodeDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.core.exception.BusinessException;

/**
 * @author Janaka
 *
 */
@Service("damageCodeService")
public class DamageCodeService {
	
	public void checkDuplicateDameCodeExistence(ExportContainer exportContainer){
	   
		final List<DamageCodeDTO> damageCodeList = Arrays.asList(exportContainer.getDamageCode_01(), exportContainer.getDamageCode_02(),exportContainer.getDamageCode_03(), 
				exportContainer.getDamageCode_05(), exportContainer.getDamageCode_06(), exportContainer.getDamageCode_07(), exportContainer.getDamageCode_08(),exportContainer.getDamageCode_09());
		
		Optional<DamageCodeDTO> duplicateOpt = damageCodeList.stream().filter(damage -> (damage!= null && StringUtils.isNotEmpty(damage.getDamageCode())) 
											&& Collections.frequency(damageCodeList, damage) >1).findFirst();
		
		if(duplicateOpt.isPresent()){
			DamageCodeDTO duplicateDamageCode = duplicateOpt.get();
			throw new BusinessException("Damage Code "+ duplicateDamageCode.getDamageCode()+" Already Exist for container "+exportContainer.getContainer().getContainerNumber());
		}
	}
	
	
}
