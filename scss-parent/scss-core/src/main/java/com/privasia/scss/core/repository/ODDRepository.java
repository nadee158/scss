/**
 * 
 */
package com.privasia.scss.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.WHODD;

/**
 * @author Janaka
 *
 */
public interface ODDRepository extends BaseRepository<WHODD, Long> {
	
	@Query(name="WHODD.countByPMPlateNoAndOddStatus")
	public int countByPMPlateNoAndOddStatus(@Param("plateNumber") String plateNumber, @Param("oddStatus") TransactionStatus oddStatus);
	
	@Query(name="WHODD.countByPMHeadNoAndOddStatus")
	public int countByPMHeadNoAndOddStatus(@Param("headNumber") String headNumber, @Param("oddStatus") TransactionStatus oddStatus);
	
	@Query(name="WHODD.countByCardIDAndOddStatus")
	public int countByCardIDAndOddStatus(@Param("cardID") Long ca, @Param("cardID") TransactionStatus oddStatus);
	

}
