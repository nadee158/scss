package com.privasia.scss.gatein.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.exception.ResultsNotFoundException;
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
		return isoCode.orElseThrow(() -> new ResultsNotFoundException("ISO not found for code : " + isoCodeString));
	}

}
