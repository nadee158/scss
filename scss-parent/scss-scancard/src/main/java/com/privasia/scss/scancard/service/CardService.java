package com.privasia.scss.scancard.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.SmartCardUserDTO;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.util.UserIpAddressUtil;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;

@Service("cardService")
public class CardService {

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public SmartCardUserDTO selectSCUInfo(Long cardID, Long cardNo, HttpServletRequest httpRequest) {

    if (cardID == null)
      cardID = new Long(0);
    if (cardNo == null)
      cardNo = new Long(0);

    Optional<SmartCardUserDTO> smartCardUser = cardRepository.findSCUInfoByCardIdOrNo(cardID, cardNo);


    if (smartCardUser.isPresent()) {
      SmartCardUserDTO cu = smartCardUser.get();
      if (!(cu == null)) {
        String clientUnitNo = getClientUnitNoFromClientService(httpRequest);
        cu.setClientUnitNo(clientUnitNo);
        return cu;
      }
    } else {
      throw new ResultsNotFoundException("Requested SCU Info not found!");
    }
    return null;


  }

  public String getClientUnitNoFromClientService(HttpServletRequest httpRequest) {
    String webIPAddress = UserIpAddressUtil.getUserIp(httpRequest);
    return clientRepository.getClientUnitNoByIp(webIPAddress).orElse(null);
  }

  public String selectCompanyCode(String cardNo) {
    Optional<Card> card = cardRepository.findByCardNo(Long.parseLong(cardNo));
    return getCompanyCodeFromCard(card.orElse(null));
  }

  public String selectCompanyCode(Long cardID) {
    Optional<Card> card = cardRepository.findOne(cardID);
    return getCompanyCodeFromCard(card.orElse(null));
  }

  private String getCompanyCodeFromCard(Card card) {
    if (card != null) {
      if (card.getCompany() != null) {
        return card.getCompany().getCompanyCode();
      } else {
        throw new ResultsNotFoundException("Card Company was not found!");
      }
    } else {
      throw new ResultsNotFoundException("Card was not found!");
    }
  }


}
