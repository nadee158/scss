package com.privasia.scss.gateout.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.security.model.UserContext;
import com.privasia.scss.opus.dto.OpusGateOutReadRequest;
import com.privasia.scss.opus.dto.OpusGateOutReadResponse;
import com.privasia.scss.opus.dto.OpusGateOutWriteRequest;
import com.privasia.scss.opus.dto.OpusGateOutWriteResponse;
import com.privasia.scss.opus.service.OpusGateOutReadService;
import com.privasia.scss.opus.service.OpusGateOutWriteService;
import com.privasia.scss.opus.service.OpusService;

@Service("importExportGateOutService")
public class ImportExportGateOutService {

	private ImportGateOutService importGateOutService;

	private ExportGateOutService exportGateOutService;

	private OpusGateOutReadService opusGateOutReadService;

	private OpusGateOutWriteService opusGateOutWriteService;

	private ClientRepository clientRepository;

	@Autowired
	public void setOpusGateOutReadService(OpusGateOutReadService opusGateOutReadService) {
		this.opusGateOutReadService = opusGateOutReadService;
	}

	@Autowired
	public void setOpusGateOutWriteService(OpusGateOutWriteService opusGateOutWriteService) {
		this.opusGateOutWriteService = opusGateOutWriteService;
	}

	@Autowired
	public void setImportGateOutService(ImportGateOutService importGateOutService) {
		this.importGateOutService = importGateOutService;
	}

	@Autowired
	public void setExportGateOutService(ExportGateOutService exportGateOutService) {
		this.exportGateOutService = exportGateOutService;
	}
	
	@Autowired
	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateOutReponse populateGateOut(GateOutRequest gateOutRequest) { // gate out read

		Optional<Client> clientOpt = clientRepository.findOne(gateOutRequest.getLaneId());
		Client client = clientOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutRequest.getLaneId()));
		gateOutRequest.setLaneNo(client.getLaneNo());
		
		List<ExportContainer> exportContainers = exportGateOutService.populateGateOut(gateOutRequest);

		List<ImportContainer> importContainers = null;
		

		if (gateOutRequest.getGatePass1() > 0 || gateOutRequest.getGatePass2() > 0) {
			importContainers = importGateOutService.populateGateOut(gateOutRequest);
		}

		if (!(StringUtils.isEmpty(gateOutRequest.getExpContainer1())
				|| StringUtils.isEmpty(gateOutRequest.getExpContainer2()))) {
			exportContainers = exportGateOutService.populateGateOut(gateOutRequest);
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		System.out.println("userContext.getUsername() " + userContext.getUsername());
		gateOutRequest.setUserName(userContext.getUsername());

		// call opus -
		OpusGateOutReadRequest gateOutReadRequest = opusGateOutReadService.constructOpenGateOutRequest(gateOutRequest);
		OpusGateOutReadResponse gateOutReadResponse = opusGateOutReadService.getGateOutReadResponse(gateOutReadRequest);
		GateOutReponse gateOutReponse = new GateOutReponse();
		gateOutReponse.setImportContainers(importContainers);
		gateOutReponse.setExportContainers(exportContainers);
		gateOutReponse = opusGateOutReadService.constructGateOutReponse(gateOutReadResponse, gateOutReponse);

		return gateOutReponse;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public GateOutReponse saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest) {
		List<ImportContainer> importContainers = null;
		List<ExportContainer> exportContainers = null;

		// call opus -
		OpusGateOutWriteRequest opusGateOutWriteRequest = opusGateOutWriteService
				.constructOpusGateOutWriteRequest(gateOutWriteRequest);
		OpusGateOutWriteResponse opusGateOutWriteResponse = opusGateOutWriteService
				.getGateOutWriteResponse(opusGateOutWriteRequest);

		String errorMessage = OpusService.hasErrorMessage(opusGateOutWriteResponse.getErrorList());
		if (StringUtils.isNotEmpty(errorMessage)) {
			// throw new business exception with constructed message - there is
			// an error
			throw new BusinessException(errorMessage);
		}

		if (!(gateOutWriteRequest.getExportContainers() == null
				|| gateOutWriteRequest.getExportContainers().isEmpty())) {
			exportContainers = exportGateOutService.saveGateOutInfo(gateOutWriteRequest);
		}

		if (!(gateOutWriteRequest.getImportContainers() == null
				|| gateOutWriteRequest.getImportContainers().isEmpty())) {
			importContainers = importGateOutService.saveGateOutInfo(gateOutWriteRequest);
		}

		GateOutReponse gateOutReponse = new GateOutReponse();
		gateOutReponse.setImportContainers(importContainers);
		gateOutReponse.setExportContainers(exportContainers);
		gateOutReponse = opusGateOutWriteService.constructGateOutReponse(opusGateOutWriteResponse, gateOutReponse);

		return gateOutReponse;
	}

}
