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

  Optional<Card> findByCardIDAndCompany_CompanyType(Long cardNo, CompanyType companyType);

  @Query(name = "Card.getSmartCardInfoByCardNo")
  Optional<List<Object[]>> getSmartCardInfoByCardNo(@Param("cardNo") long cardNo);

  @Query(name = "Card.getSmartCardInfoByCardId")
  Optional<List<Object[]>> getSmartCardInfoByCardId(@Param("cardID") long cardId);
}
