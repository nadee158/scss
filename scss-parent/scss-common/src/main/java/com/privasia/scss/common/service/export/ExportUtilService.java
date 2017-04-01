package com.privasia.scss.common.service.export;

import org.apache.commons.lang3.StringUtils;

public class ExportUtilService {

	public static String getReferTempSign(int value) {

		if (value > 0) {
			return "+";
		} else if (value < 0) {
			return "-";
		} else {
			return "";
		}

	}

	public static String getStringRepresentationOfBoolean(Boolean value) {

		if (value != null && value) {
			return "Y";
		} else {
			return "N";
		}

	}
	
	public static String getSolasVGMType(String vgmType) {

		if (vgmType!= null) {
			if(StringUtils.equalsIgnoreCase("SHIPPER VGM", vgmType)){
				return "S";
			}else if(StringUtils.equalsIgnoreCase("TERMINAL VGM", vgmType)){
				return "T";
			}else{
				return "";
			}
		}else{
			return "";
		} 
	}

	public static void main(String args[]) {
		int j = -8;
		System.out.println(Math.abs(0));
		System.out.println(ExportUtilService.getReferTempSign(-9));
	}

}
