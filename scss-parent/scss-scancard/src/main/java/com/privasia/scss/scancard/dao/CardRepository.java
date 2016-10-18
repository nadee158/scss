/**
 * 
 */
package com.privasia.scss.scancard.dao;


import java.util.Optional;

import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.repository.BaseRepository;

/**
 * @author Janaka
 *
 */
public interface CardRepository extends BaseRepository<Card, Long> {

	Optional<Card> findByCardNo(Long cardNo);
}
