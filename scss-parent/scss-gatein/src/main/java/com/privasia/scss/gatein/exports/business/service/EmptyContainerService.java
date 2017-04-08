/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.core.exception.BusinessException;
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
	public GateInWriteRequest refreshEmptyWeightByISO(GateInWriteRequest gateInWriteRequest) {
		
		gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
			if (StringUtils.equalsIgnoreCase(ContainerFullEmptyType.EMPTY.getValue(),
					exportContainer.getContainer().getContainerFullOrEmpty())) {

				if (exportContainer.getContainer().getContainerISOCode() == null)
					throw new BusinessException(
							"ISO Code not provided for the container : " + exportContainer.getContainer());
				ISOCode isoCodeDto = isoCodeService
						.getIsoCodeTarWeight(exportContainer.getContainer().getContainerISOCode());

				exportContainer.setExpNetWeight(isoCodeDto.getTareWeight());
				exportContainer.setEmptyWeight(isoCodeDto.getTareWeight());
				int emptyTotalWeightBridge = gateInWriteRequest.getTotalEmptyWeightBridge();
				emptyTotalWeightBridge =+ isoCodeDto.getTareWeight();
				gateInWriteRequest.setTotalEmptyWeightBridge(emptyTotalWeightBridge);
			}
		});

		return gateInWriteRequest;

	}

	/**
	 * Calculate the WeightBridge For Empty ExportContainer
	 */
	public void calculateWeightBridgeAndNetWeightForEmptyContainer(GateInWriteRequest gateInWriteRequest) {

		if (checkForEmptyContainer(gateInWriteRequest)) {
			gateInWriteRequest = refreshEmptyWeightByISO(gateInWriteRequest);

			ExportContainer fullContainer = gateInWriteRequest.getExportContainers().stream()
					.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
							expCon.getContainer().getContainerFullOrEmpty()))
					.findFirst().get();

			if (fullContainer != null) { // one empty and and full
				ExportContainer emptyContainer = gateInWriteRequest.getExportContainers().stream()
						.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.EMPTY.getValue(),
								expCon.getContainer().getContainerFullOrEmpty())).findFirst().get();
				emptyContainer.setExpWeightBridge(emptyContainer.getEmptyWeight());
				fullContainer.setExpWeightBridge(gateInWriteRequest.getWeightBridge() - emptyContainer.getEmptyWeight());
			} else { // containers are empty
				
				final int totalEmptyWeightBridge = gateInWriteRequest.getTotalEmptyWeightBridge();
				gateInWriteRequest.getExportContainers().stream()
					.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.EMPTY.getValue(),
							expCon.getContainer().getContainerFullOrEmpty())).forEach(emptyContainer ->{
								emptyContainer.setExpWeightBridge(totalEmptyWeightBridge);
				});
				
			}
		}

	}

	public boolean checkForEmptyContainer(GateInWriteRequest gateInWriteRequest) {

		return gateInWriteRequest.getExportContainers().stream()
				.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.EMPTY.getValue(),
						expCon.getContainer().getContainerFullOrEmpty()))
				.findFirst().isPresent();

	}

}
