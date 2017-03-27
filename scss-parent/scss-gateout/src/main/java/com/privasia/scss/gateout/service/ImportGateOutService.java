package com.privasia.scss.gateout.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CancelPickUpDTO;
import com.privasia.scss.common.dto.ConfirmedKioskDTO;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.GatePassValidateDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.UpdateSealDTO;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.CommonSealAttribute;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;
import com.privasia.scss.core.security.util.SecurityHelper;

@Service("importGateOutService")
public class ImportGateOutService {

	private GatePassRepository gatePassRepository;

	private ModelMapper modelMapper;

	private ClientRepository clientRepository;

	private WDCGatePassRepository wdcGatePassRepository;
	
	private SystemUserRepository systemUserRepository;

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
	
	@Autowired
	public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
		this.systemUserRepository = systemUserRepository;
	}

	public List<ImportContainer> populateGateOut(GateOutRequest gateOutRequest) {

		List<Long> gatePassNumberList = new ArrayList<Long>();
		if (gateOutRequest.getGatePass1() != null && gateOutRequest.getGatePass1() != 0) {
			gatePassNumberList.add(gateOutRequest.getGatePass1());
		}
		if (gateOutRequest.getGatePass2() != null && gateOutRequest.getGatePass2() != 0) {
			gatePassNumberList.add(gateOutRequest.getGatePass2());
		}
		/*List<ImportContainer> importContainers = fetchContainerInfo(gatePassNumberList);

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
		});*/

		Optional<Client> clientOpt = clientRepository.findOne(gateOutRequest.getLaneId());
		Client client = clientOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutRequest.getLaneId()));
		gateOutRequest.setLaneNo(client.getLaneNo());

		return null;

	}

	public GatePassValidateDTO validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival,
			String hpatSeqId, String truckHeadNo) {

		return new GatePassValidateDTO();
	}
	
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<ImportContainer> getGCSDeclarationInfo(List<ImportContainer> importList, List<Long> gatePassNumberList) {
		
		
		Optional<List<WDCGatePass>> optionalWdcGatePassList =  wdcGatePassRepository.findByGatePassNOIn(gatePassNumberList);
		
		List<WDCGatePass> wdcGatePassList = optionalWdcGatePassList.orElseThrow(() -> new ResultsNotFoundException(
				"GCS Declaration could be found for the given Gate Pass Numbers! "+String.join(",", gatePassNumberList.stream().map(Object::toString)
                        .collect(Collectors.toList()))));
		
		wdcGatePassList.forEach(wdcGatePass ->{
			System.out.println("getGateOrder" + wdcGatePass.getGateOrder());
			System.out.println("getLineCode" + wdcGatePass.getGateOrder().getLineCode());
			System.out.println("GcsDelcarerNo" + wdcGatePass.getGcsDelcarerNo());
			
			importList.forEach(container ->{
				System.out.println("getGateOrder" + wdcGatePass.getGateOrder());
				System.out.println("getLineCode" + wdcGatePass.getGateOrder().getLineCode());
				System.out.println("GcsDelcarerNo" + wdcGatePass.getGcsDelcarerNo());
				
				
				
			});
			
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
		return importList;

	}
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public String cancelPickUp(CancelPickUpDTO cancelPickUpDTO){
		
		List<Long> gatepassNumList = Arrays.asList(cancelPickUpDTO.getGatePass01(), cancelPickUpDTO.getGatePass02());
		Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatepassNumList);
		
		List<GatePass> gatePassList = optionalGatePassList.orElseThrow(() -> new ResultsNotFoundException(
				"Invalid Gate pass numbers ! "+String.join(",", gatepassNumList.stream().map(Object::toString)
                        .collect(Collectors.toList()))));
		
		gatePassList.forEach(gatePass->{
			gatePass.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.REJECT);
			if(gatePass.getGatePassNo() == cancelPickUpDTO.getGatePass01()){
				gatePass.getCommonGateInOut().setRejectReason(cancelPickUpDTO.getRemarks01());
			}else{
				gatePass.getCommonGateInOut().setRejectReason(cancelPickUpDTO.getRemarks02());
			}
			gatePass.getCommonGateInOut().setKioskCancelPickUp(true);
			gatePassRepository.save(gatePass);
		});
		
		return "success";
		
	}
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public String updateSeal(UpdateSealDTO updateSealDTO){
		
		List<Long> gatepassNumList = Arrays.asList(updateSealDTO.getGatePass01(), updateSealDTO.getGatePass02());
		Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatepassNumList);
		
		List<GatePass> gatePassList = optionalGatePassList.orElseThrow(() -> new ResultsNotFoundException(
				"Invalid Gate pass numbers ! "+String.join(",", gatepassNumList.stream().map(Object::toString)
                        .collect(Collectors.toList()))));
		
		gatePassList.forEach(gatePass->{
			if(gatePass.getSealAttribute()== null){
				gatePass.setSealAttribute(new CommonSealAttribute());
			}
			if(gatePass.getGatePassNo() == updateSealDTO.getGatePass01()){
				gatePass.getSealAttribute().setSeal01Number(updateSealDTO.getGatePass01Seal01().trim());
				gatePass.getSealAttribute().setSeal02Number(updateSealDTO.getGatePass01Seal02().trim());
			}else{
				gatePass.getSealAttribute().setSeal01Number(updateSealDTO.getGatePass02Seal01().trim());
				gatePass.getSealAttribute().setSeal02Number(updateSealDTO.getGatePass02Seal02().trim());
			}
			gatePass.setForcedSeal(updateSealDTO.isForceSealUpdate());
			gatePassRepository.save(gatePass);
		});
		
		return "success";
		
	}
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public String confirmedKioskTransaction(ConfirmedKioskDTO confirmedKioskDTO){
		
		List<Long> gatepassNumList = Arrays.asList(confirmedKioskDTO.getGatePass01(), confirmedKioskDTO.getGatePass02());
		Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatepassNumList);
		
		List<GatePass> gatePassList = optionalGatePassList.orElseThrow(() -> new ResultsNotFoundException(
				"Invalid Gate pass numbers ! "+String.join(",", gatepassNumList.stream().map(Object::toString)
                        .collect(Collectors.toList()))));
		
		Optional<Client> clientOpt = clientRepository.findOne(confirmedKioskDTO.getKioskID());
		Client client = clientOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid Kiosk ID ! " + confirmedKioskDTO.getKioskID()));
		SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
		          () -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));
		
		gatePassList.forEach(gatePass->{
			gatePass.getBaseCommonGateInOutAttribute().setGateOutClerk(systemUser);
			gatePass.getBaseCommonGateInOutAttribute().setGateOutClient(client);
			gatePass.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());
			if(gatePass.getGatePassNo() == confirmedKioskDTO.getGatePass01()){
				gatePass.getCommonGateInOut().setKioskConfirmed(confirmedKioskDTO.isStatus01());
			}else{
				gatePass.getCommonGateInOut().setKioskConfirmed(confirmedKioskDTO.isStatus02());
			}
			gatePassRepository.save(gatePass);
		});
		
		return "success";
		
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
		//importGateOutService.fetchContainerInfo(gatePassNumberList);
	}	

}
