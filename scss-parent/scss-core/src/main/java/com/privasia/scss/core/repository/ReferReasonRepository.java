/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.stream.Stream;

import com.privasia.scss.core.model.ReferReason;
import com.privasia.scss.core.util.constant.RecordStatus;


/**
 * @author Janaka
 *
 */
public interface ReferReasonRepository extends BaseRepository<ReferReason, Long> {

  Stream<ReferReason> findByReferStatus(RecordStatus status);

 
}
