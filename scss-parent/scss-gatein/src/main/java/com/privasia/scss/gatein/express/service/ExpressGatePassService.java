package com.privasia.scss.gatein.express.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateTicketInfoDTO;
import com.privasia.scss.common.enums.Nationality;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.SmartCardUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.gatein.service.ImportExportGateInService;

/**
 * @author Nadeeshani Senevirathna
 *
 */
@Service("expressGatePassService")
public class ExpressGatePassService {

	private Logger log = Logger.getLogger(this.getClass());

	private ImportExportGateInService importExportGateInService;

	private CardRepository cardRepository;

	@Autowired
	public void setImportExportGateInService(ImportExportGateInService importExportGateInService) {
		this.importExportGateInService = importExportGateInService;
	}

	@Autowired
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public GateTicketInfoDTO getGateTicketInfo(GateInRequest gateInRequest) {
		log.warn("getGateTicketInfo starting: gateInRequest:-  " + gateInRequest);

		if (gateInRequest.getGatePass1() == null && gateInRequest.getGatePass2() == null)
			throw new BusinessException("Gate Pass Information are not available!");

		// do the populate
		GateInResponse gateInResponse = importExportGateInService.populateGateIn(gateInRequest);

		GateInWriteRequest gateInWriteRequest = new GateInWriteRequest();
		gateInWriteRequest.setImpExpFlag("I");
		gateInWriteRequest.setCardID(gateInRequest.getCardID());
		gateInWriteRequest.setGateInClient(gateInRequest.getClientID());
		gateInWriteRequest.setGateInStatus("A");
		gateInWriteRequest.setLaneNo(gateInResponse.getLaneNo());
		gateInWriteRequest.setTruckPlateNo(gateInResponse.getTruckPlateNo());
		gateInWriteRequest.setTruckHeadNo(gateInResponse.getTruckHeadNo());
		gateInWriteRequest.setHpatBookingId(gateInRequest.getHpabSeqId());
		gateInWriteRequest.setImportContainers(gateInResponse.getImportContainers());
		gateInWriteRequest.setGateInDateTime(gateInRequest.getGateInDateTime());
		gateInWriteRequest.setTrailerNo("AXBN8198");
		gateInWriteRequest.setTosIndicator(gateInResponse.getTosIndicator());

		/**
		 * Container Position On the Truck
		 */
		if (gateInWriteRequest.getImportContainers().size() == 1) {
			gateInWriteRequest.getImportContainers().get(0).setContainerPosition("M");
		} else {
			gateInWriteRequest.getImportContainers().get(0).setContainerPosition("F");
			gateInWriteRequest.getImportContainers().get(1).setContainerPosition("A");
		}

		// saving gate in info
		log.warn("saving gate in info starting: gateInWriteRequest:-  " + gateInWriteRequest);
		gateInResponse = importExportGateInService.saveGateInInfo(gateInWriteRequest);
		log.warn("saving gate in info completed: gateInResponse :- " + gateInResponse);

		GateTicketInfoDTO gateTicketInfoDTO = printGatePassInfo(gateInWriteRequest, gateInResponse);
		log.warn("getGateTicketInfo ending: gateTicketInfoDTO:-  " + gateTicketInfoDTO);
		return gateTicketInfoDTO;
	}

	public GateTicketInfoDTO printGatePassInfo(GateInWriteRequest gateInWriteRequest, GateInResponse gateInResponse) {
		GateTicketInfoDTO gateTicketInfoDTO = new GateTicketInfoDTO();

		gateTicketInfoDTO.setStatus("OK");

		gateTicketInfoDTO.setCallCardNo(gateInResponse.getCallCardNo());

		gateTicketInfoDTO.setGateInLaneNo(gateInResponse.getLaneNo());

		Optional<Card> cardOpt = cardRepository.findOne(gateInWriteRequest.getCardID());
		Card card = cardOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid Card ID ! " + gateInWriteRequest.getCardID()));

		SmartCardUser smartCardUser = card.getSmartCardUser();

		if (smartCardUser == null)
			throw new BusinessException("System user could not be found!");

		if (smartCardUser.getNationality() == null) {
			throw new BusinessException("Driver's nationality could not be found!");
		}

		if (smartCardUser.getNationality().equals(Nationality.MALAYSIAN)) {
			if (StringUtils.isNotBlank(smartCardUser.getNewNRICNO())) {
				gateTicketInfoDTO.setIcOrPassport(smartCardUser.getNewNRICNO());
			} else {
				gateTicketInfoDTO.setIcOrPassport(smartCardUser.getOldNRICNO());
			}
		} else {
			gateTicketInfoDTO.setIcOrPassport(smartCardUser.getPassportNo());
		}

		gateTicketInfoDTO.setImportOrExport("IMP");

		gateTicketInfoDTO.setTruckHeadNo(gateInWriteRequest.getTruckHeadNo());
		gateTicketInfoDTO.setGateInDateTime(gateInResponse.getGateINDateTime());

		if (!(gateInResponse.getImportContainers() == null || gateInResponse.getImportContainers().isEmpty())) {
			gateInResponse.getImportContainers().forEach(importContainer -> {
				if (gateTicketInfoDTO.getContainer1() == null) {
					gateTicketInfoDTO.setContainer1(importContainer);
				} else {
					gateTicketInfoDTO.setContainer2(importContainer);
				}
			});
		}

		return gateTicketInfoDTO;
	}

}
