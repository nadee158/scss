/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.SSRBlockType;

/**
 * @author Janaka
 *
 */
@Service("ssrService")
public class SSRService {

	public void checkExportSSR(LocalDateTime timeGateIn, ExportContainer exportContainer) {

		LocalDateTime etaDate = exportContainer.getVesselETADate();
		etaDate.minusHours(4);

		if (etaDate.isBefore(timeGateIn)) {
			exportContainer.setReplanSSR(true);
		} else {
			etaDate.minusHours(6);
			if (etaDate.isBefore(timeGateIn)) {
				exportContainer.setOverClosingSSR(true);
			}
		}
		
		getSSRBlockStatus(exportContainer);

	}

	private void getSSRBlockStatus(ExportContainer exportContainer) {
		if (exportContainer.isReplanSSR() || exportContainer.isOverClosingSSR()) {
			exportContainer.setSsrBlockStatus(SSRBlockType.BLK.getValue());
		}else{
			exportContainer.setSsrBlockStatus(SSRBlockType.RLS.getValue());
		}
	}
	
}
