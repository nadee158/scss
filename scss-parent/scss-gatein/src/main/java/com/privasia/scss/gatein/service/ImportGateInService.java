package com.privasia.scss.gatein.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.CommonGateInOutDTO;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TOSServiceType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.CommonContainerAttribute;
import com.privasia.scss.core.model.CommonGateInOutAttribute;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.predicate.GatePassPredicates;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.gatein.imports.business.service.GatePassValidationService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("importGateInService")
public class ImportGateInService {

  private static final Log log = LogFactory.getLog(ImportGateInService.class);

  private GatePassRepository gatePassRepository;

  private ModelMapper modelMapper;

  private GatePassValidationService gatePassValidationService;

  @Autowired
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setGatePassValidationService(GatePassValidationService gatePassValidationService) {
    this.gatePassValidationService = gatePassValidationService;
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
  public List<ImportContainer> populateGateInImport(GateInRequest gateInRequest) {

    List<Long> gatePassNumberList = Arrays.asList(gateInRequest.getGatePass1(), gateInRequest.getGatePass2());

    Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatePassNumberList);

    List<GatePass> gatePassList = optionalGatePassList.get();

    if (gatePassList.isEmpty())
      throw new ResultsNotFoundException("No Import Containers could be found for the given Gate Pass Numbers!");

    List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
    gatePassList.forEach(gatePass -> {
      ImportContainer importContainer = new ImportContainer();
      modelMapper.map(gatePass, importContainer);

      importContainers.add(importContainer);
      if (StringUtils.isEmpty(gateInRequest.getImpContainer1())) {
        gateInRequest.setImpContainer1(gatePass.getContainer().getContainerNumber());
      } else {
        gateInRequest.setImpContainer2(gatePass.getContainer().getContainerNumber());
      }

    });
    return importContainers;

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

      importContainers.add(importContainer);
    });
    return importContainers;

  }

  // @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void saveGateInInfo(GateInWriteRequest gateInWriteRequest, Client gateInClient, SystemUser gateInClerk,
      Card card, HPABBooking hpabBooking) {
    // construct a new export entity for each exportcontainer and save
    if (gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())
      throw new BusinessException("Invalid GateInWriteRequest to save Imports ! ");
    	Long printEIRNo = gatePassRepository.getNextPrintEIRNo();
    gateInWriteRequest.getImportContainers().forEach(importContainer -> {
      if (importContainer.getBaseCommonGateInOutAttribute() == null) {
        importContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
      }
      
      importContainer.getBaseCommonGateInOutAttribute().setTimeGateIn(gateInWriteRequest.getGateInDateTime());

      // assign values from header level to container level
      importContainer.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS.getValue());
      importContainer.getBaseCommonGateInOutAttribute().setHpabBooking(gateInWriteRequest.getHpatBookingId());
      importContainer.getBaseCommonGateInOutAttribute().setPmHeadNo(gateInWriteRequest.getTruckHeadNo());
      importContainer.getBaseCommonGateInOutAttribute().setPmPlateNo(gateInWriteRequest.getTruckPlateNo());
      importContainer.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());
      importContainer.setPrintEIRNo(printEIRNo);
      importContainer.setTosServiceType(gateInWriteRequest.getTosIndicator());
      if (importContainer.getCommonGateInOut() == null) {
        importContainer.setCommonGateInOut(new CommonGateInOutDTO());
      }
      importContainer.getCommonGateInOut().setGateInStatus(gateInWriteRequest.getGateInStatus());
      importContainer.getCommonGateInOut().setRejectReason(gateInWriteRequest.getRejectReason());

      if (!(importContainer.getGatePassNo() == null)) {

        GatePass gatePass = gatePassRepository.findByGatePassNo(importContainer.getGatePassNo()).orElseThrow(
            () -> new ResultsNotFoundException("Invalid Gate Pass Number : " + importContainer.getGatePassNo()));
        if (!(gatePass == null)) {

          importContainerToGatePass(importContainer, gatePass);

          gatePass.prepareForInsertFromOpus(card, gateInClerk, gateInClient, hpabBooking);
          gatePass = gatePassRepository.save(gatePass);
        } else {
          throw new ResultsNotFoundException("Gatepass could not be found with the given gate pass number!");
        }

      }

    });
    // return new AsyncResult<Boolean>(true);
  }

  private void importContainerToGatePass(ImportContainer importContainer, GatePass gatePass) {
    if (gatePass.getCommonGateInOut() == null) {
      gatePass.setCommonGateInOut(new CommonGateInOutAttribute());
    }
    gatePass.getCommonGateInOut().setEirNumber(importContainer.getPrintEIRNo());
    if (gatePass.getBaseCommonGateInOutAttribute() == null) {
      gatePass.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutAttribute());
    }
    gatePass.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS);
    gatePass.getBaseCommonGateInOutAttribute()
        .setTimeGateIn(importContainer.getBaseCommonGateInOutAttribute().getTimeGateIn());
    gatePass.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());
    gatePass.getCommonGateInOut().setImpExpFlag(
        ImpExpFlagStatus.fromValue(StringUtils.upperCase(importContainer.getCommonGateInOut().getImpExpFlag())));
    gatePass.setTosServiceType(TOSServiceType.fromValue(importContainer.getTosServiceType()));
    gatePass.setOrderNo(StringUtils.upperCase(importContainer.getOrderNo()));
    gatePass.setCurrentPosition(StringUtils.upperCase(importContainer.getCurrentPosition()));
    gatePass.setGateInOut(GateInOutStatus.fromValue(StringUtils.upperCase(importContainer.getGateInOut())));

    if (gatePass.getContainer() == null) {
      gatePass.setContainer(new CommonContainerAttribute());
    }
    gatePass.getContainer().setContainerFullOrEmpty(ContainerFullEmptyType
        .fromValue(StringUtils.upperCase(importContainer.getContainer().getContainerFullOrEmpty())));
    gatePass.setShippingLine(StringUtils.upperCase(importContainer.getShippingLine()));
    gatePass.getContainer()
        .setContainerISOCode(StringUtils.upperCase(importContainer.getContainer().getContainerISOCode()));
    gatePass.setGateInLaneNo(StringUtils.upperCase(importContainer.getGateInLaneNo()));
    gatePass.getBaseCommonGateInOutAttribute()
        .setPmHeadNo(StringUtils.upperCase(importContainer.getBaseCommonGateInOutAttribute().getPmHeadNo()));
    gatePass.getBaseCommonGateInOutAttribute()
        .setPmPlateNo(StringUtils.upperCase(importContainer.getBaseCommonGateInOutAttribute().getPmPlateNo()));
    gatePass.setYardPosition(StringUtils.upperCase(importContainer.getYardPosition()));
    gatePass.setBayCode(StringUtils.upperCase(importContainer.getYardBayCode()));
    gatePass.setContainerPosition(
        ContainerPosition.fromValue(StringUtils.upperCase(importContainer.getContainerPosition())));
    gatePass.setCallCard(importContainer.getCallCard());
    gatePass.getCommonGateInOut()
        .setRejectReason(StringUtils.upperCase(importContainer.getCommonGateInOut().getRejectReason()));
    gatePass.getCommonGateInOut().setGateInStatus(
        TransactionStatus.fromCode(StringUtils.upperCase(importContainer.getCommonGateInOut().getGateInStatus())));
  }

  public void validateImport(GateInRequest gateInRequest, List<ImportContainer> importContainers) {
    if (!(importContainers == null || importContainers.isEmpty())) {
      importContainers.forEach(importContainer -> {
        // // GatePassValidationService call validate - within another
        // method
        gatePassValidationService.validateGatePass(gateInRequest.getCardID(), importContainer.getGatePassNo(),
            gateInRequest.isCheckPreArrival(), gateInRequest.getHpabSeqId(), gateInRequest.getTruckHeadNo());

      });
    }

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public boolean isPlateNoHeadNoUsed(GateInWriteRequest gateInWriteRequest) {

    Predicate byHeadNo = GatePassPredicates.byPMHeadNo(gateInWriteRequest.getTruckHeadNo());
    Predicate byPlateNo = GatePassPredicates.byPMPlateNo(gateInWriteRequest.getTruckPlateNo());
    Predicate byTransactionStatus = GatePassPredicates.byTransactionStatus(TransactionStatus.INPROGRESS);


    Predicate condition =
        ExpressionUtils.allOf(ExpressionUtils.and(ExpressionUtils.or(byHeadNo, byPlateNo), byTransactionStatus));

    Iterable<GatePass> importsList = gatePassRepository.findAll(condition);

    if (importsList == null || Stream.of(importsList).count() == 0)
      return false;

    if (importsList.iterator().hasNext()) {
      GatePass dbGatePass = importsList.iterator().next();
      if (StringUtils.equalsIgnoreCase(dbGatePass.getBaseCommonGateInOutAttribute().getPmHeadNo(),
          gateInWriteRequest.getTruckHeadNo())
          && StringUtils.equalsIgnoreCase(dbGatePass.getBaseCommonGateInOutAttribute().getPmPlateNo(),
              gateInWriteRequest.getTruckPlateNo())) {
        throw new BusinessException("PM Head No " + dbGatePass.getBaseCommonGateInOutAttribute().getPmHeadNo()
            + "and PM plate No " + dbGatePass.getBaseCommonGateInOutAttribute().getPmPlateNo() + " already in use.");
      } else if (StringUtils.equalsIgnoreCase(dbGatePass.getBaseCommonGateInOutAttribute().getPmHeadNo(),
          gateInWriteRequest.getTruckHeadNo())) {
        throw new BusinessException(
            "PM Head No " + dbGatePass.getBaseCommonGateInOutAttribute().getPmHeadNo() + " already in use");
      } else if (StringUtils.equalsIgnoreCase(dbGatePass.getBaseCommonGateInOutAttribute().getPmPlateNo(),
          gateInWriteRequest.getTruckPlateNo())) {
        throw new BusinessException(
            "PM plate No " + dbGatePass.getBaseCommonGateInOutAttribute().getPmPlateNo() + " already in use");
      }
    }

    return false;

  }

}
