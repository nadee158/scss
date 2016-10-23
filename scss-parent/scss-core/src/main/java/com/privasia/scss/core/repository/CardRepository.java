/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.Optional;

import com.privasia.scss.core.model.Card;

/**
 * @author Janaka
 *
 */
public interface CardRepository extends BaseRepository<Card, Long> {

	Optional<Card> findByCardNo(Long cardNo);
}
