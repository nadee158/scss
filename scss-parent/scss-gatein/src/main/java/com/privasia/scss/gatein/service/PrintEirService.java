package com.privasia.scss.gatein.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.DGInfo;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.SealInfo;
import com.privasia.scss.common.dto.TransactionDTO;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.common.enums.Nationality;
import com.privasia.scss.core.model.PrintEIRContainerInfo;
import com.privasia.scss.core.model.PrintEir;
import com.privasia.scss.core.repository.PrintEirRepository;

@Service("printEirService")
public class PrintEirService {

  private static final String BLANK = " ";
  private static final String PIPELINE_SEPERATOR = " | ";

  @Autowired
  private PrintEirRepository printEirRepository;

  @Transactional(readOnly = false)
  public void insertScssPrintEir(TransactionDTO transactionDTO, String customerIpAddress) throws Exception {

    ImportContainer importContainer01 = transactionDTO.getImportContainer01();
    ImportContainer importContainer02 = transactionDTO.getImportContainer02();

    StringBuilder lineInfoC1 = null;
    StringBuilder lineInfo2C1 = null;
    StringBuilder lineInfoC2 = null;
    StringBuilder lineInfo2C2 = null;
    String icNoOrPassportNo = "";
    StringBuilder sealC1 = null;
    StringBuilder sealC2 = null;


    /***********************************************************/

    /***************** FIRST Container ********************/
    constructLinesForContainer(importContainer01, lineInfoC1, lineInfo2C1, sealC1);


    /***********************************************************/

    /***************** SECOND Container ********************/
    constructLinesForContainer(importContainer02, lineInfoC2, lineInfo2C2, sealC2);



    /********************************************************/
    if (StringUtils.equals(transactionDTO.getNationality(), Nationality.MALAYSIAN.getValue())) {
      if (StringUtils.isNotBlank(transactionDTO.getNewICNo())) {
        icNoOrPassportNo = transactionDTO.getNewICNo();
      } else {
        icNoOrPassportNo = transactionDTO.getOldICNo();
      }
    } else {
      icNoOrPassportNo = transactionDTO.getPassportNo();
    }

    String weight01 = getContainerWeight(importContainer01, transactionDTO);

    String weight02 = getContainerWeight(importContainer02, transactionDTO);

    PrintEir printEir = new PrintEir();
    printEir.setTimeIn(transactionDTO.getTimeIn());
    printEir.setCallCard(Long.toString(transactionDTO.getCallCard()));
    printEir.setTruckHeadNo(transactionDTO.getPmHeadNo());
    printEir.setCompanyName(transactionDTO.getCompNamePrint());

    PrintEIRContainerInfo eirContainer01 =
        getConstructedEirContainer(importContainer01, weight01, lineInfoC1, lineInfo2C1, sealC1);
    printEir.setContainer01(eirContainer01);

    PrintEIRContainerInfo eirContainer02 =
        getConstructedEirContainer(importContainer02, weight02, lineInfoC2, lineInfo2C2, sealC2);
    printEir.setContainer02(eirContainer02);

    printEir.setIcNoOrPassport(icNoOrPassportNo);
    printEir.setScuName(transactionDTO.getScuName());
    printEir.setTruckNo(transactionDTO.getPmPlateNo());
    printEir.setGateInNo(transactionDTO.getGateInNo());
    printEir.setStatus(HpatReferStatus.ACTIVE);
    printEir.setClientIp(customerIpAddress);

    printEirRepository.save(printEir);

  }



  private PrintEIRContainerInfo getConstructedEirContainer(ImportContainer importContainer, String weight,
      StringBuilder lineInfo, StringBuilder lineInfo2, StringBuilder seal) {
    PrintEIRContainerInfo eirContainer = new PrintEIRContainerInfo();
    eirContainer.setContainerBayCode(importContainer.getBayCode());
    eirContainer.setContainerInOrOut(GateInOutStatus.fromValue(importContainer.getGateInOut()));
    eirContainer.setContainerFullOrEmpty(ContainerFullEmptyType.fromValue(importContainer.getFullOrEmpty()));
    eirContainer.setContainerPositionOnTruck(importContainer.getPositionOnTruck());
    eirContainer.setContainerNumber(importContainer.getContainerNumber());
    eirContainer.setContainerLine(importContainer.getLine());
    eirContainer.setContainerISOCode(importContainer.getIsoCode());
    eirContainer.setContainerLength(importContainer.getContainerLength());
    eirContainer.setContainerHeight(importContainer.getContainerHeight());
    eirContainer.setContainerType(importContainer.getContainerType());
    eirContainer.setContainerNetWeight(weight);
    if (!(lineInfo == null)) {
      eirContainer.setLineOneInfo(lineInfo.toString());
    }
    if (!(lineInfo2 == null)) {
      eirContainer.setLineTwoInfo(lineInfo2.toString());
    }
    if (!(seal == null)) {
      eirContainer.setContainerSeal(seal.toString());
    }
    return eirContainer;
  }



  private void constructLinesForContainer(ImportContainer importContainer, StringBuilder lineInfo,
      StringBuilder lineInfo2, StringBuilder seal) {

    if (!(importContainer == null)) {

      if (StringUtils.equals(importContainer.getFullOrEmpty(), "F")) {

        if (!(importContainer.getSealInfo01() == null)) {
          SealInfo sealInfo01 = importContainer.getSealInfo01();
          lineInfo = constructSealString(sealInfo01, lineInfo);
          seal = constructSealString(sealInfo01, lineInfo);
        }

        if (!(importContainer.getSealInfo02() == null)) {
          SealInfo sealInfo02 = importContainer.getSealInfo02();
          lineInfo = constructSealString(sealInfo02, lineInfo);

          if (!(seal == null || StringUtils.isEmpty(seal.toString()))) {
            seal.append(PIPELINE_SEPERATOR);
          }
          seal = constructSealString(sealInfo02, lineInfo);
        }

      }

      if (StringUtils.equals(importContainer.getOperationReefer(), "Y")) {
        lineInfo.append("Reefer:").append(BLANK).append(importContainer.getOperationReefer()).append(BLANK)
            .append(importContainer.getTemp()).append(BLANK).append(importContainer.getTempUnit()).append(BLANK);
      }

      if (StringUtils.isNotBlank(importContainer.getImdg())) {
        lineInfo = appendToLine("IMDG:", importContainer.getImdg(), lineInfo);
      }

      if (StringUtils.isNotBlank(importContainer.getUnc())) {
        lineInfo = appendToLine("UN:", importContainer.getUnc(), lineInfo);
      }


      // Second Line
      if (StringUtils.isNotBlank(importContainer.getOogoh())) {
        lineInfo2 = appendToLine("OH :", importContainer.getOogoh(), lineInfo2);
      }

      if (StringUtils.isNotBlank(importContainer.getOogol())) {
        lineInfo2 = appendToLine("OL :", importContainer.getOogol(), lineInfo2);
      }

      if (StringUtils.isNotBlank(importContainer.getOogof())) {
        lineInfo2 = appendToLine("OF :", importContainer.getOogof(), lineInfo2);
      }

      if (StringUtils.isNotBlank(importContainer.getOogoa())) {
        lineInfo2 = appendToLine("OA :", importContainer.getOogoa(), lineInfo2);
      }

      if (StringUtils.isNotBlank(importContainer.getOogor())) {
        lineInfo2 = appendToLine("OR :", importContainer.getOogor(), lineInfo2);
      }

      if (!(importContainer.getDgInfo() == null)) {
        DGInfo dgInfo = importContainer.getDgInfo();

        if (StringUtils.isNotEmpty(dgInfo.getDamage1())) {
          lineInfo2 = appendToLine("Damage: ", dgInfo.getDamage1(), lineInfo2);
        }

        if (StringUtils.isNotEmpty(dgInfo.getDamage2())) {
          lineInfo2 = appendToLine(null, dgInfo.getDamage2(), lineInfo2);
        }

        if (StringUtils.isNotEmpty(dgInfo.getDamage3())) {
          lineInfo2 = appendToLine(null, dgInfo.getDamage3(), lineInfo2);
        }

        if (StringUtils.isNotEmpty(dgInfo.getDamage4())) {
          lineInfo2 = appendToLine(null, dgInfo.getDamage4(), lineInfo2);
        }

        if (StringUtils.isNotEmpty(dgInfo.getDamage5())) {
          lineInfo2 = appendToLine(null, dgInfo.getDamage5(), lineInfo2);
        }

      }

    }

  }

  private StringBuilder constructSealString(SealInfo sealInfo, StringBuilder stringBuilder) {
    if (stringBuilder == null) {
      stringBuilder = new StringBuilder("");
    }
    if (!(sealInfo == null)) {
      stringBuilder.append(sealInfo.getSealOrigin()).append(BLANK).append(sealInfo.getSealType()).append(BLANK)
          .append(sealInfo.getSealNo()).append(BLANK);
    }
    return stringBuilder;
  }

  private StringBuilder appendToLine(String prefix, String property, StringBuilder stringBuilder) {
    if (stringBuilder == null) {
      stringBuilder = new StringBuilder("");
    }
    if (StringUtils.isNotEmpty(prefix)) {
      stringBuilder.append(prefix).append(BLANK);
    }
    if (StringUtils.isNotEmpty(property)) {
      stringBuilder.append(prefix).append(property);
    }
    return stringBuilder;
  }


  private String getContainerWeight(ImportContainer importContainer, TransactionDTO transactionDTO) {
    String weight = "";
    if (transactionDTO.isShipperVGM() && importContainer.isWithInTolerance()) {
      weight = String.valueOf(importContainer.getShipperVGM());
    } else {
      if (StringUtils.isNotBlank(importContainer.getNetWeight())) {
        weight = importContainer.getNetWeight();
      }
    }
    return weight;
  }



}
