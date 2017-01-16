package com.privasia.scss.gateout.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.LaneOpenFlag;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.LaneOpen;
import com.privasia.scss.core.repository.LaneOpenRepository;

@Service("laneOpenService")
public class LaneOpenService {
	
	private LaneOpenRepository laneOpenRepository;
	
	@Autowired
	public void setLaneOpenRepository(LaneOpenRepository laneOpenRepository) {
		this.laneOpenRepository = laneOpenRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value="transactionManager")
	public String updateOpenGate(Long openGateSEQ) {
		Optional<LaneOpen> laneOpenOptional = laneOpenRepository.findOne(openGateSEQ);

		LaneOpen laneOpen = laneOpenOptional
				.orElseThrow(() -> new ResultsNotFoundException("Invalid Gate Open SEQ ! " + openGateSEQ));
		laneOpen.setLaneOpenFlag(LaneOpenFlag.OPENED);
		laneOpenRepository.save(laneOpen);

		return "SCSS TRANSACTION SUCCESS";
	}

}
