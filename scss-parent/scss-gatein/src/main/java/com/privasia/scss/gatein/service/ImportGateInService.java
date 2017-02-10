package com.privasia.scss.gatein.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GatePassValidateDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.ShipSCNDTO;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;
import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;
import com.privasia.scss.opus.service.OpusGateInReadService;

@Service("importGateInService")
public class ImportGateInService {

  private GatePassRepository gatePassRepository;

  private ModelMapper modelMapper;

  private OpusGateInReadService opusGateInReadService;

  private ClientRepository clientRepository;

  private ShipCodeRepository shipCodeRepository;

  private ShipSCNRepository shipSCNRepository;

  @Autowired
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

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
  public void setOpusGateInReadService(OpusGateInReadService opusGateInReadService) {
    this.opusGateInReadService = opusGateInReadService;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateGateIn(GateInRequest gateInRequest) {

    List<Long> gatePassNumberList = new ArrayList<Long>();
    if (gateInRequest.getGatePass1() != null && gateInRequest.getGatePass1() != 0) {
      gatePassNumberList.add(gateInRequest.getGatePass1());
    }
    if (gateInRequest.getGatePass2() != null && gateInRequest.getGatePass2() != 0) {
      gatePassNumberList.add(gateInRequest.getGatePass2());
    }
    List<ImportContainer> importContainers = fetchContainerInfo(gatePassNumberList);

    importContainers.forEach(importContainer -> {
      GatePassValidateDTO gatePassValidateDTO =
          validateGatePass(gateInRequest.getCardID(), gateInRequest.getGatePass1(), gateInRequest.isCheckPreArrival(),
              gateInRequest.getHpabSeqId(), gateInRequest.getTruckHeadNo());
      if (gatePassValidateDTO.isValidGatePass()) {
        if (importContainer.getGatePassNo() == gateInRequest.getGatePass1()) {
          gateInRequest.setImpContainer1(importContainer.getContainer().getContainerNumber());
        } else {
          gateInRequest.setImpContainer2(importContainer.getContainer().getContainerNumber());
        }
      } else {
        throw new ResultsNotFoundException(
            gatePassValidateDTO.getGatePassErrorMessage() + gateInRequest.getGatePass1());
      }
    });

    Optional<Client> clientOpt = clientRepository.findOne(gateInRequest.getLaneId());
    Client client =
        clientOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateInRequest.getLaneId()));
    gateInRequest.setLaneNo(client.getLaneNo());

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // UserContext userContext = (UserContext) authentication.getPrincipal();
    gateInRequest.setUserName((String) authentication.getPrincipal());

    // call opus -
    OpusGateInReadRequest gateInReadRequest = opusGateInReadService.constructOpenGateInRequest(gateInRequest);
    OpusGateInReadResponse gateInReadResponse = opusGateInReadService.getGateInReadResponse(gateInReadRequest);
    GateInReponse gateInReponse = new GateInReponse();
    gateInReponse.setImportContainers(importContainers);
    gateInReponse = opusGateInReadService.constructGateInReponse(gateInReadResponse, gateInReponse);

    return gateInReponse;

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GatePassValidateDTO validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival,
      String hpatSeqId, String truckHeadNo) {

    return new GatePassValidateDTO();
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<ImportContainer> fetchContainerInfo(List<Long> gatePassNumberList) {

    Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatePassNumberList);

    List<GatePass> gatePassList = optionalGatePassList.orElseThrow(
        () -> new ResultsNotFoundException("No Import Containers could be found for the given Gate Pass Numbers!"));
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

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<ShipCode> checkContainer(List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      List<String> shippingCodes =
          exportContainers.stream().map(ExportContainer::getShipID).collect(Collectors.toList());

      Optional<List<ShipCode>> list = shipCodeRepository.findByShippingCodeIn(shippingCodes);
      if (list.isPresent()) {
        List<ShipCode> codes = list.orElse(null);
        for (ShipCode shipCode : codes) {
          for (ExportContainer item : exportContainers) {
            if (StringUtils.equals(shipCode.getShippingCode(), item.getShipID())) {
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

}
