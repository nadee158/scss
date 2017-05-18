package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.xml.element.GINCNTPUP;

@Service("gincntpupService")
public class GINCNTPUPService {

  public GINCNTPUP constructGINCNTPUP(ImportContainer importContainer) {
    GINCNTPUP gincntpup = new GINCNTPUP();
    gincntpup.setMSGTSE("GINCNTPUP");
    gincntpup.setUNITSE(importContainer.getContainer().getContainerNumber());
    gincntpup.setUNBTSE(importContainer.getContainer().getContainerFullOrEmpty());
    gincntpup.setCNPVSE(importContainer.getContainerPosition());
    gincntpup.setUPLKSE("MY");
    gincntpup.setUPPKSE("PKG");
    gincntpup.setUPOMSE("PKG"); 
    gincntpup.setCYOISE("N");
    gincntpup.setCYCISE("N");
    gincntpup.setACHISE("Y");
    gincntpup.setPCHISE("Y");
    gincntpup.setCRORSE("Y");
    return gincntpup;
  }
}
