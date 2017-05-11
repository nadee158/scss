package com.privasia.scss.cosmos.xml.element.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.xml.element.GOTCNTINF;

@Service("gotcntinfService")
public class GOTCNTINFService {

  public GOTCNTINF constructGOTCNTINF(ImportContainer importContainer) {
    GOTCNTINF gottrcinf = null;
    if (!(importContainer.getContainer() == null)) {
      gottrcinf = new GOTCNTINF();
      gottrcinf.setUNITSE(importContainer.getContainer().getContainerNumber());
      if (!(importContainer.getSealAttribute() == null)) {
        if (StringUtils.equals(importContainer.getContainer().getContainerFullOrEmpty(), "F")) {
          gottrcinf.setSN01SE(importContainer.getSealAttribute().getSeal01Number());
          gottrcinf.setSN02SE(importContainer.getSealAttribute().getSeal02Number());
          gottrcinf.setSO01SE(importContainer.getSealAttribute().getSeal01Origin());
          gottrcinf.setSO02SE(importContainer.getSealAttribute().getSeal02Origin());
          gottrcinf.setST01SE(importContainer.getSealAttribute().getSeal01Type());
          gottrcinf.setST02SE(importContainer.getSealAttribute().getSeal02Type());
        }
      }
    }
    return gottrcinf;
  }

//@formatter:off
//  if (f.getFullOrEmptyC1().equalsIgnoreCase("F")) {
//    String seal2 = "";
//    if (f.getSeal1C1() != null && !f.getSeal1C1().equals("")) {
//        seal = "<SO01SE>" + toUpperCase(f.getSeal1OriginC1()) + "</SO01SE>\n"
//                + "<ST01SE>" + toUpperCase(f.getSeal1TypeC1()) + "</ST01SE>\n"
//                + "<SN01SE>" + toUpperCase(f.getSeal1C1()) + "</SN01SE>\n";
//    }
//    if (f.getSeal2C1() != null && !f.getSeal2C1().equals("")) {
//        seal2 = "<SO02SE>" + toUpperCase(f.getSeal2OriginC1()) + "</SO02SE>\n"
//                + "<ST02SE>" + toUpperCase(f.getSeal2TypeC1()) + "</ST02SE>\n"
//                + "<SN02SE>" + toUpperCase(f.getSeal2C1()) + "</SN02SE>\n";
//
//    }
//    seal = seal + seal2;
//}

//  + "<GOTCNTINF>\n"
//  + "<UNITSE>" + containerNoC1 + "</UNITSE>\n"
//  + seal
//  + "</GOTCNTINF>\n"
//@formatter:on


}
