/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.model.WDCGlobalSetting;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;

/**
 * @author Janaka
 *
 */
@Service("earlyEntryService")
public class EarlyEntryService {

	private WDCGlobalSettingRepository wdcGlobalSettingRepository;

	private ShipCodeRepository shipCodeRepository;

	private ShipSCNRepository shipSCNRepository;

	@Autowired
	public void setWdcGlobalSettingRepository(WDCGlobalSettingRepository wdcGlobalSettingRepository) {
		this.wdcGlobalSettingRepository = wdcGlobalSettingRepository;
	}

	@Autowired
	public void setShipCodeRepository(ShipCodeRepository shipCodeRepository) {
		this.shipCodeRepository = shipCodeRepository;
	}

	@Autowired
	public void setShipSCNRepository(ShipSCNRepository shipSCNRepository) {
		this.shipSCNRepository = shipSCNRepository;
	}

	public boolean isContainerHasAOpening(ExportContainer container) {// !c1.isAllowIn()

		final LocalDateTime now = LocalDateTime.now();

		// final LocalDateTime now = LocalDateTime.of(2017, 06, 05, 11, 50, 30);
		// System.out.println("now *************** "+now);

		Optional<Integer> storagePeriod = getStoragePeriod(container);

		if (storagePeriod.isPresent()) {
			long earlyEnrtyDate = 0;
			LocalDateTime vesselETADate = container.getVesselETADate();
			if (vesselETADate == null)
				throw new BusinessException("Container " + container.getContainer().getContainerNumber()
						+ "vessel ETA date not provided for early entry !");
			vesselETADate.minusDays(1);
			earlyEnrtyDate = now.until(vesselETADate, ChronoUnit.DAYS);
			// System.out.println("earlyEnrtyDate "+earlyEnrtyDate);
			// Before Eta Date
			if (earlyEnrtyDate > 1) {
				if (earlyEnrtyDate <= storagePeriod.get()) {
					return true;
				} else {
					/**
					 * Check if it is allowed for early entry
					 */

					if (isRegisteredForEarlyEntry(container)) {
						container.setEarlyEntry(true);

						/**
						 * check if container coming during early entry window.
						 */

						if (container.isBypassEEntry()) {
							return true;
						} else if (withInEarlyEntryWindow(container)) {
							return true;
						} else {
							// return false;

							// here !c1.isAllowIn() && c1.isEarlyEntry() = true
							throw new BusinessException("Container " + container.getContainer().getContainerNumber()
									+ "does not have opening." + System.lineSeparator()
									+ "Early entry out of window. Early entry at "
									+ container.getStartFullEarlyEntryTime() + " to "
									+ container.getEndFullEarlyEntryTime());
						}

					} else {
						// here !c1.isAllowIn() && c1.isEarlyEntry() = false
						// should return false
						container.setEarlyEntry(false);
						throw new BusinessException(
								"Container " + container.getContainer().getContainerNumber() + "does not have opening");
					}
				}
			} else {
				// After ETA
				return true;
			}
		} else {
			// if no record found in the shp_ship_code table
			return true;
		}

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean withInEarlyEntryWindow(ExportContainer container) {
		boolean isInWindow = false;

		Optional<WDCGlobalSetting> optWDCGlobalSetting = wdcGlobalSettingRepository.findOne("EE_TIME");

		WDCGlobalSetting wdcGlobalSetting = optWDCGlobalSetting.orElseThrow(
				() -> new ResultsNotFoundException("No Result Found for WDCGlobalSetting Global Code : EE_TIME"));

		DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("h:mm a");

		container.setStartEarlyEntry(LocalTime.parse(wdcGlobalSetting.getGlobalString(), timeformatter));
		container.setEndEarlyEntry(LocalTime.parse(wdcGlobalSetting.getParamValue1(), timeformatter));

		LocalDateTime now = LocalDateTime.now();
		LocalDate nowDate = now.toLocalDate();

		LocalDateTime startFullDate = nowDate.atTime(container.getStartEarlyEntry());
		LocalDateTime endFullDate = nowDate.atTime(container.getEndEarlyEntry());

		if (startFullDate.isAfter(endFullDate)) {
			if (now.getHour() < 12) {// StringUtils.contains(dateTime.format(now),"AM")
				startFullDate = startFullDate.minusDays(1);
			} else {
				endFullDate = endFullDate.plusDays(1);
			}
		}

		if (now.isAfter(startFullDate) && now.isBefore(endFullDate)) {
			isInWindow = true;
		}

		container.setStartFullEarlyEntryTime(startFullDate.toLocalTime().format(timeformatter));
		container.setEndFullEarlyEntryTime(endFullDate.toLocalTime().format(timeformatter));
		return isInWindow;

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<Integer> getStoragePeriod(ExportContainer exportContainer) {
		if (exportContainer != null) {
			String shipCodeStr = exportContainer.getShippingLine();
			Optional<ShipCode> optionalShipCode = shipCodeRepository.findByShipStatusAndShippingCode(ShipStatus.ACTIVE,
					shipCodeStr);
			if (optionalShipCode.isPresent()) {
				ShipCode shipCode = optionalShipCode.get();
				return Optional.of(shipCode.getStoragePeriod());
			}
		}
		return Optional.ofNullable(null);

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean isRegisteredForEarlyEntry(ExportContainer exportContainer) {

		String exportContainerNumber = null;
		String vesselSCN = null; // vessel scn and ship scn are same

		if (exportContainer != null) {

			exportContainerNumber = exportContainer.getContainer().getContainerNumber();
			vesselSCN = exportContainer.getVesselSCN();

			Optional<ShipSCN> optionalshipSCN = shipSCNRepository.fetchContainerSCN(vesselSCN, exportContainerNumber);

			if (optionalshipSCN.isPresent()) {
				ShipSCN shipSCN = optionalshipSCN.get();
				exportContainer.setShipSCNID(Optional.of(shipSCN.getShipSCNID()));
				exportContainer.setBypassEEntry(shipSCN.getScnByPass());
				exportContainer.setRegisteredInEarlyEntry(true);
				return true;
			} else {
				exportContainer.setRegisteredInEarlyEntry(false);
				return false;
			}

		}

		return false;

	}

}
