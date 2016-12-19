/**
 * 
 */
package com.privasia.scss.gatein.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInfo;
import com.privasia.scss.core.service.CommonCardService;

/**
 * @author Janaka
 *
 */

@Service("gateInService")
public class GateInService {

	@Autowired
	private CommonCardService commonCardService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public GateInfo checkGateInAllow(GateInfo gateInfo) {

		boolean trxInProgress = commonCardService.isTrxInProgress(gateInfo.getCardID());
		gateInfo.setAllowGateIn(trxInProgress);

		if (trxInProgress) {
			gateInfo.setMessage("Driver has not completed a full cycle process");
		} else {
			gateInfo.setMessage("Allow Gate In");
		}

		return gateInfo;

	}

}
