/**
 * 
 */
package com.privasia.scss.gatein.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInfo;
import com.privasia.scss.common.dto.InProgressTrxDTO;
import com.privasia.scss.core.service.CommonCardService;

/**
 * @author Janaka
 *
 */

@Service("gateInService")
public class GateInService {

	
	private CommonCardService commonCardService;

	@Autowired
	public void setCommonCardService(CommonCardService commonCardService) {
		this.commonCardService = commonCardService;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, value="transactionManager")
	public GateInfo checkGateInAllow(GateInfo gateInfo) {

		InProgressTrxDTO trxInProgress = commonCardService.isTrxInProgress(gateInfo.getCardID());

		if (trxInProgress.isInProgress()) {
			gateInfo.setMessage("Driver has not completed a full cycle process");
			gateInfo.setAllowGateIn(false);
		} else {
			gateInfo.setMessage("Allow Gate In");
			gateInfo.setAllowGateIn(true);
		}

		return gateInfo;

	}

}
