package com.privasia.scss.gateout.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.GatePassValidateDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;

@Service("importGateOutService")
public class ImportGateOutService {

	private GatePassRepository gatePassRepository;

	private ModelMapper modelMapper;

	private ClientRepository clientRepository;

	private WDCGatePassRepository wdcGatePassRepository;

	@Autowired
	public void setGatePassRepository(GatePassRepository gatePassRepository) {
		this.gatePassRepository = gatePassRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}
	
	@Autowired
	public void setWdcGatePassRepository(WDCGatePassRepository wdcGatePassRepository) {
		this.wdcGatePassRepository = wdcGatePassRepository;
	}

	public List<ImportContainer> populateGateOut(GateOutRequest gateOutRequest) {

		List<Long> gatePassNumberList = new ArrayList<Long>();
		if (gateOutRequest.getGatePass1() != null && gateOutRequest.getGatePass1() != 0) {
			gatePassNumberList.add(gateOutRequest.getGatePass1());
		}
		if (gateOutRequest.getGatePass2() != null && gateOutRequest.getGatePass2() != 0) {
			gatePassNumberList.add(gateOutRequest.getGatePass2());
		}
		List<ImportContainer> importContainers = fetchContainerInfo(gatePassNumberList);

		importContainers.forEach(importContainer -> {
			GatePassValidateDTO gatePassValidateDTO = validateGatePass(gateOutRequest.getCardID(),
					gateOutRequest.getGatePass1(), gateOutRequest.isCheckPreArrival(), gateOutRequest.getHpabSeqId(),
					gateOutRequest.getTruckHeadNo());
			if (gatePassValidateDTO.isValidGatePass()) {
				if (importContainer.getGatePassNo() == gateOutRequest.getGatePass1()) {
					gateOutRequest.setImpContainer1(importContainer.getContainer().getContainerNumber());
				} else {
					gateOutRequest.setImpContainer2(importContainer.getContainer().getContainerNumber());
				}
			} else {
				throw new ResultsNotFoundException(
						gatePassValidateDTO.getGatePassErrorMessage() + gateOutRequest.getGatePass1());
			}
		});

		Optional<Client> clientOpt = clientRepository.findOne(gateOutRequest.getLaneId());
		Client client = clientOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutRequest.getLaneId()));
		gateOutRequest.setLaneNo(client.getLaneNo());

		return importContainers;

	}

	public GatePassValidateDTO validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival,
			String hpatSeqId, String truckHeadNo) {

		return new GatePassValidateDTO();
	}
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public List<ImportContainer> fetchContainerInfo(List<Long> gatePassNumberList) {
		
		
		Optional<List<WDCGatePass>> optionalWdcGatePassList =  wdcGatePassRepository.findByGatePassNOIn(gatePassNumberList);
		
		List<WDCGatePass> wdcGatePassList = optionalWdcGatePassList.orElseThrow(() -> new ResultsNotFoundException(
				"GCS Declaration could be found for the given Gate Pass Numbers! "+String.join(",", gatePassNumberList.stream().map(Object::toString)
                        .collect(Collectors.toList()))));
		
		wdcGatePassList.forEach(wdcGatePass ->{
			System.out.println("getGateOrder" + wdcGatePass.getGateOrder());
			System.out.println("getLineCode" + wdcGatePass.getGateOrder().getLineCode());
		});

		/*Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatePassNumberList);

		List<GatePass> gatePassList = optionalGatePassList.orElseThrow(() -> new ResultsNotFoundException(
				"No Import Containers could be found for the given Gate Pass Numbers!"));
		List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
		gatePassList.forEach(item -> {
			ImportContainer importContainer = new ImportContainer();
			modelMapper.map(item, importContainer);

			System.out.println("item " + item);
			System.out.println("importContainer " + importContainer);

			importContainers.add(importContainer);
		});*/
		return null;

	}

	public List<ImportContainer> saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest) {
		//

		return null;
	}
	
	public static void main(String args[]){
		ImportGateOutService importGateOutService = new ImportGateOutService();
		List<Long> gatePassNumberList = new ArrayList<>();
		gatePassNumberList.add(20000070011l);
		gatePassNumberList.add(20000140011l);
		importGateOutService.fetchContainerInfo(gatePassNumberList);
	}	

}
