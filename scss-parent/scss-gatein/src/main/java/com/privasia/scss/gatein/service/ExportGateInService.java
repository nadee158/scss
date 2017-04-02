package com.privasia.scss.gatein.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ShipSCNDTO;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.CardUsage;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ExportsQ;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.model.PrintEir;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.CardUsageRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.DamageCodeRepository;
import com.privasia.scss.core.repository.DgDetailRepository;
import com.privasia.scss.core.repository.ExportsQRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.repository.PrintEirRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.security.util.SecurityHelper;

@Service("exportGateInService")
public class ExportGateInService {

  boolean backToback = false;

  private ModelMapper modelMapper;

  private ShipCodeRepository shipCodeRepository;

  private ShipSCNRepository shipSCNRepository;

  private ExportsRepository exportsRepository;

  private ExportsQRepository exportsQRepository;

  private SystemUserRepository systemUserRepository;

  private CardRepository cardRepository;

  private ClientRepository clientRepository;

  private PrintEirRepository printEirRepository;

  private CardUsageRepository cardUsageRepository;

  private HPATBookingRepository hpatBookingRepository;

  private DamageCodeRepository damageCodeRepository;

  private DgDetailRepository dgDetailRepository;

  public void setDgDetailRepository(DgDetailRepository dgDetailRepository) {
    this.dgDetailRepository = dgDetailRepository;
  }

  @Autowired
  public void setDamageCodeRepository(DamageCodeRepository damageCodeRepository) {
    this.damageCodeRepository = damageCodeRepository;
  }

  @Autowired
  public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
    this.systemUserRepository = systemUserRepository;
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
  public void setPrintEirRepository(PrintEirRepository printEirRepository) {
    this.printEirRepository = printEirRepository;
  }

  @Autowired
  public void setCardUsageRepository(CardUsageRepository cardUsageRepository) {
    this.cardUsageRepository = cardUsageRepository;
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


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateGateInExports(GateInReponse gateInReponse) {

    if (!(gateInReponse.getExportContainers() == null || gateInReponse.getExportContainers().isEmpty())) {
      gateInReponse.getExportContainers().forEach(container -> {
        setStoragePeriod(container);
        setSCN(container);
        checkDg(container);
      });
    }

    return gateInReponse;

  }



  public List<ExportContainer> fetchContainerInfo(List<String> exportContainerNumbers) {

    return null;

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  private void checkDg(ExportContainer exportContainer) {
    if (!(exportContainer == null || exportContainer.getContainer() == null || exportContainer.getScn() == null)) {
      if (!(StringUtils.isEmpty(exportContainer.getContainer().getContainerNumber())
          || StringUtils.isEmpty(exportContainer.getScn().getScnNo()))) {
        Long count = dgDetailRepository.countByScnAndContainerNo(exportContainer.getScn().getScnNo(),
            exportContainer.getContainer().getContainerNumber());
        if (!(count == null || count <= 0)) {
          exportContainer.setBypassDg(true);
        }
      }
    }
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void setStoragePeriod(ExportContainer exportContainer) {
    if (!(exportContainer == null)) {

      String shipCodeStr = exportContainer.getShipCode();

      Optional<ShipCode> optionalShipCode =
          shipCodeRepository.findByShipStatusAndShippingCode(ShipStatus.ACTIVE, shipCodeStr);

      ShipCode shipCode = optionalShipCode.orElseThrow(
          () -> new ResultsNotFoundException("Ship Code could be found for the given Ship Code ! " + shipCodeStr));

      exportContainer.setStoragePeriod(shipCode.getStoragePeriod());
    }

  }



  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void setSCN(ExportContainer exportContainer) {

    String exportContainerNumber = null;
    String scn = null;

    if (!(exportContainer == null)) {

      exportContainerNumber = exportContainer.getContainer().getContainerNumber();
      scn = exportContainer.getScn().getScnNo();

      Optional<ShipSCN> optionalshipSCN = shipSCNRepository.fetchContainerSCN(scn, exportContainerNumber);

      if (optionalshipSCN.isPresent()) {
        ShipSCN shipSCN = optionalshipSCN.get();
        ShipSCNDTO shipSCNDTO = new ShipSCNDTO();
        modelMapper.map(shipSCN, shipSCNDTO);
        exportContainer.setScn(shipSCNDTO);
        exportContainer.setBypassEEntry(shipSCN.getScnByPass());
        exportContainer.setRegisteredInEarlyEntry(true);
      } else {
        exportContainer.setRegisteredInEarlyEntry(false);
        // throw new ResultsNotFoundException(
        // "Ship SCN could be found for the given " + " SCN / ContainerNo Info! " + scn + " / " +
        // exportContainer);
      }

    }

  }


  public List<ExportContainer> saveGateInInfo(GateInWriteRequest gateInWriteRequest) {
    // construct a new export entity for each exportcontainer and save
    backToback = false;
    if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
      System.out.println(
          "gateInWriteRequest.getExportContainers().size() " + gateInWriteRequest.getExportContainers().size());
      if (gateInWriteRequest.getExportContainers().size() > 1) {
        backToback = true;
      }
      gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
        if (exportContainer.getBaseCommonGateInOutAttribute() == null) {
          exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
        }
        System.out.println("gateInWriteRequest.getGateInDateTime() " + gateInWriteRequest.getGateInDateTime());
        exportContainer.setBackToback(backToback);
        exportContainer.getBaseCommonGateInOutAttribute()
            .setTimeGateIn(CommonUtil.getParsedDate(gateInWriteRequest.getGateInDateTime()));
        exportContainer.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());
        Exports exports = new Exports();
        System.out.println("exportContainer " + exportContainer);
        modelMapper.map(exportContainer, exports);
        System.out.println("exports after modal map from exportContainer " + exports);

        // if any of entitiees are not found throw exception
        SystemUser gateInClerk = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
            () -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));
        System.out.println("gateInClerk " + gateInClerk);

        Card card = null;
        HPABBooking hpatBooking = null;

        if (!(exportContainer.getBaseCommonGateInOutAttribute() == null)) {
          if (StringUtils.isNotEmpty(exportContainer.getBaseCommonGateInOutAttribute().getHpatBooking())) {
            hpatBooking =
                hpatBookingRepository.findOne(exportContainer.getBaseCommonGateInOutAttribute().getHpatBooking())
                    .orElseThrow(() -> new ResultsNotFoundException(
                        "Invalid Booking : " + exportContainer.getBaseCommonGateInOutAttribute().getHpatBooking()));
          }
          if (!(exportContainer.getBaseCommonGateInOutAttribute().getCard() == null)) {
            card = cardRepository.findOne(exportContainer.getBaseCommonGateInOutAttribute().getCard())
                .orElseThrow(() -> new ResultsNotFoundException(
                    "Invalid Card : " + exportContainer.getBaseCommonGateInOutAttribute().getCard()));
          }
        }

        Client gateInClient = null;
        if (!(exportContainer.getBaseCommonGateInOutAttribute() == null
            || exportContainer.getBaseCommonGateInOutAttribute().getGateInClient() == null)) {
          gateInClient = clientRepository
              .findOne(exportContainer.getBaseCommonGateInOutAttribute().getGateInClient().getClientID())
              .orElseThrow(() -> new ResultsNotFoundException(
                  "Invalid Client Id : " + exportContainer.getBaseCommonGateInOutAttribute().getGateInClient()));
        }
        ShipSCN scn = null;
        if (!(exportContainer.getScn() == null)) {
          scn = shipSCNRepository.findOne(exportContainer.getScn().getShipSCNID())
              .orElseThrow(() -> new ResultsNotFoundException("Invalid Ship SCN : " + exportContainer.getScn()));
        }
        PrintEir printEir = null;
        // if (!(exportContainer.getPrintEir() == null ||
        // exportContainer.getPrintEir().getPrintEIRID() == null)) {
        // printEir =
        // printEirRepository.findOne(exportContainer.getPrintEir().getPrintEIRID()).orElse(null);
        // }
        CardUsage cardUsage = null;
        // if (!(exportContainer.getCardUsage() == null ||
        // exportContainer.getCardUsage().getCardUsageID() == null)) {
        // cardUsage =
        // cardUsageRepository.findOne(exportContainer.getCardUsage().getCardUsageID()).orElse(null);
        // }
        exports.prepareForInsertFromOpus(gateInClerk, card, gateInClient, scn, printEir, cardUsage, hpatBooking,
            damageCodeRepository);
        System.out.println("exports after initializeing " + exports);
        exports = exportsRepository.save(exports);
        ExportsQ exportsQ = new ExportsQ();
        modelMapper.map(exports, exportsQ);
        System.out.println("exportsQ " + exportsQ);
        exportsQRepository.save(exportsQ);
      });
      return gateInWriteRequest.getExportContainers();
    }

    return null;
  }



}
