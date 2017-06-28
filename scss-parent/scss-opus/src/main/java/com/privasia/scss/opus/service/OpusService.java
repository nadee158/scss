package com.privasia.scss.opus.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.privasia.scss.common.dto.ContainerValidationInfo;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.interfaces.TOSService;
import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.dto.OpusGateOutReadRequest;
import com.privasia.scss.opus.dto.OpusGateOutReadResponse;
import com.privasia.scss.opus.dto.OpusGateOutWriteRequest;
import com.privasia.scss.opus.dto.OpusGateOutWriteResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

@Service("opus")
public class OpusService implements TOSService {

	private static final Log log = LogFactory.getLog(OpusService.class);
	
	private OpusGateInReadService opusGateInReadService;

	private OpusGateInWriteService opusGateInWriteService;

	private OpusDTOConstructService opusDTOConstructService;

	private OpusGateOutReadService opusGateOutReadService;

	private OpusGateOutWriteService opusGateOutWriteService;

	private Gson gson;
	
	@Autowired
	public void setOpusGateInReadService(OpusGateInReadService opusGateInReadService) {
		this.opusGateInReadService = opusGateInReadService;
	}

	@Autowired
	public void setOpusGateOutReadService(OpusGateOutReadService opusGateOutReadService) {
		this.opusGateOutReadService = opusGateOutReadService;
	}

	@Autowired
	public void setOpusGateInWriteService(OpusGateInWriteService opusGateInWriteService) {
		this.opusGateInWriteService = opusGateInWriteService;
	}

	@Autowired
	public void setOpusDTOConstructService(OpusDTOConstructService opusDTOConstructService) {
		this.opusDTOConstructService = opusDTOConstructService;
	}

	@Autowired
	public void setGson(Gson gson) {
		this.gson = gson;
	}

	@Autowired
	public void setOpusGateOutWriteService(OpusGateOutWriteService opusGateOutWriteService) {
		this.opusGateOutWriteService = opusGateOutWriteService;
	}

	@Override
	public GateInResponse sendGateInWriteRequest(GateInWriteRequest gateInWriteRequest) {
		OpusGateInWriteRequest opusGateInWriteRequest = opusGateInWriteService
				.constructOpusGateInWriteRequest(gateInWriteRequest);

		OpusRequestResponseDTO opusRequestResponseDTO = new OpusRequestResponseDTO(opusGateInWriteRequest, gson,
				gateInWriteRequest.getCardID());

		OpusGateInWriteResponse opusGateInWriteResponse = opusGateInWriteService
				.getGateInWriteResponse(opusGateInWriteRequest, opusRequestResponseDTO);

		String errorMessage = opusDTOConstructService.hasErrorMessage(opusGateInWriteResponse.getErrorList());
		log.error("ERROR MESSAGE FROM OPUS SERVICE: " + errorMessage);
		if (StringUtils.isNotEmpty(errorMessage)) {
			throw new BusinessException(errorMessage);
		}

		GateInResponse gateInResponse = new GateInResponse();
		gateInResponse.setImportContainers(gateInWriteRequest.getImportContainers());
		gateInResponse.setExportContainers(gateInWriteRequest.getExportContainers());
		gateInResponse = opusGateInWriteService.constructGateInReponse(opusGateInWriteResponse, gateInResponse);
		return gateInResponse;
	}

	@Override
	public GateOutReponse sendGateOutReadRequest(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {
		OpusGateOutReadRequest gateOutReadRequest = opusGateOutReadService.constructOpenGateOutRequest(gateOutRequest);

		OpusRequestResponseDTO opusRequestResponseDTO = new OpusRequestResponseDTO(gateOutReadRequest, gson,
				gateOutRequest.getCardID());

		OpusGateOutReadResponse gateOutReadResponse = opusGateOutReadService.getGateOutReadResponse(gateOutReadRequest,
				opusRequestResponseDTO);
		// check the errorlist of reponse
		String errorMessage = opusDTOConstructService.hasErrorMessage(gateOutReadResponse.getErrorList());
		log.error("ERROR MESSAGE FROM OPUS SERVICE: " + errorMessage);
		if (StringUtils.isNotEmpty(errorMessage)) {
			throw new BusinessException(errorMessage);
		}
		return opusGateOutReadService.constructGateOutReponse(gateOutReadResponse, gateOutReponse);
	}

	@Override
	public GateOutReponse sendGateOutWriteRequest(GateOutWriteRequest gateOutWriteRequest,
			GateOutReponse gateOutReponse) {

		// call opus -
		Optional<OpusGateOutWriteRequest> optOPUSGateOutWriteRequest = opusGateOutWriteService
				.constructOpusGateOutWriteRequest(gateOutWriteRequest);

		if (optOPUSGateOutWriteRequest.isPresent()) {
			OpusGateOutWriteRequest opusGateOutWriteRequest = optOPUSGateOutWriteRequest.get();
			OpusRequestResponseDTO opusRequestResponseDTO = new OpusRequestResponseDTO(opusGateOutWriteRequest, gson,
					gateOutWriteRequest.getCardID());

			OpusGateOutWriteResponse opusGateOutWriteResponse = opusGateOutWriteService
					.getGateOutWriteResponse(opusGateOutWriteRequest, opusRequestResponseDTO);

			String errorMessage = opusDTOConstructService.hasErrorMessage(opusGateOutWriteResponse.getErrorList());
			if (StringUtils.isNotEmpty(errorMessage)) {
				throw new BusinessException(errorMessage);

			}

			// TODO Auto-generated method stub
			return opusGateOutWriteService.constructGateOutReponse(opusGateOutWriteResponse, gateOutReponse);
		} else {
			return gateOutReponse;
		}

	}

	@Override
	public GateInResponse sendGateInReadRequest(GateInRequest gateInRequest, GateInResponse gateInResponse) {

		OpusGateInReadRequest gateInReadRequest = opusGateInReadService.constructOpenGateInRequest(gateInRequest);

		OpusRequestResponseDTO opusRequestResponseDTO = new OpusRequestResponseDTO(gateInReadRequest, gson,
				gateInRequest.getCardID());

		OpusGateInReadResponse gateInReadResponse = opusGateInReadService.getGateInReadResponse(gateInReadRequest,
				opusRequestResponseDTO);

		String errorMessage = opusDTOConstructService.hasErrorMessage(gateInReadResponse.getErrorList());
		if (StringUtils.isNotEmpty(errorMessage)) {
			throw new BusinessException(errorMessage);
		}

		return  opusGateInReadService.constructGateInReponse(gateInReadResponse, gateInResponse);
	}

	@Override
	public ContainerValidationInfo sendODDContainerValidationRequest(GateOutRequest gateOutRequest) {
		OpusGateOutReadRequest gateOutReadRequest = opusGateOutReadService.constructOpenGateOutRequest(gateOutRequest);

		OpusRequestResponseDTO opusRequestResponseDTO = new OpusRequestResponseDTO(gateOutReadRequest, gson,
				gateOutRequest.getCardID());

		OpusGateOutReadResponse gateOutReadResponse = opusGateOutReadService.getGateOutReadResponse(gateOutReadRequest,
				opusRequestResponseDTO);
		// check the errorlist of reponse
		String errorMessage = opusDTOConstructService.hasErrorMessage(gateOutReadResponse.getErrorList());
		log.error("ERROR MESSAGE FROM OPUS SERVICE: " + errorMessage);
		if (StringUtils.isNotEmpty(errorMessage)) {
			throw new BusinessException(errorMessage);
		}

		ContainerValidationInfo validationInfo = new ContainerValidationInfo();
		validationInfo.setContainerNo1(gateOutRequest.getOddImpContainer1());
		validationInfo.setContainerNo1Status(true);

		if (StringUtils.isNotEmpty(gateOutRequest.getOddImpContainer2())) {
			validationInfo.setContainerNo2(gateOutRequest.getOddImpContainer2());
			validationInfo.setContainerNo2Status(true);
		}
		return validationInfo;
	}

}
