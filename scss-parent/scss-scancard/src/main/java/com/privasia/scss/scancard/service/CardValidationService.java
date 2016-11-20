/**

 * 
 */
package com.privasia.scss.scancard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.CompanyStatus;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.util.service.CurrentDateTimeService;
import com.privasia.scss.scancard.dto.CardValidationDto;
import com.privasia.scss.scancard.util.ScanCardConstant;

/**
 * @author Janaka
 *
 */
@Service
public class CardValidationService {

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private CurrentDateTimeService currentDateTimeService;

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

  /**
   * Validate by card pk
   * 
   * @param cardId
   * @return
   */
  public CardValidationDto validateCard(long cardID) {
    Optional<Card> cardOptional = cardRepository.findOne(cardID);
    if (cardOptional.isPresent()) {
      return validateCard(cardOptional.get());
    }
    throw new ResultsNotFoundException("Requested Card was not found!");
  }

  /**
   * validate by card no
   * 
   * @param cardNo
   * @return
   */
  public CardValidationDto validateCard(String cardNo) {
    boolean isValidCardNo = isValidCardNoLength(cardNo);
    if (isValidCardNo) {
      Optional<Card> cardOptional = cardRepository.findByCardNo(Long.valueOf(cardNo));
      if (cardOptional.isPresent()) {
        return validateCard(
            cardOptional.orElseThrow(() -> new ResultsNotFoundException("Requested Card was not found!")));
      } else {
        throw new ResultsNotFoundException("Requested Card was not found!");
      }
    } else {
      return new CardValidationDto(null, false, ScanCardConstant.CARD_ERR_NOT_VALID_LENGTH, null, null);
    }
  }

  public CardValidationDto validateCard(Card foundCard) {
    CardValidationDto cardValidation;
    CardStatus cardStatus = foundCard.getCardStatus();
    cardValidation = validateCardStatus(foundCard.getCardID(), cardStatus);
    if (cardValidation.isValid()) {
      Company company = foundCard.getCompany();
      CompanyStatus companyStatus = company.getCompanyStatus();
      CardValidationDto companyValidation = validateCompanyStatus(foundCard.getCardID(), companyStatus);
      if (!(companyValidation.isValid())) {
        return companyValidation;
      }
    }

    return cardValidation;
  }

  public CardValidationDto validateCompanyStatus(long cardId, CompanyStatus companyStatus) {
    CardValidationDto cardValidation = null;
    switch (companyStatus) {
      case ACTIVE:
        // ret = RET_OK;
        cardValidation = new CardValidationDto(cardId, true, ScanCardConstant.RET_OK, CompanyStatus.ACTIVE.getValue(),
            currentDateTimeService.getFormattedCurrentDateAndTime());
        break;
      case CREATED:
        // RET_UNKNOWN;
        cardValidation =
            new CardValidationDto(cardId, false, ScanCardConstant.RET_UNKNOWN, CompanyStatus.CREATED.getValue(), null);
        break;
      case SUSPENDED:
        // ret = COMP_ERR_SUSPENDED;
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.COMP_ERR_SUSPENDED,
            CompanyStatus.SUSPENDED.getValue(), null);
        break;
      case TERMINATED:
        // ret = COMP_ERR_TERMINATED;
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.COMP_ERR_TERMINATED,
            CompanyStatus.TERMINATED.getValue(), null);
        break;
      case UPDATED:
        // RET_UNKNOWN;
        cardValidation =
            new CardValidationDto(cardId, false, ScanCardConstant.RET_UNKNOWN, CompanyStatus.UPDATED.getValue(), null);
        break;
      default:
        // RET_UNKNOWN;
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.RET_UNKNOWN, null, null);
        break;
    }
    return cardValidation;
  }

  public CardValidationDto validateCardStatus(long cardId, CardStatus cardStatus) {

    CardValidationDto cardValidation = null;
    switch (cardStatus) {
      case ACTIVE:
        // ret = RET_OK;
        cardValidation = new CardValidationDto(cardId, true, ScanCardConstant.RET_OK, CardStatus.ACTIVE.getValue(),
            currentDateTimeService.getFormattedCurrentDateAndTime());
        break;
      case BLACKLIST:
        // ret = CARD_ERR_BLACKLIST;
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.CARD_ERR_BLACKLIST,
            CardStatus.BLACKLIST.getValue(), null);
        break;
      case CREATED:
        // ret = RET_UNKNOWN;
        cardValidation =
            new CardValidationDto(cardId, false, ScanCardConstant.RET_UNKNOWN, CardStatus.CREATED.getValue(), null);
        break;
      case EXPIRED:
        // ret = CARD_ERR_EXPIRED;
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.CARD_ERR_EXPIRED,
            CardStatus.EXPIRED.getValue(), null);
        break;
      case NOT_ISSUED:
        // ret = CARD_ERR_NOT_ISSUED;
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.CARD_ERR_NOT_ISSUED,
            CardStatus.NOT_ISSUED.getValue(), null);
        break;
      case PENDING:
        // ret = CARD_ERR_PENDING;
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.CARD_ERR_PENDING,
            CardStatus.PENDING.getValue(), null);
        break;
      case SUSPENDED:
        // ret = CARD_ERR_SUSPENDED;
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.CARD_ERR_SUSPENDED,
            CardStatus.SUSPENDED.getValue(), null);
        break;
      case TERMINATED:
        // ret = CARD_ERR_TERMINATED;
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.CARD_ERR_TERMINATED,
            CardStatus.TERMINATED.getValue(), null);
        break;
      case UPDATED:
        // ret = RET_UNKNOWN;
        cardValidation =
            new CardValidationDto(cardId, false, ScanCardConstant.RET_UNKNOWN, CardStatus.UPDATED.getValue(), null);
        break;
      default:
        cardValidation = new CardValidationDto(cardId, false, ScanCardConstant.RET_UNKNOWN, null, null);
        break;
    }

    return cardValidation;
  }

}
