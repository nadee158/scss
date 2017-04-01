package com.privasia.scss.opus.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.CommonSolasDTO;
import com.privasia.scss.common.dto.DamageCodeDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.service.export.ExportUtilService;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.OpusErrorListItem;
import com.privasia.scss.opus.dto.GIReadResponseExporterContainer;
import com.privasia.scss.opus.dto.GIReadResponseImportContainer;
import com.privasia.scss.opus.dto.GIWriteRequestExportContainer;
import com.privasia.scss.opus.dto.GIWriteRequestImportContainer;
import com.privasia.scss.opus.dto.GOReadResponseExportContainer;
import com.privasia.scss.opus.dto.GOReadResponseImportContainer;
import com.privasia.scss.opus.dto.GOWriteRequestExportContainer;
import com.privasia.scss.opus.dto.GOWriteRequestImportContainer;

@Service("opusService")
public class OpusService {

	private static final Logger log = LoggerFactory.getLogger(OpusService.class);

	private static final String EMPTY_SPACE = StringUtils.EMPTY;
	private static final String SEPERATOR = ",";

	public List<ExportContainer> constructExportContainersFromOpusExportContainers(
			List<GIReadResponseExporterContainer> exportContainerListCY) {
		List<ExportContainer> exportContainerList = null;
		if (!(exportContainerListCY == null || exportContainerListCY.isEmpty())) {
			exportContainerList = new ArrayList<ExportContainer>();
			for (GIReadResponseExporterContainer gIReadResponseExporterContainer : exportContainerListCY) {
				exportContainerList.add(constructExportContainer(gIReadResponseExporterContainer, null));
			}
		}
		return exportContainerList;
	}

	public List<ImportContainer> constructImportContainersFromOpusImportContainers(
			List<GIReadResponseImportContainer> importContainerListCY) {
		List<ImportContainer> importContainerList = null;
		if (!(importContainerListCY == null || importContainerListCY.isEmpty())) {
			importContainerList = new ArrayList<ImportContainer>();
			for (GIReadResponseImportContainer gIReadResponseImportContainer : importContainerListCY) {
				importContainerList.add(constructImportContainer(gIReadResponseImportContainer, null));
			}
		}
		return importContainerList;
	}

	public static List<GIReadResponseExporterContainer> constructOpusExporterContainersFromExporterContainers(
			List<ExportContainer> exportContainers) {
		List<GIReadResponseExporterContainer> exportContainerListCY = new ArrayList<GIReadResponseExporterContainer>();
		;
		if (!(exportContainers == null || exportContainers.isEmpty())) {
			for (ExportContainer exportContainer : exportContainers) {
				exportContainerListCY.add(constructOpusExportContainer(exportContainer));
			}
		}
		return exportContainerListCY;
	}

	public static List<GIReadResponseImportContainer> constructOpusImportContainersFromImportContainers(
			List<ImportContainer> importContainers) {
		List<GIReadResponseImportContainer> importContainerListCY = new ArrayList<GIReadResponseImportContainer>();
		if (!(importContainers == null || importContainers.isEmpty())) {
			for (ImportContainer importContainer : importContainers) {
				importContainerListCY.add(constructOpusImportContainer(importContainer));
			}
		}
		return importContainerListCY;
	}

	public GIWriteRequestImportContainer importContainerToGIWriteRequestImportContainer(
			ImportContainer importContainer) {

		GIWriteRequestImportContainer giWriteRequestImpContainer = new GIWriteRequestImportContainer();
		CommonContainerDTO commonContainerDTO = importContainer.getContainer();
		giWriteRequestImpContainer.setContainerNo(commonContainerDTO.getContainerNumber());
		giWriteRequestImpContainer.setContainerFullOrEmpty(commonContainerDTO.getContainerFullOrEmpty());
		giWriteRequestImpContainer.setPositionOnTruck(importContainer.getContainerPosition());

		return giWriteRequestImpContainer;
	}

	public GIWriteRequestExportContainer exportContainerToGIWriteRequestExportContainer(
			ExportContainer exportContainer) {

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
			giWriteRequestExpContainer
					.setContainerReeferTempValue(String.valueOf(Math.abs(exportContainer.getReferTemp())));
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
		giWriteRequestExpContainer.setWrongDoorIndicator(
				ExportUtilService.getStringRepresentationOfBoolean(exportContainer.getWrongDoor()));
		giWriteRequestExpContainer.setContainerHasOCSSR(
				ExportUtilService.getStringRepresentationOfBoolean(exportContainer.getOverClosingSSR()));
		giWriteRequestExpContainer.setContainerHasRPSSR(
				ExportUtilService.getStringRepresentationOfBoolean(exportContainer.getReplanSSR()));
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
			String verificationDatetime = DateUtil
					.getJsonDateFromDate(exportContainer.getBaseCommonGateInOutAttribute().getTimeGateOut());
			giWriteRequestExpContainer.setVgmVerificationDatetime(verificationDatetime);

		}
		return giWriteRequestExpContainer;
	}

	public ImportContainer giReadResponseImportContainerToImportContainer(
			GIReadResponseImportContainer gIReadResponseImportContainer) {

		ImportContainer importContainer = new ImportContainer();
		CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
		// private String containerNumber; //EPLA0000002
		commonContainerDTO.setContainerNumber(gIReadResponseImportContainer.getContainerNo());
		importContainer.setOrderFOT(gIReadResponseImportContainer.getImpOrderNo());
		importContainer.setImpOrderStatus(gIReadResponseImportContainer.getImpOrderStatus());
		importContainer.setImpOrderType(gIReadResponseImportContainer.getImpOrderType());
		// private String containerInOrOut;// OUT,
		importContainer.setGateInOut(gIReadResponseImportContainer.getContainerInOrOut());
		// private String containerShippingLine;// CMA,
		importContainer.setShippingLine(gIReadResponseImportContainer.getContainerShippingLine());
		importContainer.setShippingAgent(gIReadResponseImportContainer.getContainerShippingAgent());
		// private String containerFullOrEmpty;// F,
		commonContainerDTO.setContainerFullOrEmpty(gIReadResponseImportContainer.getContainerFullOrEmpty());
		importContainer.setContainerSubHandlingType(gIReadResponseImportContainer.getContainerSubHandlingType());
		// private double containerHeight;// 8,
		commonContainerDTO
				.setContainerHeight(getIntegerValueFromString(gIReadResponseImportContainer.getContainerHeight()));
		// private String containerIso;// 4001,
		commonContainerDTO.setContainerISOCode(gIReadResponseImportContainer.getContainerIso());
		// private double containerSize;// 40,
		importContainer.setContainerLength(getIntegerValueFromString(gIReadResponseImportContainer.getContainerSize()));
		// private String containerType;// GE,
		importContainer.setContainerType(gIReadResponseImportContainer.getContainerType());
		importContainer.setTareWeight(getIntValueFromString(gIReadResponseImportContainer.getContainerTareWeight()));
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
		importContainer
				.setVesselETADate(DateUtil.getLocalDategFromString(gIReadResponseImportContainer.getVesselETA()));
		importContainer
				.setVesselATADate(DateUtil.getLocalDategFromString(gIReadResponseImportContainer.getVesselATA()));
		importContainer.setVesselStatus(gIReadResponseImportContainer.getVesselStatus());
		importContainer.setContainer(commonContainerDTO);

		return importContainer;
	}

	public ExportContainer giReadResponseExporterContainerToExportContainer(
			GIReadResponseExporterContainer gIReadResponseExporterContainer) {

		ExportContainer exportContainer = new ExportContainer();
		CommonContainerDTO containerDTO = new CommonContainerDTO();
		// private String containerNo;// ;//AZHA0000001,
		containerDTO.setContainerNumber(gIReadResponseExporterContainer.getContainerNo());
		// private String exportBookingNo;// BOOK20001,
		exportContainer.setBookingNo(gIReadResponseExporterContainer.getExportOrderNo());
		exportContainer.setExportOrderStatus(gIReadResponseExporterContainer.getExportOrderStatus());
		exportContainer.setExportOrderType(gIReadResponseExporterContainer.getExportOrderType());
		// private String containerInOrOut;// IN,
		exportContainer.setGateInOut(gIReadResponseExporterContainer.getContainerInOrOut());
		exportContainer.setShippingLine(gIReadResponseExporterContainer.getContainerShippingLine());
		// private String containerFullOrEmpty;// F,
		containerDTO.setContainerFullOrEmpty(gIReadResponseExporterContainer.getContainerFullOrEmpty());
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
		exportContainer
				.setVesselETADate(DateUtil.getLocalDategFromString(gIReadResponseExporterContainer.getVesselETA()));
		exportContainer
				.setVesselATADate(DateUtil.getLocalDategFromString(gIReadResponseExporterContainer.getVesselATA()));
		exportContainer.setVesselStatus(gIReadResponseExporterContainer.getVesselStatus());
		// private String yardOpeningDateTime;// 20160902080000,
		exportContainer.setYardOpeningDateTime(
				DateUtil.getLocalDategFromString(gIReadResponseExporterContainer.getYardOpeningDateTime()));
		// private String containerSpod;// USSVN,
		exportContainer.setExpSpod(gIReadResponseExporterContainer.getContainerSpod());
		// private String containerIso;// 22G0,
		containerDTO.setContainerISOCode(gIReadResponseExporterContainer.getContainerIso());
		// private double containerHeight;// 8.6,
		containerDTO.setContainerHeight(getIntValueFromString(gIReadResponseExporterContainer.getContainerHeight()));
		// private String containerType;// GE,
		exportContainer.setContainerType(gIReadResponseExporterContainer.getContainerType());
		containerDTO.setContainerSize(gIReadResponseExporterContainer.getContainerSize());
		// private double grossWeight;// 11000,
		exportContainer
				.setGrossWeight(getIntValueFromString(gIReadResponseExporterContainer.getContainerGrossWeight()));
		// private double containerNetWeight;// 9000,
		exportContainer.setNetWeight(getIntValueFromString(gIReadResponseExporterContainer.getContainerNetWeight()));
		exportContainer.setTareWeight(getIntValueFromString(gIReadResponseExporterContainer.getContainerTareWeight()));
		CommonSealDTO sealAttribute = new CommonSealDTO();
		// private String containerSeal1_SL;// null,
		sealAttribute.setSeal01Origin(gIReadResponseExporterContainer.getContainerSeal1_SL());
		// private String containerSeal1_NO;// SEAL001,
		sealAttribute.setSeal01Number(gIReadResponseExporterContainer.getContainerSeal1_NO());
		// private String containerSeal2_SL;// null,
		sealAttribute.setSeal02Origin(gIReadResponseExporterContainer.getContainerSeal2_SL());
		// private String containerSeal2_NO;// null,
		sealAttribute.setSeal02Origin(gIReadResponseExporterContainer.getContainerSeal2_NO());

		// private String containerReeferIndicator;// N,
		exportContainer
				.setReferFlag(getBooleanFromString(gIReadResponseExporterContainer.getContainerReeferIndicator()));
		// private String containerReeferTempSign;// null,
		exportContainer.setReferTempType(gIReadResponseExporterContainer.getContainerReeferTempSign());
		// private String containerReeferTempValue;// null,
		if (StringUtils.isNotEmpty(gIReadResponseExporterContainer.getContainerReeferTempValue())) {
			exportContainer.setReferTemp(
					getIntegerValueFromString(gIReadResponseExporterContainer.getContainerReeferTempValue()));
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
		exportContainer.setOogOH(getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OH()));
		// private double containerOOG_OL;// 3.0,
		exportContainer.setOogOL(getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OL()));
		// private double containerOOG_OF;// 1.0,
		exportContainer.setOogOF(getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OF()));
		// private double containerOOG_OA;// 2.0,
		exportContainer.setOogOA(getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OA()));
		// private double containerOOG_OR;// 4.0
		exportContainer.setOogOR(getIntegerValueFromString(gIReadResponseExporterContainer.getContainerOOG_OR()));
		// private String containerDamageCode1;//
		exportContainer
				.setDamageCode_01(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode1()));
		// private String containerDamageCode2;//
		exportContainer
				.setDamageCode_02(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode2()));
		// private String containerDamageCode3;//
		exportContainer
				.setDamageCode_03(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode3()));
		// private String containerDamageCode4;//
		exportContainer
				.setDamageCode_04(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode4()));
		// private String containerDamageCode5;//
		exportContainer
				.setDamageCode_05(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode5()));
		// private String containerDamageCode6;//
		exportContainer
				.setDamageCode_06(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode6()));
		// private String containerDamageCode7;//
		exportContainer
				.setDamageCode_07(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode7()));
		// private String containerDamageCode8;//
		exportContainer
				.setDamageCode_08(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode8()));
		// private String containerDamageCode9;//
		exportContainer
				.setDamageCode_09(constructDamageCode(gIReadResponseExporterContainer.getContainerDamageCode9()));

		exportContainer.setSealAttribute(sealAttribute);
		exportContainer.setContainer(containerDTO);

		return exportContainer;
	}
	
	public ExportContainer goReadResponseExporterContainerToExportContainer(GOReadResponseExportContainer goReadResponseExporterContainer) {

		ExportContainer exportContainer = new ExportContainer();
		CommonContainerDTO containerDTO = new CommonContainerDTO();
		// private String containerNo;// ;//AZHA0000001,
		containerDTO.setContainerNumber(goReadResponseExporterContainer.getContainerNo());
		containerDTO.setContainerFullOrEmpty(goReadResponseExporterContainer.getContainerFullOrEmpty());
		exportContainer.setRtgExecustionDateTime(
				DateUtil.getLocalDategFromString(goReadResponseExporterContainer.getRtgExecustionDateTime()));
		exportContainer.setRtgExecustionStatus(goReadResponseExporterContainer.getRtgExecustionStatus());
		exportContainer.setContainer(containerDTO);

		return exportContainer;
	}
	
	public ImportContainer goReadResponseImportContainerToImportContainer(GOReadResponseImportContainer goReadResponseImportContainer) {

		ImportContainer importContainer = new ImportContainer();
		CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
		CommonSealDTO sealAttribute = new CommonSealDTO();
		// private String containerNumber; //EPLA0000002
		commonContainerDTO.setContainerNumber(goReadResponseImportContainer.getContainerNo());
		// private String containerFullOrEmpty;// F,
		commonContainerDTO.setContainerFullOrEmpty(goReadResponseImportContainer.getContainerFullOrEmpty());
		importContainer.setRtgExecustionDateTime(
				DateUtil.getLocalDategFromString(goReadResponseImportContainer.getRtgExecustionDateTime()));
		importContainer.setRtgExecustionStatus(goReadResponseImportContainer.getRtgExecustionStatus());
		
		sealAttribute.setSeal01Number(goReadResponseImportContainer.getContainerSeal1_NO());
		sealAttribute.setSeal02Number(goReadResponseImportContainer.getContainerSeal2_NO());
		sealAttribute.setSeal01Origin(goReadResponseImportContainer.getContainerSeal1_SL());
		sealAttribute.setSeal02Origin(goReadResponseImportContainer.getContainerSeal2_SL());
		
		importContainer.setSealAttribute(sealAttribute);
		importContainer.setContainer(commonContainerDTO);

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
	
	public ExportContainer GOWriteRequestExportContainerToExportContainer(GOWriteRequestExportContainer goWriteRequestExpContainer) {

		ExportContainer expContainer = new ExportContainer();
		CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
		commonContainerDTO.setContainerNumber(goWriteRequestExpContainer.getContainerNo());
		expContainer.setContainer(commonContainerDTO);
		return expContainer;
	}
	
	public ImportContainer GOWriteRequestImportContainerToImportContainer(GOWriteRequestImportContainer goWriteRequestImpContainer) {

		ImportContainer impContainer = new ImportContainer();
		CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
		commonContainerDTO.setContainerNumber(goWriteRequestImpContainer.getContainerNo());
		impContainer.setContainer(commonContainerDTO);
		return impContainer;
	}
	

	private int getIntValueFromString(String stringVal) {
		if (StringUtils.isNotEmpty(stringVal)) {
			return Integer.parseInt(stringVal);
		}
		return 0;
	}
	
	private static String getStringValueFromInteger(Integer integerVal) {
		if (!(integerVal == null || integerVal == 0)) {
			return Integer.toString(integerVal);
		}
		return null;
	}

	private static int getIntegerValueFromString(String strValue) {
		if (StringUtils.isNotEmpty(strValue)) {
			try {
				return Integer.parseInt(strValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	private static Boolean getBooleanFromString(String string) {
		if (StringUtils.isNotEmpty(string)) {
			if (StringUtils.equals(string, "N")) {
				return false;
			} else {
				return true;
			}
		}
		return null;
	}

	private static DamageCodeDTO constructDamageCode(String containerDamageCode) {
		DamageCodeDTO damageCodeDTO = new DamageCodeDTO();
		damageCodeDTO.setDamageCode(containerDamageCode);
		return damageCodeDTO;
	}

	private static String getStringFromBoolean(Boolean bool) {
		if (!(bool == null)) {
			if (bool) {
				return "Y";
			} else {
				return "N";
			}
		}
		return null;
	}

	private static String getDamageCodeFromObject(DamageCodeDTO damageCode) {
		if (!(damageCode == null)) {
			return damageCode.getDamageCode();
		}
		return null;
	}

	public static String hasErrorMessage(List<OpusErrorListItem> errorList) {
		// String hasErrorMessage(List<Error>) in opus service
		// check if error list object is empty
		if (!(errorList == null || errorList.isEmpty())) {
			StringBuilder sb = new StringBuilder("");
			// if not empty construct error string
			// container number + error description, other eg:- NH161219003
			// There does no Truck In plan
			// for
			errorList.forEach(error -> {
				sb.append(EMPTY_SPACE).append(error.getContainerNo()).append(EMPTY_SPACE)
						.append(error.getErrorDescription()).append(SEPERATOR);
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

}
