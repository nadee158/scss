package com.privasia.scss.gatein.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ExportsQ;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.DamageCodeRepository;
import com.privasia.scss.core.repository.DgDetailRepository;
import com.privasia.scss.core.repository.ExportsQRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;
import com.privasia.scss.gatein.exports.business.service.DamageCodeService;
import com.privasia.scss.gatein.exports.business.service.SealValidationService;
import com.privasia.scss.gatein.exports.business.service.SolasService;
import com.privasia.scss.hpat.service.HPABService;

@Service("exportGateInService")
public class ExportGateInService {

  private static final Log log = LogFactory.getLog(ExportGateInService.class);

  private ModelMapper modelMapper;

  private ShipCodeRepository shipCodeRepository;

  private ShipSCNRepository shipSCNRepository;

  private ExportsRepository exportsRepository;

  private ExportsQRepository exportsQRepository;

  private HPATBookingRepository hpatBookingRepository;

  private DamageCodeRepository damageCodeRepository;

  private DgDetailRepository dgDetailRepository;

  private WDCGlobalSettingRepository globalSettingRepository;

  private LPKEDIService lpkediService;

  private DamageCodeService damageCodeService;

  private SealValidationService sealValidationService;

  private HPABService hpabService;

  private SolasService solasService;

  @Autowired
  public void setHpabService(HPABService hpabService) {
    this.hpabService = hpabService;
  }

  @Autowired
  public void setSealValidationService(SealValidationService sealValidationService) {
    this.sealValidationService = sealValidationService;
  }

  @Autowired
  public void setDamageCodeService(DamageCodeService damageCodeService) {
    this.damageCodeService = damageCodeService;
  }

  @Autowired
  public void setLpkediService(LPKEDIService lpkediService) {
    this.lpkediService = lpkediService;
  }

  @Autowired
  public void setGlobalSettingRepository(WDCGlobalSettingRepository globalSettingRepository) {
    this.globalSettingRepository = globalSettingRepository;
  }

  @Autowired
  public void setDgDetailRepository(DgDetailRepository dgDetailRepository) {
    this.dgDetailRepository = dgDetailRepository;
  }

  @Autowired
  public void setDamageCodeRepository(DamageCodeRepository damageCodeRepository) {
    this.damageCodeRepository = damageCodeRepository;
  }

  @Autowired
  public void setHPATBookingRepository(HPATBookingRepository hpatBookingRepository) {
    this.hpatBookingRepository = hpatBookingRepository;
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
  public void setExportsRepository(ExportsRepository exportsRepository) {
    this.exportsRepository = exportsRepository;
  }

  @Autowired
  public void setExportsQRepository(ExportsQRepository exportsQRepository) {
    this.exportsQRepository = exportsQRepository;
  }

  @Autowired
  public void setSolasService(SolasService solasService) {
    this.solasService = solasService;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateGateInExports(GateInReponse gateInReponse) {
    Optional<String> globalSetting = globalSettingRepository.fetchGlobalStringByGlobalCode("LPK_EDI");

    gateInReponse.getExportContainers().forEach(container -> {
      container.setExpWeightBridge(gateInReponse.getExpWeightBridge());
      setStoragePeriod(container);
      setSCN(container);
      checkDg(container);
      findLpkEdiMsg(container, globalSetting);
    });

    solasService.calculateTerminalVGM(gateInReponse.getExportContainers());
    return gateInReponse;

  }

  private void findLpkEdiMsg(ExportContainer container, Optional<String> globalSettingOpt) {
    if (StringUtils.equalsIgnoreCase(container.getImdg(), "Y")) {
      globalSettingOpt.orElseThrow(() -> new ResultsNotFoundException("Global Setting not available ! LPK_EDI"));
      lpkediService.findLPKEDITDigiMessage(container);
    }
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void checkDg(ExportContainer exportContainer) {
    if (!(exportContainer == null || exportContainer.getContainer() == null
        || exportContainer.getVesselSCN() == null)) {
      if (!(StringUtils.isEmpty(exportContainer.getContainer().getContainerNumber())
          || StringUtils.isEmpty(exportContainer.getVesselSCN()))) {
        Long count = dgDetailRepository.countByScnAndContainerNo(exportContainer.getVesselSCN(),
            exportContainer.getContainer().getContainerNumber());
        if (!(count == null || count <= 0)) {
          exportContainer.setBypassDg(true);
        }
      }
    }
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void setStoragePeriod(ExportContainer exportContainer) {
    if (exportContainer != null) {
      String shipCodeStr = exportContainer.getShippingLine();
      Optional<ShipCode> optionalShipCode =
          shipCodeRepository.findByShipStatusAndShippingCode(ShipStatus.ACTIVE, shipCodeStr);
      if (optionalShipCode.isPresent()) {
        ShipCode shipCode = optionalShipCode.get();
        exportContainer.setStoragePeriod(shipCode.getStoragePeriod());
      }

    }

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void setSCN(ExportContainer exportContainer) {

    String exportContainerNumber = null;
    String vesselSCN = null; // vessel scn and ship scn are same

    if (!(exportContainer == null)) {

      exportContainerNumber = exportContainer.getContainer().getContainerNumber();
      vesselSCN = exportContainer.getVesselSCN();
      System.out.println("scn " + vesselSCN);

      Optional<ShipSCN> optionalshipSCN = shipSCNRepository.fetchContainerSCN(vesselSCN, exportContainerNumber);

      if (optionalshipSCN.isPresent()) {
        ShipSCN shipSCN = optionalshipSCN.get();
        exportContainer.setShipSCNID(Optional.of(shipSCN.getShipSCNID()));
        exportContainer.setBypassEEntry(shipSCN.getScnByPass());
        exportContainer.setRegisteredInEarlyEntry(true);
      } else {
        exportContainer.setRegisteredInEarlyEntry(false);
      }

    }

  }

  // @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void saveGateInInfo(GateInWriteRequest gateInWriteRequest, Client gateInClient, SystemUser gateInClerk,
      Card card) {
    // construct a new export entity for each exportcontainer and save

    if (gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())
      throw new BusinessException("Invalid GateInWriteRequest to save Exports ! ");
    System.out
        .println("gateInWriteRequest.getExportContainers().size() " + gateInWriteRequest.getExportContainers().size());

    HPABBooking hpatBooking = null;
    Optional<ExportContainer> exportContainerFirstOpt = gateInWriteRequest.getExportContainers().stream().findFirst();
    if (exportContainerFirstOpt.isPresent()) {
      ExportContainer exportContainerFirst = exportContainerFirstOpt.get();
      if (!(exportContainerFirst.getBaseCommonGateInOutAttribute() == null)) {
        if (StringUtils.isNotEmpty(exportContainerFirst.getBaseCommonGateInOutAttribute().getHpatBooking())) {
          hpatBooking = hpatBookingRepository
              .findOne(exportContainerFirst.getBaseCommonGateInOutAttribute().getHpatBooking()).orElse(null);
        }
      }
    }
    final HPABBooking hpatBookingFinal = hpatBooking;

    gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
      if (exportContainer.getBaseCommonGateInOutAttribute() == null) {
        exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
      }
      System.out.println("gateInWriteRequest.getGateInDateTime() " + gateInWriteRequest.getGateInDateTime());

      if (gateInWriteRequest.getExportContainers().size() > 1) {
        exportContainer.setBackToback(true);
      }

      // assign values from header level to container level
      exportContainer.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS.getValue());
      exportContainer.getBaseCommonGateInOutAttribute().setHpatBooking(gateInWriteRequest.getHpatBookingId());
      exportContainer.getBaseCommonGateInOutAttribute().setPmHeadNo(gateInWriteRequest.getTruckHeadNo());
      exportContainer.getBaseCommonGateInOutAttribute().setPmPlateNo(gateInWriteRequest.getTruckPlateNo());
      exportContainer.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());
      exportContainer.setFuelWeight(gateInWriteRequest.getFuelWeight());
      exportContainer.setTireWeight(gateInWriteRequest.getTireWeight());
      exportContainer.setVariance(gateInWriteRequest.getVariance());

      if (exportContainer.getCommonGateInOut() == null) {
        exportContainer.setCommonGateInOut(new CommonGateInOutDTO());
      }
      exportContainer.getCommonGateInOut().setGateInStatus(gateInWriteRequest.getGateInStatus());

      exportContainer.getBaseCommonGateInOutAttribute().setTimeGateIn(gateInWriteRequest.getGateInDateTime());
      exportContainer.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());

      Exports exports = new Exports();
      System.out.println("exportContainer " + exportContainer);
      modelMapper.map(exportContainer, exports);

      System.out.println("exportContainer.getContainer().getContainerFullOrEmpty() "
          + exportContainer.getContainer().getContainerFullOrEmpty());
      System.out.println(
          "exports.getContainer().getContainerFullOrEmpty() " + exports.getContainer().getContainerFullOrEmpty());

      ShipSCN scn = null;
      if (!(exportContainer.getVesselSCN() == null)) { // scn = VesselSCN

        scn = shipSCNRepository
            .fetchContainerSCN(exportContainer.getVesselSCN(), exportContainer.getContainer().getContainerNumber())
            .orElse(null);
      }
      exports.prepareForInsertFromOpus(gateInClerk, card, gateInClient, scn, hpatBookingFinal, damageCodeRepository);
      exports = exportsRepository.save(exports);
      log.info("########## Save Exports ###############");
      ExportsQ exportsQ = new ExportsQ();
      modelMapper.map(exports, exportsQ);
      System.out.println("exports.getExportID() " + exports.getExportID());
      System.out.println("exportsQ.getExportID() " + exportsQ.getExportID());
      exportsQ = exportsQRepository.save(exportsQ);
      System.out.println("exportsQ.getExportID() after save " + exportsQ.getExportID());
    });

    // return new AsyncResult<Boolean>(true);
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void validateExport(List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      exportContainers.forEach(exportContainer -> {
        // 1. DAMAGE CONTAINER CHECK - 1313 (DamageCodeService )
        damageCodeService.checkDuplicateDameCodeExistence(exportContainer);
        // 2. Shipping Line Seal Validation -1578
        // (SealValidationServicve)
        sealValidationService.validateSeal(exportContainer);

      });
    }
  }

}
