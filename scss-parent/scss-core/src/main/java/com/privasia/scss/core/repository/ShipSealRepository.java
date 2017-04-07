/**
 * 
 */
package com.privasia.scss.core.repository;

import java.util.Optional;
import java.util.stream.Stream;

import com.privasia.scss.core.model.ShipSeal;

/**
 * @author Janaka
 *
 */
public interface ShipSealRepository extends BaseRepository<ShipSeal, String> {
	
	Optional<Stream<ShipSeal>> findByLineCode(String lineCode);

}
