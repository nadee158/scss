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
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.WDCGlobalSetting;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;

/**
 * @author Janaka
 *
 */
@Service("earlyEntryService")
public class EarlyEntryService {

	private WDCGlobalSettingRepository wdcGlobalSettingRepository;

	@Autowired
	public void setWdcGlobalSettingRepository(WDCGlobalSettingRepository wdcGlobalSettingRepository) {
		this.wdcGlobalSettingRepository = wdcGlobalSettingRepository;
	}

	public boolean isContainerHasOpening(ExportContainer container) {// !c1.isAllowIn()

		final LocalDateTime now = LocalDateTime.now();

		// if no record found in the shp_ship_code table
		if (container.getStoragePeriod() == -1) {
			return true;
		}

		long earlyEnrtyDate = 0;
		LocalDateTime vesselETADate = container.getVesselETADate();
		if(vesselETADate==null)
			throw new BusinessException("Container " + container.getContainer().getContainerNumber() + "vessel ETA date not provided for early entry !");
		vesselETADate.minusDays(1);
		earlyEnrtyDate = vesselETADate.until(now, ChronoUnit.DAYS);

		// Before Eta Date
		if (earlyEnrtyDate > 1) {
			if (earlyEnrtyDate <= container.getStoragePeriod()) {
				return true;
			} else {
				/**
				 * Check if it is allowed for early entry
				 */
				if (container.getShipSCNID().isPresent()) {
					container.setEarlyEntry(true);

					/**
					 * check if container coming during early entry window.
					 */

					if (withInEarlyEntryWindow(container)) {
						return true;
					} else {
						if (container.isBypassEEntry()) {
							return true;
						} else {
							// return false;

							// here !c1.isAllowIn() && c1.isEarlyEntry() = true
							throw new BusinessException("Container " + container.getContainer().getContainerNumber()
									+ "does not have opening." + System.lineSeparator() +"Early entry out of window. Early entry at "
									+ container.getStartFullEarlyEntryTime() + " to " + container.getEndFullEarlyEntryTime());
						}
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
	
	

}
