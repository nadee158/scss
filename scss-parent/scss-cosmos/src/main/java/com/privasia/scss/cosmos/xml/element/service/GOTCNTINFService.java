package com.privasia.scss.cosmos.xml.element.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.cosmos.util.TextString;
import com.privasia.scss.cosmos.xml.element.GOTCNTINF;

@Service("gotcntinfService")
public class GOTCNTINFService {

	public GOTCNTINF constructGOTCNTINF(ImportContainer importContainer) {
		GOTCNTINF gottrcinf = null;
		if (importContainer.getContainer() != null) {
			gottrcinf = new GOTCNTINF();
			gottrcinf.setUNITSE(TextString.toEscapeXmlUpperCase(importContainer.getContainer().getContainerNumber()));
			if (importContainer.getSealAttribute() != null) {
				if (StringUtils.equals(importContainer.getContainer().getContainerFullOrEmpty(), ContainerFullEmptyType.FULL.getValue())) {
					
					if (StringUtils.isNotEmpty(importContainer.getSealAttribute().getSeal01Number())) {
						gottrcinf.setSN01SE(TextString.toUpperCase(importContainer.getSealAttribute().getSeal01Number()));
						gottrcinf.setST01SE(TextString.toUpperCase(importContainer.getSealAttribute().getSeal01Type()));
						gottrcinf.setSO01SE(TextString.toUpperCase(importContainer.getSealAttribute().getSeal01Origin()));
					}
					if (StringUtils.isNotEmpty(importContainer.getSealAttribute().getSeal02Number())) {
						gottrcinf.setSN02SE(TextString.toUpperCase(importContainer.getSealAttribute().getSeal02Number()));
						gottrcinf.setST02SE(TextString.toUpperCase(importContainer.getSealAttribute().getSeal02Type()));
						gottrcinf.setSO02SE(TextString.toUpperCase(importContainer.getSealAttribute().getSeal02Origin()));
					}
					
				}
			}
		}
		return gottrcinf;
	}

}
