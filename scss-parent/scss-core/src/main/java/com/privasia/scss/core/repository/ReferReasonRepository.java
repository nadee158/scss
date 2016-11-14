/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.stream.Stream;

import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.core.model.ReferReason;


/**
 * @author Janaka
 *
 */
public interface ReferReasonRepository extends BaseRepository<ReferReason, Long> {

  Stream<ReferReason> findByReferStatus(RecordStatus status);

 
}
