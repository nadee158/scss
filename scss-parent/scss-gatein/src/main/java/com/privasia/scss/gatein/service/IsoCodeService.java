package com.privasia.scss.gatein.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.ISOCode;
import com.privasia.scss.core.repository.ISOCodeRepository;

@Service("isoCodeService")
public class IsoCodeService {

	@Autowired
	private ISOCodeRepository isoCodeRepository;

	@Autowired
	public void setIsoCodeRepository(ISOCodeRepository isoCodeRepository) {
		this.isoCodeRepository = isoCodeRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ISOCode getIsoCodeTarWeight(String isoCodeString) {
		Optional<ISOCode> isoCode = isoCodeRepository.findByIsoCode(isoCodeString);
		return isoCode.orElseThrow(() -> new ResultsNotFoundException("ISO not found In SCSS : " + isoCodeString));
	}
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExportContainer> setISOInfo(List<ExportContainer> exports) {
		
		if(!(exports == null || exports.isEmpty())){
			
			exports.forEach(exportContainer-> {
				if(StringUtils.isNotEmpty(exportContainer.getCosmosISOCode())){
					Optional<ISOCode> optISOCode = isoCodeRepository.findByIsoCode(exportContainer.getCosmosISOCode());
					
					if(optISOCode.isPresent()){
						ISOCode isoCode = optISOCode.get();
						exportContainer.getContainer().setContainerSize(String.valueOf(isoCode.getLength()));
						exportContainer.getContainer().setContainerHeight(isoCode.getHeight().doubleValue());
						exportContainer.setContainerType(isoCode.getType());
						exportContainer.setTareWeight(isoCode.getTareWeight());
					}
				}
			});
		}
		
		return exports;
		
	}

}
