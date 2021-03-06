/**
 * 
 */
package com.privasia.scss.common.interfaces;

import java.util.concurrent.Future;

import com.privasia.scss.common.dto.ContainerValidationInfo;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;

/**
 * @author Janaka
 *
 */
public interface TOSService {

	public Future<GateInResponse> sendGateInReadRequest(GateInRequest gateInRequest, GateInResponse gateInResponse);

	public GateInResponse sendGateInWriteRequest(GateInWriteRequest gateInWriteRequest);

	public GateOutReponse sendGateOutReadRequest(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse);

	public GateOutReponse sendGateOutWriteRequest(GateOutWriteRequest gateOutWriteRequest,
			GateOutReponse gateOutReponse);

	public Future<ContainerValidationInfo> sendODDContainerValidationRequest(GateOutRequest gateOutRequest);

}
