package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.util.TextString;
import com.privasia.scss.cosmos.xml.element.GOTTRCINF;

/**
 * @author nsenevirat001
 *
 */
@Service("gottrcinfService")
public class GOTTRCINFService {

  public GOTTRCINF constructGOTTRCINF(CosmosCommonValuesDTO commonValuesDTO) {
    GOTTRCINF gottrcinf = new GOTTRCINF();
    gottrcinf.setATDDSC(commonValuesDTO.getDate());// Date of Arrival : To capture current date??
    gottrcinf.setATDTSC(commonValuesDTO.getTime());// Time of Arrival : To capture current time??
    gottrcinf.setLANESC(TextString.toUpperCase(commonValuesDTO.getLaneNo()));// Lane : To capture gate lane no
    gottrcinf.setMSGTSC("GOTTRCINF");// Message Type : To hard code
    gottrcinf.setVMIDSC(TextString.toUpperCase(commonValuesDTO.getTruckNo()));// Truck License Plate : To capture truck no
    gottrcinf.setVMYKSC(TextString.toUpperCase(commonValuesDTO.getCompCode()));// Truck Com.Code: To capture Truck Com.Code
    return gottrcinf;
  }

}
