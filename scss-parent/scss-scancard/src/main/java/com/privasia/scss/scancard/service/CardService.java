package com.privasia.scss.scancard.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.privasia.scss.common.util.UserIpAddressUtil;
import com.privasia.scss.core.dto.SmartCardUserDTO;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.repository.CardRepository;

@Service("cardService")
public class CardService {

  @Value("${scss.client.port}")
  private String scssClientPort;

  @Autowired
  private CardRepository cardRepository;

  @Transactional(propagation=Propagation.REQUIRED, readOnly = true)
  public SmartCardUserDTO selectSCUInfo(Long cardID, Long cardNo, HttpServletRequest httpRequest) {
	
	if(cardID==null)
		cardID = new Long(0);
	if(cardNo==null)
		cardNo = new Long(0);
	
	Optional<SmartCardUserDTO> smartCardUser = cardRepository.findSCUInfoByCardIdOrNo(cardID, cardNo);  
	
	
	 String webIPAddress = UserIpAddressUtil.getUserIp(httpRequest);
	 String baseUrl = UserIpAddressUtil.getBaseUrl(httpRequest);
	
	return smartCardUser.orElseThrow(() -> new ResultsNotFoundException("Requested SCU Info not found!"));
    
  }

  public String getClientUnitNoFromClientService(String webIPAddress, String baseUrl) {
    try {
      RestTemplate restTemplate = new RestTemplate();
      return restTemplate.getForObject(baseUrl + "/scss/client/{webIp}/unitNo", String.class, webIPAddress);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
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
    if (!(card == null)) {
      if (!(card.getCompany() == null)) {
        return card.getCompany().getCompanyCode();
      } else {
        throw new ResultsNotFoundException("Card was not found!");
      }
    } else {
      throw new ResultsNotFoundException("Card was not found!");
    }
  }


}
