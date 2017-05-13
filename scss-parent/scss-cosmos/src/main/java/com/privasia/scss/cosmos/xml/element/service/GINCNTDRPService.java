package com.privasia.scss.cosmos.xml.element.service;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.cosmos.xml.element.GINCNTDRP;

@Service("gincntdrpService")
public class GINCNTDRPService {

  public GINCNTDRP constructGINCNTDRP(ExportContainer exportContainer) {
    GINCNTDRP gincntdrp = new GINCNTDRP();
    gincntdrp.setMSGTSE("GINCNTDRP");// Message Type : To hard code
    gincntdrp.setUNITSE(exportContainer.getContainer().getContainerNumber());// Container No : To
                                                                             // capture
    gincntdrp.setORRFSE(exportContainer.getBookingNo());// Order Reference : To capture Booking Ref
                                                        // no
    gincntdrp.setUNISSE(exportContainer.getContainer().getContainerISOCode());// Container ISO code
                                                                              // : To capture
                                                                              // container ISO code
    gincntdrp.setUNBGSE(exportContainer.getContainer().getContainerFullOrEmpty());// (E)mpty or
                                                                                  // (F)ull : To
                                                                                  // capture E or F
    gincntdrp.setCNPVSE(exportContainer.getContainerPosition());// Position on Truck : To capture F,
                                                                // A or M
    gincntdrp.setORGVSE(exportContainer.getShipCode());// Order supplier : To capture Agent code??
    gincntdrp.setLYNDSE(exportContainer.getShippingLine());// Line code : To capture line code
    // damageC2 // Damage Code 1 : To capture damage code 1, To loop if more than 1
    setDamageCodeDetails(gincntdrp, exportContainer);
    // + sealC2 // Seal Origin 1 : To capture seal origin, seal type and seal no 1, To loop if more
    // than 1
    setSealDetails(gincntdrp, exportContainer);
    gincntdrp.setUNBGSE(getCalculatedGrossWeight(exportContainer));

    setOperationRefer(gincntdrp, exportContainer);
    setOOG(gincntdrp, exportContainer);
    setIMDG(gincntdrp, exportContainer);
    setUNC(gincntdrp, exportContainer);

    gincntdrp.setUOLCSE("MY");
    gincntdrp.setUOLLSE("PKG");
    gincntdrp.setUOLOSE("PKG");
    gincntdrp.setCYOISE("N");
    gincntdrp.setCYCISE("N");
    gincntdrp.setACHISE("Y");
    gincntdrp.setPCHISE("Y");
    return gincntdrp;
  }



//@formatter:off
//+ "<GINCNTDRP>\n" // For Gate In container drop off
//+ "<MSGTSE>GINCNTDRP</MSGTSE>\n" // Message Type : To hard code
//+ "<UNITSE>" + toEscapeXmlUpperCase(f.getContainerNoC2()) + "</UNITSE>\n" // Container No : To capture
//+ "<ORRFSE>" + toEscapeXmlUpperCase(f.getBookingNoC2()) + "</ORRFSE>\n" // Order Reference : To capture Booking Ref no
//+ "<UNISSE>" + toUpperCase(f.getISOC2()) + "</UNISSE>\n" // Container ISO code : To capture container ISO code
//+ "<UNBTSE>" + toUpperCase(f.getFullOrEmptyC2()) + "</UNBTSE>\n" // (E)mpty or (F)ull : To capture E or F
//+ "<CNPVSE>" + toUpperCase(f.getPositionOnTruckC2()) + "</CNPVSE>\n" // Position on Truck : To capture F, A or M
//+ "<ORGVSE>" + toUpperCase(f.getExpAgentC2()) + "</ORGVSE>\n" // Order supplier : To capture Agent code??
//+ "<LYNDSE>" + toUpperCase(f.getLineC2()) + "</LYNDSE>\n" // Line code : To capture line code
//+ damageC2 // Damage Code 1 : To capture damage code 1, To loop if more than 1
//+ sealC2 // Seal Origin 1 : To capture seal origin, seal type and seal no 1, To loop if more than 1
//+ "<UNBGSE>" + weight + "</UNBGSE>\n" // Unit Gross Weight : To capture Gross Weight
//+ operationReferC2
//+ OOGC2
//+ IMDGC2 // IMDG Class : To capture IMDG Class,Conditional if exist, loop if more than 1
//+ UNC2 // UN No : To capture UN no, Conditional if exist, loop if more than 1
//+ "<UOLCSE>MY</UOLCSE>\n"
//+ "<UOLLSE>PKG</UOLLSE>\n"
//+ "<UOLOSE>PKG</UOLOSE>\n" //Changed by feroz on 11 OCT 2007
//+ "<CYOISE>N</CYOISE>\n" // Opening Time : To hard code
//+ "<CYCISE>N</CYCISE>\n" // Closing Time : To hard code
//+ "<ACHISE>Y</ACHISE>\n" // Automatic A-Check : To hardcode
//+ "<PCHISE>Y</PCHISE>\n" // Automatic P-Check : To hardcode
//+ "</GINCNTDRP>\n"
//@formatter:on

  private void setUNC(GINCNTDRP gincntdrp, ExportContainer exportContainer) {
    if (StringUtils.isNotEmpty(exportContainer.getDgUNCode())) {
      gincntdrp.setCUN1SE(exportContainer.getDgUNCode());
      // UNC2 = "<CUN1SE>" + f.getUNC2() + "</CUN1SE>\n";
    }

  }

  private void setIMDG(GINCNTDRP gincntdrp, ExportContainer exportContainer) {
    if (StringUtils.isNotEmpty(exportContainer.getImdg())) {
      gincntdrp.setCNIMSE(exportContainer.getImdg());
      gincntdrp.setCIM1SE(exportContainer.getImdg());
      gincntdrp.setISA1SE(exportContainer.getImdgLabelID());

      if (StringUtils.equals(exportContainer.getImdg(), "5.1")
          || StringUtils.equals(exportContainer.getImdg(), "5.2")) {
        gincntdrp.setISA1SE(exportContainer.getImdg());
      } else {
        gincntdrp.setISA1SE(exportContainer.getImdg().substring(0, 1));
      }

    }

    // if (!f.getIMDGC2().equals("")) {
    // IMDGC2 = "<CNIMSE>" + f.getIMDGC2() + "</CNIMSE>\n"; //- Testing
    // IMDGC2 = "<CIM1SE>" + f.getIMDGC2() + "</CIM1SE>\n";
    // IMDGC2 += "<ISA1SE>" + f.getIMDGlabelsC2() + "</ISA1SE>\n";
    //
    // if (f.getIMDGC2().equals("5.1") || f.getIMDGC2().equals("5.2")) {
    // IMDGC2 += "<ISI1SE>" + f.getIMDGC2() + "</ISI1SE>\n";
    // } else {
    // IMDGC2 += "<ISI1SE>" + f.getIMDGC2().substring(0, 1) + "</ISI1SE>\n";
    // }
    // }
    //
  }



  private void setOOG(GINCNTDRP gincntdrp, ExportContainer exportContainer) {

    String oogindicator = "N";

    if (exportContainer.getOogOA() > 0) {
      oogindicator = "Y";
      gincntdrp.setOVSASE(Integer.toString(exportContainer.getOogOA()));// Oversize Aft (in cm) :
                                                                        // 5,0,
      // Conditional if exist
    }

    if (exportContainer.getOogOF() > 0) {
      oogindicator = "Y";
      gincntdrp.setOVSVSE(Integer.toString(exportContainer.getOogOF()));// Oversize Fore (in cm) :
                                                                        // 5,0,
      // Conditional if exist
    }

    if (exportContainer.getOogOL() > 0) {
      oogindicator = "Y";
      gincntdrp.setOVSLSE(Integer.toString(exportContainer.getOogOL()));// // Oversize Left (in cm)
                                                                        // : 5,0,
      // Conditional if exist
    }

    if (exportContainer.getOogOH() > 0) {
      oogindicator = "Y";
      gincntdrp.setOVHGSE(Integer.toString(exportContainer.getOogOH()));// Oversize Height (in cm) :
                                                                        // 5,0,
      // Conditional if exist
    }

    if (exportContainer.getOogOR() > 0) {
      oogindicator = "Y";
      gincntdrp.setOVSRSE(Integer.toString(exportContainer.getOogOR()));// Oversize Height (in cm) :
                                                                        // 5,0,
      // Conditional if exist
    }

    gincntdrp.setOOGISE(oogindicator);// OOG indicator : To capture Y or N,
    // Conditional if exist



  }



  private void setOperationRefer(GINCNTDRP gincntdrp, ExportContainer exportContainer) {

    gincntdrp.setCNORSE(exportContainer.getOperationReefer());// Operational Refer : To capture
                                                              // Operational Refer Y/N, Conditional
                                                              // if exi/st

    if (StringUtils.equalsIgnoreCase(exportContainer.getOperationReefer(), "Y")) {
      String tempSign = "+";

      if (exportContainer.getReferTemp() < 0) {
        tempSign = "-";
      }

      String temp = Double.toString((exportContainer.getReferTemp() * (-1)));


      gincntdrp.setPLMNSE(tempSign);
      gincntdrp.setRGRTSE(temp);// Temperature : To capture temperature (5,2, Conditional if exist )
      gincntdrp.setRGTESE(exportContainer.getReeferTempUnit());// Temp uni


      // operationRefer = "<CNORSE>" + f.getOperationReefer() + "</CNORSE>\n" //Operational Refer :
      // To capture Operational Refer Y/N, Conditional if exi/st
      // + "<PLMNSE>" + tempSign + "</PLMNSE>\n"
      // + "<RGRTSE>" + temp + "</RGRTSE>\n" // Temperature : To capture temperature (5,2,
      // Conditional if exist )
      // + "<RGTESE>" + f.getTempUnit() + "</RGTESE>\n"; // Temp uni

    }

  }



  private String getCalculatedGrossWeight(ExportContainer exportContainer) {
    /**
     * Remove decimal format
     */
    String weight = "0";
    DecimalFormat df = new DecimalFormat("#");
    if (exportContainer.getShipperVGM() > 0 && exportContainer.isWithinTolerance()) {
      weight = String.valueOf(exportContainer.getShipperVGM());
    } else {
      if (exportContainer.getExpNetWeight() > 0) {
        weight = Integer.toString(exportContainer.getExpNetWeight());
      }
    }

    weight = df.format(Double.valueOf(weight));
    return weight;
  }



  private void setSealDetails(GINCNTDRP gincntdrp, ExportContainer exportContainer) {
    if (!(exportContainer.getSealAttribute() == null)) {
      if (StringUtils.isNotEmpty(exportContainer.getSealAttribute().getSeal01Number())) {
        gincntdrp.setSN01SE(exportContainer.getSealAttribute().getSeal01Number());
        gincntdrp.setST01SE(exportContainer.getSealAttribute().getSeal01Type());
        gincntdrp.setSO01SE(exportContainer.getSealAttribute().getSeal01Origin());
      }
      if (StringUtils.isNotEmpty(exportContainer.getSealAttribute().getSeal02Number())) {
        gincntdrp.setSN02SE(exportContainer.getSealAttribute().getSeal02Number());
        gincntdrp.setST02SE(exportContainer.getSealAttribute().getSeal02Type());
        gincntdrp.setSO02SE(exportContainer.getSealAttribute().getSeal02Origin());
      }
    }

  }

  private void setDamageCodeDetails(GINCNTDRP gincntdrp, ExportContainer exportContainer) {
    if (!(exportContainer.getDamageCode_01() == null
        || StringUtils.isEmpty(exportContainer.getDamageCode_01().getDamageCode()))) {
      gincntdrp.setDM01SE(exportContainer.getDamageCode_01().getDamageCode());
    }
    if (!(exportContainer.getDamageCode_02() == null
        || StringUtils.isEmpty(exportContainer.getDamageCode_02().getDamageCode()))) {
      gincntdrp.setDM02SE(exportContainer.getDamageCode_02().getDamageCode());
    }
    if (!(exportContainer.getDamageCode_03() == null
        || StringUtils.isEmpty(exportContainer.getDamageCode_03().getDamageCode()))) {
      gincntdrp.setDM03SE(exportContainer.getDamageCode_03().getDamageCode());
    }
    if (!(exportContainer.getDamageCode_04() == null
        || StringUtils.isEmpty(exportContainer.getDamageCode_04().getDamageCode()))) {
      gincntdrp.setDM04SE(exportContainer.getDamageCode_04().getDamageCode());
    }
    if (!(exportContainer.getDamageCode_05() == null
        || StringUtils.isEmpty(exportContainer.getDamageCode_05().getDamageCode()))) {
      gincntdrp.setDM05SE(exportContainer.getDamageCode_05().getDamageCode());
    }
    if (!(exportContainer.getDamageCode_06() == null
        || StringUtils.isEmpty(exportContainer.getDamageCode_06().getDamageCode()))) {
      gincntdrp.setDM06SE(exportContainer.getDamageCode_06().getDamageCode());
    }
    if (!(exportContainer.getDamageCode_07() == null
        || StringUtils.isEmpty(exportContainer.getDamageCode_07().getDamageCode()))) {
      gincntdrp.setDM07SE(exportContainer.getDamageCode_07().getDamageCode());
    }
    if (!(exportContainer.getDamageCode_08() == null
        || StringUtils.isEmpty(exportContainer.getDamageCode_08().getDamageCode()))) {
      gincntdrp.setDM08SE(exportContainer.getDamageCode_08().getDamageCode());
    }
    if (!(exportContainer.getDamageCode_09() == null
        || StringUtils.isEmpty(exportContainer.getDamageCode_09().getDamageCode()))) {
      gincntdrp.setDM09SE(exportContainer.getDamageCode_09().getDamageCode());
    }
  }

}
