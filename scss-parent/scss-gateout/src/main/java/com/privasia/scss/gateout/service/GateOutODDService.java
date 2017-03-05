package com.privasia.scss.gateout.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ContainerValidationInfo;
import com.privasia.scss.common.dto.WHODDDTO;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.ODDLocation;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.repository.ODDLocationRepository;
import com.privasia.scss.core.repository.ODDRepository;
import com.privasia.scss.cosmos.repository.CosmosODDRepository;

@Service("gateOutODDService")
public class GateOutODDService {

	private CosmosODDRepository cosmosODDRepository;
	
	private ODDRepository oddRepository;
	
	private ODDLocationRepository oddLocationRepository;
	
	private ModelMapper modelMapper;

	@Autowired
	public void setCosmosODDRepository(CosmosODDRepository cosmosODDRepository) {
		this.cosmosODDRepository = cosmosODDRepository;
	}
	
	@Autowired
	public void setOddRepository(ODDRepository oddRepository) {
		this.oddRepository = oddRepository;
	}
	
	@Autowired
	public void setOddLocationRepository(ODDLocationRepository oddLocationRepository) {
		this.oddLocationRepository = oddLocationRepository;
	}
	
	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, value="as400TransactionManager")
	public ContainerValidationInfo validateODDContainers(ContainerValidationInfo containerValidationInfo) {

		containerValidationInfo.setContainerNo1Status(
				cosmosODDRepository.validateODDContainer(containerValidationInfo.getContainerNo1()));

		if (StringUtils.isNotEmpty(containerValidationInfo.getContainerNo2())) {
			containerValidationInfo.setContainerNo2Status(
					cosmosODDRepository.validateODDContainer(containerValidationInfo.getContainerNo2()));
		}

		return containerValidationInfo;
	}
	
	
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<WHODD> fetchInProgressTransaction(Long cardID) {
		
		Optional<List<WHODD>> optionalODDList = oddRepository.findByCardIDAndEirStatus(cardID, TransactionStatus.INPROGRESS);
		
		if(optionalODDList.isPresent()){
			List<WHODD> transactionList = optionalODDList.get();
			transactionList.forEach(whODD ->{
				WHODDDTO oddDTO = modelMapper.map(whODD, WHODDDTO.class);
				if(oddDTO.getContainer01() != null){
					Optional<ODDLocation> optionalLocation = oddLocationRepository.findOne(oddDTO.getContainer01().getLocation());
					if(optionalLocation.isPresent()){
						oddDTO.getContainer01().setLocation(optionalLocation.get().getOddDesc());
					}
				}
				
				if(oddDTO.getContainer02() != null){
					Optional<ODDLocation> optionalLocation = oddLocationRepository.findOne(oddDTO.getContainer02().getLocation());
					if(optionalLocation.isPresent()){
						oddDTO.getContainer02().setLocation(optionalLocation.get().getOddDesc());
					}
				}
				
			});
			
		}
		
		return  optionalODDList.orElse(null);

	}

}
