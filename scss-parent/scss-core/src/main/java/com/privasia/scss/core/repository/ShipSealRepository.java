/**
 * 
 */
package com.privasia.scss.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.core.model.ShipSeal;

/**
 * @author Janaka
 *
 */
public interface ShipSealRepository extends BaseRepository<ShipSeal, String> {
	
	Optional<List<ShipSeal>> findByLineCodeAndRulesContainingIgnoreCase(String lineCode, String rule);
	
	List<ShipSeal> findByLineCode(String lineCode);
	
	@Query(name="ShipSeal.fetchRules", nativeQuery = true)
	public List<String> fetchSealRules(@Param("lineCode")String lineCode);

}
