/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.List;

import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.core.model.ReferReason;


/**
 * @author Janaka
 *
 */
public interface ReferReasonRepository extends BaseRepository<ReferReason, Long> {

  List<ReferReason> findByReferStatus(RecordStatus status);


}
