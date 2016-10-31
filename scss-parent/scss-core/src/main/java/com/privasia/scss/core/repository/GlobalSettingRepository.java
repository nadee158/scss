/**
 * 
 */
package com.privasia.scss.core.repository;


import org.springframework.data.jpa.repository.Query;

import com.privasia.scss.core.model.GlobalSetting;

/**
 * @author Janaka
 *
 */
public interface GlobalSettingRepository extends BaseRepository<GlobalSetting, Long> {

  @Query("select gs.customCheckBeforeTransaction from GlobalSetting gs")
  boolean isCustomCheckBeforeTransaction();


  // "SELECT GLOBAL_CODE, GLOBAL_NAME, GLOBAL_ITEMS, GLOBAL_AMOUNT, GLOBAL_STRING, PARAM_VALUE1 \n"
  // //
  // + " FROM WDC_GLOBAL_SETTINGS \n" //
  // + " WHERE GLOBAL_CODE = ? "

}
