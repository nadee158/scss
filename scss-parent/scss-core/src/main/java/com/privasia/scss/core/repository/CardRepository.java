/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.Optional;

import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.util.constant.CardStatus;

/**
 * @author Janaka
 *
 */
public interface CardRepository extends BaseRepository<Card, Long> {

  Optional<Card> findByCardNo(Long cardNo);

  Card findByCardIDAndCardStatus(String cardIdSeq, CardStatus active);
}
