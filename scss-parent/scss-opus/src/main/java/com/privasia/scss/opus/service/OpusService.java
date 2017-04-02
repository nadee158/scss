package com.privasia.scss.opus.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.CommonSolasDTO;
import com.privasia.scss.common.dto.DamageCodeDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.service.export.ExportUtilService;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.GIReadResponseExporterContainer;
import com.privasia.scss.opus.dto.GIReadResponseImportContainer;
import com.privasia.scss.opus.dto.GIWriteRequestExportContainer;
import com.privasia.scss.opus.dto.GIWriteRequestImportContainer;
import com.privasia.scss.opus.dto.GIWriteResponseExportContainer;
import com.privasia.scss.opus.dto.GIWriteResponseImportContainer;
import com.privasia.scss.opus.dto.GOReadResponseExportContainer;
import com.privasia.scss.opus.dto.GOReadResponseImportContainer;
import com.privasia.scss.opus.dto.GOWriteRequestExportContainer;
import com.privasia.scss.opus.dto.GOWriteRequestImportContainer;
import com.privasia.scss.opus.dto.OpusErrorListItem;

@Service("opusService")
public class OpusService {

  private static final Logger log = LoggerFactory.getLogger(OpusService.class);

  private static final String EMPTY_SPACE = StringUtils.EMPTY;
  private static final String SEPERATOR = ",";

  public GIWriteRequestImportContainer importContainerToGIWriteRequestImportContainer(ImportContainer importContainer) {

    GIWriteRequestImportContainer giWriteRequestImpContainer = new GIWriteRequestImportContainer();
    CommonContainerDTO commonContainerDTO = importContainer.getContainer();
    giWriteRequestImpContainer.setContainerNo(commonContainerDTO.getContainerNumber());
    giWriteRequestImpContainer.setContainerFullOrEmpty(commonContainerDTO.getContainerFullOrEmpty());
    giWriteRequestImpContainer.setPositionOnTruck(importContainer.getContainerPosition());

    return giWriteRequestImpContainer;
  }

  public GIWriteRequestExportContainer exportContainerToGIWriteRequestExportContainer(ExportContainer exportContainer) {

    GIWriteRequestExportContainer giWriteRequestExpContainer = new GIWriteRequestExportContainer();
    CommonContainerDTO commonContainerDTO = exportContainer.getContainer();
    CommonSealDTO sealDTO = exportContainer.getSealAttribute();
    giWriteRequestExpContainer.setContainerNo(commonContainerDTO.getContainerNumber());
    giWriteRequestExpContainer.setContainerIso(commonContainerDTO.getContainerISOCode());

    if (exportContainer.getExpNetWeight() != null) {
      giWriteRequestExpContainer.setContainerNetWeight(String.valueOf(exportContainer.getExpNetWeight()));
    }

    if (sealDTO != null) {
      giWriteRequestExpContainer.setContainerSeal1_NO(sealDTO.getSeal01Number());
      giWriteRequestExpContainer.setContainerSeal2_NO(sealDTO.getSeal02Number());
      giWriteRequestExpContainer.setContainerSeal1_SL(sealDTO.getSeal01Origin());
      giWriteRequestExpContainer.setContainerSeal2_SL(sealDTO.getSeal02Origin());
    }
    giWriteRequestExpContainer.setContainerReeferIndicator(exportContainer.getOperationReefer());

    if (exportContainer.getReferTemp() != null) {
      giWriteRequestExpContainer
          .setContainerReeferTempSign(ExportUtilService.getReferTempSign(exportContainer.getReferTemp()));
      giWriteRequestExpContainer.setContainerReeferTempUnit(exportContainer.getReeferTempUnit());
      giWriteRequestExpContainer.setContainerReeferTempValue(String.valueOf(Math.abs(exportContainer.getReferTemp())));
    }

    giWriteRequestExpContainer.setContainerDGImdg(exportContainer.getImdg());
    giWriteRequestExpContainer.setContainerDGUNCode(exportContainer.getDgUNCode());
    giWriteRequestExpContainer.setContainerDGImdgLabel(exportContainer.getImdgLabelID());

    if (exportContainer.getOogOA() != null) {
      giWriteRequestExpContainer.setContainerOOG_OA(String.valueOf(exportContainer.getOogOA()));
    }

    if (exportContainer.getOogOF() != null) {
      giWriteRequestExpContainer.setContainerOOG_OF(String.valueOf(exportContainer.getOogOF()));
    }

    if (exportContainer.getOogOH() != null) {
      giWriteRequestExpContainer.setContainerOOG_OH(String.valueOf(exportContainer.getOogOH()));
    }

    if (exportContainer.getOogOL() != null) {
      giWriteRequestExpContainer.setContainerOOG_OL(String.valueOf(exportContainer.getOogOL()));
    }

    if (exportContainer.getOogOR() != null) {
      giWriteRequestExpContainer.setContainerOOG_OR(String.valueOf(exportContainer.getOogOR()));
    }
    giWriteRequestExpContainer.setContainerDamageCode1(exportContainer.getDamageCode_01().getDamageCode());
    giWriteRequestExpContainer.setContainerDamageCode2(exportContainer.getDamageCode_02().getDamageCode());
    giWriteRequestExpContainer.setContainerDamageCode3(exportContainer.getDamageCode_03().getDamageCode());
    giWriteRequestExpContainer.setContainerDamageCode4(exportContainer.getDamageCode_04().getDamageCode());
    giWriteRequestExpContainer.setContainerDamageCode5(exportContainer.getDamageCode_05().getDamageCode());
    giWriteRequestExpContainer.setContainerDamageCode6(exportContainer.getDamageCode_06().getDamageCode());
    giWriteRequestExpContainer.setContainerDamageCode7(exportContainer.getDamageCode_07().getDamageCode());
    giWriteRequestExpContainer.setContainerDamageCode8(exportContainer.getDamageCode_08().getDamageCode());
    giWriteRequestExpContainer.setContainerDamageCode9(exportContainer.getDamageCode_09().getDamageCode());

    giWriteRequestExpContainer.setPositionOnTruck(exportContainer.getContainerPosition());
    giWriteRequestExpContainer
        .setWrongDoorIndicator(ExportUtilService.getStringRepresentationOfBoolean(exportContainer.getWrongDoor()));
    giWriteRequestExpContainer
        .setContainerHasOCSSR(ExportUtilService.getStringRepresentationOfBoolean(exportContainer.getOverClosingSSR()));
    giWriteRequestExpContainer
        .setContainerHasRPSSR(ExportUtilService.getStringRepresentationOfBoolean(exportContainer.getReplanSSR()));
    giWriteRequestExpContainer
        .setContainerHasOOGSSR(ExportUtilService.getStringRepresentationOfBoolean(exportContainer.getOogSSR()));

    CommonSolasDTO solasDTO = exportContainer.getSolas();
    if (solasDTO != null) {
      giWriteRequestExpContainer.setVgmType(ExportUtilService.getSolasVGMType(solasDTO.getSolasInstruction()));
      giWriteRequestExpContainer.setVgmWitnessName(solasDTO.getVgmWitnessName());
      giWriteRequestExpContainer.setVgmWitnessID(solasDTO.getVgmWitnessID());
      giWriteRequestExpContainer.setVgmNetWeight(solasDTO.getVgmWeighingStation());
      giWriteRequestExpContainer.setVgmRefNo(exportContainer.getSolasCertNo());
      giWriteRequestExpContainer.setVgmMGW(String.valueOf(solasDTO.getMgw()));
      giWriteRequestExpContainer.setVgmNetWeight(String.valueOf(exportContainer.getExpNetWeight()));
      giWriteRequestExpContainer.setVgmSolasRefNo(solasDTO.getSolasRefNumber());
      giWriteRequestExpContainer.setVgmSolasWithinTolerance(
          ExportUtilService.getStringRepresentationOfBoolean(exportContainer.isWithinTolerance()));
      String verificationDatetime =
          DateUtil.getJsonDateFromDate(exportContainer.getBaseCommonGateInOutAttribute().getTimeGateOut());
      giWriteRequestExpContainer.setVgmVerificationDatetime(verificationDatetime);

    }
    return giWriteRequestExpContainer;
  }

  public ExportContainer giWriteResponseExportContainerToExportContainer(
      GIWriteResponseExportContainer giWriteResponseExportContainer) {
    ExportContainer exportContainer = new ExportContainer();
    CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
    commonContainerDTO.setContainerNumber(giWriteResponseExportContainer.getContainerNo());
    exportContainer.setContainer(commonContainerDTO);
    exportContainer.setYardPosition(giWriteResponseExportContainer.getYardPosition());
    exportContainer.setYardBayCode(giWriteResponseExportContainer.getYardBayCode());
    exportContainer.setManualPlanIndicator(giWriteResponseExportContainer.getManualPlanIndicator());
    return exportContainer;
  }

  public ImportContainer giWriteResponseImportContainerToImportContainer(
      GIWriteResponseImportContainer giWriteResponseImportContainer) {
    ImportContainer importContainer = new ImportContainer();

    CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
    commonContainerDTO.setContainerNumber(giWriteResponseImportContainer.getContainerNo());
    importContainer.setContainer(commonContainerDTO);
    importContainer.setYardPosition(giWriteResponseImportContainer.getYardPosition());
    importContainer.setYardBayCode(giWriteResponseImportContainer.getYardBayCode());
    return importContainer;
  }

  public ImportContainer giReadResponseImportContainerToImportContainer(
      GIReadResponseImportContainer gIReadResponseImportContainer, ImportContainer importContainer) {

    if (importContainer == null) {
      importContainer = new ImportContainer();
    }
    if (importContainer.getContainer() == null) {
      importContainer.setContainer(new CommonContainerDTO());
    }
    // private String containerNumber; //EPLA0000002
    //importContainer.getContainer().setContainerNumber(gIReadResponseImportContainer.getContainerNo());
    importContainer.setOrderFOT(gIReadResponseImportContainer.getImpOrderNo());
    importContainer.setImpOrderStatus(gIReadResponseImportContainer.getImpOrderStatus());
    importContainer.setImpOrderType(gIReadResponseImportContainer.getImpOrderType());
    // private String containerInOrOut;// OUT,
    importContainer.setGateInOut(gIReadResponseImportContainer.getContainerInOrOut());
    // private String containerShippingLine;// CMA,
    importContainer.setShippingLine(gIReadResponseImportContainer.getContainerShippingLine());
    importContainer.setShippingAgent(gIReadResponseImportContainer.getContainerShippingAgent());
    // private String containerFullOrEmpty;// F,
    importContainer.getContainer().setContainerFullOrEmpty(gIReadResponseImportContainer.getContainerFullOrEmpty());
    importContainer.setContainerSubHandlingType(gIReadResponseImportContainer.getContainerSubHandlingType());
    // private double containerHeight;// 8,
    importContainer.getContainer().setContainerHeight(
        ExportUtilService.getIntegerValueFromString(gIReadResponseImportContainer.getContainerHeight()));
    // private String containerIso;// 4001,
    importContainer.getContainer().setContainerISOCode(gIReadResponseImportContainer.getContainerIso());
    // private double containerSize;// 40,
    importContainer.setContainerLength(
        ExportUtilService.getIntegerValueFromString(gIReadResponseImportContainer.getContainerSize()));
    // private String containerType;// GE,
    importContainer.setContainerType(gIReadResponseImportContainer.getContainerType());
    importContainer
        .setTareWeight(ExportUtilService.getIntValueFromString(gIReadResponseImportContainer.getContainerTareWeight()));
    importContainer.setContainerDischargeDateTime(
        DateUtil.getLocalDategFromString(gIReadResponseImportContainer.getContainerDischargeDateTime()));
    // private String currentYardPosition;// 02S-0102-C-1,
    importContainer.setYardPosition(gIReadResponseImportContainer.getCurrentYardPosition());
    importContainer.setImpCarrierType(gIReadResponseImportContainer.getImpCarrierType());
    // private String vesselCode;// CSQD,
    importContainer.setVesselCode(gIReadResponseImportContainer.getVesselCode());
    importContainer.setVesselVoyage(gIReadResponseImportContainer.getVesselVoyage());
    importContainer.setVesselVisitID(gIReadResponseImportContainer.getVisitId());
    // private String vesselScn;// ETX9,
    importContainer.setVesselScn(gIReadResponseImportContainer.getVesselScn());
    // private String vesselName;// XIN QING DAO,
    importContainer.setVesselName(gIReadResponseImportContainer.getVesselName());
    // private String vesselETA;// 20160907080000,
    importContainer.setVesselETADate(DateUtil.getLocalDategFromString(gIReadResponseImportContainer.getVesselETA()));
    importContainer.setVesselATADate(DateUtil.getLocalDategFromString(gIReadResponseImportContainer.getVesselATA()));
    importContainer.setVesselStatus(gIReadResponseImportContainer.getVesselStatus());

    return importContainer;
  }

  public ExportContainer giReadResponseExporterContainerToExportContainer(
      GIReadResponseExporterContainer gIReadResponseExporterContainer, ExportContainer exportContainer) {

    if (exportContainer == null) {
      exportContainer = new ExportContainer();
    }

    if (exportContainer.getContainer() == null) {
      exportContainer.setContainer(new CommonContainerDTO());
    }
    // private String containerNo;// ;//AZHA0000001,
    //exportContainer.getContainer().setContainerNumber(gIReadResponseExporterContainer.getContainerNo());
    // private String exportBookingNo;// BOOK20001,
    exportContainer.setBookingNo(gIReadResponseExporterContainer.getExportOrderNo());
    exportContainer.setExportOrderStatus(gIReadResponseExporterContainer.getExportOrderStatus());
    exportContainer.setExportOrderType(gIReadResponseExporterContainer.getExportOrderType());
    // private String containerInOrOut;// IN,
    exportContainer.setGateInOut(gIReadResponseExporterContainer.getContainerInOrOut());
    exportContainer.setShippingLine(gIReadResponseExporterContainer.getContainerShippingLine());
    // private String containerFullOrEmpty;// F,
    exportContainer.getContainer().setContainerFullOrEmpty(gIReadResponseExporterContainer.getContainerFullOrEmpty());
    // private String subHandlingType;// null,
    exportContainer.setSubHandlingType(gIReadResponseExporterContainer.getContainerSubHandlingType());
    // private String expCarrierType;// null,
    exportContainer.setExpCarrierType(gIReadResponseExporterContainer.getExpCarrierType());
    // private String vesselCode;// CSQD,
    exportContainer.setVesselCode(gIReadResponseExporterContainer.getVesselCode());
    exportContainer.setVesselVoyageIN(gIReadResponseExporterContainer.getVesselVoyageIN());
    exportContainer.setVesselVoyageOUT(gIReadResponseExporterContainer.getVesselVoyageOUT());
    exportContainer.setVesselVisitID(gIReadResponseExporterContainer.getVisitId());
    // private String vesselScn;// ETX9,
    exportContainer.setVesselSCN(gIReadResponseExporterContainer.getVesselScn());
    // private String vesselName;// XIN QING DAO,
    exportContainer.setVesselName(gIReadResponseExporterContainer.getVesselName());
    // private String vesselETA;// 20160907080000,
    exportContainer.setVesselETADate(DateUtil.getLocalDategFromString(gIReadResponseExporterContainer.getVesselETA()));
    exportContainer.setVesselATADate(DateUtil.getLocalDategFromString(gIReadResponseExporterContainer.getVesselATA()));
    exportContainer.setVesselStatus(gIReadResponseExporterContainer.getVesselStatus());
    // private String yardOpeningDateTime;// 20160902080000,
    exportContainer.setYardOpeningDateTime(
        DateUtil.getLocalDategFromString(gIReadResponseExporterContainer.getYardOpeningDateTime()));
    // private String containerSpod;// USSVN,
    exportContainer.setExpSpod(gIReadResponseExporterContainer.getContainerSpod());
    // private String containerIso;// 22G0,
    exportContainer.getContainer().setContainerISOCode(gIReadResponseExporterContainer.getContainerIso());
    // private double containerHeight;// 8.6,
    exportContainer.getContainer().setContainerHeight(
        ExportUtilService.getIntValueFromString(gIReadResponseExporterContainer.getContainerHeight()));
    // private String containerType;// GE,
    exportContainer.setContainerType(gIReadResponseExporterContainer.getContainerType());
    exportContainer.getContainer().setContainerSize(gIReadResponseExporterContainer.getContainerSize());
    // private double grossWeight;// 11000,
    exportContainer.setGrossWeight(
        ExportUtilService.getIntValueFromString(gIReadResponseExporterContainer.getContainerGrossWeight()));
    // private double containerNetWeight;// 9000,
    exportContainer
        .setNetWeight(ExportUtilService.getIntValueFromString(gIReadResponseExporterContainer.getContainerNetWeight()));
    exportContainer.setTareWeight(
        ExportUtilService.getIntValueFromString(gIReadResponseExporterContainer.getContainerTareWeight()));

    if (exportContainer.getSealAttribute() == null) {
      exportContainer.setSealAttribute(new CommonSealDTO());
    }
    // private String containerSeal1_SL;// null,
    exportContainer.getSealAttribute().setSeal01Origin(gIReadResponseExporterContainer.getContainerSeal1_SL());
    // private String containerSeal1_NO;// SEAL001,
    exportContainer.getSealAttribute().setSeal01Number(gIReadResponseExporterContainer.getContainerSeal1_NO());
    // private String containerSeal2_SL;// null,
    exportContainer.getSealAttribute().setSeal02Origin(gIReadResponseExporterContainer.getContainerSeal2_SL());
    // private String containerSeal2_NO;// null,
    exportContainer.getSealAttribute().setSeal02Origin(gIReadResponseExporterContainer.getContainerSeal2_NO());

    // private String containerReeferIndicator;// N,
    exportContainer.setReferFlag(
        ExportUtilService.getBooleanFromString(gIReadResponseExporterContainer.getContainerReeferIndicator()));
    // private String containerReeferTempSign;// null,
    exportContainer.setReferTempType(gIReadResponseExporterContainer.getContainerReeferTempSign());
    // private String containerReeferTempValue;// null,
    if (StringUtils.isNotEmpty(gIReadResponseExporterContainer.getContainerReeferTempValue())) {
      exportContainer.setReferTemp(
          ExportUtilService.getIntegerValueFromString(gIReadResponseExporterContainer.getContainerReeferTempValue()));
    }
    // private String containerReeferTempUnit;// null,
    exportContainer.setReeferTempUnit(gIReadResponseExporterContainer.getContainerReeferTempUnit());

    // private String containerDGImdg;// null,
    exportContainer.setImdg(gIReadResponseExporterContainer.getContainerDGImdg());
    // private String containerDGUNCode;// null,
    exportContainer.setDgUNCode(gIReadResponseExporterContainer.getContainerDGUNCode());
    // private String containerDGImdgLabel;// null,
    exportContainer.setImdgLabelID(gIReadResponseExporterContainer.getContainerDGImdgLabel());
    // private String yardDGOpeningDateTime;// 20160904080000,
    exportContainer.setYardDGOpeningDateTime(
        DateUtil.getLocalDategFromString(gIReadResponseExporterContainer.getYardDGOpeningDateTime()));

    // private double containerOOG_OH;// 5.0,
    exportContainer
        .setOogOH(ExportUtilService.getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OH()));
    // private double containerOOG_OL;// 3.0,
    exportContainer
        .setOogOL(ExportUtilService.getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OL()));
    // private double containerOOG_OF;// 1.0,
    exportContainer
        .setOogOF(ExportUtilService.getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OF()));
    // private double containerOOG_OA;// 2.0,
    exportContainer
        .setOogOA(ExportUtilService.getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OA()));
    // private double containerOOG_OR;// 4.0
    exportContainer
        .setOogOR(ExportUtilService.getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OR()));
    // private String containerDamageCode1;//
    exportContainer.setDamageCode_01(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode1()));
    // private String containerDamageCode2;//
    exportContainer.setDamageCode_02(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode2()));
    // private String containerDamageCode3;//
    exportContainer.setDamageCode_03(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode3()));
    // private String containerDamageCode4;//
    exportContainer.setDamageCode_04(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode4()));
    // private String containerDamageCode5;//
    exportContainer.setDamageCode_05(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode5()));
    // private String containerDamageCode6;//
    exportContainer.setDamageCode_06(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode6()));
    // private String containerDamageCode7;//
    exportContainer.setDamageCode_07(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode7()));
    // private String containerDamageCode8;//
    exportContainer.setDamageCode_08(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode8()));
    // private String containerDamageCode9;//
    exportContainer.setDamageCode_09(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode9()));


    return exportContainer;
  }

  public ExportContainer goReadResponseExporterContainerToExportContainer(
      GOReadResponseExportContainer goReadResponseExporterContainer, ExportContainer exportContainer) {
	
	if(exportContainer.getContainer() == null){
	    CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
	    exportContainer.setContainer(commonContainerDTO);
	}
    exportContainer.getContainer().setContainerFullOrEmpty(goReadResponseExporterContainer.getContainerFullOrEmpty());
    exportContainer.setRtgExecustionDateTime(
        DateUtil.getLocalDategFromString(goReadResponseExporterContainer.getRtgExecustionDateTime()));
    exportContainer.setRtgExecustionStatus(goReadResponseExporterContainer.getRtgExecustionStatus());
 
    return exportContainer;
  }

  public ImportContainer goReadResponseImportContainerToImportContainer(
      GOReadResponseImportContainer goReadResponseImportContainer, ImportContainer importContainer) {
    
    if(importContainer.getSealAttribute() == null){
    	CommonSealDTO sealAttribute = new CommonSealDTO();
    	importContainer.setSealAttribute(sealAttribute);
    }
    // private String containerFullOrEmpty;// F,
    importContainer.getContainer().setContainerFullOrEmpty(goReadResponseImportContainer.getContainerFullOrEmpty());
    importContainer.setRtgExecustionDateTime(
        DateUtil.getLocalDategFromString(goReadResponseImportContainer.getRtgExecustionDateTime()));
    importContainer.setRtgExecustionStatus(goReadResponseImportContainer.getRtgExecustionStatus());

    importContainer.getSealAttribute().setSeal01Number(goReadResponseImportContainer.getContainerSeal1_NO());
    importContainer.getSealAttribute().setSeal02Number(goReadResponseImportContainer.getContainerSeal2_NO());
    importContainer.getSealAttribute().setSeal01Origin(goReadResponseImportContainer.getContainerSeal1_SL());
    importContainer.getSealAttribute().setSeal02Origin(goReadResponseImportContainer.getContainerSeal2_SL());
    return importContainer;
  }

  public GOWriteRequestExportContainer exportContainerToGOWriteRequestExportContainer(ExportContainer exportContainer) {

    GOWriteRequestExportContainer goWriteRequestExpContainer = new GOWriteRequestExportContainer();
    goWriteRequestExpContainer.setContainerNo(exportContainer.getContainer().getContainerNumber());
    return goWriteRequestExpContainer;

  }

  public GOWriteRequestImportContainer importContainerToGOWriteRequestImportContainer(ImportContainer importContainer) {

    GOWriteRequestImportContainer goWriteRequestImpContainer = new GOWriteRequestImportContainer();

    CommonSealDTO sealDTO = importContainer.getSealAttribute();
    goWriteRequestImpContainer.setContainerNo(importContainer.getContainer().getContainerNumber());
    if (sealDTO != null) {
      goWriteRequestImpContainer.setContainerSeal1_NO(sealDTO.getSeal01Number());
      goWriteRequestImpContainer.setContainerSeal2_NO(sealDTO.getSeal02Number());
      goWriteRequestImpContainer.setContainerSeal1_SL(sealDTO.getSeal01Origin());
      goWriteRequestImpContainer.setContainerSeal2_SL(sealDTO.getSeal02Origin());

    }
    return goWriteRequestImpContainer;

  }

  public ExportContainer goWriteRequestExportContainerToExportContainer(
      GOWriteRequestExportContainer goWriteRequestExpContainer) {

    ExportContainer expContainer = new ExportContainer();
    CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
    commonContainerDTO.setContainerNumber(goWriteRequestExpContainer.getContainerNo());
    expContainer.setContainer(commonContainerDTO);
    return expContainer;
  }

  public ImportContainer goWriteRequestImportContainerToImportContainer(
      GOWriteRequestImportContainer goWriteRequestImpContainer) {

    ImportContainer impContainer = new ImportContainer();
    CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
    commonContainerDTO.setContainerNumber(goWriteRequestImpContainer.getContainerNo());
    impContainer.setContainer(commonContainerDTO);
    return impContainer;
  }



  private static DamageCodeDTO constructDamageCode(String containerDamageCode) {
    DamageCodeDTO damageCodeDTO = new DamageCodeDTO();
    damageCodeDTO.setDamageCode(containerDamageCode);
    return damageCodeDTO;
  }



  private static String getDamageCodeFromObject(DamageCodeDTO damageCode) {
    if (!(damageCode == null)) {
      return damageCode.getDamageCode();
    }
    return null;
  }

  public String hasErrorMessage(List<OpusErrorListItem> errorList) {
    // String hasErrorMessage(List<Error>) in opus service
    // check if error list object is empty
    if (!(errorList == null || errorList.isEmpty())) {
      StringBuilder sb = new StringBuilder("OPUS_ERROR_MESSAGE :");
      // if not empty construct error string
      // container number + error description, other eg:- NH161219003
      // There does no Truck In plan
      // for
      errorList.forEach(error -> {
        sb.append(EMPTY_SPACE).append(error.getContainerNo()).append(EMPTY_SPACE).append(error.getErrorDescription())
            .append(SEPERATOR);
      });
      String errorMessage = sb.toString();
      if (errorMessage.startsWith(EMPTY_SPACE)) {
        errorMessage = errorMessage.substring(1, errorMessage.length());
      }
      if (errorMessage.endsWith(SEPERATOR)) {
        errorMessage = errorMessage.substring(0, (errorMessage.length() - 1));
      }
      System.out.println("errorMessage " + errorMessage);
      return errorMessage;
    }
    return null;
  }

  public List<ExportContainer> giWriteResponseExportContainerListToExportContainerList(
      List<GIWriteResponseExportContainer> gIReadResponseExporterContainerList) {
    if (!(gIReadResponseExporterContainerList == null || gIReadResponseExporterContainerList.isEmpty())) {
      List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
      gIReadResponseExporterContainerList.forEach(gIReadResponseExporterContainer -> {
        exportContainers.add(giWriteResponseExportContainerToExportContainer(gIReadResponseExporterContainer));
      });
      return exportContainers;
    }
    return null;
  }

  public List<ImportContainer> giWriteResponseImportContainerListToImportContainerList(
      List<GIWriteResponseImportContainer> giWriteResponseImportContainerList) {
    if (!(giWriteResponseImportContainerList == null || giWriteResponseImportContainerList.isEmpty())) {
      List<ImportContainer> imortContainers = new ArrayList<ImportContainer>();
      giWriteResponseImportContainerList.forEach(giWriteResponseImportContainer -> {
        imortContainers.add(giWriteResponseImportContainerToImportContainer(giWriteResponseImportContainer));
      });
      return imortContainers;
    }
    return null;
  }

  public List<GIWriteRequestExportContainer> exportContainerListToGIWriteRequestExportContainerList(
      List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      List<GIWriteRequestExportContainer> giWriteRequestExportContainers =
          new ArrayList<GIWriteRequestExportContainer>();
      exportContainers.forEach(exportContainer -> {
        giWriteRequestExportContainers.add(exportContainerToGIWriteRequestExportContainer(exportContainer));
      });
      return giWriteRequestExportContainers;
    }
    return null;
  }

  public List<GIWriteRequestImportContainer> importContainerListToGIWriteRequestImportContainerList(
      List<ImportContainer> importContainers) {
    if (!(importContainers == null || importContainers.isEmpty())) {
      List<GIWriteRequestImportContainer> giWriteRequestImportContainers =
          new ArrayList<GIWriteRequestImportContainer>();
      importContainers.forEach(importContainer -> {
        giWriteRequestImportContainers.add(importContainerToGIWriteRequestImportContainer(importContainer));
      });
      return giWriteRequestImportContainers;
    }
    return null;
  }

  public List<ImportContainer> goReadResponseImportContainerListToImportContainerList(
      List<GOReadResponseImportContainer> goReadResponseImportContainerList, List<ImportContainer> importContainers) {
    
	  if (!(goReadResponseImportContainerList == null || goReadResponseImportContainerList.isEmpty())) {
   
		  goReadResponseImportContainerList.forEach(goReadResponseImportContainer -> {
			  ImportContainer importContainer = null;	
				if (!(importContainers == null || importContainers.isEmpty())) {
					importContainer =
							importContainers
			                  .stream().filter(e -> (e.getContainer() != null) && (StringUtils
			                      .equals(e.getContainer().getContainerNumber(), goReadResponseImportContainer.getContainerNo())))
			                  .findFirst().get();
			    }else{
			    	importContainer = new ImportContainer();
			    }
				importContainer = goReadResponseImportContainerToImportContainer(goReadResponseImportContainer, importContainer);
      });
      return importContainers;
    }
    return null;
  }

  public List<ExportContainer> goReadResponseExportContainerListToExportContainerList(
      List<GOReadResponseExportContainer> goReadResponseExportContainerList, List<ExportContainer> exportContainers) {
	  
    if (!(goReadResponseExportContainerList == null || goReadResponseExportContainerList.isEmpty())) {
    	goReadResponseExportContainerList.forEach(goReadResponseExportContainer -> {
    	ExportContainer exportContainer = null;	
		if (!(exportContainers == null || exportContainers.isEmpty())) {
	          exportContainer =
	              exportContainers
	                  .stream().filter(e -> (e.getContainer() != null) && (StringUtils
	                      .equals(e.getContainer().getContainerNumber(), goReadResponseExportContainer.getContainerNo())))
	                  .findFirst().get();
	    }else{
	    	exportContainer = new ExportContainer();
	    }
		exportContainer = goReadResponseExporterContainerToExportContainer(goReadResponseExportContainer, exportContainer);
      });
      return exportContainers;
    }
    return null;
  }

  public List<ExportContainer> goWriteRequestExportContainerListToExportContainerList(
      List<GOWriteRequestExportContainer> goWriteRequestExportContainerList) {
    if (!(goWriteRequestExportContainerList == null || goWriteRequestExportContainerList.isEmpty())) {
      List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
      goWriteRequestExportContainerList.forEach(goWriteRequestExportContainer -> {
        exportContainers.add(goWriteRequestExportContainerToExportContainer(goWriteRequestExportContainer));
      });
      return exportContainers;
    }
    return null;
  }

  public List<ImportContainer> goWriteRequestImportContainerListToImportContainerList(
      List<GOWriteRequestImportContainer> goWriteRequestImportContainerList) {
    if (!(goWriteRequestImportContainerList == null || goWriteRequestImportContainerList.isEmpty())) {
      List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
      goWriteRequestImportContainerList.forEach(goWriteRequestImportContainer -> {
        importContainers.add(goWriteRequestImportContainerToImportContainer(goWriteRequestImportContainer));
      });
      return importContainers;
    }
    return null;
  }


  public List<GOWriteRequestExportContainer> exportContainerListToGOWriteRequestExportContainerList(
      List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      List<GOWriteRequestExportContainer> goWriteRequestExportContainerList =
          new ArrayList<GOWriteRequestExportContainer>();
      exportContainers.forEach(exportContainer -> {
        goWriteRequestExportContainerList.add(exportContainerToGOWriteRequestExportContainer(exportContainer));
      });
      return goWriteRequestExportContainerList;
    }
    return null;
  }

  public List<GOWriteRequestImportContainer> importContainerListToGOWriteRequestImportContainerList(
      List<ImportContainer> importContainers) {
    if (!(importContainers == null || importContainers.isEmpty())) {
      List<GOWriteRequestImportContainer> goWriteRequestImportContainerList =
          new ArrayList<GOWriteRequestImportContainer>();
      importContainers.forEach(importContainer -> {
        goWriteRequestImportContainerList.add(importContainerToGOWriteRequestImportContainer(importContainer));
      });
      return goWriteRequestImportContainerList;
    }
    return null;
  }

  public List<ExportContainer> giReadResponseExporterContainerListToExportContainerList(
      List<GIReadResponseExporterContainer> exportContainerListCY, List<ExportContainer> exportContainers) {
    if (!(exportContainerListCY == null || exportContainerListCY.isEmpty())) {
      List<ExportContainer> updatedExportContainers = new ArrayList<ExportContainer>();
      exportContainerListCY.forEach(opusExportContainer -> {
        ExportContainer exportContainer = null;
        if (!(exportContainers == null || exportContainers.isEmpty())) {
          exportContainer =
              exportContainers
                  .stream().filter(e -> (e.getContainer() != null) && (StringUtils
                      .equals(e.getContainer().getContainerNumber(), opusExportContainer.getContainerNo())))
                  .findFirst().get();
        }
        exportContainer = giReadResponseExporterContainerToExportContainer(opusExportContainer, exportContainer);

        log.info("exportContainer.getGateInOut " + exportContainer.getGateInOut());
        log.info("exportContainer.getShippingLine " + exportContainer.getShippingLine());
        if (!(exportContainer.getContainer() == null)) {
          log.info("exportContainer.getContainer().getContainerNumber() "
              + exportContainer.getContainer().getContainerNumber());
        } else {
          log.warn("exportContainer.getContainer().getContainerNumber() is NULL ");
        }
        if (!(exportContainer.getBaseCommonGateInOutAttribute() == null)) {
          log.info("exportContainer.getBaseCommonGateInOutAttribute().getPmHeadNo() "
              + exportContainer.getBaseCommonGateInOutAttribute().getPmHeadNo());
          log.info("exportContainer.getBaseCommonGateInOutAttribute().getPmPlateNo()"
              + exportContainer.getBaseCommonGateInOutAttribute().getPmPlateNo());
        } else {
          log.warn("exportContainer.getBaseCommonGateInOutAttribute() is NULL ");
        }


        updatedExportContainers.add(exportContainer);

      });
      return updatedExportContainers;
    }
    return null;
  }

  public List<ImportContainer> giReadResponseImportContainerListToImportContainerList(
      List<GIReadResponseImportContainer> importContainerListCY, List<ImportContainer> importContainers) {
    if (!(importContainerListCY == null || importContainerListCY.isEmpty())) {
      List<ImportContainer> updatedImportContainers = new ArrayList<ImportContainer>();
      importContainerListCY.forEach(opusExportContainer -> {
        ImportContainer importContainer = null;
        if (!(importContainers == null || importContainers.isEmpty())) {
          importContainer =
              importContainers
                  .stream().filter(e -> (e.getContainer() != null) && (StringUtils
                      .equals(e.getContainer().getContainerNumber(), opusExportContainer.getContainerNo())))
                  .findFirst().get();
        }
        importContainer = giReadResponseImportContainerToImportContainer(opusExportContainer, importContainer);

        log.info("importContainer.getGateInOut " + importContainer.getGateInOut());
        log.info("importContainer.getShippingLine " + importContainer.getShippingLine());
        if (!(importContainer.getContainer() == null)) {
          log.info("importContainer.getContainer().getContainerNumber() "
              + importContainer.getContainer().getContainerNumber());
        } else {
          log.warn("importContainer.getContainer().getContainerNumber() is NULL ");
        }
        if (!(importContainer.getBaseCommonGateInOutAttribute() == null)) {
          log.info("importContainer.getBaseCommonGateInOutAttribute().getPmHeadNo() "
              + importContainer.getBaseCommonGateInOutAttribute().getPmHeadNo());
          log.info("importContainer.getBaseCommonGateInOutAttribute().getPmPlateNo()"
              + importContainer.getBaseCommonGateInOutAttribute().getPmPlateNo());
        } else {
          log.warn("importContainer.getBaseCommonGateInOutAttribute() is NULL ");
        }


        updatedImportContainers.add(importContainer);

      });
      return updatedImportContainers;
    }
    return null;
  }



}
