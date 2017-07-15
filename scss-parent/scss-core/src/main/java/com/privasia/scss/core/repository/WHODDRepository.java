/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.WHODD;

/**
 * @author Janaka
 *
 */
public interface WHODDRepository extends BaseRepository<WHODD, Long> {

  // WHERE odd_hctdid = SQL.format(cardIdSeq)
  // AND (odd_timegateoutok IS NULL OR odd_timegateoutok = '')
  // AND odd_impexpflag= SQL.format(WHODD.IMPEXPFLAG_IMPORT)
  // AND odd_status= SQL.format(WHODD.ODD_STATUS_INPROGRESS);
  @Query(name = "WHODD.findWHODDInfo")
  Optional<WHODD> findWHODDInfo(@Param("cardId") Long cardId, @Param("impExpFlag") ImpExpFlagStatus impExpFlag,
      @Param("oddStatus") TransactionStatus oddStatus);


}
