package com.privasia.scss.scancard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ODDRepository;

@Service("transactionService")
public class TransactionService {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private GatePassRepository gatePassRepository;

	@Autowired
	private ODDRepository oddRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean isGateInAllow(Long cardID) {

		int count = oddRepository.countByCardIDAndOddStatus(cardID, TransactionStatus.INPROGRESS);

		if (count > 0)
			return true;

		return false;

	}

}
