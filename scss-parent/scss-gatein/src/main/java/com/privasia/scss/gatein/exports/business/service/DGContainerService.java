/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.LpkClassType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.model.DGValidateLog;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.repository.DGValidationLogRepository;
import com.privasia.scss.core.repository.DgDetailRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;
import com.privasia.scss.gatein.service.LPKEDIService;

/**
 * @author Janaka
 *
 */
@Service("dgContainerService")
public class DGContainerService {

	private static final LinkedHashMap<String, Optional<Integer>> dgHours;

	static {
		dgHours = new LinkedHashMap<String, Optional<Integer>>();
		dgHours.put("120 HOURS", Optional.of(120));
		dgHours.put("96 HOURS", Optional.of(96));
		dgHours.put("72 HOURS", Optional.of(72));
		dgHours.put("48 HOURS", Optional.of(48));
		dgHours.put("36 HOURS", Optional.of(36));
		dgHours.put("24 HOURS", Optional.of(24));
		dgHours.put("12 HOURS", Optional.of(12));
		dgHours.put("04 HOURS", Optional.of(4));
	}

	private WDCGlobalSettingRepository wdcGlobalSettingRepository;

	private EarlyEntryService earlyEntryService;

	private DgDetailRepository dgDetailRepository;

	private ExportsRepository exportsRepository;

	private DGValidationLogRepository dgValidationLogRepository;

	private LPKEDIService lpkediService;

	@Autowired
	public void setWdcGlobalSettingRepository(WDCGlobalSettingRepository wdcGlobalSettingRepository) {
		this.wdcGlobalSettingRepository = wdcGlobalSettingRepository;
	}

	@Autowired
	public void setEarlyEntryService(EarlyEntryService earlyEntryService) {
		this.earlyEntryService = earlyEntryService;
	}

	@Autowired
	public void setDgDetailRepository(DgDetailRepository dgDetailRepository) {
		this.dgDetailRepository = dgDetailRepository;
	}

	@Autowired
	public void setExportsRepository(ExportsRepository exportsRepository) {
		this.exportsRepository = exportsRepository;
	}

	@Autowired
	public void setDgValidationLogRepository(DGValidationLogRepository dgValidationLogRepository) {
		this.dgValidationLogRepository = dgValidationLogRepository;
	}

	@Autowired
	public void setLpkediService(LPKEDIService lpkediService) {
		this.lpkediService = lpkediService;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean allowDGToBeValidate() {

		Optional<String> optLPKEDI = wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode("LPK_EDI");

		String lpkEDI = optLPKEDI.orElseThrow(() -> new ResultsNotFoundException("Could not Found LPK_EDI ! "));

		if (StringUtils.equalsIgnoreCase(lpkEDI, "Y"))
			return true;

		return false;
	}

	private boolean validateDGContainer(ExportContainer exportContainer) {

		if (allowDGToBeValidate()) {
			exportContainer.setLpkEdiEnabled(true);

			if (!allowDGByPass(exportContainer)) {
				// can't by pass dg

				if (hasAKpaApproval(exportContainer)) {
					/**
					 * DG is approve. Check class to verify entry. class 1 has
					 * to wait till vessel status is ACT. class 2 and 3 get
					 * hours from LPI remarks. Step 3: validate LPK class and
					 * timing
					 */
					if (!isKpaClass1(exportContainer)) {

						if (!isKpaClass2(exportContainer)) {

							if (!isKpaClass3(exportContainer)) {

							}
						}
					}
				}
			}

		} else {
			return false;
		}

		return false;
	}

	public boolean isaDGContainer(ExportContainer exportContainer) {

		if (StringUtils.isNotEmpty(exportContainer.getImdg())) {
			setBypassDg(exportContainer);
			findLpkEdiMsg(exportContainer);
			userAccessToByPassDG(exportContainer);
			validateDGContainer(exportContainer);
			return true;
		}
		return false;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void findLpkEdiMsg(ExportContainer container) {

		Optional<String> globalSetting = wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode("LPK_EDI");
		String lpkEdiFlag = globalSetting
				.orElseThrow(() -> new ResultsNotFoundException("Global Setting not available ! LPK_EDI"));
		if (StringUtils.equalsIgnoreCase(lpkEdiFlag, "Y")) {
			lpkediService.findLPKEDITDigiMessage(container);
		}
	}

	private boolean allowDGByPass(ExportContainer exportContainer) {

		if (exportContainer.isBypassDg())
			return true;

		return false;
	}

	private boolean hasAKpaApproval(ExportContainer exportContainer) {

		if (StringUtils.isBlank(exportContainer.getLpkApproval())) {
			/**
			 * No approval from LPK
			 */
			exportContainer.setDgWithinWindowEntry(false);
			throw new BusinessException("DG container " + exportContainer.getContainer().getContainerNumber()
					+ " approval record not found");

		}
		return true;

	}

	private boolean isKpaClass1(ExportContainer exportContainer) {

		if (StringUtils.equals(LpkClassType.LPK_CLASS1.getValue(), exportContainer.getLpkClass())) {
			/**
			 * No approval from LPK
			 */
			exportContainer.setDgWithinWindowEntry(false);
			throw new BusinessException(
					"Class 1 block. Please call supervisor for entry confirmation for container no : "
							+ exportContainer.getContainer().getContainerNumber());

		}
		return false;

	}

	private boolean isKpaClass2(ExportContainer exportContainer) {

		if (StringUtils.equals(LpkClassType.LPK_CLASS2.getValue(), exportContainer.getLpkClass())) {
			int hours = dgHours.get(exportContainer.getHdlGoodsCode()).orElse(72);
			LocalDateTime vesselETADate = exportContainer.getVesselETADate().plusHours(2);
			LocalDateTime allowGateInDate = vesselETADate.minusHours(hours);
			LocalDateTime now = LocalDateTime.now();

			if (now.isBefore(allowGateInDate) || now.isAfter(vesselETADate)) {
				// not with in the slot
				exportContainer.setDgWithinWindowEntry(false);
				throw new BusinessException("Class 2 block. " + exportContainer.getContainer().getContainerNumber()
						+ " not within " + hours + " hours allowed window (" + allowGateInDate.toString() + " - "
						+ vesselETADate.toString() + ")");
			} else {
				exportContainer.setDgMessage("Container " + exportContainer.getContainer().getContainerNumber()
						+ " has arrived within the assigned entry slot");
				return true;
			}

		}
		return false;

	}

	private boolean isKpaClass3(ExportContainer exportContainer) {

		if (StringUtils.equals(LpkClassType.LPK_CLASS3.getValue(), exportContainer.getLpkClass())) {

			LocalDateTime vesselETADate = exportContainer.getVesselETADate().plusHours(2);
			LocalDateTime allowGateInDate = vesselETADate.minusHours(72);
			LocalDateTime now = LocalDateTime.now();

			if (now.isBefore(allowGateInDate) || now.isAfter(vesselETADate)) {
				// not with the slot
				if (exportContainer.isRegisteredInEarlyEntry()) {

					if (!exportContainer.isBypassEEntry()) {
						exportContainer.setDgWithinWindowEntry(false);

						if (earlyEntryService.isContainerHasAOpening(exportContainer)) {
							exportContainer.setDgWithinWindowEntry(true);
						}
					}

				} else {
					/*
					 * did not register as early entry
					 */
					exportContainer.setDgWithinWindowEntry(false);
					throw new BusinessException("DG Container " + exportContainer.getContainer().getContainerNumber()
							+ " does not have opening ");
				}

			} else {
				exportContainer.setDgMessage("Container " + exportContainer.getContainer().getContainerNumber()
						+ " has arrived within the assigned entry slot");
				return true;
			}
		}
		return false;

	}

	private void userAccessToByPassDG(ExportContainer exportContainer) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		boolean access = userContext.getFunctions().contains(ApplicationConstants.GATE_BYPASS_DG_VALIDATION);
		exportContainer.setAccessToByPassDg(access);
	}

	public void validateUserByPassDG(ExportContainer exportContainer) {

		if (isaDGContainer(exportContainer)) {

			if (allowDGToBeValidate()) {

				if (!exportContainer.isDgWithinWindowEntry()) {

					if (!allowDGByPass(exportContainer)) {

						userAccessToByPassDG(exportContainer);

						if (exportContainer.isAccessToByPassDg()) {

							if (exportContainer.isDontValidateDg()) {

								if (StringUtils.isBlank(exportContainer.getDgBypassRemark())) {
									/**
									 * TODO: create err message: remarks cant be
									 * blank
									 */
									throw new BusinessException("DG bypass remarks is empty");
								}

							} else {
								/**
								 * TODO: create err message: need to check the
								 * checkbox.
								 */
								throw new BusinessException("DG bypass checkbox was not checked");
							}

						} else {
							/**
							 * TODO: create err message: User not allowed to
							 * allow gate in
							 */
							throw new BusinessException("User is not allowed to bypass DG validation");
						}
					}
				}
			}
		}
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void setBypassDg(ExportContainer exportContainer) {
		if (!(exportContainer == null || exportContainer.getContainer() == null
				|| exportContainer.getVesselSCN() == null)) {
			if (!(StringUtils.isEmpty(exportContainer.getContainer().getContainerNumber())
					|| StringUtils.isEmpty(exportContainer.getVesselSCN()))) {
				Long count = dgDetailRepository.countByScnAndContainerNo(exportContainer.getVesselSCN(),
						exportContainer.getContainer().getContainerNumber());
				if (!(count == null || count <= 0)) {
					exportContainer.setBypassDg(true);
				}
			}
		}
	}

	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveDGValidationLog(List<ExportContainer> dgContainerList) {

		dgContainerList.forEach(dgContainer -> {

			DGValidateLog dgValidationLog = new DGValidateLog();
			dgValidationLog.setLpkApproval(dgContainer.getLpkApproval());
			dgValidationLog.setLpkClass(LpkClassType.fromValue(dgContainer.getLpkClass()));
			dgValidationLog.setGateInTime(dgContainer.getBaseCommonGateInOutAttribute().getTimeGateIn());
			dgValidationLog.setContNo(dgContainer.getContainer().getContainerNumber());
			dgValidationLog.setGateInDuringWindow(dgContainer.isDgWithinWindowEntry() ? 1 : 0);
			dgValidationLog.setDgByPassRemark(dgContainer.getDgBypassRemark());
			Optional<Exports> optExports = exportsRepository.findOne(dgContainer.getExportID());
			Exports exports = optExports.orElseThrow(() -> new ResultsNotFoundException(
					"Exports Reference cannot be found to save DG validation Log !"));
			dgValidationLog.setExports(exports);
			dgValidationLog.setGateInClerk(exports.getBaseCommonGateInOutAttribute().getGateInClerk());
			dgValidationLogRepository.save(dgValidationLog);

		});
	}

}
