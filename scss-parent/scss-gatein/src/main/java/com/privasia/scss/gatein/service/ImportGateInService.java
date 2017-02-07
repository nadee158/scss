package com.privasia.scss.gatein.service;

import java.time.LocalDateTime;
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
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;
import com.privasia.scss.opus.dto.GIR01Request;
import com.privasia.scss.opus.dto.GIR01Response;
import com.privasia.scss.opus.dto.OpusImportContainer;
import com.privasia.scss.opus.service.OpusService;

@Service("importGateInService")
public class ImportGateInService {

  private GatePassRepository gatePassRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private OpusService opusService;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ShipCodeRepository shipCodeRepository;

  @Autowired
  private ShipSCNRepository shipSCNRepository;

  @Autowired
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateGateIn(GateInRequest gateInRequest) {
   
      List<Long> gatePassNumberList = new ArrayList<Long>();
      if (gateInRequest.getGatePass1() != null && gateInRequest.getGatePass1() != 0) { 
        gatePassNumberList.add(gateInRequest.getGatePass1());
      }
      if (gateInRequest.getGatePass2() != null && gateInRequest.getGatePass2() != 0) {
        gatePassNumberList.add(gateInRequest.getGatePass2());
      }
      List<ImportContainer> importContainers = fetchContainerInfo(gatePassNumberList);
      
      boolean isValid = true;
      importContainers.forEach(importContainer -> {
    	  isValid = validateGatePass(gateInRequest.getCardID(), gateInRequest.getGatePass1(), gateInRequest.isCheckPreArrival(),
                  gateInRequest.getHpabSeqId(), gateInRequest.getTruckHeadNo());
          if (!isValid) {
             break;
             throw new ResultsNotFoundException("Invalid gate pass ! "+gateInRequest.getGatePass1());
          }
      });
      
      
      if (!(importContainers == null || importContainers.isEmpty())) {
        // call validategatepass
       

        if (isValid) {
          // call opus -
          GIR01Request gir01Request = constructGIR01Request(gateInRequest, importContainers);
          GIR01Response gir01Response = opusService.getGIR01Response(gir01Request);
          // response - gateinresponse- list of import container/list of export container
          GateInReponse gateInReponse = constructGateInReponse(gir01Response, gateInRequest);

          return gateInReponse;
        }
      }
    
    throw new ResultsNotFoundException("No results could be found for the given details!");
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public boolean validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival, String hpatSeqId,
      String truckHeadNo) {

    return true;
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ImportContainer> fetchContainerInfo(List<Long> gatePassNumberList) {
    
	Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatePassNumberList);
    
    List<GatePass> gatePassList = optionalGatePassList.orElseThrow(() -> new ResultsNotFoundException("No Import Containers could be found for the given Gate Pass Numbers!"));
    List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
    gatePassList.forEach(item -> {
        ImportContainer importContainer = new ImportContainer();
        modelMapper.map(item, importContainer);
        importContainers.add(importContainer);
    });
    return importContainers;

  }


  private GIR01Request constructGIR01Request(GateInRequest gateInRequest, List<ImportContainer> importContainers) {
    GIR01Request gir01Request = new GIR01Request();
    if (!(importContainers == null || importContainers.isEmpty())) {
      gir01Request.setContainerNo1ImportCY(importContainers.get(0).getContainerNumber());
      if (importContainers.size() > 1) {
        gir01Request.setContainerNo2ImportCY(importContainers.get(1).getContainerNumber());
      }

    }
    // 20161130112233 - yyyyMMddHHmmss
    LocalDateTime gateInDateTime = CommonUtil.getParsedDate(gateInRequest.getGateInDateTime());
    gir01Request.setGateINDateTime(OpusService.getJsonDateFromDate(gateInDateTime));
    gir01Request.setHaulageCode("HAUCD");
    long laneId = Long.parseLong(gateInRequest.getLaneId());
    Optional<Client> clientOpt = clientRepository.findOne(laneId);
    if (clientOpt.isPresent()) {
      Client client = clientOpt.orElse(null);
      if (!(client == null)) {
        gir01Request.setLaneNo(client.getLaneNo());
      }
    }
    gir01Request.setTruckHeadNo(gateInRequest.getTruckHeadNo());
    gir01Request.setUserID(gateInRequest.getUserId());
    return gir01Request;
  }

  private GateInReponse constructGateInReponse(GIR01Response gir01Response, GateInRequest gateInRequest) {
    GateInReponse gateInReponse = new GateInReponse();
    LocalDateTime localDateTime = OpusService.getLocalDategFromString(gir01Response.getGateINDateTime());
    gateInReponse.setGateINDateTime(CommonUtil.getFormatteDate(localDateTime));
    gateInReponse.setHaulageCode(gir01Response.getHaulageCode());
    gateInReponse.setImportContainers(constructImportContainers(gir01Response.getImportContainerListCY()));
    gateInReponse.setLaneNo(gir01Response.getLaneNo());
    gateInReponse.setTruckHeadNo(gir01Response.getTruckHeadNo());
    gateInReponse.setTruckPlateNo(gir01Response.getTruckPlateNo());
    return gateInReponse;
  }

  private List<ImportContainer> constructImportContainers(List<OpusImportContainer> importContainerListCY) {
    if (!(importContainerListCY == null || importContainerListCY.isEmpty())) {
      List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
      for (OpusImportContainer opusImportContainer : importContainerListCY) {
        importContainers.add(constructImportContainer(opusImportContainer));
      }
      return importContainers;
    }
    return null;
  }

  private ImportContainer constructImportContainer(OpusImportContainer opusImportContainer) {
    ImportContainer container = new ImportContainer();

    // not currently available- added to importcontainer
    // private long containerDischargeDateTime;// 20161124162510,
    // private String impCarrierType;// null,
    // private String impCarrier;// null,
    // private String vesselCode;// UANE,
    // private String vesselVoyage;// 003/2016,
    // private String visitId;// 121212,
    // private String vesselScn;// DB0899,
    // private String vesselName;// AL NEFUD,
    // private String vesselATA;// 20161124161800
    modelMapper.map(opusImportContainer, container);

    // private double containerHeight;// 8,
    container.setContainerHeight(opusImportContainer.getContainerHeight());
    // private double containerSize;// 40,
    container.setContainerLength(opusImportContainer.getContainerSize());
    // private String containerNo;// QASS1234566,
    container.setContainerNumber(opusImportContainer.getContainerNo());

    // private String containerInOrOut;// OUT,
    container.setInOrOut(opusImportContainer.getContainerInOrOut());

    // private String impOrderNo;// ORDER0001,
    container.setOrderFOT(opusImportContainer.getImpOrderNo());

    // private String containerShippingLine;// CMA,
    container.setLine(opusImportContainer.getContainerShippingLine());

    // private String containerFullOrEmpty;// F,
    container.setFullOrEmpty(opusImportContainer.getContainerFullOrEmpty());

    // private String containerIso;// 4001,
    container.setIsoCode(opusImportContainer.getContainerIso());

    // private String containerType;// GE,
    container.setContainerType(opusImportContainer.getContainerType());

    // private String currentYardPosition;// 02S-0102-C-1,
    container.setYardPosition(opusImportContainer.getCurrentYardPosition());

    return container;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ShipCode> checkContainer(List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      List<String> shippingCodes =
          exportContainers.stream().map(ExportContainer::getShipCode).collect(Collectors.toList());

      Optional<List<ShipCode>> list = shipCodeRepository.findByShippingCodeIn(shippingCodes);
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
    Optional<ShipSCN> shipSCNOpt = shipSCNRepository.findByContainerNo(exportContainer.getContainerNumber());
    if (shipSCNOpt.isPresent()) {
      ShipSCN shipSCN = shipSCNOpt.orElse(null);
      if (!(shipSCN == null)) {
        exportContainer.setBypassEEntry(shipSCN.getScnByPass());
        exportContainer.setRegisteredInEarlyEntry(true);
        exportContainer.setSeq(Long.toString(shipSCN.getShipSCNID()));
      }
    }
  }


}
