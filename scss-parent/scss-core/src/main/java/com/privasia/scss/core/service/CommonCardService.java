package com.privasia.scss.core.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.WHODDRepository;

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
  private WHODDRepository whoddRepository;

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

  public boolean isImpExpCompleted(long cardId) {
    TransactionStatus transactionStatus = TransactionStatus.INPROGRESS;

    long importRecordCount = gatePassRepository.countRecordsByCardIdAndEirStatus(cardId, transactionStatus);
    if (importRecordCount > 0) {
      return false;
    }

    long exportRecordCount = exportsRepository.countRecordsByCardIdAndEirStatus(cardId, transactionStatus);
    if (exportRecordCount > 0) {
      return false;
    }

    long wHODDCount = whoddRepository.countByCard_CardIDAndContainer01_OddStatus(cardId, transactionStatus);
    if (wHODDCount > 0) {
      return false;
    }

    return true;
  }

}
