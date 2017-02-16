package com.privasia.scss.opus.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.DamageCodeDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.ErrorListItem;
import com.privasia.scss.opus.dto.OpusExporterContainer;
import com.privasia.scss.opus.dto.OpusImportContainer;

@Service("opusService")
public class OpusService {

	private static final Logger log = LoggerFactory.getLogger(OpusService.class);

	private static final String EMPTY_SPACE = StringUtils.EMPTY;
	private static final String SEPERATOR = ",";

	public List<ExportContainer> constructExportContainersFromOpusExportContainers(
			List<OpusExporterContainer> exportContainerListCY) {
		List<ExportContainer> exportContainerList = null;
		if (!(exportContainerListCY == null || exportContainerListCY.isEmpty())) {
			exportContainerList = new ArrayList<ExportContainer>();
			for (OpusExporterContainer opusExporterContainer : exportContainerListCY) {
				exportContainerList.add(constructExportContainer(opusExporterContainer, null));
			}
		}
		return exportContainerList;
	}

	public List<ImportContainer> constructImportContainersFromOpusImportContainers(
			List<OpusImportContainer> importContainerListCY) {
		List<ImportContainer> importContainerList = null;
		if (!(importContainerListCY == null || importContainerListCY.isEmpty())) {
			importContainerList = new ArrayList<ImportContainer>();
			for (OpusImportContainer opusImportContainer : importContainerListCY) {
				importContainerList.add(constructImportContainer(opusImportContainer, null));
			}
		}
		return importContainerList;
	}

	public static List<OpusExporterContainer> constructOpusExporterContainersFromExporterContainers(
			List<ExportContainer> exportContainers) {
		List<OpusExporterContainer> exportContainerListCY = null;
		if (!(exportContainers == null || exportContainers.isEmpty())) {
			exportContainerListCY = new ArrayList<OpusExporterContainer>();
			for (ExportContainer exportContainer : exportContainers) {
				exportContainerListCY.add(constructOpusExportContainer(exportContainer));
			}
		}
		return exportContainerListCY;
	}

	public static List<OpusImportContainer> constructOpusImportContainersFromImportContainers(
			List<ImportContainer> importContainers) {
		List<OpusImportContainer> importContainerListCY = null;
		if (!(importContainers == null || importContainers.isEmpty())) {
			importContainerListCY = new ArrayList<OpusImportContainer>();
			for (ImportContainer importContainer : importContainers) {
				importContainerListCY.add(constructOpusImportContainer(importContainer));
			}
		}
		return importContainerListCY;
	}

	private static OpusImportContainer constructOpusImportContainer(ImportContainer importContainer) {
		if (!(importContainer == null)) {
			OpusImportContainer opusImportContainer = new OpusImportContainer();
			CommonContainerDTO commonContainerDTO = importContainer.getContainer();
			if (!(commonContainerDTO == null)) {
				opusImportContainer
						.setContainerHeight(getStringValueFromInteger(commonContainerDTO.getContainerHeight()));
				opusImportContainer.setContainerFullOrEmpty(commonContainerDTO.getContainerFullOrEmpty());
				opusImportContainer.setContainerIso(commonContainerDTO.getContainerISOCode());
			}
			opusImportContainer.setContainerSize(getStringValueFromInteger(importContainer.getContainerLength()));
			opusImportContainer.setContainerInOrOut(importContainer.getGateInOut());
			opusImportContainer.setImpOrderNo(importContainer.getOrderNo());
			opusImportContainer.setContainerShippingLine(importContainer.getLine());
			opusImportContainer.setContainerType(importContainer.getContainerType());
			opusImportContainer.setCurrentYardPosition(importContainer.getYardPosition());
			opusImportContainer
					.setRtgExecustionDateTime(DateUtil.getJsonDateFromDate(importContainer.getRtgExecustionDateTime()));
			opusImportContainer.setRtgExecustionStatus(importContainer.getRtgExecustionStatus());
			return opusImportContainer;
		}
		return null;
	}

	public ImportContainer constructImportContainer(OpusImportContainer opusImportContainer,
			ImportContainer importContainer) {
		if (opusImportContainer != null) {
			CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
			//private String containerNumber; //EPLA0000002
			commonContainerDTO.setContainerNumber(opusImportContainer.getContainerNo());
			// private double containerHeight;// 8,
			commonContainerDTO.setContainerHeight(getIntegerValueFromString(opusImportContainer.getContainerHeight()));
			// private String containerFullOrEmpty;// F,
			commonContainerDTO.setContainerFullOrEmpty(opusImportContainer.getContainerFullOrEmpty());
			// private String containerIso;// 4001,
			commonContainerDTO.setContainerISOCode(opusImportContainer.getContainerIso());
			// private double containerSize;// 40,
			importContainer.setContainerLength(getIntegerValueFromString(opusImportContainer.getContainerSize()));
			// private String containerInOrOut;// OUT,
			importContainer.setGateInOut(opusImportContainer.getContainerInOrOut());
			// private String impOrderNo;// ORDER0001,
			importContainer.setOrderNo(opusImportContainer.getImpOrderNo());
			// private String containerShippingLine;// CMA,
			importContainer.setLine(opusImportContainer.getContainerShippingLine());
			// private String containerType;// GE,
			importContainer.setContainerType(opusImportContainer.getContainerType());
			// private String currentYardPosition;// 02S-0102-C-1,
			importContainer.setYardPosition(opusImportContainer.getCurrentYardPosition());
			importContainer.setContainer(commonContainerDTO);

			importContainer.setRtgExecustionDateTime(
					DateUtil.getLocalDategFromString(opusImportContainer.getRtgExecustionDateTime()));
			importContainer.setRtgExecustionStatus(opusImportContainer.getRtgExecustionStatus());

		}
		return importContainer;
	}

	private static OpusExporterContainer constructOpusExportContainer(ExportContainer exportContainer) {
		OpusExporterContainer opusExporterContainer = new OpusExporterContainer();
		opusExporterContainer.setExportBookingNo(exportContainer.getBookingNo());
		opusExporterContainer.setContainerInOrOut(exportContainer.getGateInOut());
		opusExporterContainer.setContainerShippingLine(exportContainer.getShippingLine());
		opusExporterContainer.setSubHandlingType(exportContainer.getSubHandlingType());
		opusExporterContainer.setExpCarrierType(exportContainer.getExpCarrierType());
		opusExporterContainer
				.setRtgExecustionDateTime(DateUtil.getJsonDateFromDate(exportContainer.getRtgExecustionDateTime()));
		opusExporterContainer.setRtgExecustionStatus(exportContainer.getRtgExecustionStatus());
		opusExporterContainer.setExpCarrier(exportContainer.getExpCar());
		opusExporterContainer.setVesselCode(exportContainer.getVesselCode());
		opusExporterContainer.setVesselVoyage(exportContainer.getVesselVoyage());
		opusExporterContainer.setVisitId(exportContainer.getVesselVisitID());
		opusExporterContainer.setVesselScn(exportContainer.getVesselSCN());
		opusExporterContainer.setVesselName(exportContainer.getVesselName());
		opusExporterContainer.setVesselETA(DateUtil.getJsonDateFromDate(exportContainer.getVesselETADate()));
		opusExporterContainer
				.setYardOpeningDateTime(DateUtil.getJsonDateFromDate(exportContainer.getYardOpeningDateTime()));
		opusExporterContainer.setContainerSpod(exportContainer.getExpSpod());
		CommonContainerDTO containerDTO = exportContainer.getContainer();
		if (!(containerDTO == null)) {
			opusExporterContainer.setContainerNo(containerDTO.getContainerNumber());
			opusExporterContainer.setContainerFullOrEmpty(containerDTO.getContainerFullOrEmpty());
			opusExporterContainer.setContainerIso(containerDTO.getContainerISOCode());
			opusExporterContainer.setContainerHeight(containerDTO.getContainerHeight());
		}
		opusExporterContainer.setContainerType(exportContainer.getContainerType());
		opusExporterContainer.setGrossWeight(exportContainer.getCosmosGrossWeight());
		if (!(exportContainer.getExpNetWeight() == null)) {
			opusExporterContainer.setContainerNetWeight(exportContainer.getExpNetWeight());
		}
		CommonSealDTO sealAttribute = exportContainer.getSealAttribute();
		if (!(sealAttribute == null)) {
			opusExporterContainer.setContainerSeal1_SL(sealAttribute.getSeal01Origin());
			opusExporterContainer.setContainerSeal1_NO(sealAttribute.getSeal01Number());
			opusExporterContainer.setContainerSeal2_SL(sealAttribute.getSeal02Origin());
			opusExporterContainer.setContainerSeal2_NO(sealAttribute.getSeal02Origin());
		}
		opusExporterContainer.setContainerReeferIndicator(getStringFromBoolean(exportContainer.getReferFlag()));
		opusExporterContainer.setContainerReeferTempSign(exportContainer.getReferTempType());
		opusExporterContainer.setContainerReeferTempValue(exportContainer.getReeferTempUnit());
		opusExporterContainer.setContainerReeferTempUnit(exportContainer.getReeferTempUnit());
		opusExporterContainer.setContainerDGImdg(exportContainer.getImdg());
		opusExporterContainer.setContainerDGUNCode(exportContainer.getContainerDGUNCode());
		opusExporterContainer.setContainerDGImdgLabel(exportContainer.getImdgLabelID());
		opusExporterContainer
				.setYardDGOpeningDateTime(DateUtil.getJsonDateFromDate(exportContainer.getYardDGOpeningDateTime()));

		opusExporterContainer.setContainerOOG_OH(getStringValueFromInteger(exportContainer.getOogOH()));
		opusExporterContainer.setContainerOOG_OL(getStringValueFromInteger(exportContainer.getOogOL()));
		opusExporterContainer.setContainerOOG_OF(getStringValueFromInteger(exportContainer.getOogOF()));
		opusExporterContainer.setContainerOOG_OA(getStringValueFromInteger(exportContainer.getOogOA()));
		opusExporterContainer.setContainerOOG_OR(getStringValueFromInteger(exportContainer.getOogOR()));

		opusExporterContainer.setPositionOnTruck(exportContainer.getContainerPosition());
		opusExporterContainer.setWrongDoorIndicator(getStringFromBoolean(exportContainer.getWrongDoor()));
		opusExporterContainer.setContainerHasOCSSR(getStringFromBoolean(exportContainer.getOogSSR()));
		opusExporterContainer.setContainerHasRPSSR(getStringFromBoolean(exportContainer.getReplanSSR()));
		opusExporterContainer.setContainerDamageCode1(getDamageCodeFromObject(exportContainer.getDamageCode_01()));
		opusExporterContainer.setContainerDamageCode2(getDamageCodeFromObject(exportContainer.getDamageCode_02()));
		opusExporterContainer.setContainerDamageCode3(getDamageCodeFromObject(exportContainer.getDamageCode_03()));
		opusExporterContainer.setContainerDamageCode4(getDamageCodeFromObject(exportContainer.getDamageCode_04()));
		opusExporterContainer.setContainerDamageCode5(getDamageCodeFromObject(exportContainer.getDamageCode_05()));
		opusExporterContainer.setContainerDamageCode6(getDamageCodeFromObject(exportContainer.getDamageCode_06()));
		opusExporterContainer.setContainerDamageCode7(getDamageCodeFromObject(exportContainer.getDamageCode_07()));
		opusExporterContainer.setContainerDamageCode8(getDamageCodeFromObject(exportContainer.getDamageCode_08()));
		opusExporterContainer.setContainerDamageCode9(getDamageCodeFromObject(exportContainer.getDamageCode_09()));
		opusExporterContainer.setVgmType(exportContainer.getVgmType());
		opusExporterContainer.setVgmWeighingStation(exportContainer.getVgmWeighingStation());
		opusExporterContainer.setVgmWitnessName(exportContainer.getVgmWitnessName());
		opusExporterContainer.setVgmWitnessID(exportContainer.getVgmWitnessID());
		opusExporterContainer.setVgmRefNo(exportContainer.getVgmRefNo());
		opusExporterContainer.setVgmMGW(exportContainer.getVgmMGW());
		opusExporterContainer.setVgmNetWeight(exportContainer.getVgmNetWeight());
		opusExporterContainer.setVgmVerificationDatetime(exportContainer.getVgmVerificationDatetime());
		return opusExporterContainer;
	}

	private static String getStringValueFromInteger(Integer integerVal) {
		if (!(integerVal == null)) {
			return Integer.toString(integerVal);
		}
		return null;
	}

	public ExportContainer constructExportContainer(OpusExporterContainer opusExporterContainer,
			ExportContainer exportContainer) {
		if (opusExporterContainer != null) {
			CommonContainerDTO containerDTO = new CommonContainerDTO();
			// private String containerNo;// ;//AZHA0000001,
			containerDTO.setContainerNumber(opusExporterContainer.getContainerNo());
			// private String exportBookingNo;// BOOK20001,
			exportContainer.setBookingNo(opusExporterContainer.getExportBookingNo());
			// private String containerInOrOut;// IN,
			exportContainer.setGateInOut(opusExporterContainer.getContainerInOrOut());
			exportContainer.setShippingLine(opusExporterContainer.getContainerShippingLine());
			// private String containerFullOrEmpty;// F,
			containerDTO.setContainerFullOrEmpty(opusExporterContainer.getContainerFullOrEmpty());
			// private String subHandlingType;// null,
			exportContainer.setSubHandlingType(opusExporterContainer.getSubHandlingType());
			exportContainer.setRtgExecustionDateTime(
					DateUtil.getLocalDategFromString(opusExporterContainer.getRtgExecustionDateTime()));
			exportContainer.setRtgExecustionStatus(opusExporterContainer.getRtgExecustionStatus());
			// private String expCarrierType;// null,
			exportContainer.setExpCarrierType(opusExporterContainer.getExpCarrierType());
			// private String expCarrier;// null,
			exportContainer.setExpCar(opusExporterContainer.getExpCarrier());
			// private String vesselCode;// CSQD,
			exportContainer.setVesselCode(opusExporterContainer.getVesselCode());
			// private String vesselVoyage;// 001/2016,
			exportContainer.setVesselVoyage(opusExporterContainer.getVesselVoyage());
			// private String visitId;// 10267538,
			exportContainer.setVesselVisitID(opusExporterContainer.getVisitId());
			// private String vesselScn;// ETX9,
			exportContainer.setVesselSCN(opusExporterContainer.getVesselScn());
			// private String vesselName;// XIN QING DAO,
			exportContainer.setVesselName(opusExporterContainer.getVesselName());
			// private String vesselETA;// 20160907080000,
			exportContainer.setVesselETADate(DateUtil.getLocalDategFromString(opusExporterContainer.getVesselETA()));
			// private String yardOpeningDateTime;// 20160902080000,
			exportContainer.setYardOpeningDateTime(
					DateUtil.getLocalDategFromString(opusExporterContainer.getYardOpeningDateTime()));
			// private String containerSpod;// USSVN,
			exportContainer.setExpSpod(opusExporterContainer.getContainerSpod());
			// private String containerIso;// 22G0,
			containerDTO.setContainerISOCode(opusExporterContainer.getContainerIso());
			// private double containerSize;// 20,
			// containerDTO.setContainerLength(opusExporterContainer.getContainerSize());
			// private double containerHeight;// 8.6,
			containerDTO.setContainerHeight(opusExporterContainer.getContainerHeight());
			// private String containerType;// GE,
			exportContainer.setContainerType(opusExporterContainer.getContainerType());
			// private double grossWeight;// 11000,
			exportContainer.setCosmosGrossWeight(opusExporterContainer.getGrossWeight());
			// private double containerNetWeight;// 9000,
			exportContainer.setExpNetWeight(opusExporterContainer.getContainerNetWeight());
			CommonSealDTO sealAttribute = new CommonSealDTO();
			// private String containerSeal1_SL;// null,
			sealAttribute.setSeal01Origin(opusExporterContainer.getContainerSeal1_SL());
			// private String containerSeal1_NO;// SEAL001,
			sealAttribute.setSeal01Number(opusExporterContainer.getContainerSeal1_NO());
			// private String containerSeal2_SL;// null,
			sealAttribute.setSeal02Origin(opusExporterContainer.getContainerSeal2_SL());
			// private String containerSeal2_NO;// null,
			sealAttribute.setSeal02Origin(opusExporterContainer.getContainerSeal2_NO());
			// private String containerReeferIndicator;// N,
			exportContainer.setReferFlag(getBooleanFromString(opusExporterContainer.getContainerReeferIndicator()));
			// private String containerReeferTempSign;// null,
			exportContainer.setReferTempType(opusExporterContainer.getContainerReeferTempSign());
			// private String containerReeferTempValue;// null,
			if (StringUtils.isNotEmpty(opusExporterContainer.getContainerReeferTempValue())) {
				exportContainer
						.setReferTemp(getIntegerValueFromString(opusExporterContainer.getContainerReeferTempValue()));
			}
			// private String containerReeferTempUnit;// null,
			exportContainer.setReeferTempUnit(opusExporterContainer.getContainerReeferTempUnit());
			// private String containerDGImdg;// null,
			exportContainer.setImdg(opusExporterContainer.getContainerDGImdg());
			// private String containerDGUNCode;// null,
			exportContainer.setContainerDGUNCode(opusExporterContainer.getContainerDGUNCode());
			// private String containerDGImdgLabel;// null,
			exportContainer.setImdgLabelID(opusExporterContainer.getContainerDGImdgLabel());
			// private String yardDGOpeningDateTime;// 20160904080000,
			exportContainer.setYardDGOpeningDateTime(
					DateUtil.getLocalDategFromString(opusExporterContainer.getYardDGOpeningDateTime()));
			// private double containerOOG_OH;// 5.0,
			exportContainer.setOogOH(getIntegerValueFromString(opusExporterContainer.getContainerOOG_OH()));
			// private double containerOOG_OL;// 3.0,
			exportContainer.setOogOL(getIntegerValueFromString(opusExporterContainer.getContainerOOG_OL()));
			// private double containerOOG_OF;// 1.0,
			exportContainer.setOogOF(getIntegerValueFromString(opusExporterContainer.getContainerOOG_OF()));
			// private double containerOOG_OA;// 2.0,
			exportContainer.setOogOA(getIntegerValueFromString(opusExporterContainer.getContainerOOG_OA()));
			// private double containerOOG_OR;// 4.0
			exportContainer.setOogOR(getIntegerValueFromString(opusExporterContainer.getContainerOOG_OR()));
			// private String containerDamageCode1;//
			exportContainer.setDamageCode_01(constructDamageCode(opusExporterContainer.getContainerDamageCode1()));
			// private String containerDamageCode2;//
			exportContainer.setDamageCode_02(constructDamageCode(opusExporterContainer.getContainerDamageCode2()));
			// private String containerDamageCode3;//
			exportContainer.setDamageCode_03(constructDamageCode(opusExporterContainer.getContainerDamageCode3()));
			// private String containerDamageCode4;//
			exportContainer.setDamageCode_04(constructDamageCode(opusExporterContainer.getContainerDamageCode4()));
			// private String containerDamageCode5;//
			exportContainer.setDamageCode_05(constructDamageCode(opusExporterContainer.getContainerDamageCode5()));
			// private String containerDamageCode6;//
			exportContainer.setDamageCode_06(constructDamageCode(opusExporterContainer.getContainerDamageCode6()));
			// private String containerDamageCode7;//
			exportContainer.setDamageCode_07(constructDamageCode(opusExporterContainer.getContainerDamageCode7()));
			// private String containerDamageCode8;//
			exportContainer.setDamageCode_08(constructDamageCode(opusExporterContainer.getContainerDamageCode8()));
			// private String containerDamageCode9;//
			exportContainer.setDamageCode_09(constructDamageCode(opusExporterContainer.getContainerDamageCode9()));
			// private String positionOnTruck;// M
			exportContainer.setContainerPosition(opusExporterContainer.getPositionOnTruck());
			// private String wrongDoorIndicator;//
			exportContainer.setWrongDoor(getBooleanFromString(opusExporterContainer.getWrongDoorIndicator()));
			// private String containerHasOCSSR;// Y
			exportContainer.setOogSSR(getBooleanFromString(opusExporterContainer.getContainerHasOCSSR()));
			// private String containerHasRPSSR;// Y
			exportContainer.setReplanSSR(getBooleanFromString(opusExporterContainer.getContainerHasRPSSR()));
			// private String vgmType;// S
			exportContainer.setVgmType(opusExporterContainer.getVgmType());
			// private String vgmWeighingStation;//
			exportContainer.setVgmWeighingStation(opusExporterContainer.getVgmWeighingStation());
			// private String vgmWitnessName;//
			exportContainer.setVgmWitnessName(opusExporterContainer.getVgmWitnessName());
			// private String vgmWitnessID;//
			exportContainer.setVgmWitnessID(opusExporterContainer.getVgmWitnessID());
			// private String vgmRefNo;//
			exportContainer.setVgmRefNo(opusExporterContainer.getVgmRefNo());
			// private String vgmMGW;// 1
			exportContainer.setVgmMGW(opusExporterContainer.getVgmMGW());
			// private String vgmNetWeight;//
			exportContainer.setVgmNetWeight(opusExporterContainer.getVgmNetWeight());
			// private String vgmVerificationDatetime;//
			exportContainer.setVgmVerificationDatetime(opusExporterContainer.getVgmVerificationDatetime());
			exportContainer.setSealAttribute(sealAttribute);
			exportContainer.setContainer(containerDTO);
		}
		return exportContainer;
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

	public static String hasErrorMessage(List<ErrorListItem> errorList) {
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
