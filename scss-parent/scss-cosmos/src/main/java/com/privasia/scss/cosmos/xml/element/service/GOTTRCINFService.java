package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
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
    gottrcinf.setLANESC(commonValuesDTO.getLaneNo());// Lane : To capture gate lane no
    gottrcinf.setMSGTSC("GOTTRCINF");// Message Type : To hard code
    gottrcinf.setVMIDSC(commonValuesDTO.getTruckNo());// Truck License Plate : To capture truck no
    gottrcinf.setVMYKSC(commonValuesDTO.getCompCode());// Truck Com.Code: To capture Truck Com.Code
    return gottrcinf;
  }

//@formatter:off
//      + "<GOTTRCINF>\n" // For Gate In Truck Information
//      + "<MSGTSC>GOTTRCINF</MSGTSC>\n" //Message Type : To hard code
//      + "<LANESC>" + toUpperCase(laneNo) + "</LANESC>\n" // Lane : To capture gate lane no
//      + "<VMIDSC>" + toUpperCase(truckNo) + "</VMIDSC>\n" // Truck License Plate : To capture truck no
//      + "<ATDDSC>" + date + "</ATDDSC>\n" // Date of Arrival : To capture current date??
//      + "<ATDTSC>" + time + "</ATDTSC>\n" // Time of Arrival : To capture current time??
//  + "<VMYKSC>" + compCode + "</VMYKSC>\n" //Truck Com. Code : To capture Truck Com. Code
//      + "</GOTTRCINF>\n"
//@formatter:on

}
