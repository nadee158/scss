package com.privasia.scss.gatein.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInOutODDDTO;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.repository.WHODDRepository;
import com.privasia.scss.core.security.model.UserContext;

@Service("oddService")
public class ODDService {

	private ModelMapper modelMapper;

	private WHODDRepository whoddRepository;

	private CardRepository cardRepository;

	private ClientRepository clientRepository;

	private SystemUserRepository systemUserRepository;
	

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setWhoddRepository(WHODDRepository whoddRepository) {
		this.whoddRepository = whoddRepository;
	}
	
	@Autowired
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}
	
	@Autowired
	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}
	
	@Autowired
	public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
		this.systemUserRepository = systemUserRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public Long saveODDGateInFo(GateInWriteRequest gateInWriteRequest) {

		gateInWriteRequest.getWhoddContainers().forEach(whODD -> {
			
			if(gateInWriteRequest.isOddReject()){
				whODD.setGateInStatus(TransactionStatus.REJECT.getValue());
			}else{
				whODD.setGateInStatus(TransactionStatus.APPROVED.getValue());
			}
			
			WHODD whodd = modelMapper.map(whODD, WHODD.class);
			
			whoddRepository.save(whodd);
		});

		/*
		 * if (!(gateInOutODDDTO == null || gateInOutODDDTO.getWhoddds() == null
		 * || gateInOutODDDTO.getWhoddds().isEmpty())) {
		 * gateInOutODDDTO.getWhoddds().forEach(item -> { WHODD whodd = new
		 * WHODD(); modelMapper.map(gateInOutODDDTO, whodd);
		 * System.out.println("Initial_MAPPING_gateInOutODDDTO" + whodd);
		 * modelMapper.map(item, whodd);
		 * System.out.println("Secod_MAPPING_gateInOutODDDTO" + whodd); if
		 * (whodd.getGateInStatus() == null) {
		 * whodd.setGateInStatus(TransactionStatus.APPROVED); } if
		 * (!(whodd.getContainer01() == null)) { if
		 * (StringUtils.isNotEmpty(whodd.getContainer01().getLocation()) ||
		 * StringUtils.isNotEmpty(whodd.getContainer01().getRejectionReason()))
		 * { whodd.getContainer01().setOddStatus(TransactionStatus.INPROGRESS);
		 * } } if (!(whodd.getContainer02() == null)) { if
		 * (StringUtils.isNotEmpty(whodd.getContainer02().getLocation()) ||
		 * StringUtils.isNotEmpty(whodd.getContainer02().getRejectionReason()))
		 * { whodd.getContainer02().setOddStatus(TransactionStatus.INPROGRESS);
		 * } } whodd = whoddRepository.save(whodd); }); } else { throw new
		 * BusinessException("No object to be saved!"); }
		 */

		return null;
	}

}
