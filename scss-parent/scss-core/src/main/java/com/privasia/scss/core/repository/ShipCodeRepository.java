/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.List;
import java.util.Optional;

import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.core.model.ShipCode;

/**
 * @author Janaka
 *
 */
public interface ShipCodeRepository extends BaseRepository<ShipCode, Long> {

  Optional<List<ShipCode>> findByShipStatusAndShippingCodeIn(ShipStatus status, List<String> shippingCodes);

}
