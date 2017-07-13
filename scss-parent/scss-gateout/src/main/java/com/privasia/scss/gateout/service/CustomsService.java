package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CustomsDTO;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.util.LPS;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.CustomContainer;
import com.privasia.scss.core.model.Customs;
import com.privasia.scss.core.model.CustomsReport;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.ODDContainerDetails;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.predicate.ExportsPredicates;
import com.privasia.scss.core.predicate.GatePassPredicates;
import com.privasia.scss.core.predicate.ODDPredicates;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.CustomsReportRepository;
import com.privasia.scss.core.repository.CustomsRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ODDRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("customsService")
public class CustomsService {

  private ExportsRepository exportsRepository;

  private GatePassRepository gatePassRepository;

  private ODDRepository oddRepository;

  private CustomsReportRepository customsReportRepository;

  private CustomsRepository customsRepository;

  private ClientRepository clientRepository;

  private WDCGatePassRepository wdcGatePassRepository;

  private ModelMapper modelMapper;

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setCustomsReportRepository(CustomsReportRepository customsReportRepository) {
    this.customsReportRepository = customsReportRepository;
  }

  @Autowired
  public void setCustomsRepository(CustomsRepository customsRepository) {
    this.customsRepository = customsRepository;
  }

  @Autowired
  public void setExportsRepository(ExportsRepository exportsRepository) {
    this.exportsRepository = exportsRepository;
  }

  @Autowired
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

  @Autowired
  public void setOddRepository(ODDRepository oddRepository) {
    this.oddRepository = oddRepository;
  }

  @Autowired
  public void setWdcGatePassRepository(WDCGatePassRepository wdcGatePassRepository) {
    this.wdcGatePassRepository = wdcGatePassRepository;
  }

  public String updateCustoms(CustomsDTO customsDTO) {

    if ((customsDTO.getExportIDSeq01().isPresent() || customsDTO.getExportIDSeq02().isPresent())
        && (customsDTO.getGatePassIDSeq01().isPresent()
            || customsDTO.getGatePassIDSeq02().isPresent())) {
      updateCustomsExport(customsDTO);
      updateCustomsImport(customsDTO);
      return "success";
    }

    if (customsDTO.getExportIDSeq01().isPresent() || customsDTO.getExportIDSeq02().isPresent()) {
      updateCustomsExport(customsDTO);
      return "success";
    }
    if (customsDTO.getGatePassIDSeq01().isPresent()
        || customsDTO.getGatePassIDSeq02().isPresent()) {
      updateCustomsImport(customsDTO);
      return "success";
    }
    if (customsDTO.getOddIdSeq01().isPresent() || customsDTO.getOddIdSeq02().isPresent()) {
      updateCustomsODD(customsDTO);
      return "success";
    }

    throw new BusinessException("No transaction type to update customs !");

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateCustomsODD(CustomsDTO customsDTO) {

    Predicate byGateInStatus = ODDPredicates.byGateInStatus(TransactionStatus.APPROVED);
    Predicate byWHoddIDList = ODDPredicates.byWHoddIDList(Arrays
        .asList(customsDTO.getOddIdSeq01().orElse(null), customsDTO.getOddIdSeq02().orElse(null)));

    Predicate condition = ExpressionUtils.allOf(byGateInStatus, byWHoddIDList);
    Iterable<WHODD> whODDList = oddRepository.findAll(condition);
    if ((whODDList != null && whODDList.iterator().hasNext())) {
      deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
      whODDList.forEach(whODD -> {
        Customs customs = constructCustoms(customsDTO,
            TransactionType.fromCode(whODD.getImpExpFlag().getValue()));
        customs.setWhODD(whODD);
        if (customs.getContainer01() == null) {
          customs.setContainer01(
              constructCustomContainer(whODD.getContainer01(), whODD.getImpExpFlag()));
        } else {
          customs.setContainer02(
              constructCustomContainer(whODD.getContainer02(), whODD.getImpExpFlag()));
        }

        customsRepository.save(customs);
        if (!(customs.getCustomsID() == null || customs.getCustomsID() == 0)) {
          CustomsReport customsReport = new CustomsReport();
          modelMapper.map(customs, customsReport);
          customsReport.setCustomsReportID(customs.getCustomsID());
          customsReportRepository.save(customsReport);
        } else {
          throw new BusinessException("Save customs failed ! ");
        }
      });
    } else {
      throw new BusinessException("Save customs failed. Given data not valid ");
    }


    return null;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateCustomsImport(CustomsDTO customsDTO) {
    Predicate byContainerFullOrEmpty =
        GatePassPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);
    Predicate byCancelPickup = GatePassPredicates.byCancelPickup(false);
    Predicate byGatePassIDList = GatePassPredicates
        .byGatePassIDList(Arrays.asList(customsDTO.getGatePassIDSeq01().orElse(null),
            customsDTO.getGatePassIDSeq02().orElse(null)));

    Predicate condition =
        ExpressionUtils.allOf(byContainerFullOrEmpty, byCancelPickup, byGatePassIDList);
    Iterable<GatePass> gatePassList = gatePassRepository.findAll(condition);
    if ((gatePassList != null && gatePassList.iterator().hasNext())) {
      deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
      final Customs customs = constructCustoms(customsDTO, TransactionType.IMPORT);
      gatePassList.forEach(gatePass -> {
        if (customs.getContainer01() == null) {
          customs.setContainer01(constructCustomContainer(gatePass));
        } else {
          customs.setContainer02(constructCustomContainer(gatePass));
        }
      });

      customsRepository.save(customs);
      if (!(customs.getCustomsID() == null || customs.getCustomsID() == 0)) {
        CustomsReport customsReport = new CustomsReport();
        modelMapper.map(customs, customsReport);
        customsReport.setCustomsReportID(customs.getCustomsID());
        customsReportRepository.save(customsReport);
      } else {
        throw new BusinessException("Save customs failed ! ");
      }

    } else {
      throw new BusinessException("Save customs failed. Given data not valid ");
    }
    return "success";
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateCustomsExport(CustomsDTO customsDTO) {

    Predicate byContainerFullOrEmpty =
        ExportsPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);
    Predicate byGateInStatus = ExportsPredicates.byEirStatus(TransactionStatus.APPROVED);
    Predicate byExportsIDList =
        ExportsPredicates.byExportsIDList(Arrays.asList(customsDTO.getExportIDSeq01().orElse(null),
            customsDTO.getExportIDSeq02().orElse(null)));

    Predicate condition =
        ExpressionUtils.allOf(byContainerFullOrEmpty, byGateInStatus, byExportsIDList);
    Iterable<Exports> exportsList = exportsRepository.findAll(condition);
    if ((exportsList != null && exportsList.iterator().hasNext())) {
      deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
      final Customs customs = constructCustoms(customsDTO, TransactionType.EXPORT);
      exportsList.forEach(exports -> {
        if (customs.getContainer01() == null) {
          customs.setContainer01(constructCustomContainer(exports));
        } else {
          customs.setContainer02(constructCustomContainer(exports));
        }
      });
      customsRepository.save(customs);
      if (!(customs.getCustomsID() == null || customs.getCustomsID() == 0)) {
        CustomsReport customsReport = new CustomsReport();
        modelMapper.map(customs, customsReport);
        customsReport.setCustomsReportID(customs.getCustomsID());
        customsReportRepository.save(customsReport);
      } else {
        throw new BusinessException("Save customs failed ! ");
      }
    } else {
      throw new BusinessException("Save customs failed. Given data not valid ");
    }
    return "success";
  }

  private CustomContainer constructCustomContainer(Exports exports) {
    CustomContainer customContainer = new CustomContainer();
    customContainer.setContainerFullOrEmpty(exports.getContainer().getContainerFullOrEmpty());
    customContainer.setContainerNumber(exports.getContainer().getContainerNumber());
    customContainer.setCustomEirStatus(exports.getBaseCommonGateInOutAttribute().getEirStatus());
    customContainer.setCustomRejection(exports.getCommonGateInOut().getRejectReason());
    customContainer.setExport(exports);

    return customContainer;
  }

  private CustomContainer constructCustomContainer(GatePass gatePass) {
    CustomContainer customContainer = new CustomContainer();
    customContainer.setContainerNumber(gatePass.getContainer().getContainerNumber());
    customContainer.setContainerFullOrEmpty(gatePass.getContainer().getContainerFullOrEmpty());
    customContainer.setGatePass(gatePass);
    customContainer.setCustomRejection(gatePass.getCommonGateInOut().getRejectReason());
    customContainer.setCustomEirStatus(gatePass.getBaseCommonGateInOutAttribute().getEirStatus());

    Optional<WDCGatePass> optionalWdcGatePass =
        wdcGatePassRepository.findByGatePassNO(gatePass.getGatePassNo());

    WDCGatePass wdcGatePass = optionalWdcGatePass.orElseThrow(() -> new ResultsNotFoundException(
        "GCS Declaration could be found for the given Gate Pass Numbers! "
            + gatePass.getGatePassNo()));

    customContainer.setGcsDelcarerNo(wdcGatePass.getGcsDelcarerNo());
    customContainer.setCusGCSReleaseDate(wdcGatePass.getCusGCSReleaseDate());
    customContainer.setPortPoliceDate(wdcGatePass.getDateTimeADD());
    customContainer.setGatePassIssuedDate(wdcGatePass.getDateTimeADD());

    return customContainer;
  }

  private CustomContainer constructCustomContainer(ODDContainerDetails oddContainerDetails,
      ImpExpFlagStatus impExpFlag) {

    if (oddContainerDetails != null
        && (StringUtils.equalsIgnoreCase(oddContainerDetails.getFullOrEmpty().getValue(),
            ContainerFullEmptyType.FULL.getValue()))) {

      if ((StringUtils.equalsIgnoreCase(impExpFlag.getValue(), ImpExpFlagStatus.EXPORT.getValue()))
          && (StringUtils.equalsIgnoreCase(oddContainerDetails.getOddStatus().getValue(),
              TransactionStatus.APPROVED.getValue()))) {
        return null;
      }
      CustomContainer customContainer = new CustomContainer();
      customContainer.setContainerFullOrEmpty(oddContainerDetails.getFullOrEmpty());
      customContainer.setContainerNumber(oddContainerDetails.getContainerNo());
      customContainer.setCustomEirStatus(oddContainerDetails.getOddStatus());
      customContainer.setOddLocation(oddContainerDetails.getLocation().getOddCode());
      customContainer.setCustomRejection(oddContainerDetails.getRejectionReason());
      return customContainer;
    }
    return null;
  }

  public Customs constructCustoms(CustomsDTO customsDTO, TransactionType transactionType) {
    Customs customs = new Customs();
    customs.setCsmFlag(transactionType);
    Optional<Client> clientOpt = clientRepository.findOne(customsDTO.getGateOutClientId());
    Client client = clientOpt.orElseThrow(
        () -> new ResultsNotFoundException("Invalid lane ID ! " + customsDTO.getGateOutClientId()));
    customs.setCsmGateOutClient(client);
    customs.setTimeGateOut(LocalDateTime.now());
    return customs;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteCustomsByGateOutClientId(Long gateOutClientId) {
    if (gateOutClientId == null || gateOutClientId <= 0) {
      throw new BusinessException("Gate out client id is not available!");
    }
    customsRepository.deleteByClientID(gateOutClientId);
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public String checkCustomStatus(Long clientID) {
    String status = customsRepository.checkCustomStatus(clientID);
    if (StringUtils.isEmpty(status)) {
      status = LPS.RES_WAIT;
    }
    System.out.println("******* Response for Check Custom Status ******* " + status);
    return status;
  }
}
