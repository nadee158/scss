package com.privasia.scss.core.repository;

import com.privasia.scss.common.enums.CardUsageStatus;
import com.privasia.scss.core.model.CardUsage;

public interface CardUsageRepository extends BaseRepository<CardUsage, Long> {

//@formatter:off
  // String sql = "SELECT cug_id_seq, crd_cardid, TO_CHAR(cug_time_start, 'DD-MM-YYYY HH24:MI:SS')
  // AS cug_time_start, cug_mc_flag, exp_weight_bridge "
  // + "FROM scss_card_usage "
  // + "WHERE cli_clientid = " + SQL.format(clientId)
  // + "AND cug_status = " + SQL.format(STATUS_LOCK)
  // + "AND (cug_time_end IS NULL OR cug_time_end = '')";
  CardUsage findByClient_ClientIDAndUsageStatusAndDateTimeUpdateIsNull(String clientId, CardUsageStatus cardUsageStatus);
//@formatter:on


}
