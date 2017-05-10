/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.core.model.ISOCode;
import com.privasia.scss.gatein.service.IsoCodeService;

/**
 * @author Janaka
 *
 */
@Service("emptyContainerService")
public class EmptyContainerService {

	@Autowired
	private IsoCodeService isoCodeService;

	@Autowired
	public void setIsoCodeService(IsoCodeService isoCodeService) {
		this.isoCodeService = isoCodeService;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void setEmptyContainerWeight(ExportContainer emptyContainer, String iso) {
		
		if (StringUtils.isEmpty(iso))
			throw new BusinessException("ISO Code mandatory. Please enter the code");

		if (StringUtils.isNotEmpty(emptyContainer.getContainer().getContainerISOCode())) {
			ISOCode isoCode = isoCodeService.getIsoCodeTarWeight(iso);

			emptyContainer.setExpNetWeight(isoCode.getTareWeight());
			emptyContainer.setEmptyWeight(isoCode.getTareWeight());
		}

	}

	/**
	 * Calculate the WeightBridge For Empty ExportContainer
	 */
	public void calculateWeightBridgeForEmpty(List<ExportContainer> exports, int weightBridge) {

		List<ExportContainer> emptyContainerList = exports.stream()
				.filter(exportContainer -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.EMPTY.getValue(),
						exportContainer.getContainer().getContainerFullOrEmpty()))
				.collect(Collectors.toList());

		if (emptyContainerList != null && !emptyContainerList.isEmpty()) {
			// 1empty 0r both

			if (emptyContainerList.size() == 1) {
				// one empty and can have one full

				ExportContainer fullContainer = exports.stream()
						.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
								expCon.getContainer().getContainerFullOrEmpty()))
						.findFirst().orElse(null);

				calculateWeightBridge(emptyContainerList.get(0), Optional.ofNullable(fullContainer), weightBridge);

			} else { // all empty

				ExportContainer emptyContainer02 = exports.stream()
						.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.EMPTY.getValue(),
								expCon.getContainer().getContainerFullOrEmpty()))
						.findFirst().orElse(null);

				calculateWeightBridge(emptyContainerList.get(0), Optional.ofNullable(emptyContainer02), weightBridge);
			}
		}

	}

	private void calculateWeightBridge(ExportContainer emptyContainer, Optional<ExportContainer> fullEmptyContainer,
			int weightBridge) {

		if (fullEmptyContainer.isPresent()) {

			if (StringUtils.equalsIgnoreCase(ContainerFullEmptyType.EMPTY.getValue(),
					fullEmptyContainer.get().getContainer().getContainerFullOrEmpty())) { // both
																							// empty

				Double totalWeightBridge = new Double(emptyContainer.getEmptyWeight())
						+ new Double(fullEmptyContainer.get().getEmptyWeight());
				emptyContainer.setExpWeightBridge(totalWeightBridge.intValue());
				fullEmptyContainer.get().setExpWeightBridge(totalWeightBridge.intValue());

			} else { // optContainer is a FUll
				emptyContainer.setExpWeightBridge(emptyContainer.getEmptyWeight());
				fullEmptyContainer.get().setExpWeightBridge(weightBridge - emptyContainer.getEmptyWeight());
			}

		} else {// single empty
			emptyContainer.setExpWeightBridge(emptyContainer.getEmptyWeight());
		}

	}

}
