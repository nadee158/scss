package com.privasia.scss.gatein.service;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.TransactionDTO;

@Service("gateInXMLRequestService")
public class GateInXMLRequestService {

  public final static String OPTFLAG_NORMAL = "N";
  public final static String OPTFLAG_MANUAL = "M";

  public static final String EXP_FLAG = "E";
  public static final String IMPEXP_FLAG = "B";
  private static final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
  private static final SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");



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

  public String getRequestXML(TransactionDTO transactionDTO, String userName) {

    ImportContainer importContainer01 = transactionDTO.getImportContainer01();
    ImportContainer importContainer02 = transactionDTO.getImportContainer02();
    String date = yyyymmdd.format(new java.util.Date());
    String time = hhmmss.format(new java.util.Date());

    String msgUniqueId = System.currentTimeMillis() + "";
    StringBuilder createRequestXML = new StringBuilder("");
    String errXMLMsg = "";
    if (StringUtils.isNotEmpty(importContainer01.getErrXMLMsg())) {
      errXMLMsg = "<ERRI>" + importContainer01.getErrXMLMsg() + "</ERRI>\n";
    }

    if (StringUtils.equals(IMPEXP_FLAG, transactionDTO.getGateImpOrExp())) {
      if (!(importContainer01 == null)) {
        if (StringUtils.isEmpty(importContainer01.getContainerNumber())) {
          importContainer01.setContainerNumber(
              importContainer01.getContainerNumber() == null ? "" : importContainer01.getContainerNumber());
          importContainer01
              .setFullOrEmpty(importContainer01.getFullOrEmpty() == null ? "" : importContainer01.getFullOrEmpty());
          importContainer01.setPositionOnTruck(
              importContainer01.getPositionOnTruck() == null ? "" : importContainer01.getPositionOnTruck());
          importContainer01
              .setErrXMLMsg(importContainer01.getErrXMLMsg() == null ? "" : importContainer01.getErrXMLMsg());
        }
      }

      if (!(importContainer02 == null)) {
        if (StringUtils.isEmpty(importContainer02.getContainerNumber())) {
          importContainer02.setContainerNumber(
              importContainer02.getContainerNumber() == null ? "" : importContainer02.getContainerNumber());
          importContainer02
              .setFullOrEmpty(importContainer02.getFullOrEmpty() == null ? "" : importContainer02.getFullOrEmpty());
          importContainer02.setPositionOnTruck(
              importContainer02.getPositionOnTruck() == null ? "" : importContainer02.getPositionOnTruck());
          importContainer02
              .setErrXMLMsg(importContainer02.getErrXMLMsg() == null ? "" : importContainer02.getErrXMLMsg());
        }
      }

      boolean sentExport = false;
      if (!(importContainer01 == null || importContainer02 == null)) {
        if (StringUtils.isNotBlank(importContainer01.getContRefer())
            && StringUtils.isNotBlank(importContainer02.getContRefer())) {
          sentExport = false;
        } else {
          sentExport = true;
        }
      } else if (!(importContainer01 == null)) {
        if (StringUtils.isNotBlank(importContainer01.getContainerNumber())) {
          if (StringUtils.isBlank(importContainer01.getContRefer())) {
            sentExport = true;
          }
        }
      }

      if (StringUtils.isNotBlank(importContainer01.getContainerNumber()) && sentExport) {
        String impRequestXML = createImpRequestXML(transactionDTO, userName, msgUniqueId, true, "2", "3");
        createRequestXML.append(impRequestXML);
        String cont1Index = "4";
        String cont2Index = "5";

        if (StringUtils.isBlank(importContainer02.getContainerNumber())) {
          cont1Index = "3";
          cont2Index = "4";
        }
        String expRequestXML = createExpRequestXML(transactionDTO, userName, msgUniqueId, true, cont1Index, cont2Index);
        createRequestXML.append(expRequestXML);
      } else if (sentExport) {
        String expRequestXML = createExpRequestXML(transactionDTO, userName, msgUniqueId);
        createRequestXML.append(expRequestXML);
      } else if (StringUtils.isNotBlank(importContainer01.getContainerNumber())) {
        String impRequestXML = createImpRequestXML(transactionDTO, userName, msgUniqueId);
        createRequestXML.append(impRequestXML);
      }


    } else if (StringUtils.equals(EXP_FLAG, transactionDTO.getGateImpOrExp())) {
      String expRequestXML = createExpRequestXML(transactionDTO, userName, msgUniqueId);
      createRequestXML.append(expRequestXML);
    } else {
      String impRequestXML = createImpRequestXML(transactionDTO, userName, msgUniqueId);
      createRequestXML.append(impRequestXML);
    }
    StringBuilder requestXML = new StringBuilder("");
    //@formatter:off
       requestXML.append("<?xml version=\"1.0\" encoding=\"ASCII\" standalone=\"no\"?>\n")
                        .append("<SGS2Cosmos>\n")
                        .append("<Message Index=\"1\">\n")
                        .append("<CSMCTL>\n")
                        .append("<RQST>GSRQS</RQST>\n") //Request Code : To hard code
                        .append("<ACTN>CRT</ACTN>\n") //Action Code : To hard code
                        .append("<RTNC>0</RTNC>\n") //Return Code : To hard code
                        .append(errXMLMsg)
                        .append("<RQDS>CTEDSC</RQDS>\n") //Requestor Data Structure : To hard code
                        .append("<RTNM>AS</RTNM>\n") //Return Mode : To hard code
                        .append("<USID>").append(userName).append("</USID>\n") //User ID : To capture SCSS user id
                        .append("<RQUI>").append(msgUniqueId).append("</RQUI>\n") //To input msg unique id
                        .append("<TRMC>WPT1</TRMC>\n") //Terminal : To hard code
                        .append("</CSMCTL>\n")
                        .append("<GINTRCINF>\n") // For Gate In Truck Information
                        .append("<MSGTSC>GINTRCINF</MSGTSC>\n") //Message Type : To hard code
                        .append("<LANESC>").append(toUpperCase(transactionDTO.getLaneNo())).append("</LANESC>\n") // Lane : To capture gate lane no
                        .append("<VMIDSC>").append(toUpperCase(transactionDTO.getPmHeadNo())).append("</VMIDSC>\n") // Truck License Plate : To capture truck no
                        .append("<ATDDSC>").append(date).append("</ATDDSC>\n") // Date of Arrival : To capture current date
                        .append("<ATDTSC>").append(time).append("</ATDTSC>\n") // Time of Arrival : To capture current time
                        .append("<VMYKSC>").append(toUpperCase(transactionDTO.getCompCode())).append("</VMYKSC>\n") //Truck Com. Code : To capture Truck Com. Code
                        .append("</GINTRCINF>\n")
                        .append("</Message>\n")
                        .append(createRequestXML)
                        .append("</SGS2Cosmos>\n");
  //@formatter:on

    if (StringUtils.isBlank(createRequestXML)) {
      return "";
    }

    return requestXML.toString();

  }

  private String createExpRequestXML(TransactionDTO transactionDTO, String userName, String msgUniqueId) {
    // TODO Auto-generated method stub
    return null;
  }

  private String createExpRequestXML(TransactionDTO transactionDTO, String userName, String msgUniqueId, boolean b,
      String cont1Index, String cont2Index) {
    // TODO Auto-generated method stub
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


}
