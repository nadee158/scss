package com.privasia.scss.gatein.test.service;

import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.gatein.service.ImportExportGateInService;
import com.privasia.scss.gatein.test.GateInAbstractTest;

public class ImportExportGateInServiceTest extends GateInAbstractTest {

  @Autowired
  private ImportExportGateInService importExportGateInService;

  /*
   * { "gatePass1":0, "gatePass2":0, "cardID":359129, "expContainer1":"KMAL0000003",
   * "expContainer2":"KMAL0000002", "truckHeadNo":"QK01", "gateInDateTime":"20-06-2017 19:18:05",
   * "clientID":481, "expWeightBridge":18650, "checkPreArrival":false,
   * "hpabSeqId":"2c95da965cc34629015cc49fa8ce00ca", "referID":null }
   */

  @Test
  public void testPopulateGateIn() {
    GateInRequest gateInRequest = new GateInRequest();
    gateInRequest.setCardID(359129l);
    gateInRequest.setCheckPreArrival(false);
    gateInRequest.setClientID(481l);
    gateInRequest.setExpContainer1("KMAL0000003");
    gateInRequest.setExpContainer2("KMAL0000002");
    gateInRequest.setExpWeightBridge(18650);
    gateInRequest.setGateInDateTime(LocalDateTime.now());
    gateInRequest.setGatePass1(0l);
    gateInRequest.setGatePass2(0l);
    gateInRequest.setHaulageCode("");
    gateInRequest.setHpabSeqId("2c95da965cc34629015cc49fa8ce00ca");
    gateInRequest.setImpContainer1(null);
    gateInRequest.setImpContainer2(null);
    gateInRequest.setLaneNo(null);
    gateInRequest.setOddReject(false);
    gateInRequest.setReferID(null);
    gateInRequest.setTruckHeadNo("QK01");
    gateInRequest.setUserId(1l);
    gateInRequest.setUserName("Test");
    GateInResponse response = importExportGateInService.populateGateIn(gateInRequest);
    System.out.println("response " + response);
    Assert.assertNotNull(response);
  }

  // @Test
  public void testSetExternalContainerInformationService() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetGateInReferService() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetSystemUserRepository() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetCommonCardService() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetHpabService() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetImportGateInService() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetExportGateInService() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetClientRepository() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetCardRepository() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetSolasService() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSetHpabBookingRepository() {
    fail("Not yet implemented");
  }


  // @Test
  public void testGetTOSServiceDataAtGateInRead() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSendAllTOSServiceGateInReadRequest() {
    fail("Not yet implemented");
  }

  // @Test
  public void testSaveGateInInfo() {
    fail("Not yet implemented");
  }

}
