package com.privasia.scss.gatein.service;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.TransactionDTO;

@Service("gateInXMLRequestService")
public class GateInXMLRequestService {


  public String createImpRequestXML(TransactionDTO transactionDTO, String userName, String msgUniqueId) {
    return createImpRequestXML(transactionDTO, userName, msgUniqueId, false, "2", "3");
  }

  public String createImpRequestXML(TransactionDTO transactionDTO, String userName, String msgUniqueId,
      boolean isImpExp, String cont1Index, String cont2Index) {

    ImportContainer importContainer01 = transactionDTO.getImportContainer01();
    ImportContainer importContainer02 = transactionDTO.getImportContainer02();

    StringBuilder requestXMLC2 = new StringBuilder("");
    StringBuilder requestXML = new StringBuilder("");

    if (!(importContainer02 == null)) {
      if (isImpExp) {
        requestXMLC2.append("<Message Index=\"").append(cont2Index).append("\">\n");
      } else {
        requestXMLC2.append("<Message Index=\"3\">\n");
      }

      requestXMLC2 = constructCommonRequestXML(importContainer02, userName, msgUniqueId, requestXMLC2);
    }

    if (!(importContainer01 == null)) {

      if (isImpExp) {
        requestXML.append("<Message Index=\"").append(cont1Index).append("\">\n");
      } else {
        requestXML.append("<Message Index=\"2\">\n");
      }

      requestXML = constructCommonRequestXML(importContainer01, userName, msgUniqueId, requestXML);

      requestXML.append(requestXMLC2.toString());
    }

    return requestXML.toString();

  }

  private StringBuilder constructCommonRequestXML(ImportContainer importContainer, String userName, String msgUniqueId,
      StringBuilder requestXMLC2) {

    //@formatter:off
    
    String errXMLMsg = "";

    if (StringUtils.isNotEmpty(importContainer.getContainerNumber())) {
      if (StringUtils.isNotBlank(importContainer.getErrXMLMsg())) {
        errXMLMsg = "<ERRI>" + importContainer.getErrXMLMsg() + "</ERRI>\n";
      }
    }
    
    requestXMLC2.append("<CSMCTL>\n")
                 .append("<RQST>GSRQS</RQST>\n") // Request Code : To hard code
                 .append("<ACTN>CRT</ACTN>\n") // Action Code : To hard code
                 .append("<RTNC>0</RTNC>\n") // Return Code : To hard code
                     .append(errXMLMsg)
                 .append("<RQDS>CTEDSE</RQDS>\n") // Requestor Data Structure : To hard code
                 .append("<RTNM>AS</RTNM>\n") // Return Mode : To hard code
                 .append("<USID>").append(userName).append("</USID>\n") // User ID : To capture SCSS user id
                 .append("<RQUI>").append(msgUniqueId).append("</RQUI>\n") // To input msg unique id
                 .append("<TRMC>WPT1</TRMC>\n") // Terminal : To hard code
                 .append("</CSMCTL>\n")
                 .append("<GINCNTPUP>\n") // For Gate Incontainer pick up
                 .append("<MSGTSE>GINCNTPUP</MSGTSE>\n") // Message Type : To hard code
                 .append("<UNITSE>").append(toEscapeXmlUpperCase(importContainer.getContainerNumber())).append("</UNITSE>\n") // Container No : To capture
                 .append("<UNBTSE>").append(toUpperCase(importContainer.getFullOrEmpty())).append("</UNBTSE>\n") // (E)mpty or (F)ull : To capture E or F
                 .append("<CNPVSE>").append(toUpperCase(importContainer.getPositionOnTruck())).append("</CNPVSE>\n") // Position on Truck : To capture F = Front, A = Aft or M = Middle
                 .append("<UPLKSE>MY</UPLKSE>\n")
                 .append("<UPPKSE>PKG</UPPKSE>\n")
                 .append("<UPOMSE>PKG</UPOMSE>\n") //Changed by feroz on 11 OCT 2007
                 .append("<CYOISE>N</CYOISE>\n") // Opening Time : To hard code
                 .append("<CYCISE>N</CYCISE>\n") // Closing Time : To hard code
                 .append("<ACHISE>Y</ACHISE>\n") // Automatic A-Check : To hardcode
                 .append("<PCHISE>Y</PCHISE>\n") // Automatic P-Check : To hardcode
                 .append("<CRORSE>Y</CRORSE>\n") // Create Order : To hardcode
                 .append("</GINCNTPUP>\n")
                 .append("</Message>\n");
    
  //@formatter:on

    return requestXMLC2;
  }

  public static int send(TransactionDTO transactionDTO, String userName) throws Exception {
  //@formatter:off
//    String requestXML = getRequestXML(f, userName);
//    //System.out.println("request xml : " + requestXML);
//    f.setRequestXML(requestXML);
//    
//   if (StringUtils.isNotBlank(requestXML)) {
//        gateInDao.insertAGSXmlLog(requestXML, f.getPortNo(), AGSClient.SEND);
//        String replyXML = AGSClient.sendXMLMessage(requestXML, f.getPortNo());
//        gateInDao.insertAGSXmlLog(replyXML, f.getPortNo(), AGSClient.RECEIVE);
//        if (!replyXML.equals("")) {
//            //System.out.println("reply xml : " + replyXML);
//            f.setReplyXML(replyXML);
//      
//
//            ByteArrayInputStream b = new ByteArrayInputStream(replyXML.getBytes());
//            XPathErrorReader x = new XPathErrorReader();
//            return x.parseGateInReplyXML(b, f);
//        }
//    } else {
//        if (StringUtils.isNotBlank(f.getCont1Refer()) || StringUtils.isNotBlank(f.getCont2Refer())) {
//            return GateInErrMsg.NO_ERROR;
//        }
//    }
//
//    return GateInErrMsg.AGS_REPLY_TIME_OUT;
    //@formatter:on
    return 0;
  }

  public static String getRequestXML(TransactionDTO transactionDTO, String userName) {
    //@formatter:off
//    String msgUniqueId = System.currentTimeMillis() + "";
//    String date = yyyymmdd.format(new java.util.Date());
//    String time = hhmmss.format(new java.util.Date());
//    String createRequestXML = "";
//    String errXMLMsg1 = "";
//    if (!f.getErrXMLMsg1().equals("")) {
//      errXMLMsg1 = "<ERRI>" + f.getErrXMLMsg1() + "</ERRI>\n";
//    }
//
//      if (Exports.IMPEXP_FLAG.equals(f.getGateImpOrExp())) {
//          GateInForm impGateInForm = new GateInForm();
//          if (StringUtils.isNotBlank(f.getGp1ContainerNoC1())) {
//            impGateInForm.setContainerNoC1(f.getGp1ContainerNoC1() == null ? "" : f.getGp1ContainerNoC1());
//            impGateInForm.setFullOrEmptyC1(f.getGp1FullOrEmptyC1() == null ? "" : f.getGp1FullOrEmptyC1());
//            impGateInForm.setPositionOnTruckC1(f.getGp1PositionOnTruckC1() == null ? "" : f.getGp1PositionOnTruckC1());
//
//            impGateInForm.setContainerNoC2(f.getGp2ContainerNoC2() == null ? "" : f.getGp2ContainerNoC2());
//            impGateInForm.setFullOrEmptyC2(f.getGp2FullOrEmptyC2() == null ? "" : f.getGp2FullOrEmptyC2());
//            impGateInForm.setPositionOnTruckC2(f.getGp2PositionOnTruckC2() == null ? "" : f.getGp2PositionOnTruckC2());
//            impGateInForm.setErrXMLMsg2(f.getErrXMLMsg2() == null ? "" : f.getErrXMLMsg2());
//            impGateInForm.setErrXMLMsg3(f.getErrXMLMsg3() == null ? "" : f.getErrXMLMsg3());
//            
//          }
//          
//          boolean sentExport = false;
//          if (StringUtils.isNotBlank(f.getContainerNoC1()) && StringUtils.isNotBlank(f.getContainerNoC2())){
//              if (StringUtils.isNotBlank(f.getCont1Refer()) && StringUtils.isNotBlank(f.getCont2Refer())){
//                  sentExport = false;
//              } else {
//                  sentExport = true;
//              }
//          } else if (StringUtils.isNotBlank(f.getContainerNoC1())){
//              if (StringUtils.isBlank(f.getCont1Refer())){
//                  sentExport = true;
//              }
//          }
//          
//          //if (StringUtils.isNotBlank(f.getGp1ContainerNoC1()) && StringUtils.isNotBlank(f.getContainerNoC1())) {
//          if (StringUtils.isNotBlank(f.getGp1ContainerNoC1()) && sentExport) {
//            createRequestXML = createImpRequestXML(impGateInForm, userName, msgUniqueId, true, "2", "3");
//            String cont1Index = "4";
//            String cont2Index = "5";
//
//            if (StringUtils.isBlank(f.getGp2ContainerNoC2())) {
//                cont1Index = "3";
//                cont2Index = "4";
//            }
//            createRequestXML += createExpRequestXML(f, userName, msgUniqueId, true, cont1Index, cont2Index);
//        //} else if (StringUtils.isNotBlank(f.getContainerNoC1())) {
//        } else if (sentExport) {
//            createRequestXML = createExpRequestXML(f, userName, msgUniqueId);
//        } else if (StringUtils.isNotBlank(f.getGp1ContainerNoC1())) {
//            createRequestXML = createImpRequestXML(impGateInForm, userName, msgUniqueId);
//        }
//    } else if (f.getGateImpOrExp().equals(Exports.EXP_FLAG)) {
//      createRequestXML = createExpRequestXML(f, userName, msgUniqueId);
//    } else {
//      createRequestXML = createImpRequestXML(f, userName, msgUniqueId);
//    }
//
//    String requestXML = "<?xml version=\"1.0\" encoding=\"ASCII\" standalone=\"no\"?>\n"
//                        + "<SGS2Cosmos>\n"
//                        + "<Message Index=\"1\">\n"
//                        + "<CSMCTL>\n"
//                        + "<RQST>GSRQS</RQST>\n" //Request Code : To hard code
//                        + "<ACTN>CRT</ACTN>\n" //Action Code : To hard code
//                        + "<RTNC>0</RTNC>\n" //Return Code : To hard code
//                        + errXMLMsg1
//                        + "<RQDS>CTEDSC</RQDS>\n" //Requestor Data Structure : To hard code
//                        + "<RTNM>AS</RTNM>\n" //Return Mode : To hard code
//                        + "<USID>" + userName + "</USID>\n" //User ID : To capture SCSS user id
//                        + "<RQUI>" + msgUniqueId + "</RQUI>\n" //To input msg unique id
//                        + "<TRMC>WPT1</TRMC>\n" //Terminal : To hard code
//                        + "</CSMCTL>\n"
//                        + "<GINTRCINF>\n" // For Gate In Truck Information
//                        + "<MSGTSC>GINTRCINF</MSGTSC>\n" //Message Type : To hard code
//                        + "<LANESC>" + toUpperCase(f.getLaneNo()) + "</LANESC>\n" // Lane : To capture gate lane no
//                        + "<VMIDSC>" + toUpperCase(f.getTruckHeadNo()) + "</VMIDSC>\n" // Truck License Plate : To capture truck no
//                        + "<ATDDSC>" + date + "</ATDDSC>\n" // Date of Arrival : To capture current date
//                        + "<ATDTSC>" + time + "</ATDTSC>\n" // Time of Arrival : To capture current time
//                        + "<VMYKSC>" + toUpperCase(f.getCompCode()) + "</VMYKSC>\n" //Truck Com. Code : To capture Truck Com. Code
//                        + "</GINTRCINF>\n"
//                        + "</Message>\n"
//                        + createRequestXML
//                        + "</SGS2Cosmos>\n";
//    
//    if (StringUtils.isBlank(createRequestXML)){
//        return "";
//    }
//    
//    return requestXML;
    //@formatter:on
    return null;
  }

  public static String toEscapeXmlUpperCase(String str) {
    if (StringUtils.isNotEmpty(str)) {
      return StringEscapeUtils.escapeXml(StringUtils.upperCase(StringUtils.trim(str)));
    } else {
      return str;
    }
  }

  public static String toUpperCase(String str) {
    if (StringUtils.isNotEmpty(str)) {
      return StringUtils.upperCase(StringUtils.trim(str));
    } else {
      return str;
    }
  }

  private static final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
  private static final SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");

}
