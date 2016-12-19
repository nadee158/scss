package com.privasia.scss.core.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ODDRepository;

@Service("commonCardService")
@Transactional
public class CommonCardService {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private GatePassRepository gatePassRepository;

	@Autowired
	private ExportsRepository exportsRepository;

	@Autowired
	private ODDRepository oddRepository; 

	@Transactional(readOnly = false)
	public boolean checkIfValidCard(Card card) {
		if (!(card.getCardStatus() == null)) {
			if (StringUtils.equals(card.getCardStatus().getValue(), CardStatus.ACTIVE.getValue())) {
				if (!(card.getDateThrough() == null)) {
					if (card.getDateThrough().isAfter(LocalDateTime.now())) {
						return true;
					} else {
						card.setCardStatus(CardStatus.EXPIRED);
						cardRepository.save(card);
					}
				}
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public Card getCardByCardNumber(long cardNumber) {
		Optional<Card> cardOpt = cardRepository.findByCardNo(cardNumber);
		if (cardOpt.isPresent()) {
			return cardOpt.get();
		}
		throw new ResultsNotFoundException("Card was not found!");
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean isTrxInProgress(Long cardID) {

		long count = 0;

		count = oddRepository.countByCardIDAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());
		if (count > 0)
			return false;

		count = gatePassRepository.countRecordsByCardIdAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());
		if (count > 0)
			return false;

		count = exportsRepository.countRecordsByCardIdAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());
		if (count > 0)
			return false;

		return true;

	}

}
