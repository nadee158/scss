package com.privasia.scss.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPath;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.TransactionDTO;

public class XPathErrorReader {

  public static void main(String[] args) throws Exception {
    ByteArrayInputStream b = new ByteArrayInputStream(testReplyXML.getBytes());
    parseGateOutReplyXML(b);
  }

  public static void parseErrorMsg(InputStream is) throws Exception {
    SAXBuilder sax = new SAXBuilder();
    Document doc = sax.build(is);

    XPath msgPath = XPath.newInstance("//Message/XMLERRINF");

    List msgs = msgPath.selectNodes(doc);
    // System.out.println("XML reply has " + msgs.size() + " messages:");

    Iterator i = msgs.iterator();
    while (i.hasNext()) {
      Element msg = (Element) i.next();
      // System.out.println(msg.getChild("ERRDSG").getTextTrim());
    }
  }

  public static int parseGateInReplyXML(InputStream is, TransactionDTO transactionDTO) throws Exception {
    SAXBuilder sax = new SAXBuilder();
    Document doc = sax.build(is);
    String errorsMsg = "";
    boolean isMPL = false;
    XPath msgPath = XPath.newInstance("//Message/CSMCTL");

    List msgs = msgPath.selectNodes(doc);
    // System.out.println("XML reply has " + msgs.size() + " messages:");

    Iterator i = msgs.iterator();
    while (i.hasNext()) {
      Element msg = (Element) i.next();

      if (msg.getChild("ERRI") != null) { // check for any error code
        if (msg.getChild("ERRI").getTextTrim().equals("INF0016")) { // MPL error
          isMPL = true;
        }
        errorsMsg += COSMOSMsgCode.get(msg.getChild("ERRI").getTextTrim()) + "<br>";
      }

    }

    if (!isMPL) {
      if (transactionDTO.getGateImpOrExp().equals("B")) {
        ExportContainer exportContainer01 = transactionDTO.getExportContainer01();
        if (!(exportContainer01 == null)) {
          if (StringUtils.isNotBlank(exportContainer01.getContainer().getContainerNumber())) {
            ExportContainer exportContainer02 = transactionDTO.getExportContainer02();
            if (exportContainer02 == null) {
              exportContainer02 = new ExportContainer();
              transactionDTO.setExportContainer02(exportContainer02);
            }
            getExportYardBayCode(doc, exportContainer01, exportContainer02);
          }
        }
        ImportContainer importContainer01 = transactionDTO.getImportContainer01();
        if (!(importContainer01 == null)) {
          if (importContainer01.getGatePassNo() != null) {
            ImportContainer importContainer02 = transactionDTO.getImportContainer01();
            if (importContainer02 == null) {
              importContainer02 = new ImportContainer();
            }
            transactionDTO.setImportContainer02(importContainer02);
            getImportYardBayCode(doc, importContainer01, importContainer02);
          }
        }
      } else {
        getYardAndBayCode(doc, transactionDTO);
      }
    }

    getCallCardCode(doc, transactionDTO);

    transactionDTO.setMsg(errorsMsg);
    if (isMPL) {
      return ApplicationConstants.MPL;
    }
    if (!errorsMsg.equals("")) {
      return ApplicationConstants.ERROR_EXIST;
    }

    return ApplicationConstants.NO_ERROR;
  }

  public static String parseGateOutReplyXML(InputStream is) throws Exception {
    SAXBuilder sax = new SAXBuilder();
    Document doc = sax.build(is);
    String errorsMsg = "";
    XPath msgPath = XPath.newInstance("//Message/CSMCTL");

    List msgs = msgPath.selectNodes(doc);
    // System.out.println("XML reply has " + msgs.size() + " messages:");

    Iterator i = msgs.iterator();
    while (i.hasNext()) {
      Element msg = (Element) i.next();
      if (msg.getChild("ERRI") != null) {
        errorsMsg += COSMOSMsgCode.get(msg.getChild("ERRI").getTextTrim()) + "<br>";
      }
    }
    return errorsMsg;
  }

  public static void getYardAndBayCode(Document doc, TransactionDTO transactionDTO) throws Exception {
    XPath msgPath = null;

    ExportContainer exportContainer01 = transactionDTO.getExportContainer01();
    ExportContainer exportContainer02 = transactionDTO.getExportContainer02();
    if (exportContainer02 == null) {
      exportContainer02 = new ExportContainer();
      transactionDTO.setExportContainer02(exportContainer02);
    }

    ImportContainer importContainer01 = transactionDTO.getImportContainer01();
    ImportContainer importContainer02 = transactionDTO.getImportContainer01();
    if (importContainer02 == null) {
      importContainer02 = new ImportContainer();
      transactionDTO.setImportContainer02(importContainer02);
    }

    if (transactionDTO.getGateImpOrExp().equals("E")) {
      msgPath = XPath.newInstance("//Message/GINCNTDRPR");
    } else {
      msgPath = XPath.newInstance("//Message/GINCNTPUPR");
    }

    List msgs = msgPath.selectNodes(doc);
    Iterator i = msgs.iterator();
    int msgCnt = 1;
    while (i.hasNext()) {
      Element msg = (Element) i.next();
      if (msg.getChild("PSIDSE") != null) { // yard position
        if (msgCnt == 1) {
          if (transactionDTO.getGateImpOrExp().equals("E")) {
            exportContainer01.setYardPosition(msg.getChild("PSIDSE").getTextTrim());
          } else {
            importContainer01.setYardPosition(msg.getChild("PSIDSE").getTextTrim());
          }
        }
        if (msgCnt == 2) {
          if (transactionDTO.getGateImpOrExp().equals("E")) {
            exportContainer02.setYardPosition(msg.getChild("PSIDSE").getTextTrim());
          } else {
            importContainer02.setYardPosition(msg.getChild("PSIDSE").getTextTrim());
          }
        }
      }
      if (msg.getChild("PKIDSE") != null) { // bay code
        if (msgCnt == 1) {
          if (transactionDTO.getGateImpOrExp().equals("E")) {
            exportContainer01.setBayCode(msg.getChild("PKIDSE").getTextTrim());
          } else {
            importContainer01.setBayCode(msg.getChild("PKIDSE").getTextTrim());
          }
        }
        if (msgCnt == 2) {
          if (transactionDTO.getGateImpOrExp().equals("E")) {
            exportContainer02.setBayCode(msg.getChild("PKIDSE").getTextTrim());
          } else {
            importContainer02.setBayCode(msg.getChild("PKIDSE").getTextTrim());
          }
        }
      }
      msgCnt++;
    }
  }

  public static void getImportYardBayCode(Document doc, ImportContainer importContainer01,
      ImportContainer importContainer02) throws Exception {
    XPath msgPath = null;

    msgPath = XPath.newInstance("//Message/GINCNTPUPR");

    List msgs = msgPath.selectNodes(doc);

    Iterator i = msgs.iterator();
    int msgCnt = 1;
    while (i.hasNext()) {
      Element msg = (Element) i.next();
      if (msg.getChild("PSIDSE") != null) { // yard position
        if (msgCnt == 1) {
          importContainer01.setYardPosition(msg.getChild("PSIDSE").getTextTrim());
        }
        if (msgCnt == 2) {
          importContainer02.setYardPosition(msg.getChild("PSIDSE").getTextTrim());
        }
      }
      if (msg.getChild("PKIDSE") != null) { // bay code
        if (msgCnt == 1) {
          importContainer01.setBayCode(msg.getChild("PKIDSE").getTextTrim());
        }
        if (msgCnt == 2) {
          importContainer02.setBayCode(msg.getChild("PKIDSE").getTextTrim());
        }
      }
      msgCnt++;
    }
  }

  public static void getExportYardBayCode(Document doc, ExportContainer exportContainer01,
      ExportContainer exportContainer02) throws Exception {
    XPath msgPath = null;

    msgPath = XPath.newInstance("//Message/GINCNTDRPR");

    List msgs = msgPath.selectNodes(doc);

    Iterator i = msgs.iterator();
    int msgCnt = 1;
    while (i.hasNext()) {
      Element msg = (Element) i.next();
      if (msg.getChild("PSIDSE") != null) { // yard position
        if (msgCnt == 1) {
          exportContainer01.setYardPosition(msg.getChild("PSIDSE").getTextTrim());
        }
        if (msgCnt == 2) {
          exportContainer02.setYardPosition(msg.getChild("PSIDSE").getTextTrim());
        }
      }
      if (msg.getChild("PKIDSE") != null) { // bay code
        if (msgCnt == 1) {
          exportContainer01.setBayCode(msg.getChild("PKIDSE").getTextTrim());
        }
        if (msgCnt == 2) {
          exportContainer02.setBayCode(msg.getChild("PKIDSE").getTextTrim());
        }
      }
      msgCnt++;
    }
  }

  public static void getCallCardCode(Document doc, TransactionDTO transactionDTO) throws Exception {
    XPath msgPath = null;

    if (transactionDTO.getGateImpOrExp().equals("E")) {
      msgPath = XPath.newInstance("//Message/GINTRCINFR");
    } else {
      msgPath = XPath.newInstance("//Message/GINTRCINFR");
    }

    List msgs = msgPath.selectNodes(doc);
    Iterator i = msgs.iterator();
    int msgCnt = 1;
    while (i.hasNext()) {
      Element msg = (Element) i.next();
      if (msg.getChild("BZKNSC") != null) { // Call Card
        if (msgCnt == 1) {
          transactionDTO.setCallCard(Long.parseLong(msg.getChild("BZKNSC").getTextTrim()));
        }
      }
      msgCnt++;
    }
  }

  private static String testReplyXML = "<?xml version=\"1.0\" encoding=\"ASCII\"?>" + " <SGS2Cosmos>"
      + "<Message Index=\"1\">" + "<CSMCTL>" + "<RQST>GSRQS</RQST>" + "<ACTN>CRT</ACTN>" + "<RTNC>0</RTNC>"
      + "<MORE>R</MORE>" + "<RQLD>1187771014125</RQLD>" + "<RQDS>CTEDSC</RQDS>" + "<SAID>CTCS</SAID>" + "<SAVR>5</SAVR>"
      + "<SARL>30</SARL>" + "<SAPT>240</SAPT>" + "<SANV>T</SANV>" + "<RSDS>CTEDSC</RSDS>" + "<RTNM>AS</RTNM>"
      + "<USID>1</USID>" + "<RQUI>1187771014031</RQUI>" + "<TRMC>WPT1</TRMC>" + "</CSMCTL>" + "<GINTRCINFR>"
      + "<MSGTSC>GINTRCINFR</MSGTSC>" + "<LANESC>001</LANESC>" + "<VMIDSC>abc2285</VMIDSC>"
      + "<ATDDSC>20070822</ATDDSC>" + "<ATDTSC>162334</ATDTSC>" + "<VMYKSC>TEL</VMYKSC>"
      + "<VMYOSC>TANJUNG EX S/B</VMYOSC>" + "<BZIDSC>005168146</BZIDSC>" + "<BZKNSC>84050</BZKNSC>" + "</GINTRCINFR>"
      + "</Message>" + "<Message Index=\"2\">" + "<CSMCTL>" + "<RQST>GSRQS</RQST>" + "<ACTN>CRT</ACTN>"
      + "<RTNC>0</RTNC>" + "<MORE>N</MORE>" + "<RQLD>1187771014125</RQLD>" + "<RQDS>CTEDSE</RQDS>" + "<SAID>CTCS</SAID>"
      + "<SAVR>5</SAVR>" + "<SARL>30</SARL>" + "<SAPT>240</SAPT>" + "<SANV>T</SANV>" + "<RSDS>CTEDSE</RSDS>"
      + "<RTNM>AS</RTNM>" + "<USID>1</USID>" + "<RQUI>1187771014031</RQUI>" + "<TRMC>WPT1</TRMC>" + "</CSMCTL>"
      + "<GINCNTDRPR>" + "<MSGTSE>GINCNTDRPR</MSGTSE>" + "<ORRFSE>TEST220807A</ORRFSE>" + "<LYNDSE>ACL</LYNDSE>"
      + "<UNITSE>TEST220807A1</UNITSE>" + "<UNCLSE>CN</UNCLSE>" + "<UNBTSE>F</UNBTSE>" + "<UNISSE>2210</UNISSE>"
      + "<UNTPSE>DV</UNTPSE>" + "<UNLLSE>20.00</UNLLSE>" + "<UNHHSE>8.60</UNHHSE>" + "<UNBBSE>8.00</UNBBSE>"
      + "<UNTSSE>OK</UNTSSE>" + "<UNBGSE>0019200</UNBGSE>" + "<UNTGSE>0001500</UNTGSE>" + "<PLKDSE>ACL</PLKDSE>"
      + "<PSIDSE>06D17B1</PSIDSE>" + "<PKIDSE>06D17</PKIDSE>" + "<CNPVSE>M</CNPVSE>" + "<SO01SE>L</SO01SE>"
      + "<ST01SE>SL</ST01SE>" + "<SN01SE>001</SN01SE>" + "<CYOISE>N</CYOISE>" + "<CYCISE>N</CYCISE>"
      + "<ACHISE>Y</ACHISE>" + "<PCHISE>Y</PCHISE>" + "<DOKBSE>N</DOKBSE>" + "<VMIISE>2285</VMIISE>"
      + "<VMIOSE>CXNB1</VMIOSE>" + "<RSUTSE>0081W</RSUTSE>" + "<SPLKSE>BE</SPLKSE>" + "<SPPKSE>ANR</SPPKSE>"
      + "<SPOMSE>ANTWERP/ANTWERPEN</SPOMSE>" + "<POLKSE>BE</POLKSE>" + "<POPKSE>ANR</POPKSE>" + "<POLOSE>?</POLOSE>"
      + "<MVSQSE>00001</MVSQSE>" + "<HDIDSE>022963477</HDIDSE>" + "<HDTPSE>IN</HDTPSE>" + "<HDRASE>022963477</HDRASE>"
      + "<ORGVSE>SAC</ORGVSE>" + "</GINCNTDRPR>" + "</Message>" + "</SGS2Cosmos>";

}
