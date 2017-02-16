package com.privasia.scss.gatein.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GatePassValidateDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.repository.GatePassRepository;

@Service("importGateInService")
public class ImportGateInService {

	private GatePassRepository gatePassRepository;

	private ModelMapper modelMapper;

	@Autowired
	public void setGatePassRepository(GatePassRepository gatePassRepository) {
		this.gatePassRepository = gatePassRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateInRequest findContainerNoByGatePass(GateInRequest gateInRequest) {
		
		if (gateInRequest.getGatePass1() != null && gateInRequest.getGatePass1() != 0) {
			String impContainer01 = gatePassRepository.findContainerNoByGatePassNo(gateInRequest.getGatePass1());
			gateInRequest.setImpContainer1(impContainer01);
		}
		if (gateInRequest.getGatePass2() != null && gateInRequest.getGatePass2() != 0) {
			String impContainer02 = gatePassRepository.findContainerNoByGatePassNo(gateInRequest.getGatePass2());
			gateInRequest.setImpContainer2(impContainer02);
		}
		return gateInRequest;

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateInReponse populateGateInImport(GateInReponse gateInReponse) {

		gateInReponse.getImportContainers().forEach(importContainer -> {
			GatePassValidateDTO gatePassValidateDTO = validateGatePass(importContainer.getBaseCommonGateInOutAttribute().getCard(),
					importContainer.getGatePassNo(), gateInReponse.isCheckPreArrival(), importContainer.getBaseCommonGateInOutAttribute().getHpatBooking(),
					gateInReponse.getTruckHeadNo());
			if (!gatePassValidateDTO.isValidGatePass()) {
				throw new ResultsNotFoundException(
						gatePassValidateDTO.getGatePassErrorMessage() + importContainer.getGatePassNo());
			}
		});

		return gateInReponse;

	}

	public GatePassValidateDTO validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival,
			String hpatSeqId, String truckHeadNo) {

		return new GatePassValidateDTO();
	}

	public List<ImportContainer> fetchContainerInfo(List<Long> gatePassNumberList) {

		Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatePassNumberList);

		List<GatePass> gatePassList = optionalGatePassList.orElseThrow(() -> new ResultsNotFoundException(
				"No Import Containers could be found for the given Gate Pass Numbers!"));
		List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
		gatePassList.forEach(item -> {
			ImportContainer importContainer = new ImportContainer();
			modelMapper.map(item, importContainer);

			System.out.println("item " + item);
			System.out.println("importContainer " + importContainer);

			importContainers.add(importContainer);
		});
		return importContainers;

	}

	public List<ImportContainer> saveGateInInfo(GateInWriteRequest gateInWriteRequest) {
		//

		return null;
	}

}
