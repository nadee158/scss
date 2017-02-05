package com.privasia.scss.gatein.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.repository.GatePassRepository;

@Service("importGateInService")
public class ImportGateInService {
	
	private GatePassRepository gatePassRepository;
	
	@Autowired
	public void setGatePassRepository(GatePassRepository gatePassRepository) {
		this.gatePassRepository = gatePassRepository;
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly = true)
	public void populateGateIn(){
		
		//fetchContainerInfo();
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly = true)
	public boolean validateGatePass(Long cardIdSeq, Long gatePassNo, String checkPreArrival, String hpatSeqId, String truckHeadNo){
		
		return true;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly = true)
	public List<ImportContainer> fetchContainerInfo(List<Long> gatePassNumberList){
		
		Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatePassNumberList);
		
		return null;
		
	}
	

}
