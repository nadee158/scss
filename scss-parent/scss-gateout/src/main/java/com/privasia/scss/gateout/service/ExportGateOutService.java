package com.privasia.scss.gateout.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ShipSCNDTO;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ExportsQ;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.ExportsQRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;

@Service("exportGateOutService")
public class ExportGateOutService {

  private ModelMapper modelMapper;

  private ClientRepository clientRepository;

  private ShipCodeRepository shipCodeRepository;

  private ShipSCNRepository shipSCNRepository;

  private ExportsRepository exportsRepository;

  private ExportsQRepository exportsQRepository;

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setShipCodeRepository(ShipCodeRepository shipCodeRepository) {
    this.shipCodeRepository = shipCodeRepository;
  }

  @Autowired
  public void setShipSCNRepository(ShipSCNRepository shipSCNRepository) {
    this.shipSCNRepository = shipSCNRepository;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setExportsRepository(ExportsRepository exportsRepository) {
    this.exportsRepository = exportsRepository;
  }

  @Autowired
  public void setExportsQRepository(ExportsQRepository exportsQRepository) {
    this.exportsQRepository = exportsQRepository;
  }
  
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<ExportContainer> populateGateOut(GateOutRequest gateOutRequest) {

    Optional<List<Exports>> optExpList = exportsRepository.fetchInProgressTransaction(gateOutRequest.getCardID(), TransactionStatus.INPROGRESS);
    List<Exports> inprogressExpList =
    		optExpList.orElseThrow(() -> new BusinessException("No InProgress Export Transaction for the scan card ! " + gateOutRequest.getCardID()));
    List<ExportContainer> exportContainerList = new ArrayList<ExportContainer>();
    inprogressExpList.forEach(export->{
    	ExportContainer exportContainer = new ExportContainer();
    	modelMapper.map(export, exportContainer);
    	//adding log info
    	exportContainerList.add(exportContainer);
    	if(StringUtils.isEmpty(gateOutRequest.getExpContainer1())){
    		gateOutRequest.setExpContainer1(export.getContainer().getContainerNumber());
    	}else{
    		gateOutRequest.setExpContainer2(export.getContainer().getContainerNumber());
    	}
    	gateOutRequest.setTruckHeadNo(export.getBaseCommonGateInOutAttribute().getPmHeadNo());
    });

    return exportContainerList;

  }

  

  public List<ShipCode> checkContainer(List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      List<String> shippingCodes =
          exportContainers.stream().map(ExportContainer::getShipCode).collect(Collectors.toList());

      Optional<List<ShipCode>> list =
          shipCodeRepository.findByShipStatusAndShippingCodeIn(ShipStatus.ACTIVE, shippingCodes);
      if (list.isPresent()) {
        List<ShipCode> codes = list.orElse(null);
        for (ShipCode shipCode : codes) {
          for (ExportContainer item : exportContainers) {
            if (StringUtils.equals(shipCode.getShippingCode(), item.getShipCode())) {
              item.setStoragePeriod(shipCode.getStoragePeriod());
            }
          }
        }

        return codes;
      }
    }
    return null;
  }

  public void checkSCN(ExportContainer exportContainer) throws Exception {
    Optional<ShipSCN> shipSCNOpt =
        shipSCNRepository.findByContainerNo(exportContainer.getContainer().getContainerNumber());
    if (shipSCNOpt.isPresent()) {
      ShipSCN shipSCN = shipSCNOpt.orElse(null);
      if (!(shipSCN == null)) {
        ShipSCNDTO shipSCNDTO = new ShipSCNDTO();
        modelMapper.map(shipSCN, shipSCNDTO);
        exportContainer.setScn(shipSCNDTO);
        exportContainer.setBypassEEntry(shipSCN.getScnByPass());
        exportContainer.setRegisteredInEarlyEntry(true);
      }
    }
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void updateGateOutExport(List<ExportContainer> exportContainerList) {

    exportContainerList.forEach(exportContainer -> {
      // fetch based on id

      Optional<Exports> exportOpt = exportsRepository.findOne(exportContainer.getExportID());
      Exports export = exportOpt.orElseThrow(() -> new ResultsNotFoundException(
          "No valid Gate In Export Process found ! " + exportContainer.getExportID()));

      modelMapper.map(exportContainer, export);
      exportsRepository.save(export);

      Optional<ExportsQ> exportQOpt = exportsQRepository.findOne(exportContainer.getExportID());
      ExportsQ exportq = exportQOpt.orElseThrow(() -> new ResultsNotFoundException(
          "No valid Gate In ExportQ Process found ! " + exportContainer.getExportID()));

      modelMapper.map(exportContainer, exportq);
      exportsQRepository.save(exportq);

    });
  }

  public List<ExportContainer> saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest) {
    throw new BusinessException("METHOD NOT IMPLEMENTED YET!");
  }

}
