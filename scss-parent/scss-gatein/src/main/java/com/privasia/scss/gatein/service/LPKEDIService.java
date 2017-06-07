package com.privasia.scss.gatein.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.gatein.lpkedi.repository.LPKEDIRepository;

@Service("lpkediService")
public class LPKEDIService {

	private LPKEDIRepository lpkediRepository;

	@Autowired
	public void setLpkediRepository(LPKEDIRepository lpkediRepository) {
		this.lpkediRepository = lpkediRepository;
	}

	public ExportContainer findLPKEDITDigiMessage(ExportContainer exportContainer) {
		exportContainer = lpkediRepository.findLPKEDITDigiMessage(exportContainer);

		return exportContainer;
	}

}
