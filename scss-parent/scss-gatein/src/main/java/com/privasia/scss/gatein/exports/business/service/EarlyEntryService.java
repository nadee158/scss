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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
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

	public boolean isContainerHasOpening(ExportContainer container) {

		final LocalDateTime now = LocalDateTime.now();

		// if no record found in the shp_ship_code table
		if (container.getStoragePeriod() == -1) {
			return true;
		}

		long earlyEnrtyDate = 0;
		LocalDateTime vesselETADate = container.getVesselETADate();
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
					/**
					 * check if container coming during early entry window.
					 */
					if (withInEarlyEntryWindow(container)) {
						container.setEarlyEntry(true);
						return true;
					} else {
						//container.setEarlyEntry(true);
						container.setEarlyEntry(false);
						if (container.isBypassEEntry()) {
							return true;
						} else {
							//return false;
							throw new BusinessException("Container "+ container.getContainer().getContainerNumber() +"does not have opening");
						}
					}
				}
			}
		} else {
			// After ETA
			return true;
		}

		//return false;
		
		return true;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean withInEarlyEntryWindow(ExportContainer container) {
		boolean isInWindow = false;

		Optional<WDCGlobalSetting> optWDCGlobalSetting = wdcGlobalSettingRepository.findOne("EE_TIME");

		WDCGlobalSetting wdcGlobalSetting = optWDCGlobalSetting.orElseThrow(
				() -> new ResultsNotFoundException("No Result Found for WDCGlobalSetting Global Code : EE_TIME"));

		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("h:mm a");
		DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		container.setStartEarlyEntry(LocalTime.parse(wdcGlobalSetting.getGlobalString(), timeformatter));
		container.setEndEarlyEntry(LocalTime.parse(wdcGlobalSetting.getParamValue1(), timeformatter));

		final LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().toString(), dateTimeformatter);
		final LocalDate nowDate = LocalDate.parse(now.toLocalDate().toString(), dateformatter);

		LocalDateTime startFullDate = nowDate.atTime(container.getStartEarlyEntry());
		LocalDateTime endFullDate = nowDate.atTime(container.getEndEarlyEntry());

		if (startFullDate.isAfter(endFullDate)) {
			if (now.getHour() < 12) {//StringUtils.contains(dateTime.format(now),"AM")
				startFullDate = startFullDate.minusDays(1);
			} else {
				endFullDate = endFullDate.plusDays(1);
			}
			container.setEarlyEntry(false);
			throw new BusinessException("Container "+ container.getContainer().getContainerNumber() +"does not have opening."
					+ "<br/> Early entry out of window. Early entry at "+timeformatter.format(startFullDate) +" to "+
					timeformatter.format(endFullDate));
		}

		if (now.isAfter(startFullDate) && now.isBefore(endFullDate)) {
			isInWindow = true;
		}
		return isInWindow;

	}

}
