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
  public InProgressTrxDTO isTrxInProgress(Long cardID) {

    InProgressTrxDTO inProgressTrxDTO = new InProgressTrxDTO();
    inProgressTrxDTO.setInProgress(false);
    
    long oddCount = oddRepository.countByCardIDAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());
   
    long impCount = gatePassRepository.countRecordsByCardIdAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());
  
    long expCount = exportsRepository.countRecordsByCardIdAndEirStatus(cardID, TransactionStatus.INPROGRESS.getValue());
    
    if(oddCount > 0){
    	inProgressTrxDTO.setTrxType(TransactionType.ODD);
    	inProgressTrxDTO.setInProgress(true);
    }else if(impCount > 0 && expCount > 0){
    	inProgressTrxDTO.setTrxType(TransactionType.IMPORT_EXPORT);
    	inProgressTrxDTO.setInProgress(true);
    }else if(impCount > 0){
    	inProgressTrxDTO.setTrxType(TransactionType.IMPORT);
    	inProgressTrxDTO.setInProgress(true);
    }else if(expCount > 0){
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
	if(haulageCompany == null) 
		throw new BusinessException("Company was not assigned for card id "+card.getCardID());
	
	if(haulageCompany.getCompanyType().equals(CompanyType.HAULAGE)){
		return haulageCompany.getCompanyCode();
	}else{
		throw new BusinessException("Not a haulage Company ! "+haulageCompany.getCompanyCode());
	}
	
  }

}
