/**
 * 
 */
package com.privasia.scss.gateout.service.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.cosmos.service.CosmosService;
import com.privasia.scss.gateout.test.GateOutAbstractTest;

/**
 * @author Janaka
 *
 */
@Transactional(value = "cosmosOracleTransactionManager")
public class CosmosServiceTest extends GateOutAbstractTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Autowired
  private CosmosService cosmos;

  // @Test
  public void testSendGateOutWriteRequest() {

    GateOutWriteRequest gateOutWriteRequest = new GateOutWriteRequest();
    GateOutReponse gateOutReponse = new GateOutReponse();

    ExportContainer exportContainer = new ExportContainer();
    exportContainer.setContainer(new CommonContainerDTO());
    exportContainer.getContainer().setContainerNumber("ASYT98765420");
    exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
    exportContainer.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS.getValue());
    List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
    exportContainers.add(exportContainer);

    ImportContainer importContainer = new ImportContainer();
    importContainer.setContainer(new CommonContainerDTO());
    importContainer.getContainer().setContainerNumber("ATOS12345611");
    importContainer.getContainer().setContainerFullOrEmpty("F");
    importContainer.setContainerPosition("A");
    importContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
    importContainer.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS.getValue());
    List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
    importContainers.add(importContainer);

    gateOutWriteRequest.setImportContainers(importContainers);
    gateOutWriteRequest.setExportContainers(exportContainers);
    gateOutWriteRequest.setUserName("TESTT");
    gateOutWriteRequest.setHaulageCode("KN");
    gateOutWriteRequest.setCosmosPort(12222);
    gateOutWriteRequest.setTruckHeadNo("KN6785");
    gateOutWriteRequest.setTruckPlateNo("WHY7778");
    gateOutWriteRequest.setLaneNo("K7");// SELECT cli_unitno
    gateOutWriteRequest.setImpExpFlag(ImpExpFlagStatus.IMPORT_EXPORT.getValue());

    gateOutReponse = cosmos.sendGateOutWriteRequest(gateOutWriteRequest, gateOutReponse);

    Assert.assertNotNull(gateOutReponse);


  }


  @Test
  public void testSendGateOutReadRequest() {

    GateOutRequest gateOutRequest = new GateOutRequest();


    gateOutRequest.setUserName("TESTT");
    gateOutRequest.setHaulageCode("KN");
    gateOutRequest.setTruckHeadNo("KN6785");
    gateOutRequest.setLaneNo("K7");// SELECT cli_unitno
    gateOutRequest.setGatePass1(1235l);
    gateOutRequest.setGatePass2(12356l);
    gateOutRequest.setCardID(152l);
    gateOutRequest.setComID(158l);
    gateOutRequest.setExpContainer1("ASYT98765420");
    gateOutRequest.setExpContainer2("ASYT98765421");
    gateOutRequest.setImpContainer1("ATOS12345611");
    gateOutRequest.setImpContainer2("ATOS12345612");
    gateOutRequest.setOddImpContainer1("DTOS12345611");
    gateOutRequest.setOddImpContainer2("CTOS12345611");
    gateOutRequest.setGateOUTDateTime(LocalDateTime.now());
    gateOutRequest.setClientID(1245l);
    gateOutRequest.setExpWeightBridge(564);
    gateOutRequest.setCheckPreArrival(false);

    GateOutReponse gateOutReponse = new GateOutReponse();

    gateOutReponse.setTransactionType(TransactionType.IMPORT_EXPORT.getValue());
    ExportContainer exportContainer = new ExportContainer();
    exportContainer.setContainer(new CommonContainerDTO());
    exportContainer.getContainer().setContainerNumber("ASYT98765420");
    exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
    exportContainer.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS.getValue());
    List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
    exportContainers.add(exportContainer);

    ImportContainer importContainer = new ImportContainer();
    importContainer.setContainer(new CommonContainerDTO());
    importContainer.getContainer().setContainerNumber("ATOS12345611");
    importContainer.getContainer().setContainerFullOrEmpty("F");
    importContainer.setContainerPosition("A");
    importContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
    importContainer.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS.getValue());
    List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
    importContainers.add(importContainer);

    gateOutReponse.setImportContainers(importContainers);
    gateOutReponse.setExportContainers(exportContainers);


    gateOutReponse = cosmos.sendGateOutReadRequest(gateOutRequest, gateOutReponse);

    Assert.assertNotNull(gateOutReponse);


  }



}
