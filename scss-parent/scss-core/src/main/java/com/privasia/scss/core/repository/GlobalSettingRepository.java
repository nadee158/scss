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
	
	@Query(name="GlobalSetting.isCustomCheckBeforeTransaction")
	boolean isCustomCheckBeforeTransaction();
	
	@Query(name="GlobalSetting.mainGateCustomCheck")
	boolean mainGateCustomCheck();

}
