package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.xml.element.GINCNTPUP;

@Service("gincntpupService")
public class GINCNTPUPService {

  public GINCNTPUP constructGINCNTPUP(ImportContainer importContainer) {
    GINCNTPUP gincntpup = new GINCNTPUP();
    gincntpup.setMSGTSE("GINCNTPUP");// Message Type : To hard code

    gincntpup.setUNITSE(importContainer.getContainer().getContainerNumber());// Container No : To
                                                                             // capture

    gincntpup.setUNBTSE(importContainer.getContainer().getContainerFullOrEmpty());// (E)mpty or
                                                                                  // (F)ull : To
                                                                                  // capture E or F

    gincntpup.setCNPVSE(importContainer.getContainerPosition());// Position on Truck : To capture F
                                                                // = Front, A = Aft or M = Middle
    gincntpup.setUPLKSE("MY");
    gincntpup.setUPPKSE("PKG");
    gincntpup.setUPOMSE("PKG"); // Changed by feroz on 11 OCT 2007
    gincntpup.setCYOISE("N");// Opening Time : To hard code
    gincntpup.setCYCISE("N");// Closing Time : To hard code
    gincntpup.setACHISE("Y");// Automatic A-Check : To hardcode
    gincntpup.setPCHISE("Y");// Automatic P-Check : To hardcode
    gincntpup.setCRORSE("Y");// Create Order : To hardcode
    return gincntpup;
  }

//@formatter:off  
//  + "<GINCNTPUP>\n" // For Gate Incontainer pick up
//  + "<MSGTSE>GINCNTPUP</MSGTSE>\n" // Message Type : To hard code
//  + "<UNITSE>" + toEscapeXmlUpperCase(f.getContainerNoC2()) + "</UNITSE>\n" // Container No : To capture
//  + "<UNBTSE>" + toUpperCase(f.getFullOrEmptyC2()) + "</UNBTSE>\n" // (E)mpty or (F)ull : To capture E or F
//  + "<CNPVSE>" + toUpperCase(f.getPositionOnTruckC2()) + "</CNPVSE>\n" // Position on Truck : To capture F = Front, A = Aft or M = Middle
//  + "<UPLKSE>MY</UPLKSE>\n"
//  + "<UPPKSE>PKG</UPPKSE>\n"
//  + "<UPOMSE>PKG</UPOMSE>\n" //Changed by feroz on 11 OCT 2007
//  + "<CYOISE>N</CYOISE>\n" // Opening Time : To hard code
//  + "<CYCISE>N</CYCISE>\n" // Closing Time : To hard code
//  + "<ACHISE>Y</ACHISE>\n" // Automatic A-Check : To hardcode
//  + "<PCHISE>Y</PCHISE>\n" // Automatic P-Check : To hardcode
//  + "<CRORSE>Y</CRORSE>\n" // Create Order : To hardcode
//  + "</GINCNTPUP>"
//@formatter:on
}
