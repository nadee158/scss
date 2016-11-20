package com.privasia.scss.core.repository;



import org.springframework.data.jpa.repository.Query;

import com.privasia.scss.core.model.WDCGlobalSetting;

public interface WDCGlobalSettingRepository extends BaseRepository<WDCGlobalSetting, String> {
	
	@Query(name="WDCGlobalSetting.fetchGlobalItemsByGlobalCode")
	public Long fetchGlobalItemsByGlobalCode(String globalCode);


}
