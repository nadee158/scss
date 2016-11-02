/**

 * 
 */
package com.privasia.scss.scancard.service;

import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.util.constant.CardStatus;
import com.privasia.scss.core.util.constant.CompanyStatus;
import com.privasia.scss.scancard.util.CardValidation;
import com.privasia.scss.scancard.util.ScanCardConstant;

/**
 * @author Janaka
 *
 */
@Service
public class CardValidationService {

  @Autowired
  private CardRepository cardRepository;


  public boolean isValidCardNoLength(String cardNo) {

    boolean valid = false;

    switch (cardNo.length()) {

      case ScanCardConstant.CARD_NO_14_DIGITS_LENGTH:
      case ScanCardConstant.CARD_NO_16_DIGITS_LENGTH:
        valid = true;
        break;

      default:
        valid = false;
        break;
    }

    return valid;

  }


  public CardValidation validateCard(String cardNo) {
    CardValidation cardValidation;
    Optional<Card> cardOptional = cardRepository.findByCardNo(Long.valueOf(cardNo));

    Card foundCard = cardOptional.orElse(null);
    if (!(foundCard == null)) {
      CardStatus cardStatus = EnumUtils.getEnum(CardStatus.class, foundCard.getCardStatus().getCardStatus());
      cardValidation = validateCardStatus(cardStatus);

      if (cardValidation.isValid()) {
        Company company = foundCard.getCompany();

        if (!(company == null)) {
          CompanyStatus companyStatus =
              EnumUtils.getEnum(CompanyStatus.class, company.getCompanyStatus().getComStatus());
          CardValidation companyValidation = validateCompanyStatus(companyStatus);
          if (!(companyValidation.isValid())) {
            cardValidation = companyValidation;
          }

        }
      }
    } else {
      cardValidation = new CardValidation(false, ScanCardConstant.CARD_ERR_NOT_FOUND, null);
    }
    return cardValidation;
  }

  public CardValidation validateCompanyStatus(CompanyStatus companyStatus) {
    CardValidation cardValidation = null;
    switch (companyStatus) {
      case ACTIVE:
        // ret = RET_OK;
        cardValidation = new CardValidation(true, ScanCardConstant.RET_OK, CompanyStatus.ACTIVE.getComStatus());
        break;
      case CREATED:
        // RET_UNKNOWN;
        cardValidation = new CardValidation(false, ScanCardConstant.RET_UNKNOWN, CompanyStatus.CREATED.getComStatus());
        break;
      case SUSPENDED:
        // ret = COMP_ERR_SUSPENDED;
        cardValidation =
            new CardValidation(false, ScanCardConstant.COMP_ERR_SUSPENDED, CompanyStatus.SUSPENDED.getComStatus());
        break;
      case TERMINATED:
        // ret = COMP_ERR_TERMINATED;
        cardValidation =
            new CardValidation(false, ScanCardConstant.COMP_ERR_TERMINATED, CompanyStatus.TERMINATED.getComStatus());
        break;
      case UPDATED:
        // RET_UNKNOWN;
        cardValidation = new CardValidation(false, ScanCardConstant.RET_UNKNOWN, CompanyStatus.UPDATED.getComStatus());
        break;
      default:
        // RET_UNKNOWN;
        cardValidation = new CardValidation(false, ScanCardConstant.RET_UNKNOWN, null);
        break;
    }
    return cardValidation;
  }


  public CardValidation validateCardStatus(CardStatus cardStatus) {
    /*
     * check the card number in scss_company comp, scss_card card if(found){
     * 
     * write a switch statement for following validation return the object with card status and isMC
     * true }else { return null object }
     */
    CardValidation cardValidation = null;
    switch (cardStatus) {
      case ACTIVE:
        // ret = RET_OK;
        cardValidation = new CardValidation(true, ScanCardConstant.RET_OK, CardStatus.ACTIVE.getCardStatus());
        break;
      case BLACKLIST:
        // ret = CARD_ERR_BLACKLIST;
        cardValidation =
            new CardValidation(false, ScanCardConstant.CARD_ERR_BLACKLIST, CardStatus.BLACKLIST.getCardStatus());
        break;
      case CREATED:
        // ret = RET_UNKNOWN;
        cardValidation = new CardValidation(false, ScanCardConstant.RET_UNKNOWN, CardStatus.CREATED.getCardStatus());
        break;
      case EXPIRED:
        // ret = CARD_ERR_EXPIRED;
        cardValidation =
            new CardValidation(false, ScanCardConstant.CARD_ERR_EXPIRED, CardStatus.EXPIRED.getCardStatus());
        break;
      case NOT_ISSUED:
        // ret = CARD_ERR_NOT_ISSUED;
        cardValidation =
            new CardValidation(false, ScanCardConstant.CARD_ERR_NOT_ISSUED, CardStatus.NOT_ISSUED.getCardStatus());
        break;
      case PENDING:
        // ret = CARD_ERR_PENDING;
        cardValidation =
            new CardValidation(false, ScanCardConstant.CARD_ERR_PENDING, CardStatus.PENDING.getCardStatus());
        break;
      case SUSPENDED:
        // ret = CARD_ERR_SUSPENDED;
        cardValidation =
            new CardValidation(false, ScanCardConstant.CARD_ERR_SUSPENDED, CardStatus.SUSPENDED.getCardStatus());
        break;
      case TERMINATED:
        // ret = CARD_ERR_TERMINATED;
        cardValidation =
            new CardValidation(false, ScanCardConstant.CARD_ERR_TERMINATED, CardStatus.TERMINATED.getCardStatus());
        break;
      case UPDATED:
        // ret = RET_UNKNOWN;
        cardValidation = new CardValidation(false, ScanCardConstant.RET_UNKNOWN, CardStatus.UPDATED.getCardStatus());
        break;
      default:
        cardValidation = new CardValidation(false, ScanCardConstant.RET_UNKNOWN, null);
        break;
    }

    return cardValidation;
  }



}
