package com.privasia.scss.core.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.InProgressTrxDTO;
import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.CompanyType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ODDRepository;

@Service("commonCardService")
public class CommonCardService {

  private CardRepository cardRepository;

  private GatePassRepository gatePassRepository;

  private ExportsRepository exportsRepository;

  private ODDRepository oddRepository;

  @Autowired
  public void setCardRepository(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Autowired
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

  @Autowired
  public void setExportsRepository(ExportsRepository exportsRepository) {
    this.exportsRepository = exportsRepository;
  }

  @Autowired
  public void setOddRepository(ODDRepository oddRepository) {
    this.oddRepository = oddRepository;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public boolean checkIfValidCard(Card card) {
    if (card.getCardStatus() != null
        && StringUtils.equals(card.getCardStatus().getValue(), CardStatus.ACTIVE.getValue())) {
      if (card.getDateThrough() != null && card.getDateThrough().isAfter(LocalDateTime.now())) {
        return true;
      } else {
        card.setCardStatus(CardStatus.EXPIRED);
        cardRepository.save(card);
      }
    }
    return false;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "transactionManager")
  public Card getCardByCardNumber(long cardNumber) {
    Optional<Card> cardOpt = cardRepository.findByCardNo(cardNumber);
    return cardOpt.orElseThrow(() -> new ResultsNotFoundException("Card was not found for card number " + cardNumber));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "transactionManager")
  public InProgressTrxDTO isTrxInProgress(Long cardID) {

    InProgressTrxDTO inProgressTrxDTO = new InProgressTrxDTO();
    inProgressTrxDTO.setInProgress(false);

    if (cardID == null)
      throw new BusinessException("Card information not provided!");

    long oddCount = oddRepository.countByCardIDAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());

    long impCount =
        gatePassRepository.countRecordsByCardIdAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());

    long expCount = exportsRepository.countRecordsByCardIdAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());

    if (oddCount > 0) {
      inProgressTrxDTO.setTrxType(TransactionType.ODD);
      inProgressTrxDTO.setInProgress(true);
    } else if (impCount > 0 && expCount > 0) {
      inProgressTrxDTO.setTrxType(TransactionType.IMPORT_EXPORT);
      inProgressTrxDTO.setInProgress(true);
    } else if (impCount > 0) {
      inProgressTrxDTO.setTrxType(TransactionType.IMPORT);
      inProgressTrxDTO.setInProgress(true);
    } else if (expCount > 0) {
      inProgressTrxDTO.setTrxType(TransactionType.EXPORT);
      inProgressTrxDTO.setInProgress(true);
    }
    return inProgressTrxDTO;

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "transactionManager")
  public String getHaulierCodeByScanCard(Card card) {
    // fetch company of which type is
    // CompanyType - > HAULAGE
    Company haulageCompany = card.getCompany();
    if (haulageCompany == null)
      throw new BusinessException("Company was not assigned for card id " + card.getCardID());

    if (haulageCompany.getCompanyType().equals(CompanyType.HAULAGE)) {
      return haulageCompany.getCompanyCode();
    } else {
      throw new BusinessException("Not a haulage Company ! " + haulageCompany.getCompanyCode());
    }

  }

}
