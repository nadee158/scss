/**

 * 
 */
package com.privasia.scss.scancard.service;

import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.util.constant.CardStatus;
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

  public boolean validateCardStatus(String cardNo) {


    Optional<Card> cardOptional = cardRepository.findByCardNo(Long.valueOf(cardNo));

    cardOptional.ifPresent(foundCard -> {
      cardOptional.get();

      EnumUtils.getEnum(CardStatus.class, foundCard.getCardStatus().getCardStatus());

    });

    // EnumUtils.getEnum(CardStatus.class, card.orElseGet(card));



    // Card foundCard = card.orElseGet( Card::new );

    /*
     * check the card number in scss_company comp, scss_card card if(found){
     * 
     * write a switch statement for following validation if (cardStatus.equals(Card.ACTIVE) &&
     * compStatus.equals(Company.ACTIVE)) { ret = RET_OK; } else if
     * (cardStatus.equals(Card.BLACKLIST)) { ret = CARD_ERR_BLACKLIST; } else if
     * (cardStatus.equals(Card.EXPIRED)) { ret = CARD_ERR_EXPIRED; } else if
     * (cardStatus.equals(Card.SUSPENDED)) { ret = CARD_ERR_SUSPENDED; } else if
     * (cardStatus.equals(Card.TERMINATED)) { ret = CARD_ERR_TERMINATED; } else if
     * (cardStatus.equals(Card.PENDING)) { ret = CARD_ERR_PENDING; } else if
     * (cardStatus.equals(Card.NOT_ISSUED)) { ret = CARD_ERR_NOT_ISSUED; } else if
     * (compStatus.equals(Company.SUSPENDED)) { ret = COMP_ERR_SUSPENDED; } else if
     * (compStatus.equals(Company.TERMINATED)) { ret = COMP_ERR_TERMINATED; } else { ret =
     * RET_UNKNOWN; }
     * 
     * return the object with card status and isMC true }else{ return null object }
     */

    return false;
  }

}
