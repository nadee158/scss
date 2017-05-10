/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.Optional;

import com.privasia.scss.common.enums.ShippingLineReportType;
import com.privasia.scss.core.model.ShipEmail;

/**
 * @author nsenevirat001
 *
 */
public interface ShipEmailRepository extends BaseRepository<ShipEmail, Long> {

  Optional<ShipEmail> findBylineCodeAndTypeCode(String lineCode, ShippingLineReportType typeCode);

}
