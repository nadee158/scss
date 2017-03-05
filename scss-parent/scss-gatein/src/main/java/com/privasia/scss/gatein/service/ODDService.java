package com.privasia.scss.gatein.service;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInOutODDDTO;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.repository.WHODDRepository;

@Service("oddService")
public class ODDService {

  private ModelMapper modelMapper;

  private WHODDRepository whoddRepository;

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setWhoddRepository(WHODDRepository whoddRepository) {
    this.whoddRepository = whoddRepository;
  }


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public Long savempWHODDDB(GateInOutODDDTO gateInOutODDDTO) {

    if (!(gateInOutODDDTO == null || gateInOutODDDTO.getWhoddds() == null || gateInOutODDDTO.getWhoddds().isEmpty())) {
      gateInOutODDDTO.getWhoddds().forEach(item -> {
        WHODD whodd = new WHODD();
        modelMapper.map(gateInOutODDDTO, whodd);
        System.out.println("Initial_MAPPING_gateInOutODDDTO" + whodd);
        modelMapper.map(item, whodd);
        System.out.println("Secod_MAPPING_gateInOutODDDTO" + whodd);
        if (whodd.getGateInStatus() == null) {
          whodd.setGateInStatus(TransactionStatus.APPROVED);
        }
        if (!(whodd.getContainer01() == null)) {
          if (StringUtils.isNotEmpty(whodd.getContainer01().getLocation())
              || StringUtils.isNotEmpty(whodd.getContainer01().getRejectionReason())) {
            whodd.getContainer01().setOddStatus(TransactionStatus.INPROGRESS);
          }
        }
        if (!(whodd.getContainer02() == null)) {
          if (StringUtils.isNotEmpty(whodd.getContainer02().getLocation())
              || StringUtils.isNotEmpty(whodd.getContainer02().getRejectionReason())) {
            whodd.getContainer02().setOddStatus(TransactionStatus.INPROGRESS);
          }
        }
        whodd = whoddRepository.save(whodd);
      });
    } else {
      throw new BusinessException("No object to be saved!");
    }


    return null;
  }

}
