package com.privasia.scss.gateout.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ContainerValidationInfo;
import com.privasia.scss.cosmos.repository.CosmosODDRepository;

@Service("gateOutODDService")
public class GateOutODDService {

	private CosmosODDRepository cosmosODDRepository;

	@Autowired
	public void setCosmosODDRepository(CosmosODDRepository cosmosODDRepository) {
		this.cosmosODDRepository = cosmosODDRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ContainerValidationInfo validateODDContainers(ContainerValidationInfo containerValidationInfo) {

		containerValidationInfo.setContainerNo1Status(
				cosmosODDRepository.validateODDContainer(containerValidationInfo.getContainerNo1()));

		if (StringUtils.isNotEmpty(containerValidationInfo.getContainerNo2())) {
			containerValidationInfo.setContainerNo2Status(
					cosmosODDRepository.validateODDContainer(containerValidationInfo.getContainerNo2()));
		}

		return containerValidationInfo;
	}

}
