/**
 * 
 */
package com.privasia.scss.common.interfaces;

import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;

/**
 * @author Janaka
 *
 */
public interface OpusCosmosBusinessService {

	public GateInReponse sendGateInRequest(GateInWriteRequest gateInWriteRequest);

	public GateOutReponse sendGateOutReadRequest(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse);
	
	public GateOutReponse sendGateOutWriteRequest(GateOutWriteRequest gateOutWriteRequest, GateOutReponse gateOutReponse);

}
