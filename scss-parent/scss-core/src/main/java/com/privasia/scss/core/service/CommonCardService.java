package com.privasia.scss.core.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.CompanyType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ODDRepository;

@Service("commonCardService")
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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "transactionManager")
  public boolean isTrxInProgress(Long cardID) {

    long count = 0;

    count = oddRepository.countByCardIDAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());
    if (count > 0)
      return true;

    count = gatePassRepository.countRecordsByCardIdAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());
    if (count > 0)
      return true;

    count = exportsRepository.countRecordsByCardIdAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());
    if (count > 0)
      return true;

    return false;

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "transactionManager")
  public String getHaulierCodeByScanCard(Long cardID) {
    // fetch company of which type is
    // CompanyType - > HAULAGE
    Optional<Card> cardOpt = cardRepository.findByCardIDAndCompany_CompanyType(cardID, CompanyType.HAULAGE);
    if (cardOpt.isPresent()) {
      Card card = cardOpt.orElse(null);
      if (!(card == null || card.getCompany() == null)) {
        return card.getCompany().getCompanyCode();
      } else {
        throw new ResultsNotFoundException("Company was not found for card id :" + cardID);
      }
    } else {
      throw new ResultsNotFoundException("Card was not found for id :" + cardID);
    }
    // return companyCode
  }

}
