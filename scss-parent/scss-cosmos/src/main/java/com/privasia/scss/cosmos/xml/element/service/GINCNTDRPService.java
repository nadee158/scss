package com.privasia.scss.cosmos.xml.element.service;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.CommonSolasDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.SolasInstructionType;
import com.privasia.scss.common.service.export.ExportUtilService;
import com.privasia.scss.cosmos.util.TextString;
import com.privasia.scss.cosmos.xml.element.GINCNTDRP;

@Service("gincntdrpService")
public class GINCNTDRPService {

	public GINCNTDRP constructGINCNTDRP(ExportContainer exportContainer) {
		GINCNTDRP gincntdrp = new GINCNTDRP();
		gincntdrp.setMSGTSE("GINCNTDRP");// Message Type : To hard code

		// Container No : To capture
		gincntdrp.setUNITSE(TextString.toEscapeXmlUpperCase(exportContainer.getContainer().getContainerNumber()));

		// Order Reference : To capture Booking Ref no
		gincntdrp.setORRFSE(TextString.toEscapeXmlUpperCase(exportContainer.getBookingNo()));

		// : To capture container ISO code
		gincntdrp.setUNISSE(TextString.toUpperCase(exportContainer.getContainer().getContainerISOCode()));// Container
																											// ISO
																											// code

		// capture E or F (E)mpty or (F)ull : To
		gincntdrp.setUNBTSE(TextString.toUpperCase(exportContainer.getContainer().getContainerFullOrEmpty()));

		// Position on Truck : To capture F, A or M
		gincntdrp.setCNPVSE(TextString.toUpperCase(exportContainer.getContainerPosition()));

		// Order supplier : To capture Agent code??
		gincntdrp.setORGVSE(TextString.toUpperCase(exportContainer.getShippingAgent()));

		// Line code : To capture line code
		gincntdrp.setLYNDSE(TextString.toUpperCase(exportContainer.getShippingLine()));

		// damageC2 // Damage Code 1 : To capture damage code 1, To loop if more
		// than 1
		setDamageCodeDetails(gincntdrp, exportContainer);

		// Seal Origin 1 : To capture seal origin, seal type and seal no 1, To
		// loop if more than 1
		setSealDetails(gincntdrp, exportContainer);

		// Unit Gross Weight : To capture Gross Weight
		setCalculatedGrossWeight(gincntdrp, exportContainer);

		setOperationRefer(gincntdrp, exportContainer);
		setOOG(gincntdrp, exportContainer);
		setIMDG(gincntdrp, exportContainer);
		setUNC(gincntdrp, exportContainer);

		gincntdrp.setUOLCSE("MY");
		gincntdrp.setUOLLSE("PKG");
		gincntdrp.setUOLOSE("PKG");
		gincntdrp.setCYOISE("N");
		gincntdrp.setCYCISE("N");
		gincntdrp.setACHISE("Y");
		gincntdrp.setPCHISE("Y");
		return gincntdrp;
	}

	private void setUNC(GINCNTDRP gincntdrp, ExportContainer exportContainer) {
		if (StringUtils.isNotEmpty(exportContainer.getDgUNCode())) {
			gincntdrp.setCUN1SE(exportContainer.getDgUNCode());
		}

	}

	private void setIMDG(GINCNTDRP gincntdrp, ExportContainer exportContainer) {
		if (StringUtils.isNotEmpty(exportContainer.getImdg())) {
			gincntdrp.setCNIMSE(exportContainer.getImdg());
			gincntdrp.setCIM1SE(exportContainer.getImdg());
			gincntdrp.setISA1SE(exportContainer.getImdgLabelID());

			if (StringUtils.equals(exportContainer.getImdg(), "5.1")
					|| StringUtils.equals(exportContainer.getImdg(), "5.2")) {
				gincntdrp.setISA1SE(exportContainer.getImdg());
			} else {
				gincntdrp.setISA1SE(exportContainer.getImdg().substring(0, 1));
			}

		}
	}

	private void setOOG(GINCNTDRP gincntdrp, ExportContainer exportContainer) {

		String oogindicator = "N";

		// Oversize Fore (in cm) : 5,0, Conditional if exist
		if (exportContainer.getOogOF() != null && exportContainer.getOogOF() > 0) {
			oogindicator = "Y";
			gincntdrp.setOVSVSE(Integer.toString(exportContainer.getOogOF()));
		}

		// Oversize Aft (in cm) : 5,0, Conditional if exist
		if (exportContainer.getOogOA() != null && exportContainer.getOogOA() > 0) {
			oogindicator = "Y";
			gincntdrp.setOVSASE(Integer.toString(exportContainer.getOogOA()));
		}
		
		// Oversize Left (in cm) : 5,0, Conditional if exist
		if (exportContainer.getOogOL() != null && exportContainer.getOogOL() > 0) {
			oogindicator = "Y";
			gincntdrp.setOVSLSE(Integer.toString(exportContainer.getOogOL()));
			
		}
		// Oversize Height (in cm) : 5,0, Conditional if exist
		if (exportContainer.getOogOH() != null && exportContainer.getOogOH() > 0) {
			oogindicator = "Y";
			gincntdrp.setOVHGSE(Integer.toString(exportContainer.getOogOH()));
		}
		
		// Oversize Rare (in cm) : 5,0, Conditional if exist
		if (exportContainer.getOogOR() != null && exportContainer.getOogOR() > 0) {
			oogindicator = "Y";
			gincntdrp.setOVSRSE(Integer.toString(exportContainer.getOogOR()));
		}
		
		if(StringUtils.equalsIgnoreCase("Y", oogindicator)){
			gincntdrp.setOOGISE(oogindicator);// OOG indicator : To capture Y or N,
		}
		

	}

	private void setOperationRefer(GINCNTDRP gincntdrp, ExportContainer exportContainer) {

		// Operational Refer : To capture Operational Refer Y/N, Conditional if
		// exi/st
		if (exportContainer.getReferFlag()) {
			gincntdrp.setPLMNSE(exportContainer.getReferTempType()); // refer
																		// sign
			// Temperature : To capture temperature (5,2, Conditional if exist )
			if (exportContainer.getReferTemp() != null) {
				gincntdrp.setRGRTSE(String.valueOf(exportContainer.getReferTemp()));
			}

			gincntdrp.setRGTESE(exportContainer.getReeferTempUnit());// Temp uni
		}

		gincntdrp.setCNORSE(ExportUtilService.getStringRepresentationOfBoolean(exportContainer.getReferFlag()));
	}

	private void setCalculatedGrossWeight(GINCNTDRP gincntdrp, ExportContainer exportContainer) {
		/**
		 * Remove decimal format
		 */
		String weight = "0";
		DecimalFormat df = new DecimalFormat("#");
		CommonSolasDTO solasDTO = exportContainer.getSolas();
		if (StringUtils.equalsIgnoreCase(solasDTO.getSolasInstruction(),
				SolasInstructionType.VGM_INSTRUCTION_SHIPPER.getValue()) && exportContainer.isWithinTolerance()) {
			weight = String.valueOf(exportContainer.getShipperVGM());
		} else {
			if (exportContainer.getExpNetWeight() != null) {
				weight = Integer.toString(exportContainer.getExpNetWeight());
			}
		}

		weight = df.format(Double.valueOf(weight));
		gincntdrp.setUNBGSE(weight);
	}

	private void setSealDetails(GINCNTDRP gincntdrp, ExportContainer exportContainer) {

		if (StringUtils.equalsIgnoreCase(exportContainer.getContainer().getContainerFullOrEmpty(),
				ContainerFullEmptyType.FULL.getValue())) {

			if (exportContainer.getSealAttribute() != null) {
				if (StringUtils.isNotEmpty(exportContainer.getSealAttribute().getSeal01Number())) {
					gincntdrp.setSN01SE(TextString.toUpperCase(exportContainer.getSealAttribute().getSeal01Number()));
					gincntdrp.setST01SE(TextString.toUpperCase(exportContainer.getSealAttribute().getSeal01Type()));
					gincntdrp.setSO01SE(TextString.toUpperCase(exportContainer.getSealAttribute().getSeal01Origin()));
				}
				if (StringUtils.isNotEmpty(exportContainer.getSealAttribute().getSeal02Number())) {
					gincntdrp.setSN02SE(TextString.toUpperCase(exportContainer.getSealAttribute().getSeal02Number()));
					gincntdrp.setST02SE(TextString.toUpperCase(exportContainer.getSealAttribute().getSeal02Type()));
					gincntdrp.setSO02SE(TextString.toUpperCase(exportContainer.getSealAttribute().getSeal02Origin()));
				}
			}

		}

	}

	private void setDamageCodeDetails(GINCNTDRP gincntdrp, ExportContainer exportContainer) {
		if (!(exportContainer.getDamageCode_01() == null
				|| StringUtils.isEmpty(exportContainer.getDamageCode_01().getDamageCode()))) {
			gincntdrp.setDM01SE(TextString.toUpperCase(exportContainer.getDamageCode_01().getDamageCode()));
		}
		if (!(exportContainer.getDamageCode_02() == null
				|| StringUtils.isEmpty(exportContainer.getDamageCode_02().getDamageCode()))) {
			gincntdrp.setDM02SE(TextString.toUpperCase(exportContainer.getDamageCode_02().getDamageCode()));
		}
		if (!(exportContainer.getDamageCode_03() == null
				|| StringUtils.isEmpty(exportContainer.getDamageCode_03().getDamageCode()))) {
			gincntdrp.setDM03SE(TextString.toUpperCase(exportContainer.getDamageCode_03().getDamageCode()));
		}
		if (!(exportContainer.getDamageCode_04() == null
				|| StringUtils.isEmpty(exportContainer.getDamageCode_04().getDamageCode()))) {
			gincntdrp.setDM04SE(TextString.toUpperCase(exportContainer.getDamageCode_04().getDamageCode()));
		}
		if (!(exportContainer.getDamageCode_05() == null
				|| StringUtils.isEmpty(exportContainer.getDamageCode_05().getDamageCode()))) {
			gincntdrp.setDM05SE(TextString.toUpperCase(exportContainer.getDamageCode_05().getDamageCode()));
		}
		if (!(exportContainer.getDamageCode_06() == null
				|| StringUtils.isEmpty(exportContainer.getDamageCode_06().getDamageCode()))) {
			gincntdrp.setDM06SE(TextString.toUpperCase(exportContainer.getDamageCode_06().getDamageCode()));
		}
		if (!(exportContainer.getDamageCode_07() == null
				|| StringUtils.isEmpty(exportContainer.getDamageCode_07().getDamageCode()))) {
			gincntdrp.setDM07SE(TextString.toUpperCase(exportContainer.getDamageCode_07().getDamageCode()));
		}
		if (!(exportContainer.getDamageCode_08() == null
				|| StringUtils.isEmpty(exportContainer.getDamageCode_08().getDamageCode()))) {
			gincntdrp.setDM08SE(TextString.toUpperCase(exportContainer.getDamageCode_08().getDamageCode()));
		}
		if (!(exportContainer.getDamageCode_09() == null
				|| StringUtils.isEmpty(exportContainer.getDamageCode_09().getDamageCode()))) {
			gincntdrp.setDM09SE(TextString.toUpperCase(exportContainer.getDamageCode_09().getDamageCode()));
		}
	}

}
