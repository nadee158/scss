/**
 * 
 */
package com.privasia.scss.core.repository;


import com.privasia.scss.core.model.DgDetail;

/**
 * @author Janaka
 *
 */
public interface DgDetailRepository extends BaseRepository<DgDetail, Long> {

  Long countByScnAndContainerNo(String scn, String containerNo);

}
