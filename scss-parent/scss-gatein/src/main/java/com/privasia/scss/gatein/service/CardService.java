package com.privasia.scss.gatein.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.gatein.dto.SCUInfo;

@Service("cardService")
@Transactional
public class CardService {

  @Autowired
  private CardRepository cardRepository;

  public SCUInfo selectSCUInfo(String cardIdSeq) {
    Optional<Card> card = cardRepository.findOne(Long.parseLong(cardIdSeq));
    return convertCardToSCUInfo(card.orElse(null));
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
  }

  private SCUInfo convertCardToSCUInfo(Card card) {
    if (!(card == null)) {
      SCUInfo scuInfo = new SCUInfo();
      SystemUser systemUser = card.getSystemUser();
      scuInfo.setSCUName(systemUser.getName());
      scuInfo.setNewICNo(systemUser.getCommonContactAttribute().getNewNRICNO());
      scuInfo.setOldICNo(systemUser.getCommonContactAttribute().getOldNRICNO());
      scuInfo.setNationality(systemUser.getNationality().toString());
      scuInfo.setPassportNo(systemUser.getPassportNo());
      scuInfo.setSCUIdSeq(Long.toString(systemUser.getSystemUserID()));

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
    }
    return null;
  }


}
