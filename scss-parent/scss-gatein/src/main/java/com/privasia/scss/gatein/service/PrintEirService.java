package com.privasia.scss.gatein.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.DamageCodeInfo;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.TransactionDTO;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.ContainerSize;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.common.enums.Nationality;
import com.privasia.scss.core.model.PrintEIRContainerInfo;
import com.privasia.scss.core.model.PrintEir;
import com.privasia.scss.core.repository.PrintEirRepository;

@Service("printEirService")
public class PrintEirService {

	private static final String BLANK = " ";
	private static final String PIPELINE_SEPERATOR = " | ";

	private PrintEirRepository printEirRepository;
	
	@Autowired
	public void setPrintEirRepository(PrintEirRepository printEirRepository) {
		this.printEirRepository = printEirRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
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
		//constructLinesForContainer(importContainer01, lineInfoC1, lineInfo2C1, sealC1);

		/***********************************************************/

		/***************** SECOND Container ********************/
		//constructLinesForContainer(importContainer02, lineInfoC2, lineInfo2C2, sealC2);

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

		PrintEIRContainerInfo eirContainer01 = getConstructedEirContainer(importContainer01, weight01, lineInfoC1,
				lineInfo2C1, sealC1);
		printEir.setContainer01(eirContainer01);

		PrintEIRContainerInfo eirContainer02 = getConstructedEirContainer(importContainer02, weight02, lineInfoC2,
				lineInfo2C2, sealC2);
		printEir.setContainer02(eirContainer02);

		printEir.setIcNoOrPassport(icNoOrPassportNo);
		printEir.setScuName(transactionDTO.getScuName());
		printEir.setTruckNo(transactionDTO.getPmPlateNo());
		printEir.setGateInNo(transactionDTO.getGateInNo());
		printEir.setStatus(HpabReferStatus.ACTIVE);
		printEir.setClientIp(customerIpAddress);

		printEirRepository.save(printEir);

	}

	private PrintEIRContainerInfo getConstructedEirContainer(ImportContainer importContainer, String weight,
			StringBuilder lineInfo, StringBuilder lineInfo2, StringBuilder seal) {
		PrintEIRContainerInfo eirContainer = new PrintEIRContainerInfo();
		eirContainer.setContainerBayCode(importContainer.getYardBayCode());
		eirContainer.setContainerInOrOut(GateInOutStatus.fromValue(importContainer.getGateInOut()));
		eirContainer.setContainerFullOrEmpty(
				ContainerFullEmptyType.fromValue(importContainer.getContainer().getContainerFullOrEmpty()));
		eirContainer.setContainerPositionOnTruck(importContainer.getContainerPosition());
		eirContainer.setContainerNumber(importContainer.getContainer().getContainerNumber());
		eirContainer.setContainerLine(importContainer.getShippingLine());
		eirContainer.setContainerISOCode(importContainer.getContainer().getContainerISOCode());
		eirContainer
				.setContainerLength(ContainerSize.fromValue(Integer.toString(importContainer.getContainerLength())));
		eirContainer.setContainerHeight(Double.toString(importContainer.getContainer().getContainerHeight()));
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

	/*private void constructLinesForContainer(ImportContainer importContainer, StringBuilder lineInfo,
			StringBuilder lineInfo2, StringBuilder seal) {

		if (!(importContainer == null)) {

			if (StringUtils.equals(importContainer.getContainer().getContainerFullOrEmpty(), "F")) {

				if (!(importContainer.getSealAttribute() == null)) {
					CommonSealDTO sealAttribute = importContainer.getSealAttribute();
					lineInfo = constructSealString(sealAttribute, lineInfo, 1);
					seal = constructSealString(sealAttribute, lineInfo, 1);

					lineInfo = constructSealString(sealAttribute, lineInfo, 2);
					if (!(seal == null || StringUtils.isEmpty(seal.toString()))) {
						seal.append(PIPELINE_SEPERATOR);
					}
					seal = constructSealString(sealAttribute, lineInfo, 2);
				}

			}

			if (StringUtils.equals(importContainer.getOperationReefer(), "Y")) {
				lineInfo.append("Reefer:").append(BLANK).append(importContainer.getOperationReefer()).append(BLANK)
						.append(importContainer.getTemp()).append(BLANK).append(importContainer.getTempUnit())
						.append(BLANK);
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

			if (!(importContainer.getDamageCodeInfo() == null)) {
				DamageCodeInfo dgInfo = importContainer.getDamageCodeInfo();

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

	}*/

	/*private void constructLineFirstForExportContainer(ExportContainer exportContainer) {

		if (exportContainer != null) {

			StringBuffer line = new StringBuffer();

			if (exportContainer.getContainer() != null) {

				if (StringUtils.equals(exportContainer.getContainer().getContainerFullOrEmpty(), "F")) {

					if (exportContainer.getSealAttribute() != null) {
						CommonSealDTO sealAttribute = exportContainer.getSealAttribute();

						line = constructSealString(sealAttribute, line, 1);
					}

				}

			}

			if (StringUtils.equals(exportContainer.getContainer().getContainerFullOrEmpty(), "F")) {

				if (!(importContainer.getSealAttribute() == null)) {
					CommonSealDTO sealAttribute = importContainer.getSealAttribute();
					lineInfo = constructSealString(sealAttribute, lineInfo, 1);
					seal = constructSealString(sealAttribute, lineInfo, 1);

					lineInfo = constructSealString(sealAttribute, lineInfo, 2);
					if (!(seal == null || StringUtils.isEmpty(seal.toString()))) {
						seal.append(PIPELINE_SEPERATOR);
					}
					seal = constructSealString(sealAttribute, lineInfo, 2);
				}

			}

			if (StringUtils.equals(importContainer.getOperationReefer(), "Y")) {
				lineInfo.append("Reefer:").append(BLANK).append(importContainer.getOperationReefer()).append(BLANK)
						.append(importContainer.getTemp()).append(BLANK).append(importContainer.getTempUnit())
						.append(BLANK);
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

			if (!(importContainer.getDamageCodeInfo() == null)) {
				DamageCodeInfo dgInfo = importContainer.getDamageCodeInfo();

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

	}*/

	private StringBuffer constructSealLineString(CommonSealDTO sealAttribute, StringBuffer line) {

		if (sealAttribute != null) {
			if (StringUtils.isNotEmpty(sealAttribute.getSeal01Number())) {
				line.append(sealAttribute.getSeal01Origin()).append(BLANK).append(sealAttribute.getSeal01Type())
						.append(BLANK).append(sealAttribute.getSeal01Number()).append(BLANK);
			}

			if (StringUtils.isNotEmpty(sealAttribute.getSeal02Number())) {
				line.append(sealAttribute.getSeal02Origin()).append(BLANK).append(sealAttribute.getSeal02Type())
						.append(BLANK).append(sealAttribute.getSeal02Number());
			}
		}

		return line;
	}

	private StringBuffer constructReferLineString(ExportContainer exportContainer, StringBuffer line) {

		if (exportContainer.getReferFlag()) {
			line.append("Reefer:").append(BLANK).append(exportContainer.getOperationReefer()).append(BLANK)
					.append(exportContainer.getReferTemp()).append(exportContainer.getReeferTempUnit()).append(BLANK);
		}

		return line;
	}
	
	private StringBuffer constructDGLableLineString(ExportContainer exportContainer, StringBuffer line) {

		if (StringUtils.isNotEmpty(exportContainer.getImdg())) {
			line.append("IMDG:").append(BLANK).append(exportContainer.getImdg()).append(BLANK);

		}

		return line;
	}
	
	private StringBuffer constructDGUNCodeLineString(ExportContainer exportContainer, StringBuffer line) {

		if (StringUtils.isNotEmpty(exportContainer.getDgUNCode())) {
			line.append("UN:").append(BLANK).append(exportContainer.getDgUNCode()).append(BLANK);

		}

		return line;
	}

	private StringBuffer constructSealString(CommonSealDTO sealAttribute, StringBuffer seal) {

		if (sealAttribute != null) {
			if (StringUtils.isNotEmpty(sealAttribute.getSeal01Number())) {
				seal.append(sealAttribute.getSeal01Origin()).append(BLANK).append(sealAttribute.getSeal01Type())
						.append(BLANK).append(sealAttribute.getSeal01Number()).append(BLANK);
			}

			if (StringUtils.isNotEmpty(sealAttribute.getSeal02Number())) {
				if (StringUtils.isNotEmpty(seal)) {
					seal.append(PIPELINE_SEPERATOR).append(sealAttribute.getSeal02Origin()).append(BLANK)
							.append(sealAttribute.getSeal02Type()).append(BLANK)
							.append(sealAttribute.getSeal02Number());
				} else {
					seal.append(sealAttribute.getSeal02Origin()).append(BLANK).append(sealAttribute.getSeal02Type())
							.append(BLANK).append(sealAttribute.getSeal02Number());
				}

			}
		}
		return seal;
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
		/*
		 * if (transactionDTO.isShipperVGM() &&
		 * importContainer.isWithInTolerance()) { weight =
		 * String.valueOf(importContainer.getShipperVGM()); } else { if
		 * (StringUtils.isNotBlank(importContainer.getNetWeight())) { weight =
		 * importContainer.getNetWeight(); } }
		 */
		return weight;
	}

}
