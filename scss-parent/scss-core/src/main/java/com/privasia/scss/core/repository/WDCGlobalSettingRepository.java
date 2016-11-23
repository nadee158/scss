package com.privasia.scss.core.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.core.model.WDCGlobalSetting;

public interface WDCGlobalSettingRepository extends BaseRepository<WDCGlobalSetting, String> {
	
	@Query(name="WDCGlobalSetting.fetchGlobalItemsByGlobalCode")
	public Optional<Integer> fetchGlobalItemsByGlobalCode(@Param("globalCode") String globalCode);
	
	@Query(name="WDCGlobalSetting.fetchGlobalStringByGlobalCode")
	public Optional<String> fetchGlobalStringByGlobalCode(@Param("globalCode") String globalCode);
	
	
	


}
