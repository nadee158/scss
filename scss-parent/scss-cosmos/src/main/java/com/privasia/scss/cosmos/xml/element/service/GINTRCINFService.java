package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.xml.element.GINTRCINF;

@Service("gintrcinfService")
public class GINTRCINFService {

	public GINTRCINF constructGINTRCINF(CosmosCommonValuesDTO commonValuesDTO) {
		GINTRCINF gintrcinf = new GINTRCINF();
		gintrcinf.setMSGTSC("GINTRCINF");
		gintrcinf.setLANESC(commonValuesDTO.getLaneNo());
		gintrcinf.setVMIDSC(commonValuesDTO.getTruckNo());
		gintrcinf.setATDDSC(commonValuesDTO.getDate());
		gintrcinf.setATDTSC(commonValuesDTO.getTime());
		gintrcinf.setVMYKSC(commonValuesDTO.getCompCode());
		return gintrcinf;
	}
}
