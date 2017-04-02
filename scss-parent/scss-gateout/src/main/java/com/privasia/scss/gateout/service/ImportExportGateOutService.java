package com.privasia.scss.gateout.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.privasia.scss.common.dto.InProgressTrxDTO;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.security.model.UserContext;
import com.privasia.scss.core.service.CommonCardService;
import com.privasia.scss.opus.dto.OpusGateOutReadRequest;
import com.privasia.scss.opus.dto.OpusGateOutReadResponse;
import com.privasia.scss.opus.dto.OpusGateOutWriteRequest;
import com.privasia.scss.opus.dto.OpusGateOutWriteResponse;
import com.privasia.scss.opus.service.OpusGateOutReadService;
import com.privasia.scss.opus.service.OpusGateOutWriteService;
import com.privasia.scss.opus.service.OpusService;

@Service("importExportGateOutService")
public class ImportExportGateOutService {
	
	private static final Log log = LogFactory.getLog(ImportExportGateOutService.class);
	
	private ImportGateOutService importGateOutService;

	private ExportGateOutService exportGateOutService;

	private OpusGateOutReadService opusGateOutReadService;

	private OpusGateOutWriteService opusGateOutWriteService;
	
	private CommonCardService commonCardService;

	private ClientRepository clientRepository;

	private OpusService opusService;
	
	private CardRepository cardRepository;

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
	public void setCommonCardService(CommonCardService commonCardService) {
		this.commonCardService = commonCardService;
	}

	@Autowired
	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Autowired
	public void setOpusService(OpusService opusService) {
		this.opusService = opusService;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateOutReponse populateGateOut(GateOutRequest gateOutRequest) { // gate out read
		
		Optional<Card> cardOpt = cardRepository.findOne(gateOutRequest.getCardID());
		Card card = cardOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid Scan Card ID ! " + gateOutRequest.getCardID()));
		gateOutRequest.setComID(card.getCompany().getCompanyID());
		
		gateOutRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));
		
		InProgressTrxDTO trxDTO = commonCardService.isTrxInProgress(gateOutRequest.getCardID());
		
		if(trxDTO.isInProgress()){
			
			List<ExportContainer> exportContainers = null;
			
			List<ImportContainer> importContainers = null;
			
			switch (trxDTO.getTrxType()) {
			case EXPORT:
				exportContainers = exportGateOutService.populateGateOut(gateOutRequest);
				break;
			case IMPORT:
				importContainers = importGateOutService.populateGateOut(gateOutRequest);
				break;
			case IMPORT_EXPORT:
				exportContainers = exportGateOutService.populateGateOut(gateOutRequest);
				importContainers = importGateOutService.populateGateOut(gateOutRequest);
				break;
			case ODD:
				
				break;
			default:
				break;
			}
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserContext userContext = (UserContext) authentication.getPrincipal();
			log.info("userContext.getUsername() " + userContext.getUsername());
			gateOutRequest.setUserName(userContext.getUsername());
			
			Optional<Client> clientOpt = clientRepository.findOne(gateOutRequest.getLaneId());
			Client client = clientOpt
					.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutRequest.getLaneId()));
			gateOutRequest.setLaneNo(client.getLaneNo());

			// call opus -
			OpusGateOutReadRequest gateOutReadRequest = opusGateOutReadService.constructOpenGateOutRequest(gateOutRequest);
			OpusGateOutReadResponse gateOutReadResponse = opusGateOutReadService.getGateOutReadResponse(gateOutReadRequest);
			// check the errorlist of reponse
		    String errorMessage = opusService.hasErrorMessage(gateOutReadResponse.getErrorList());
		    log.error("ERROR MESSAGE FROM OPUS SERVICE: " + errorMessage);
		    if (StringUtils.isNotEmpty(errorMessage)) {
		      // save it to the db - TO BE IMPLEMENTED
		      // throw new business exception with constructed message - there is
		      // an error
		      throw new BusinessException(errorMessage);
		    }
			GateOutReponse gateOutReponse = new GateOutReponse();
			gateOutReponse.setImportContainers(importContainers);
			gateOutReponse.setExportContainers(exportContainers);
			gateOutReponse = opusGateOutReadService.constructGateOutReponse(gateOutReadResponse, gateOutReponse);
			gateOutReponse.setTransactionType(trxDTO.getTrxType().name());
			return gateOutReponse;
		}else{
			throw new BusinessException("No Valid Gate in Transaction Found for the Scan Card : "+gateOutRequest.getCardID());
		}

		
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

		String errorMessage = opusService.hasErrorMessage(opusGateOutWriteResponse.getErrorList());
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
