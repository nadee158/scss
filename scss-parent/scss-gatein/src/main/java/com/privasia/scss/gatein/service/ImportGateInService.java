package com.privasia.scss.gatein.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GatePassValidateDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.GatePassRepository;

@Service("importGateInService")
public class ImportGateInService {

  private GatePassRepository gatePassRepository;

  private ModelMapper modelMapper;

  private ClientRepository clientRepository;


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


  public List<ImportContainer> populateGateIn(GateInRequest gateInRequest) {

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

    return importContainers;

  }

  public GatePassValidateDTO validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival,
      String hpatSeqId, String truckHeadNo) {

    return new GatePassValidateDTO();
  }

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



  public List<ImportContainer> saveGateInInfo(GateInWriteRequest gateInWriteRequest) {



    return null;
  }

}
