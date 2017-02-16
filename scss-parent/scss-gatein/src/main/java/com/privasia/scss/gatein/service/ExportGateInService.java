package com.privasia.scss.gatein.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.privasia.scss.core.model.HPATBooking;
import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.model.PrintEir;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.CardUsageRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.DamageCodeRepository;
import com.privasia.scss.core.repository.ExportsQRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.repository.LoginRepository;
import com.privasia.scss.core.repository.PrintEirRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;

@Service("exportGateInService")
public class ExportGateInService {

  private ModelMapper modelMapper;

  private ShipCodeRepository shipCodeRepository;

  private ShipSCNRepository shipSCNRepository;

  private ExportsRepository exportsRepository;

  private ExportsQRepository exportsQRepository;

  private LoginRepository loginRepository;

  private CardRepository cardRepository;

  private ClientRepository clientRepository;

  private PrintEirRepository printEirRepository;

  private CardUsageRepository cardUsageRepository;

  private HPATBookingRepository hpatBookingRepository;

  private DamageCodeRepository damageCodeRepository;

  @Autowired
  public void setDamageCodeRepository(DamageCodeRepository damageCodeRepository) {
    this.damageCodeRepository = damageCodeRepository;
  }

  @Autowired
  public void setLoginRepository(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
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

    setStoragePeriod(gateInReponse.getExportContainers());
    setSCN(gateInReponse.getExportContainers());
    return gateInReponse;

  }


  public List<ExportContainer> fetchContainerInfo(List<String> exportContainerNumbers) {

    return null;

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void setStoragePeriod(List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      List<String> shippingCodes =
          exportContainers.stream().map(ExportContainer::getShipCode).collect(Collectors.toList());

      Optional<List<ShipCode>> optionalShipCodelist =
          shipCodeRepository.findByShipStatusAndShippingCodeIn(ShipStatus.ACTIVE, shippingCodes);

      List<ShipCode> shipCodelist = optionalShipCodelist.orElseThrow(() -> new ResultsNotFoundException(
          "Ship Code could be found for the given Ship Codes ! " + String.join(",", shippingCodes)));

      exportContainers.forEach(exportContainer -> {
        shipCodelist.forEach(shipCode -> {
          if (StringUtils.equals(shipCode.getShippingCode(), exportContainer.getShipCode())) {
            exportContainer.setStoragePeriod(shipCode.getStoragePeriod());
          }
        });
      });
    }

  }



  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void setSCN(List<ExportContainer> exportContainers) {

    String exportContainer01 = null;
    String scn01 = null;
    String exportContainer02 = null;
    String scn02 = null;

    if (!(exportContainers == null || exportContainers.isEmpty())) {

      for (ExportContainer container : exportContainers) {
        if (StringUtils.isBlank(exportContainer01)) {
          exportContainer01 = container.getContainer().getContainerNumber();
          scn01 = container.getScn().getScnNo();
        } else {
          exportContainer02 = container.getContainer().getContainerNumber();
          scn02 = container.getScn().getScnNo();
        }
      }

      Optional<List<ShipSCN>> optionalshipSCN =
          shipSCNRepository.fetchContainerSCN(scn01, exportContainer01, scn02, exportContainer02);

      if (optionalshipSCN.isPresent()) {
        List<ShipSCN> shipSCNlist = optionalshipSCN.get();
        exportContainers.forEach(exportContainer -> {
          shipSCNlist.forEach(shipSCN -> {
            if (StringUtils.equals(exportContainer.getContainer().getContainerNumber(), shipSCN.getContainerNo())) {
              ShipSCNDTO shipSCNDTO = new ShipSCNDTO();
              modelMapper.map(shipSCN, shipSCNDTO);
              exportContainer.setScn(shipSCNDTO);
              exportContainer.setBypassEEntry(shipSCN.getScnByPass());
              exportContainer.setRegisteredInEarlyEntry(true);
            }
          });
        });
      } else {
        throw new ResultsNotFoundException("Ship SCN could be found for the given " + "	SCN / ContainerNo Info! "
            + scn01 + " / " + exportContainer01 + " OR " + scn02 + " / " + exportContainer02);
      }

    }

  }

  public List<ExportContainer> saveGateInInfo(GateInWriteRequest gateInWriteRequest) {
    // construct a new export entity for each exportcontainer and save
    if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
      gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
        if (exportContainer.getBaseCommonGateInOutAttribute() == null) {
          exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
        }
        System.out.println("gateInWriteRequest.getGateInDateTime() " + gateInWriteRequest.getGateInDateTime());
        exportContainer.getBaseCommonGateInOutAttribute()
            .setTimeGateIn(CommonUtil.getParsedDate(gateInWriteRequest.getGateInDateTime()));
        Exports exports = new Exports();
        System.out.println("exportContainer " + exportContainer);
        modelMapper.map(exportContainer, exports);
        System.out.println("exports after modal map from exportContainer " + exports);
        SystemUser gateInClerk = null;
        System.out.println("gateInWriteRequest.getUserName() " + gateInWriteRequest.getUserName());
        Login login = loginRepository.findByUserName(gateInWriteRequest.getUserName()).orElse(null);
        if (!(login == null)) {
          gateInClerk = login.getSystemUser();
        }
        System.out.println("gateInClerk " + gateInClerk);
        Card card = null;
        HPATBooking hpatBooking = null;
        if (!(exportContainer.getBaseCommonGateInOutAttribute() == null)) {
          if (StringUtils.isNotEmpty(exportContainer.getBaseCommonGateInOutAttribute().getHpatBooking())) {
            hpatBooking = hpatBookingRepository
                .findOne(exportContainer.getBaseCommonGateInOutAttribute().getHpatBooking()).orElse(null);
          }
          if (!(exportContainer.getBaseCommonGateInOutAttribute().getCard() == null)) {
            card = cardRepository.findOne(exportContainer.getBaseCommonGateInOutAttribute().getCard()).orElse(null);
          }
        }
        Client gateInClient = null;
        if (!(exportContainer.getBaseCommonGateInOutAttribute() == null
            || exportContainer.getBaseCommonGateInOutAttribute().getGateInClient() == null)) {
          gateInClient = clientRepository
              .findOne(exportContainer.getBaseCommonGateInOutAttribute().getGateInClient().getClientID()).orElse(null);
        }
        ShipSCN scn = null;
        if (!(exportContainer.getScn() == null)) {
          scn = shipSCNRepository.findOne(exportContainer.getScn().getShipSCNID()).orElse(null);
        }
        PrintEir printEir = null;
        if (!(exportContainer.getPrintEir() == null || exportContainer.getPrintEir().getPrintEIRID() == null)) {
          printEir = printEirRepository.findOne(exportContainer.getPrintEir().getPrintEIRID()).orElse(null);
        }
        CardUsage cardUsage = null;
        if (!(exportContainer.getCardUsage() == null || exportContainer.getCardUsage().getCardUsageID() == null)) {
          cardUsage = cardUsageRepository.findOne(exportContainer.getCardUsage().getCardUsageID()).orElse(null);
        }
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
