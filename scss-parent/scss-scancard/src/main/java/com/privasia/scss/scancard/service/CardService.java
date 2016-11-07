package com.privasia.scss.scancard.service;

import java.util.List;
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
import com.privasia.scss.core.util.constant.CardStatus;
import com.privasia.scss.core.util.constant.CompanyStatus;
import com.privasia.scss.core.util.constant.CompanyType;
import com.privasia.scss.core.util.constant.Nationality;
import com.privasia.scss.scancard.dto.SCUInfoDto;

@Service("cardService")
@Transactional
public class CardService {

  @Value("scss.client.port")
  private String scssClientPort;

  @Autowired
  private CardRepository cardRepository;

  public SCUInfoDto selectSCUInfo(String cardNo, String webIPAddress, String baseUrl) {
    Optional<List<Object[]>> cardListOfArray = cardRepository.getSmartCardInfoByCardNo(Long.parseLong(cardNo));
    if (cardListOfArray.isPresent()) {
      List<Object[]> foundCardListOfArray = cardListOfArray.orElse(null);
      Object[] cardArray = foundCardListOfArray.get(0);
      return convertCardDetailArrayToSCUInfo(cardArray, webIPAddress, baseUrl);
    }
    throw new ResultsNotFoundException("Card was not found!");
  }


  public SCUInfoDto selectSCUInfo(long cardId, String webIPAddress, String baseUrl) {
    Optional<List<Object[]>> cardListOfArray = cardRepository.getSmartCardInfoByCardId(cardId);
    if (cardListOfArray.isPresent()) {
      List<Object[]> foundCardListOfArray = cardListOfArray.orElse(null);
      Object[] cardArray = foundCardListOfArray.get(0);
      return convertCardDetailArrayToSCUInfo(cardArray, webIPAddress, baseUrl);
    }
    throw new ResultsNotFoundException("Card was not found!");
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


  private SCUInfoDto convertCardDetailArrayToSCUInfo(Object[] cardArray, String webIPAddress, String baseUrl) {
    if (!(cardArray == null || cardArray.length <= 0)) {
      SCUInfoDto scuInfo = new SCUInfoDto();

      // c.smartCardUser.commonContactAttribute.personName,
      // c.smartCardUser.commonContactAttribute.newNRICNO,\
      // c.smartCardUser.commonContactAttribute.oldNRICNO, c.smartCardUser.nationality,
      // c.smartCardUser.passportNo,\
      // c.smartCardUser.smartCardUserID,c.company.companyName, c.company.companyID,
      // c.company.companyAccountNo, c.company.companyStatus,\
      // c.company.companyType, c.cardNo, c.cardStatus \
      // FROM Card c WHERE c.cardNo=:cardNo

      scuInfo.setSCUName((String) cardArray[0]);
      scuInfo.setNewICNo((String) cardArray[1]);
      scuInfo.setOldICNo((String) cardArray[2]);

      if (!(cardArray[3] == null)) {
        scuInfo.setNationality(((Nationality) cardArray[3]).getValue());
      }

      scuInfo.setPassportNo((String) cardArray[4]);
      if (!(cardArray[5] == null)) {
        scuInfo.setSCUIdSeq(Long.toString((Long) cardArray[5]));
      }

      scuInfo.setCompName((String) cardArray[6]);
      if (!(cardArray[7] == null)) {
        scuInfo.setComIdSeq(Long.toString((Long) cardArray[7]));
      }

      scuInfo.setCompAcctNo((String) cardArray[8]);
      if (!(cardArray[9] == null)) {
        scuInfo.setCompStatus(((CompanyStatus) cardArray[9]).getValue());
      }
      if (!(cardArray[10] == null)) {
        scuInfo.setCompType(((CompanyType) cardArray[10]).getValue());
      }

      scuInfo.setCompCode((String) cardArray[11]);

      if (!(cardArray[12] == null)) {
        scuInfo.setSCNo(Long.toString((Long) cardArray[12]));
      }


      if (!(cardArray[13] == null)) {
        scuInfo.setCardStatus(((CardStatus) cardArray[13]).getValue());
      }

      baseUrl = baseUrl + scssClientPort;
      System.out.println("baseUrl :" + baseUrl);

      String unitNo = getClientUnitNoFromClientService(webIPAddress, baseUrl);
      scuInfo.setClientUnitNo(unitNo);

      return scuInfo;
    } else {
      throw new ResultsNotFoundException("Card was not found!");
    }
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
