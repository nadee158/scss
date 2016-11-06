package com.privasia.scss.scancard.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.model.SmartCardUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.scancard.dto.SCUInfoDto;

@Service("cardService")
@Transactional
public class CardService {

  @Value("scss.client.port")
  private String scssClientPort;

  @Autowired
  private CardRepository cardRepository;

  public SCUInfoDto selectSCUInfo(String cardNo, String webIPAddress, String baseUrl) {
    Optional<Card> card = cardRepository.getSmartCardInfoByCardNo(Long.parseLong(cardNo));
    return convertCardToSCUInfo(card.orElse(null), webIPAddress, baseUrl);
  }

  public SCUInfoDto selectSCUInfo(long cardId, String webIPAddress, String baseUrl) {
    Optional<Card> card = cardRepository.getSmartCardInfoByCardId(cardId);
    return convertCardToSCUInfo(card.orElse(null), webIPAddress, baseUrl);
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

  private SCUInfoDto convertCardToSCUInfo(Card card, String webIPAddress, String baseUrl) {
    if (!(card == null)) {
      SCUInfoDto scuInfo = new SCUInfoDto();
      SmartCardUser smartCardUser = card.getSmartCardUser();
      if (!(smartCardUser.getCommonContactAttribute() == null)) {
        scuInfo.setSCUName(smartCardUser.getCommonContactAttribute().getPersonName());
        scuInfo.setNewICNo(smartCardUser.getCommonContactAttribute().getNewNRICNO());
        scuInfo.setOldICNo(smartCardUser.getCommonContactAttribute().getOldNRICNO());
      }

      scuInfo.setNationality(smartCardUser.getNationality().toString());
      scuInfo.setPassportNo(smartCardUser.getPassportNo());
      scuInfo.setSCUIdSeq(Long.toString(smartCardUser.getSmartCardUserID()));

      Company company = card.getCompany();
      scuInfo.setCompName(company.getCompanyName());
      scuInfo.setCompAcctNo(company.getCompanyAccountNo());
      scuInfo.setComIdSeq(Long.toString(company.getCompanyID()));
      scuInfo.setCompStatus(company.getCompanyStatus().toString());
      scuInfo.setCompType(company.getCompanyType().toString());
      scuInfo.setCompCode(company.getCompanyCode());

      scuInfo.setSCNo(Long.toString(card.getCardNo()));
      scuInfo.setCardStatus(card.getCardStatus().toString());

      baseUrl = baseUrl + scssClientPort;
      System.out.println("baseUrl :" + baseUrl);

      String unitNo = getClientUnitNoFromClientService(webIPAddress, baseUrl);
      scuInfo.setClientUnitNo(unitNo);

      return scuInfo;
    } else {
      throw new ResultsNotFoundException("Card was not found!");
    }
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
