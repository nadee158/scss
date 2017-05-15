package com.privasia.scss.cosmos.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.privasia.scss.common.util.AGSClient;
import com.privasia.scss.cosmos.dto.exprequest.SGS2Cosmos;
import com.privasia.scss.cosmos.dto.request.CosmosGateInExport;
import com.privasia.scss.cosmos.dto.request.CosmosGateInImport;
import com.privasia.scss.cosmos.dto.request.CosmosGateInWriteRequest;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutExport;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutImport;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutWriteRequest;
import com.privasia.scss.cosmos.xml.element.CSMCTL;
import com.privasia.scss.cosmos.xml.element.GINCNTDRP;
import com.privasia.scss.cosmos.xml.element.GINCNTPUP;
import com.privasia.scss.cosmos.xml.element.GOTCNTINF;
import com.privasia.scss.cosmos.xml.element.GOTTRCINF;

public class GateOutXMLRequestService {

  public static void main(String[] args) {
    // sendRequestExample();
    // readResponseExample();
    // testNewCosmosGateOutWriteRequest("impexp");// imp, exp, impexp
    testNewCosmosGateInWriteRequest("exnnp");// imp, exp, impexp
  }

  private static void testNewCosmosGateInWriteRequest(String type) {

    final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
    final SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");
    String date = yyyymmdd.format(new java.util.Date());
    String time = hhmmss.format(new java.util.Date());

    com.privasia.scss.cosmos.xml.element.CSMCTL csmctl = new com.privasia.scss.cosmos.xml.element.CSMCTL();
    csmctl.setRQST("GSRQS");
    csmctl.setACTN("CRT");
    csmctl.setRTNC("0");
    csmctl.setERRI("ErrorMessage");
    csmctl.setRQDS("CTEDSC");
    csmctl.setRTNM("AS");
    csmctl.setUSID(toUpperCase("isuru"));
    csmctl.setRQUI(String.valueOf(System.currentTimeMillis()));
    csmctl.setTRMC("WPT1");

    com.privasia.scss.cosmos.xml.element.GOTTRCINF gottrcinf = new com.privasia.scss.cosmos.xml.element.GOTTRCINF();
    gottrcinf.setMSGTSC("GOTTRCINF");
    gottrcinf.setLANESC(toUpperCase("gate08"));
    gottrcinf.setVMIDSC(toUpperCase("truck123"));
    gottrcinf.setATDDSC(date);
    gottrcinf.setATDTSC(time);
    gottrcinf.setVMYKSC("VMYKSC");

    GINCNTPUP gincntpup = new GINCNTPUP();
    gincntpup.setMSGTSE("GINCNTPUP");
    gincntpup.setUNITSE("UNITSE");
    gincntpup.setUNBTSE("UNBTSE");
    gincntpup.setCNPVSE("CNPVSE");
    gincntpup.setUPLKSE("MY");
    gincntpup.setUPPKSE("PKG");
    gincntpup.setUPOMSE("PKG");
    gincntpup.setCYOISE("N");
    gincntpup.setCYCISE("N");
    gincntpup.setACHISE("Y");
    gincntpup.setPCHISE("Y");
    gincntpup.setCRORSE("Y");

    GINCNTDRP gincntdrp = new GINCNTDRP();
    gincntdrp.setMSGTSE("GINCNTDRP");
    gincntdrp.setUNISSE("UNISSE");
    gincntdrp.setORRFSE("ORRFSE");
    gincntdrp.setUNISSE("UNISSE");
    gincntdrp.setUNBTSE("UNBTSE");
    gincntdrp.setCNPVSE("CNPVSE");
    gincntdrp.setORGVSE("ORGVSE");
    gincntdrp.setLYNDSE("LYNDSE");

    gincntdrp.setDM01SE("DM01SE");
    gincntdrp.setDM02SE("DM02SE");
    gincntdrp.setDM03SE("DM03SE");
    gincntdrp.setDM04SE("DM04SE");
    gincntdrp.setDM05SE("DM05SE");
    gincntdrp.setDM06SE("DM06SE");
    gincntdrp.setDM07SE("DM07SE");
    gincntdrp.setDM08SE("DM08SE");
    gincntdrp.setDM09SE("DM09SE");

    gincntdrp.setSO01SE("SO01SE");
    gincntdrp.setST01SE("ST01SE");
    gincntdrp.setSN01SE("SN01SE");
    gincntdrp.setSO02SE("SO02SE");
    gincntdrp.setST02SE("ST02SE");
    gincntdrp.setSN02SE("SN02SE");

    gincntdrp.setUNBGSE("UNBGSE");

    gincntdrp.setCNORSE("CNORSE");
    gincntdrp.setPLMNSE("PLMNSE");
    gincntdrp.setRGRTSE("RGRTSE");
    gincntdrp.setRGTESE("RGTESE");

    gincntdrp.setOOGISE("OOGISE");
    gincntdrp.setOVSVSE("OVSVSE");
    gincntdrp.setOVSASE("OVSASE");
    gincntdrp.setOVSLSE("OVSLSE");
    gincntdrp.setOVHGSE("OVHGSE");
    gincntdrp.setOVSRSE("OVSRSE");

    gincntdrp.setCNIMSE("CNIMSE");
    gincntdrp.setCIM1SE("CIM1SE");
    gincntdrp.setISA1SE("ISA1SE");

    gincntdrp.setCUN1SE("CUN1SE");

    gincntdrp.setUOLCSE("MY");
    gincntdrp.setUOLLSE("PKG");
    gincntdrp.setUOLOSE("PKG");
    gincntdrp.setCYOISE("N");
    gincntdrp.setCYCISE("N");
    gincntdrp.setACHISE("Y");
    gincntdrp.setPCHISE("Y");

    CosmosGateInExport export = new CosmosGateInExport();
    export.setCSMCTL(csmctl);
    export.setGINCNTDRP(gincntdrp);
    export.setIndex(1);

    CosmosGateInImport import1 = new CosmosGateInImport();
    import1.setCSMCTL(csmctl);
    import1.setGINCNTPUP(gincntpup);
    import1.setGOTTRCINF(gottrcinf);
    import1.setIndex(1);

    CosmosGateInWriteRequest cosmos = new CosmosGateInWriteRequest();
    if (type.equals("exp")) {
      List<CosmosGateInExport> exportList = new ArrayList<>();
      exportList.add(export);
      cosmos.setExportList(exportList);
      cosmos.setImportList(null);
    } else if (type.equals("imp")) {
      List<CosmosGateInImport> importList = new ArrayList<>();
      importList.add(import1);
      cosmos.setImportList(importList);
      cosmos.setExportList(null);
    } else {
      List<CosmosGateInExport> exportList = new ArrayList<>();
      exportList.add(export);
      cosmos.setExportList(exportList);
      List<CosmosGateInImport> importList = new ArrayList<>();
      importList.add(import1);
      cosmos.setImportList(importList);
    }

    try {
      // Marshalling
      JAXBContext jaxbContext = JAXBContext.newInstance(CosmosGateInWriteRequest.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

      // output pretty printed
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      jaxbMarshaller.marshal(cosmos, System.out);

    } catch (Exception e) {
      e.printStackTrace();

    }

  }

  public static void testNewCosmosGateOutWriteRequest(String type) {

    final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
    final SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");
    String date = yyyymmdd.format(new java.util.Date());
    String time = hhmmss.format(new java.util.Date());

    CSMCTL csmctl = new CSMCTL();
    csmctl.setRQST("GSRQS");
    csmctl.setACTN("CRT");
    csmctl.setRTNC("0");
    csmctl.setERRI("ErrorMessage");
    csmctl.setRQDS("CTEDSC");
    csmctl.setRTNM("AS");
    csmctl.setUSID(toUpperCase("isuru"));
    csmctl.setRQUI(String.valueOf(System.currentTimeMillis()));
    csmctl.setTRMC("WPT1");

    GOTTRCINF gottrcinf = new GOTTRCINF();
    gottrcinf.setMSGTSC("GOTTRCINF");
    gottrcinf.setLANESC(toUpperCase("gate08"));
    gottrcinf.setVMIDSC(toUpperCase("truck123"));
    gottrcinf.setATDDSC(date);
    gottrcinf.setATDTSC(time);

    GOTCNTINF gotcninf = new GOTCNTINF();
    gotcninf.setUNITSE("UNITSE");
    gotcninf.setSN01SE("SN01SE");
    gotcninf.setSO01SE("SO01SE");
    gotcninf.setST01SE("ST01SE");

    CosmosGateOutExport export = new CosmosGateOutExport();
    export.setCSMCTL(csmctl);
    export.setGOTTRCINF(gottrcinf);
    export.setIndex(1);

    CosmosGateOutImport import1 = new CosmosGateOutImport();
    import1.setCSMCTL(csmctl);
    import1.setGOTCNTINF(gotcninf);
    import1.setGOTTRCINF(gottrcinf);
    import1.setIndex(1);

    CosmosGateOutWriteRequest cosmos = new CosmosGateOutWriteRequest();
    if (type.equals("exp")) {
      cosmos.setExport(export);
      cosmos.setImportList(null);
    } else if (type.equals("imp")) {
      List<CosmosGateOutImport> importList = new ArrayList<>();
      importList.add(import1);
      cosmos.setImportList(importList);
      cosmos.setExport(null);
    } else {
      cosmos.setExport(export);
      List<CosmosGateOutImport> importList = new ArrayList<>();
      importList.add(import1);
      cosmos.setImportList(importList);

    }

    try {
      // Marshalling
      JAXBContext jaxbContext = JAXBContext.newInstance(CosmosGateOutWriteRequest.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

      // output pretty printed
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      jaxbMarshaller.marshal(cosmos, System.out);

    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  public static void readResponseExample() {

    try {
      File file = new File("D:\\SCSS\\scss\\scss-parent\\scss-cosmos\\src\\main\\resources\\response.xml");
      if (file.exists()) {
        System.out.println("found");
      } else {
        System.out.println("not found");
      }
      JAXBContext jaxbContext = JAXBContext.newInstance(com.privasia.scss.cosmos.dto.response.SGS2Cosmos.class);

      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      com.privasia.scss.cosmos.dto.response.SGS2Cosmos response =
          (com.privasia.scss.cosmos.dto.response.SGS2Cosmos) jaxbUnmarshaller.unmarshal(file);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void sendRequestExample() {
    final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
    final SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");
    String date = yyyymmdd.format(new java.util.Date());
    String time = hhmmss.format(new java.util.Date());

    CSMCTL csmctl = new CSMCTL();
    csmctl.setRQST("GSRQS");
    csmctl.setACTN("CRT");
    csmctl.setRTNC("0");
    csmctl.setERRI("ErrorMessage");
    csmctl.setRQDS("CTEDSC");
    csmctl.setRTNM("AS");
    csmctl.setUSID(toUpperCase("isuru"));
    csmctl.setRQUI(String.valueOf(System.currentTimeMillis()));
    csmctl.setTRMC("WPT1");

    GOTTRCINF gottrcinf = new GOTTRCINF();
    gottrcinf.setMSGTSC("GOTTRCINF");
    gottrcinf.setLANESC(toUpperCase("gate08"));
    gottrcinf.setVMIDSC(toUpperCase("truck123"));
    gottrcinf.setATDDSC(date);
    gottrcinf.setATDTSC(time);

    // Message message = new Message();
    // message.setCSMCTL(csmctl);
    // message.setGOTTRCINF(gottrcinf);
    // message.setIndex(1);
    //
    // List<Message> massageList = new ArrayList<Message>();
    // massageList.add(message);

    SGS2Cosmos cosmos = new SGS2Cosmos();
    // cosmos.setMessage(massageList);

    try {
      // Marshalling
      JAXBContext jaxbContext = JAXBContext.newInstance(SGS2Cosmos.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

      // output pretty printed
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      jaxbMarshaller.marshal(cosmos, System.out);
      java.io.StringWriter sw = new StringWriter();
      jaxbMarshaller.marshal(cosmos, sw);

      int portNo = 12013;
      String host = "172.21.150.1";

      String replyXML = AGSClient.sendXMLMessage(sw.toString(), portNo);
      System.out.println(replyXML);

      // Unmarshalling
      JAXBContext jaxbContext2 = JAXBContext.newInstance(com.privasia.scss.cosmos.dto.response.SGS2Cosmos.class);

      Unmarshaller jaxbUnmarshaller = jaxbContext2.createUnmarshaller();
      InputStream is = new ByteArrayInputStream(replyXML.getBytes());
      com.privasia.scss.cosmos.dto.response.SGS2Cosmos customer =
          (com.privasia.scss.cosmos.dto.response.SGS2Cosmos) jaxbUnmarshaller.unmarshal(is);
      System.out.println(customer.getMessage().get(0).getCSMCTL().getERRI());

    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  public static String toUpperCase(String str) {
    if (str != null) {
      str = str.trim();
      return str.toUpperCase();
    } else {
      return str;
    }
  }

}
