package com.privasia.scss.gatein.service;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.DGInfo;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.SealInfo;
import com.privasia.scss.common.dto.TransactionDTO;
import com.privasia.scss.common.util.AGSClient;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.common.util.XPathErrorReader;
import com.privasia.scss.core.model.AGSLog;
import com.privasia.scss.core.repository.AGSLogRepository;

@Service("gateInXMLRequestService")
public class GateInXMLRequestService {

  public final static String OPTFLAG_NORMAL = "N";
  public final static String OPTFLAG_MANUAL = "M";

  public static final String EXP_FLAG = "E";
  public static final String IMPEXP_FLAG = "B";
  private static final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
  private static final SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");

  @Autowired
  private AGSLogRepository agsLogRepository;



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

    if (StringUtils.isNotEmpty(importContainer.getContainer().getContainerNumber())) {
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
                 .append("<UNITSE>").append(toEscapeXmlUpperCase(importContainer.getContainer().getContainerNumber())).append("</UNITSE>\n") // Container No : To capture
                 .append("<UNBTSE>").append(toUpperCase(importContainer.getContainer().getContainerFullOrEmpty())).append("</UNBTSE>\n") // (E)mpty or (F)ull : To capture E or F
                 .append("<CNPVSE>").append(toUpperCase(importContainer.getContainerPosition())).append("</CNPVSE>\n") // Position on Truck : To capture F = Front, A = Aft or M = Middle
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

  @Transactional
  public int send(TransactionDTO transactionDTO, String userName) throws Exception {
  //@formatter:off
    String requestXML = getRequestXML(transactionDTO, userName);
    //System.out.println("request xml : " + requestXML);
    transactionDTO.setRequestXML(requestXML);
    
   if (StringUtils.isNotBlank(requestXML)) {
        insertAGSXmlLog(requestXML, transactionDTO.getPortNo(), AGSClient.SEND);
        String replyXML = AGSClient.sendXMLMessage(requestXML, transactionDTO.getPortNo());
        insertAGSXmlLog(replyXML, transactionDTO.getPortNo(), AGSClient.RECEIVE);
        if (!replyXML.equals("")) {
            //System.out.println("reply xml : " + replyXML);
          transactionDTO.setReplyXML(replyXML);
          ByteArrayInputStream b = new ByteArrayInputStream(replyXML.getBytes());
          XPathErrorReader x = new XPathErrorReader();
          return x.parseGateInReplyXML(b, transactionDTO);
        }
    } else {
        ImportContainer container01=transactionDTO.getImportContainer01();
        ImportContainer container02=transactionDTO.getImportContainer02();
        if(!(container01==null || container02==null)){
          if (StringUtils.isNotBlank(container01.getContRefer()) || StringUtils.isNotBlank(container02.getContRefer())) {
            return ApplicationConstants.NO_ERROR;
        }
        }
    }

    return ApplicationConstants.AGS_REPLY_TIME_OUT;
    //@formatter:on
  }

  public void insertAGSXmlLog(String requestXML, int portNo, String sendReceive) {
    AGSLog agsLog = new AGSLog();
    agsLog.setSendRCV(sendReceive);
    agsLog.setPortNumber(Integer.toString(portNo));
    agsLog.setDateTimeAdd(LocalDateTime.now());
    agsLog.setXmlData(requestXML);
    agsLogRepository.save(agsLog);
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
        if (StringUtils.isEmpty(importContainer01.getContainer().getContainerNumber())) {
          importContainer01.getContainer()
              .setContainerNumber(importContainer01.getContainer().getContainerNumber() == null ? ""
                  : importContainer01.getContainer().getContainerNumber());
          importContainer01.getContainer()
              .setContainerFullOrEmpty(importContainer01.getContainer().getContainerFullOrEmpty() == null ? ""
                  : importContainer01.getContainer().getContainerFullOrEmpty());
          importContainer01.setContainerPosition(
              importContainer01.getContainerPosition() == null ? "" : importContainer01.getContainerPosition());
          importContainer01
              .setErrXMLMsg(importContainer01.getErrXMLMsg() == null ? "" : importContainer01.getErrXMLMsg());
        }
      }

      if (!(importContainer02 == null)) {
        if (StringUtils.isEmpty(importContainer02.getContainer().getContainerNumber())) {
          importContainer02.getContainer()
              .setContainerNumber(importContainer02.getContainer().getContainerNumber() == null ? ""
                  : importContainer02.getContainer().getContainerNumber());
          importContainer02.getContainer()
              .setContainerFullOrEmpty(importContainer02.getContainer().getContainerFullOrEmpty() == null ? ""
                  : importContainer02.getContainer().getContainerFullOrEmpty());
          importContainer02.setContainerPosition(
              importContainer02.getContainerPosition() == null ? "" : importContainer02.getContainerPosition());
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
        if (StringUtils.isNotBlank(importContainer01.getContainer().getContainerNumber())) {
          if (StringUtils.isBlank(importContainer01.getContRefer())) {
            sentExport = true;
          }
        }
      }

      if (StringUtils.isNotBlank(importContainer01.getContainer().getContainerNumber()) && sentExport) {
        String impRequestXML = createImpRequestXML(transactionDTO, userName, msgUniqueId, true, "2", "3");
        createRequestXML.append(impRequestXML);
        String cont1Index = "4";
        String cont2Index = "5";

        if (StringUtils.isBlank(importContainer02.getContainer().getContainerNumber())) {
          cont1Index = "3";
          cont2Index = "4";
        }
        String expRequestXML = createExpRequestXML(transactionDTO, userName, msgUniqueId, true, cont1Index, cont2Index);
        createRequestXML.append(expRequestXML);
      } else if (sentExport) {
        String expRequestXML = createExpRequestXML(transactionDTO, userName, msgUniqueId);
        createRequestXML.append(expRequestXML);
      } else if (StringUtils.isNotBlank(importContainer01.getContainer().getContainerNumber())) {
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
    return createExpRequestXML(transactionDTO, userName, msgUniqueId, false, "2", "3");
  }

  private String createExpRequestXML(TransactionDTO transactionDTO, String userName, String msgUniqueId,
      boolean isImpExp, String cont1Index, String cont2Index) {
    ExportContainer exportContainer01 = transactionDTO.getExportContainer01();
    ExportContainer exportContainer02 = transactionDTO.getExportContainer02();

    StringBuilder errXMLMsg2 = new StringBuilder("");
    StringBuilder damageC1 = new StringBuilder("");
    StringBuilder sealC1 = new StringBuilder("");
    StringBuilder operationReferC1 = new StringBuilder("");
    StringBuilder OOGC1 = new StringBuilder("");
    StringBuilder IMDGC1 = new StringBuilder("");
    StringBuilder UNC1 = new StringBuilder("");
    StringBuilder requestXMLC2 = new StringBuilder("");

    if (!(exportContainer01 == null)) {

      errXMLMsg2 = constructExportContainerErrMsg(exportContainer01, errXMLMsg2);

      damageC1 = constructExportContainerDamageInfo(exportContainer01, damageC1);

      sealC1 = constructExportContainerSealInfo(exportContainer01, sealC1);

      operationReferC1 = constructExportContainerOperationReeferInfo(exportContainer01, operationReferC1);

      OOGC1 = constructExportContainerOOGInfo(exportContainer01, OOGC1);

      IMDGC1 = constructExportContainerIMDGInfo(exportContainer01, IMDGC1);

      UNC1 = constructExportContainerUNCInfo(exportContainer01, UNC1);


    }

    if (!(exportContainer02 == null)) {

      if (StringUtils.isNotEmpty(exportContainer02.getContainerNumber())
          && StringUtils.isEmpty(exportContainer02.getContRefer())) {

        StringBuilder errXMLMsg3 = new StringBuilder("");
        StringBuilder damageC2 = new StringBuilder("");
        StringBuilder sealC2 = new StringBuilder("");
        StringBuilder operationReferC2 = new StringBuilder("");
        StringBuilder OOGC2 = new StringBuilder("");
        StringBuilder IMDGC2 = new StringBuilder("");
        StringBuilder UNC2 = new StringBuilder("");

        errXMLMsg3 = constructExportContainerErrMsg(exportContainer02, errXMLMsg3);

        damageC2 = constructExportContainerDamageInfo(exportContainer02, damageC2);

        sealC2 = constructExportContainerSealInfo(exportContainer02, sealC2);

        operationReferC2 = constructExportContainerOperationReeferInfo(exportContainer02, operationReferC2);

        OOGC2 = constructExportContainerOOGInfo(exportContainer02, OOGC2);

        IMDGC2 = constructExportContainerIMDGInfo(exportContainer02, IMDGC2);

        UNC2 = constructExportContainerUNCInfo(exportContainer02, UNC2);

        if (isImpExp) {
          if (StringUtils.isNotBlank(exportContainer02.getContainerNumber())
              && StringUtils.isNotBlank(exportContainer02.getContRefer())) {
            requestXMLC2.append("<Message Index=\"").append(cont1Index).append("\">\n");
          } else {
            requestXMLC2.append("<Message Index=\"").append(cont2Index).append("\">\n");
          }
        } else {
          requestXMLC2.append("<Message Index=\"3\">\n");
        }

        /**
         * Remove decimal format
         */
        String weight = constructWeightForExportContainer(transactionDTO, exportContainer01);

        requestXMLC2 = constructExportContainerRequestXML(exportContainer02, requestXMLC2, errXMLMsg3, damageC2, sealC2,
            operationReferC2, OOGC2, IMDGC2, UNC2, userName, msgUniqueId, weight);
      }

    }

    StringBuilder requestXML = new StringBuilder("");

    if (StringUtils.isBlank(exportContainer01.getContRefer())
        && StringUtils.isNotBlank(exportContainer01.getContainerNumber())) {
      if (isImpExp) {
        requestXML.append("<Message Index=\"").append(cont1Index).append("\">\n");
      } else {
        requestXML.append("<Message Index=\"2\">\n");
      }
      /**
       * Remove decimal format
       */
      String weight = constructWeightForExportContainer(transactionDTO, exportContainer01);

      requestXML = constructExportContainerRequestXML(exportContainer01, requestXML, errXMLMsg2, damageC1, sealC1,
          operationReferC1, OOGC1, IMDGC1, UNC1, userName, msgUniqueId, weight);

      requestXML.append(requestXMLC2);
    } else {
      requestXML.append(requestXMLC2);
    }

    return requestXML.toString();
  }

  private String constructWeightForExportContainer(TransactionDTO transactionDTO, ExportContainer exportContainer) {
    String weight = "0";
    DecimalFormat df = new DecimalFormat("#");
    if (transactionDTO.isShipperVGM() && exportContainer.isWithInTolerance()) {
      weight = String.valueOf(exportContainer.getShipperVGM());
    } else {
      weight = Double.toString(exportContainer.getNetWeight());
    }
    weight = df.format(Double.valueOf(weight));
    return weight;
  }

  private StringBuilder constructExportContainerRequestXML(ExportContainer exportContainer, StringBuilder requestXML,
      StringBuilder errXMLMsg, StringBuilder damage, StringBuilder seal, StringBuilder operationRefer,
      StringBuilder oOG, StringBuilder iMDG, StringBuilder uN, String userName, String msgUniqueId, String weight) {
  //@formatter:off   
    requestXML.append("<CSMCTL>\n")
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
        .append("<GINCNTDRP>\n") // For Gate In container drop off
        .append("<MSGTSE>GINCNTDRP</MSGTSE>\n") // Message Type : To hard code
        .append("<UNITSE>").append(toEscapeXmlUpperCase(exportContainer.getContainerNumber())).append("</UNITSE>\n") // Container No : To capture
        .append("<ORRFSE>").append(toEscapeXmlUpperCase(exportContainer.getBookingNo())).append("</ORRFSE>\n") // Order Reference : To capture Booking Ref no
        .append("<UNISSE>").append(toUpperCase(exportContainer.getContainerISO())).append("</UNISSE>\n") // Container ISO code : To capture container ISO code
        .append("<UNBTSE>").append(toUpperCase(exportContainer.getFullOrEmpty())).append("</UNBTSE>\n") // (E)mpty or (F)ull : To capture E or F
        .append("<CNPVSE>").append(toUpperCase(exportContainer.getPositionOnTruck())).append("</CNPVSE>\n") // Position on Truck : To capture F, A or M
        .append("<ORGVSE>").append(toUpperCase(exportContainer.getExpAgent())).append("</ORGVSE>\n") // Order supplier : To capture Agent code??
        .append("<LYNDSE>").append(toUpperCase(exportContainer.getLine())).append("</LYNDSE>\n") // Line code : To capture line code
        .append(damage.toString()) // Damage Code 1 : To capture damage code 1, To loop if more than 1
        .append(seal.toString()) // Seal Origin 1 : To capture seal origin, seal type and seal no 1, To loop if more than 1
        .append("<UNBGSE>").append(weight).append("</UNBGSE>\n") // Unit Gross Weight : To capture Gross Weight
        .append(operationRefer.toString())
        .append(oOG.toString())
        .append(iMDG.toString()) // IMDG Class : To capture IMDG Class,Conditional if exist, loop if more than 1
        .append(uN.toString()) // UN No : To capture UN no, Conditional if exist, loop if more than 1
        .append("<UOLCSE>MY</UOLCSE>\n")
        .append("<UOLLSE>PKG</UOLLSE>\n")
        .append("<UOLOSE>PKG</UOLOSE>\n") //Changed by feroz on 11 OCT 2007
        .append("<CYOISE>N</CYOISE>\n") // Opening Time : To hard code
        .append("<CYCISE>N</CYCISE>\n") // Closing Time : To hard code
        .append("<ACHISE>Y</ACHISE>\n") // Automatic A-Check : To hardcode
        .append("<PCHISE>Y</PCHISE>\n") // Automatic P-Check : To hardcode
        .append("</GINCNTDRP>\n")
        .append("</Message>\n");
  //@formatter:on
    return requestXML;
  }

  private StringBuilder constructExportContainerErrMsg(ExportContainer exportContainer, StringBuilder errXMLMsg) {
    if (StringUtils.isNotBlank(exportContainer.getErrXMLMsg())) {
      errXMLMsg.append("<ERRI>").append(exportContainer.getErrXMLMsg()).append("</ERRI>\n");
    }
    return errXMLMsg;
  }

  //@formatter:on
  private StringBuilder constructExportContainerUNCInfo(ExportContainer exportContainer, StringBuilder uN) {
    if (StringUtils.isNotEmpty(exportContainer.getUN())) {
      uN.append("<CUN1SE>").append(exportContainer.getUN()).append("</CUN1SE>\n");
    }
    return uN;
  }

  //@formatter:on
  private StringBuilder constructExportContainerIMDGInfo(ExportContainer exportContainer, StringBuilder iMDG) {
    if (StringUtils.isNotEmpty(exportContainer.getIMDG())) {
      iMDG.append("<CNIMSE>").append(exportContainer.getIMDG()).append("</CNIMSE>\n"); // Testing
      iMDG.append("<CIM1SE>").append(exportContainer.getIMDG()).append("</CIM1SE>\n");
      iMDG.append("<ISA1SE>").append(exportContainer.getIMDGlabels()).append("</ISA1SE>\n");

      if (exportContainer.getIMDG().equals("5.1") || exportContainer.getIMDG().equals("5.2")) {
        iMDG.append("<ISI1SE>").append(exportContainer.getIMDG()).append("</ISI1SE>\n");
      } else {
        iMDG.append("<ISI1SE>").append(exportContainer.getIMDG().substring(0, 1)).append("</ISI1SE>\n");
      }
    }
    return iMDG;
  }

  //@formatter:on
  private StringBuilder constructExportContainerOOGInfo(ExportContainer exportContainer, StringBuilder OOG) {

    String OOGindicator = "N";


    if (!(StringUtils.isEmpty(exportContainer.getOOGOA()) || StringUtils.isEmpty(exportContainer.getOOGOF())
        || StringUtils.isEmpty(exportContainer.getOOGOH()) || StringUtils.isEmpty(exportContainer.getOOGOL())
        || StringUtils.isEmpty(exportContainer.getOOGOR()))) {
      OOGindicator = "Y";
    }
  //@formatter:off
    if (OOGindicator.equalsIgnoreCase("Y")) {

      OOG.append("<OOGISE>").append(OOGindicator).append("</OOGISE>\n"); // OOG indicator : To // capture Y or N,
      // Conditional if exist

      if (StringUtils.isNotEmpty(exportContainer.getOOGOF())) {
        OOG.append("<OVSVSE>").append(exportContainer.getOOGOF()).append("</OVSVSE>\n"); // Oversize Fore (in cm) : 5,0,
                                                            // Conditional if exist
      }

      if (StringUtils.isNotEmpty(exportContainer.getOOGOA())) {
        OOG.append("<OVSASE>").append(exportContainer.getOOGOA()).append("</OVSASE>\n"); // Oversize Aft (in cm) : 5,0,
                                                            // Conditional if exist
      }

      if (StringUtils.isNotEmpty(exportContainer.getOOGOL())) {
        OOG.append("<OVSLSE>").append(exportContainer.getOOGOL()).append("</OVSLSE>\n"); // // Oversize Left (in cm) : 5,0,
                                                            // Conditional if exist
      }
      if (StringUtils.isNotEmpty(exportContainer.getOOGOH())) {
        OOG.append("<OVHGSE>").append(exportContainer.getOOGOH()).append("</OVHGSE>\n"); // Oversize Height (in cm) : 5,0,
                                                            // Conditional if exist
      }
      if (StringUtils.isNotEmpty(exportContainer.getOOGOR())) {
        OOG.append("<OVSRSE>").append(exportContainer.getOOGOR()).append("</OVSRSE>\n"); // Oversize Rare (in cm) : 5,0,
                                                            // Conditional if exist
      }
    }
  //@formatter:on
    return OOG;
  }


  private StringBuilder constructExportContainerOperationReeferInfo(ExportContainer exportContainer,
      StringBuilder operationRefer) {
    if ("Y".equals(exportContainer.getOperationReefer())) {
      String tempSignC1 = "";
      String tempC1 = "";
      int tempC1Length = exportContainer.getTemp().length();

      if (exportContainer.getTemp().startsWith("+") || exportContainer.getTemp().startsWith("-")) {
        tempSignC1 = exportContainer.getTemp().substring(0, 1);
        tempC1 = exportContainer.getTemp().substring(1, tempC1Length - 1);
      } else {
        tempC1 = exportContainer.getTemp();
      }
      //@formatter:off
      operationRefer.append("<CNORSE>").append(toUpperCase(exportContainer.getOperationReefer())).append("</CNORSE>\n") //Operational Refer : To capture Operational Refer Y/N, Conditional if exi/st
                      .append("<PLMNSE>").append(tempSignC1).append("</PLMNSE>\n")
                      .append("<RGRTSE>").append(tempC1).append("</RGRTSE>\n") // Temperature : To capture temperature (5,2, Conditional if exist )
                      .append("<RGTESE>").append(exportContainer.getTempUnit()).append("</RGTESE>\n"); // Temp uni
    //@formatter:on

    } else {
      operationRefer.append("<CNORSE>").append(toUpperCase(exportContainer.getOperationReefer())).append("</CNORSE>\n");
    } // added by feroz on 11 OCT 2007
    return operationRefer;
  }

  private StringBuilder constructExportContainerSealInfo(ExportContainer exportContainer, StringBuilder seal) {
    if ("F".equals(exportContainer.getFullOrEmpty())) {
      //@formatter:off
      if(!(exportContainer.getSealInfo01()==null)){
        SealInfo sealInfo=exportContainer.getSealInfo01();
        if (StringUtils.isNotBlank(sealInfo.getSealNo())) {
          seal.append("<SO01SE>").append(toUpperCase(sealInfo.getSealOrigin())).append("</SO01SE>\n")
              .append("<ST01SE>").append(toUpperCase(sealInfo.getSealType())).append("</ST01SE>\n")
              .append("<SN01SE>").append(toUpperCase(sealInfo.getSealNo())).append("</SN01SE>\n");
        }
      }
      if(!(exportContainer.getSealInfo02()==null)){
        SealInfo sealInfo=exportContainer.getSealInfo02();
        if (StringUtils.isNotBlank(sealInfo.getSealNo())) {
          seal.append("<SO02SE>").append(toUpperCase(sealInfo.getSealOrigin())).append("</SO02SE>\n")
               .append("<ST02SE>").append(toUpperCase(sealInfo.getSealType())).append("</ST02SE>\n")
               .append("<SN02SE>").append(toUpperCase(sealInfo.getSealNo())).append("</SN02SE>\n");
        }
      }
    //@formatter:on
    }
    return seal;
  }

  private StringBuilder constructExportContainerDamageInfo(ExportContainer exportContainer, StringBuilder damage) {
    if (!(exportContainer.getDgInfo() == null)) {

      DGInfo dgInfo = exportContainer.getDgInfo();

      if (StringUtils.isNotBlank(dgInfo.getDamage1())) {
        damage.append("<DM01SE>").append(toUpperCase(dgInfo.getDamage1())).append("</DM01SE>\n");
      }

      if (StringUtils.isNotBlank(dgInfo.getDamage2())) {
        damage.append("<DM02SE>").append(toUpperCase(dgInfo.getDamage2())).append("</DM02SE>\n");
      }
      if (StringUtils.isNotBlank(dgInfo.getDamage3())) {
        damage.append("<DM03SE>").append(toUpperCase(dgInfo.getDamage3())).append("</DM03SE>\n");
      }
      if (StringUtils.isNotBlank(dgInfo.getDamage4())) {
        damage.append("<DM04SE>").append(toUpperCase(dgInfo.getDamage4())).append("</DM04SE>\n");
      }
      if (StringUtils.isNotBlank(dgInfo.getDamage5())) {
        damage.append("<DM05SE>").append(toUpperCase(dgInfo.getDamage5())).append("</DM05SE>\n");
      }
      if (StringUtils.isNotBlank(dgInfo.getDamage6())) {
        damage.append("<DM06SE>").append(toUpperCase(dgInfo.getDamage6())).append("</DM06SE>\n");
      }
      if (StringUtils.isNotBlank(dgInfo.getDamage7())) {
        damage.append("<DM07SE>").append(toUpperCase(dgInfo.getDamage7())).append("</DM07SE>\n");
      }
      if (StringUtils.isNotBlank(dgInfo.getDamage8())) {
        damage.append("<DM08SE>").append(toUpperCase(dgInfo.getDamage8())).append("</DM08SE>\n");
      }
      if (StringUtils.isNotBlank(dgInfo.getDamage9())) {
        damage.append("<DM09SE>").append(toUpperCase(dgInfo.getDamage9())).append("</DM09SE>\n");
      }

    }
    return damage;
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
