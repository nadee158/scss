/**
 * 
 */
package com.privasia.scss.cosmos.xml.element.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.xml.element.CSMCTL;

/**
 * @author Janaka
 *
 */
@Service("csmctlService")
public class CSMCTLService {

	public CSMCTL constructCSMCTL(CosmosCommonValuesDTO commonValuesDTO) {

		CSMCTL csmctl = new CSMCTL();
		csmctl.setRQST("GSRQS");
		csmctl.setACTN("CRT");
		csmctl.setRTNC("0");
		csmctl.setRQDS("CTEDSC");
		csmctl.setRTNM("AS");
		csmctl.setUSID(StringUtils.upperCase(commonValuesDTO.getLoginUser()));
		csmctl.setRQUI(commonValuesDTO.getMsgUniqueId());
		csmctl.setTRMC("WPT1");
		return csmctl;
	}
	
	public CSMCTL constructCSMCTLForImport(CosmosCommonValuesDTO commonValuesDTO) {

		CSMCTL csmctl = new CSMCTL();
		csmctl.setRQST("GSRQS");
		csmctl.setACTN("CRT");
		csmctl.setRTNC("0");
		csmctl.setRQDS("CTEDSE");
		csmctl.setRTNM("AS");
		csmctl.setUSID(StringUtils.upperCase(commonValuesDTO.getLoginUser()));
		csmctl.setRQUI(commonValuesDTO.getMsgUniqueId());
		csmctl.setTRMC("WPT1");
		return csmctl;
	}

}
