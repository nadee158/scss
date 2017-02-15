package com.privasia.scss.gateout.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ShipSCNDTO;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;

@Service("exportGateOutService")
public class ExportGateOutService {

  private ModelMapper modelMapper;

  private ClientRepository clientRepository;

  private ShipCodeRepository shipCodeRepository;

  private ShipSCNRepository shipSCNRepository;


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


  public List<ExportContainer> populateGateOut(GateOutRequest gateOutRequest) {

    Optional<Client> clientOpt = clientRepository.findOne(gateOutRequest.getLaneId());
    Client client =
        clientOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutRequest.getLaneId()));
    gateOutRequest.setLaneNo(client.getLaneNo());


    return null;

  }


  public List<ExportContainer> fetchContainerInfo(List<String> exportContainerNumbers) {

    return null;

  }


  public List<ShipCode> checkContainer(List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      List<String> shippingCodes =
          exportContainers.stream().map(ExportContainer::getShipCode).collect(Collectors.toList());

      Optional<List<ShipCode>> list = shipCodeRepository.findByShipStatusAndShippingCodeIn(ShipStatus.ACTIVE,shippingCodes);
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

  public List<ExportContainer> saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest) {



    return null;
  }



}
