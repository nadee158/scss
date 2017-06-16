/**
 * 
 */
package com.privasia.scss.cosmos.oracle.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.privasia.scss.common.enums.AGSMessageStatus;
import com.privasia.scss.cosmos.model.AGSLog;

/**
 * @author Janaka
 *
 */
@Repository("agsLogRepository")
public interface AGSLogRepository extends BaseRepository<AGSLog, Long> {

  List<AGSLog> findBySendRCV(AGSMessageStatus sendRCV);

}
