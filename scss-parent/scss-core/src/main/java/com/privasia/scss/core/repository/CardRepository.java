/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.util.constant.CardStatus;
import com.privasia.scss.core.util.constant.CompanyType;

/**
 * @author Janaka
 *
 */
public interface CardRepository extends BaseRepository<Card, Long> {

  Optional<Card> findByCardNo(Long cardNo);

  Card findByCardIDAndCardStatus(String cardIdSeq, CardStatus active);

//  @formatter:off
//  sql =
//      " SELECT card.CRD_SCARDNO, scuser.SCU_NEWNRICNO, scuser.SCU_OLDNRICNO, scuser.SCU_PASSPORTNO, comp.COM_CODE \n" //
//          + " FROM SCSS_SCUSER scuser \n" //
//          + " LEFT JOIN SCSS_CARD card ON scuser.SCU_USERID_SEQ = card.SCU_USERID \n" //
//          + " LEFT JOIN SCSS_COMPANY comp ON comp.COM_ID_SEQ = card.COM_ID \n" //
//          + " WHERE comp.COM_TYPE = 'H' AND card.CRD_CARDID_SEQ = '" + cardIdSeq + "'";//
//@formatter:on
  Optional<Card> findByCardIDAndCompany_CompanyType(Long cardNo, CompanyType companyType);

  @Query(name = "Card.getSmartCardInfoByCardNo")
  Optional<List<Object[]>> getSmartCardInfoByCardNo(@Param("cardNo") long cardNo);

  @Query(name = "Card.getSmartCardInfoByCardId")
  Optional<List<Object[]>> getSmartCardInfoByCardId(@Param("cardID") long cardId);
}
