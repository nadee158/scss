/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.DamageCodeDTO;
import com.privasia.scss.core.model.DamageCode;
import com.privasia.scss.core.repository.DamageCodeRepository;

/**
 * @author Janaka
 *
 */
@Service("damageCodeService")
public class DamageCodeService {

	
	private DamageCodeRepository damageCodeRepository;

	private ModelMapper modelMapper;
	
	@Autowired
	public void setDamageCodeRepository(DamageCodeRepository damageCodeRepository) {
		this.damageCodeRepository = damageCodeRepository;
	}
	
	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	private DamageCodeDTO convertDomainToDto(DamageCode damageCode) {
		DamageCodeDTO damageCodeDTO = modelMapper.map(damageCode, DamageCodeDTO.class);
		return damageCodeDTO;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<DamageCodeDTO> getDamageList() {
		List<DamageCodeDTO> damageDTOList = new ArrayList<DamageCodeDTO>();
		List<DamageCode> damageCodes = damageCodeRepository.findAll().collect(Collectors.toList());
		if (!(damageCodes == null || damageCodes.isEmpty())) {
			damageCodes.forEach(item -> {
				damageDTOList.add(convertDomainToDto(item));
			});
		}
		return damageDTOList;
	}

}
