package com.privasia.scss.gatein.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GatePassValidateDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.CardUsage;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.HPATBooking;
import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.model.PrintEir;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.CardUsageRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.DamageCodeRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.repository.LoginRepository;
import com.privasia.scss.core.repository.PrintEirRepository;

@Service("importGateInService")
public class ImportGateInService {

  private GatePassRepository gatePassRepository;

  private ModelMapper modelMapper;

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
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
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
    // construct a new export entity for each exportcontainer and save
    if (!(gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())) {
      gateInWriteRequest.getImportContainers().forEach(importContainer -> {
        if (importContainer.getBaseCommonGateInOutAttribute() == null) {
          importContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
        }
        System.out.println("gateInWriteRequest.getGateInDateTime() " + gateInWriteRequest.getGateInDateTime());
        importContainer.getBaseCommonGateInOutAttribute()
            .setTimeGateIn(CommonUtil.getParsedDate(gateInWriteRequest.getGateInDateTime()));

        System.out.println("importContainer " + importContainer);

        if (!(importContainer.getGatePassNo() == null)) {

          GatePass gatePass = gatePassRepository.findByGatePassNo(importContainer.getGatePassNo()).orElse(null);
          if (!(gatePass == null)) {

            Card card = null;
            HPATBooking hpatBooking = null;
            if (!(importContainer.getBaseCommonGateInOutAttribute() == null)) {
              if (StringUtils.isNotEmpty(importContainer.getBaseCommonGateInOutAttribute().getHpatBooking())) {
                hpatBooking = hpatBookingRepository
                    .findOne(importContainer.getBaseCommonGateInOutAttribute().getHpatBooking()).orElse(null);
              }
              if (!(importContainer.getBaseCommonGateInOutAttribute().getCard() == null)) {
                card = cardRepository.findOne(importContainer.getBaseCommonGateInOutAttribute().getCard()).orElse(null);
              }
            }

            SystemUser gateInClerk = null;
            System.out.println("gateInWriteRequest.getUserName() " + gateInWriteRequest.getUserName());
            Login login = loginRepository.findByUserName(gateInWriteRequest.getUserName()).orElse(null);
            if (!(login == null)) {
              gateInClerk = login.getSystemUser();
            }
            System.out.println("gateInClerk " + gateInClerk);

            Client gateInClient = null;
            if (!(importContainer.getBaseCommonGateInOutAttribute() == null
                || importContainer.getBaseCommonGateInOutAttribute().getGateInClient() == null)) {
              gateInClient = clientRepository
                  .findOne(importContainer.getBaseCommonGateInOutAttribute().getGateInClient().getClientID())
                  .orElse(null);
            }

            PrintEir printEir = null;
            if (!(importContainer.getPrintEir() == null || importContainer.getPrintEir().getPrintEIRID() == null)) {
              printEir = printEirRepository.findOne(importContainer.getPrintEir().getPrintEIRID()).orElse(null);
            }

            CardUsage cardUsage = null;
            if (!(importContainer.getCardUsage() == null || importContainer.getCardUsage().getCardUsageID() == null)) {
              cardUsage = cardUsageRepository.findOne(importContainer.getCardUsage().getCardUsageID()).orElse(null);
            }


            modelMapper.map(importContainer, gatePass);
            gatePass.prepareForInsertFromOpus(card, gateInClerk, gateInClient, printEir, cardUsage, hpatBooking);
            System.out.println("gatePass after initializeing " + gatePass);
            gatePass = gatePassRepository.save(gatePass);
            System.out.println("gatePass after modal map from importContainer " + gatePass);
          }

        }

      });
      return gateInWriteRequest.getImportContainers();
    }
  }

}
