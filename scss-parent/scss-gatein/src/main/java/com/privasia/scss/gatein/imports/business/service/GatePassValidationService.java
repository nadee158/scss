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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.GatePassStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;

/**
 * @author Janaka
 *
 */
@Service("gatePassValidationService")
public class GatePassValidationService {

	private static final Log log = LogFactory.getLog(GatePassValidationService.class);

	private GatePassRepository gatePassRepository;
	
	private WDCGatePassRepository wdcGatePassRepository;

	@Autowired
	public void setGatePassRepository(GatePassRepository gatePassRepository) {
		this.gatePassRepository = gatePassRepository;
	}
	
	@Autowired
	public void setWdcGatePassRepository(WDCGatePassRepository wdcGatePassRepository) {
		this.wdcGatePassRepository = wdcGatePassRepository;
	}


	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival, String hpatSeqId,
			String truckHeadNo) {

		TransactionStatus eirStatus = null;
		GatePassStatus gatePassStatus = null;
		LocalDateTime today = LocalDateTime.now();

		log.info("------Gate Pass Validate Inputs ------" + " gatePassNo :" + gatePassNo + " truckHeadNo :"
				+ truckHeadNo + " cardIdSeq :" + cardIdSeq + " checkPreArrival : " + checkPreArrival + " hpatSeqId :"
				+ hpatSeqId);
		Optional<GatePass> scssGatePassOpt = gatePassRepository.findByGatePassNo(gatePassNo);

		GatePass gatePass = scssGatePassOpt
				.orElseThrow(() -> new BusinessException("Invalid Gate Pass. Not Found !" + gatePassNo));

		if (gatePass.getBaseCommonGateInOutAttribute() != null
				&& gatePass.getBaseCommonGateInOutAttribute().getEirStatus() != null
				&& gatePass.getGatePassStatus() != null) {
			eirStatus = gatePass.getBaseCommonGateInOutAttribute().getEirStatus();
			gatePassStatus = gatePass.getGatePassStatus();
			gatePassUsedOrInprogress(gatePass, eirStatus);
			isGatePassCancelled(gatePass, gatePassStatus);
			isGatePassValid(gatePass, gatePassStatus, eirStatus);
			isGatePassExpired(gatePass, today);
			isGatePassExpiredByYPN(gatePass, today);
			
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
	
	private void isGatePassExpired(GatePass gatePass, LocalDateTime today) {
	    
		Optional<WDCGatePass> wdcGatePassOpt = wdcGatePassRepository.findByGatePassNO(gatePass.getGatePassNo());

	    WDCGatePass wdcGatePass =
	          wdcGatePassOpt.orElseThrow(() -> new BusinessException("Gate Pass Not Found In WDC !" + gatePass.getGatePassNo()));

        LocalDateTime wdcValidateDate = wdcGatePass.getGatePassValidDate();

	    log.debug("------START check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today " + today + " / WDCValidateDate " + wdcValidateDate);

	      if (wdcValidateDate == null || today.isAfter(wdcValidateDate)) {

	        log.debug("------END check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today " + today + " / WDCValidateDate " + wdcValidateDate);
	        throw new BusinessException("Gate Pass No " + gatePass.getGatePassNo() + " is already Expired");
	        
	      }

	    log.debug("------END check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today " + today + " / WDCValidateDate " + wdcValidateDate);

	}
	
	private void isGatePassExpiredByYPN(GatePass gatePass, LocalDateTime today) {

        LocalDateTime validateDate = gatePass.getGatePassValidDate();

	    log.debug("------START check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today " + today + " / ValidateDate " + validateDate);

	      if (validateDate == null || today.isAfter(validateDate)) {

	        log.debug("------END check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today " + today + " / ValidateDate " + validateDate);
	        throw new BusinessException("Gate Pass No " + gatePass.getGatePassNo() + " is already Expired");
	        
	      }

	    log.debug("------END check gatepass is valid ---- GatePassNo: " + gatePass.getGatePassNo() + " / Today " + today + " / ValidateDate " + validateDate);

	}

}
