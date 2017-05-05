/**
 * 
 */
package com.privasia.scss.gatein.imports.business.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.GatePassStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.WDCGateOrderType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.model.WDCGlobalSetting;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;
import com.privasia.scss.etpws.service.EdoExpiryForLineResponseType;
import com.privasia.scss.etpws.service.client.ETPWebserviceClient;

/**
 * @author Janaka
 *
 */
@Service("gatePassValidationService")
public class GatePassValidationService {

	private static final Log log = LogFactory.getLog(GatePassValidationService.class);

	private GatePassRepository gatePassRepository;

	private WDCGatePassRepository wdcGatePassRepository;

	private WDCGlobalSettingRepository wdcGlobalSettingRepository;

	private ETPWebserviceClient etpWebserviceClient;

	private CardRepository cardRepository;

	@Autowired
	public void setGatePassRepository(GatePassRepository gatePassRepository) {
		this.gatePassRepository = gatePassRepository;
	}

	@Autowired
	public void setWdcGatePassRepository(WDCGatePassRepository wdcGatePassRepository) {
		this.wdcGatePassRepository = wdcGatePassRepository;
	}

	@Autowired
	public void setWdcGlobalSettingRepository(WDCGlobalSettingRepository wdcGlobalSettingRepository) {
		this.wdcGlobalSettingRepository = wdcGlobalSettingRepository;
	}

	@Autowired
	public void setEtpWebserviceClient(@Qualifier("etpWebserviceClient") ETPWebserviceClient etpWebserviceClient) {
		this.etpWebserviceClient = etpWebserviceClient;
	}

	@Autowired
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival, String hpabSeqId,
			String truckHeadNo) {

		TransactionStatus eirStatus = null;
		GatePassStatus gatePassStatus = null;
		LocalDateTime today = LocalDateTime.now();

		log.info("------Gate Pass Validate Inputs ------" + " gatePassNo :" + gatePassNo + " truckHeadNo :"
				+ truckHeadNo + " cardIdSeq :" + cardIdSeq + " checkPreArrival : " + checkPreArrival + " hpatSeqId :"
				+ hpabSeqId);
		Optional<GatePass> scssGatePassOpt = gatePassRepository.findByGatePassNo(gatePassNo);

		GatePass gatePass = scssGatePassOpt
				.orElseThrow(() -> new BusinessException("Invalid Gate Pass. Not Found !" + gatePassNo));
		Optional<WDCGatePass> wdcGatePassOpt = wdcGatePassRepository.findByGatePassNO(gatePass.getGatePassNo());
		WDCGatePass wdcGatePass = wdcGatePassOpt
				.orElseThrow(() -> new BusinessException("Gate Pass Not Found In WDC !" + gatePass.getGatePassNo()));

		if (gatePass.getBaseCommonGateInOutAttribute() != null
				&& gatePass.getBaseCommonGateInOutAttribute().getEirStatus() != null
				&& gatePass.getGatePassStatus() != null) {
			eirStatus = gatePass.getBaseCommonGateInOutAttribute().getEirStatus();
			gatePassStatus = gatePass.getGatePassStatus();
			gatePassUsedOrInprogress(gatePass, eirStatus);
			isGatePassCancelled(gatePass, gatePassStatus);
			isGatePassValid(gatePass, gatePassStatus, eirStatus);
			isGatePassExpired(gatePass, today, wdcGatePass);
			isGatePassExpiredByYPN(gatePass, today);

			Optional<WDCGlobalSetting> wdcGlobalSettingOpt = wdcGlobalSettingRepository.findOne("EDO_EXP");
			if (wdcGlobalSettingOpt.isPresent()) {
				WDCGlobalSetting wdcGlobalSetting = wdcGlobalSettingOpt.get();
				String etpEdoExpiryDateFlag = wdcGlobalSetting.getGlobalString();
				isEDOExpired(gatePass, today, wdcGatePass, etpEdoExpiryDateFlag);
			}

			Optional<Card> cardOpt = cardRepository.findOne(cardIdSeq);
			Card card = cardOpt.orElseThrow(
					() -> new ResultsNotFoundException("Card could not be Found for given card Id ! " + cardIdSeq));
			matchCompany(gatePass, card);

		} else {
			log.info("--Invalid Gate Pass record -- GatePassNo " + gatePassNo + " / TransactionStatus " + eirStatus
					+ " / GatePassStatus " + gatePassStatus);
			throw new BusinessException("Invalid Gate Pass record " + gatePassNo);
		}

	}

	private void gatePassUsedOrInprogress(GatePass gatePass, TransactionStatus eirStatus) {

		log.debug("-----START check gatepass is status  ----" + gatePass.getGatePassNo() + " / TransactionStatus "
				+ eirStatus.name());
		if (StringUtils.equalsIgnoreCase(eirStatus.getValue(), TransactionStatus.APPROVED.getValue())
				|| StringUtils.equalsIgnoreCase(eirStatus.getValue(), TransactionStatus.INPROGRESS.getValue())) {

			throw new BusinessException(
					"Gate Pass No " + gatePass.getGatePassNo() + " is already been " + eirStatus.name());

		}

		log.debug("---END check gatepass status ----" + gatePass.getGatePassNo() + " / TransactionStatus "
				+ eirStatus.name());
	}

	private void isGatePassCancelled(GatePass gatePass, GatePassStatus gatePassStatus) {

		log.debug("-----START check gatepass is cancelled  ----" + gatePass.getGatePassNo() + " / GatePassStatus "
				+ gatePassStatus.name());

		if (StringUtils.equalsIgnoreCase(gatePassStatus.getValue(), GatePassStatus.CANCEL.getValue())) {
			throw new BusinessException("Gate Pass No " + gatePass.getGatePassNo() + " is already been cancelled");
		}

		log.debug("-----End check gatepass is cancelled  ----" + gatePass.getGatePassNo() + " / GatePassStatus "
				+ gatePassStatus.name());
	}

	private void isGatePassValid(GatePass gatePass, GatePassStatus gatePassStatus, TransactionStatus eirStatus) {

		log.debug("-----START check gatepass is valid  ----" + gatePass.getGatePassNo() + " / GatePassStatus "
				+ gatePassStatus.name() + " / TransactionStatus " + eirStatus.name());

		if (!(StringUtils.equalsIgnoreCase(gatePass.getGatePassStatus().getValue(), GatePassStatus.ACTIVE.getValue())
				&& StringUtils.equalsIgnoreCase(eirStatus.getValue(), TransactionStatus.NEW.getValue()))) {
			throw new BusinessException("Gate Pass is Invalid " + gatePass.getGatePassNo());
		}

		log.debug("------END check gatepass is valid , EIRStatus = N? ------" + +gatePass.getGatePassNo()
				+ " / GatePassStatus " + gatePassStatus.name() + " / TransactionStatus " + eirStatus.name());

	}

	private void isGatePassExpired(GatePass gatePass, LocalDateTime today, WDCGatePass wdcGatePass) {

		LocalDateTime wdcValidateDate = wdcGatePass.getGatePassValidDate();

		log.debug("------START check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today "
				+ today + " / WDCValidateDate " + wdcValidateDate);

		if (wdcValidateDate == null || today.isAfter(wdcValidateDate)) {

			log.debug("------END check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today "
					+ today + " / WDCValidateDate " + wdcValidateDate);
			throw new BusinessException("Gate Pass No " + gatePass.getGatePassNo() + " is already Expired");

		}

		log.debug("------END check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today " + today
				+ " / WDCValidateDate " + wdcValidateDate);

	}

	private void isGatePassExpiredByYPN(GatePass gatePass, LocalDateTime today) {

		LocalDateTime validateDate = gatePass.getGatePassValidDate();

		log.debug("------START check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today "
				+ today + " / ValidateDate " + validateDate);

		if (validateDate == null || today.isAfter(validateDate)) {

			log.debug("------END check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today "
					+ today + " / ValidateDate " + validateDate);
			throw new BusinessException("Gate Pass No " + gatePass.getGatePassNo() + " is already Expired");

		}

		log.debug("------END check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today " + today
				+ " / ValidateDate " + validateDate);

	}

	private void isEDOExpired(GatePass gatePass, LocalDateTime today, WDCGatePass wdcGatePass,
			String etpEdoExpiryDateFlag) {

		/**
		 * Edo Expiry Date //edo logs
		 */
		if (StringUtils.equalsIgnoreCase("Y", etpEdoExpiryDateFlag)) {

			log.debug("------START check EDO Expired ---- GatePassNo: " + gatePass.getGatePassNo()
					+ " / etpEdoExpiryDateFlag " + etpEdoExpiryDateFlag + " / GateOrder " + wdcGatePass.getGateOrder()
					+ " / Today " + today + " / LineCode " + wdcGatePass.getGateOrder().getLineCode()
					+ " / ETPEdoExpiryDateFlag " + etpEdoExpiryDateFlag);

			if (wdcGatePass.getGateOrder() != null) {
				String gateOrderType = wdcGatePass.getGateOrder().getTypeCode();
				if (StringUtils.equalsIgnoreCase(WDCGateOrderType.IMPORT.getValue(), gateOrderType)
						|| StringUtils.equalsIgnoreCase(WDCGateOrderType.IMPORT_CHARGABLE.getValue(), gateOrderType)
						|| StringUtils.equalsIgnoreCase(WDCGateOrderType.IMPORT_NON_CHARGABLE.getValue(),
								gateOrderType)) {
					LocalDateTime edoExpiryDate = wdcGatePass.getEdoExpiryDate();
					if (edoExpiryDate != null) {
						if (today.isAfter(edoExpiryDate)) {
							throw new BusinessException(
									"Gate Pass No " + gatePass.getGatePassNo() + "  Demurrage already Expired");
						}
					} else {
						String lineCode = wdcGatePass.getGateOrder().getLineCode();
						if (StringUtils.isNotBlank(lineCode)) {
							EdoExpiryForLineResponseType responseType = etpWebserviceClient
									.getEdoExpiryForLine(lineCode);
							if (responseType.isEdoExpiryEnabled()) {
								if (edoExpiryDate == null) {
									throw new BusinessException("Gate Pass No " + gatePass.getGatePassNo()
											+ "  Demurrage Expiry Date is Empty");
								}
							}
						}
					}
				}
			}
		}

		log.debug("------End check EDO Expired ---- GatePassNo: " + gatePass.getGatePassNo()
				+ " / etpEdoExpiryDateFlag " + etpEdoExpiryDateFlag + " / GateOrder " + wdcGatePass.getGateOrder()
				+ " / Today " + today + " / LineCode " + wdcGatePass.getGateOrder().getLineCode()
				+ " / ETPEdoExpiryDateFlag " + etpEdoExpiryDateFlag);
	}

	private void matchCompany(GatePass gatePass, Card card) {

		/**
		 * Match Company
		 * 
		 */
		log.debug("-------------START Match Company -------- GataPass " + gatePass.getGatePassNo() + " / CardID "
				+ card.getCardID());

		if (gatePass.getCompany() != null || card.getCompany() != null) {
			if (gatePass.getCompany().getCompanyID().longValue() != card.getCompany().getCompanyID().longValue()) {
				throw new BusinessException(
						"Company''s Gate Pass No " + gatePass.getGatePassNo() + " and Smart Card No do not match");
			}
			log.debug("-------------End Match Company -------- GataPass " + gatePass.getGatePassNo() + " / CardID "
					+ card.getCardID());
		} else {
			log.debug("-------------End Match Company -------- GataPass " + gatePass.getGatePassNo() + " / CardID "
					+ card.getCardID());
			throw new BusinessException(
					"Company could not be Found for given Gate Pass No ! " + gatePass.getGatePassNo());
		}
	}

}
