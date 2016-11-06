package com.privasia.scss.scancard.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.model.SmartCardUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.scancard.dto.SCUInfoDto;

@Service("cardService")
@Transactional
public class CardService {

  @Autowired
  private CardRepository cardRepository;

  // String sql = "SELECT " + " a.scu_name " + ", a.scu_newnricno " + ", a.scu_oldnricno " + ",
  // a.scu_nationality "
  // + ", a.scu_passportno " + ", a.scu_userid_seq " + ", b.com_name " + ", b.com_id_seq "
  // + ", b.com_acctno " + ", b.com_status " + ", b.com_type " + ", b.com_code " + ",
  // c.crd_scardno "
  // + ", c.crd_cardstatus " + "FROM " + " scss_scuser a " + ", scss_card c " + ", scss_company b
  // "
  // + "WHERE " + " b.com_id_seq = c.com_id " + " AND a.scu_userid_seq = c.scu_userid"
  // + " AND c.crd_cardid_seq = " + SQL.format(cardIdSeq);
  //
  public SCUInfoDto selectSCUInfo(String cardNo) {
    Optional<Card> card = cardRepository.findByCardNo(Long.parseLong(cardNo));
    return convertCardToSCUInfo(card.orElse(null));
  }

  public SCUInfoDto selectSCUInfo(long cardId) {
    Optional<Card> card = cardRepository.findOne(cardId);
    return convertCardToSCUInfo(card.orElse(null));
  }

  private SCUInfoDto convertCardToSCUInfo(Card card) {
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
