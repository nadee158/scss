/**
 * 
 */
package com.privasia.scss.cosmos.util;

import org.springframework.stereotype.Component;

import com.privasia.scss.common.dto.DamageCodeDTO;

/**
 * @author Janaka
 *
 */
@Component("utilService")
public class UtilService {
	
	
	public static DamageCodeDTO constructDamageCode(String containerDamageCode) {
	    DamageCodeDTO damageCodeDTO = new DamageCodeDTO();
	    damageCodeDTO.setDamageCode(containerDamageCode);
	    return damageCodeDTO;
	}

}
