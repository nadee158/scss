/**
 * 
 */
package com.privasia.scss.core.modelmap.config;

import org.modelmapper.PropertyMap;

import com.privasia.scss.common.dto.KioskBoothRightsDTO;
import com.privasia.scss.core.model.KioskBoothRights;

/**
 * @author Janaka
 *
 */

public final class ModelMapPropertyMap {

	public static PropertyMap<KioskBoothRights, KioskBoothRightsDTO> kioskBoothRightsDomainToDto() {

		return new PropertyMap<KioskBoothRights, KioskBoothRightsDTO>() { 
			  protected void configure() {
				  map().setBoothClientID(source.getKioskBoothRightsID().getBooth().getClientID());
				  map().setKioskClientID(source.getKioskBoothRightsID().getKiosk().getClientID());
			  }
		  };

	}
	
	public static PropertyMap<KioskBoothRightsDTO, KioskBoothRights> kioskBoothRightsDtoToDomain() {

		return new PropertyMap<KioskBoothRightsDTO, KioskBoothRights>() { 
			  protected void configure() {
				  map().getKioskBoothRightsID().getKiosk().setClientID(source.getKioskClientID());
				  map().getKioskBoothRightsID().getBooth().setClientID(source.getBoothClientID());
				  
			  }
		  };

	}

	
}
