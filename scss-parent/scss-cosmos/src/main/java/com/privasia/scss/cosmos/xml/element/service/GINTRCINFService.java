package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.xml.element.GINTRCINF;

@Service("gintrcinfService")
public class GINTRCINFService {

  public GINTRCINF constructGINTRCINF(CosmosCommonValuesDTO commonValuesDTO) {
    GINTRCINF gintrcinf = new GINTRCINF();
    gintrcinf.setATDDSC(commonValuesDTO.getDate());
    gintrcinf.setATDTSC(commonValuesDTO.getTime());
    gintrcinf.setLANESC(commonValuesDTO.getLaneNo());
    gintrcinf.setMSGTSC("GINTRCINF");
    gintrcinf.setVMIDSC(commonValuesDTO.getTruckNo());
    gintrcinf.setVMYKSC(commonValuesDTO.getCompCode());
    return gintrcinf;
  }
//@formatter:off
//  + "<GINTRCINF>\n" // For Gate In Truck Information
//  + "<MSGTSC>GINTRCINF</MSGTSC>\n" //Message Type : To hard code
//  + "<LANESC>" + toUpperCase(f.getLaneNo()) + "</LANESC>\n" // Lane : To capture gate lane no
//  + "<VMIDSC>" + toUpperCase(f.getTruckHeadNo()) + "</VMIDSC>\n" // Truck License Plate : To capture truck no
//  + "<ATDDSC>" + date + "</ATDDSC>\n" // Date of Arrival : To capture current date
//  + "<ATDTSC>" + time + "</ATDTSC>\n" // Time of Arrival : To capture current time
//  + "<VMYKSC>" + toUpperCase(f.getCompCode()) + "</VMYKSC>\n" //Truck Com. Code : To capture Truck Com. Code
//  + "</GINTRCINF>\n"
//@formatter:on
}
