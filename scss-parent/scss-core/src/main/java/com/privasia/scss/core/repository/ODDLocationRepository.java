/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.stream.Stream;

import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.core.model.ODDLocation;

/**
 * @author Janaka
 *
 */
public interface ODDLocationRepository extends BaseRepository<ODDLocation, String> {
	
	public Stream<ODDLocation> findByStatusCodeOrderByOddCodeAsc(RecordStatus recordStatus);
	

}
