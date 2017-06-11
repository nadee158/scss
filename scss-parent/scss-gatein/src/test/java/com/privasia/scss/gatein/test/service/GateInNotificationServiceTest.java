package com.privasia.scss.gatein.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.gatein.service.NotificationService;
import com.privasia.scss.gatein.test.GateInAbstractTest;

public class GateInNotificationServiceTest extends GateInAbstractTest {

  @Autowired
  private NotificationService notificationService;

  @Test
  public void testSendContainerSizeDiscrepancyEmail() {
    List<ExportContainer> sizeDiscrepancyContainers = new ArrayList<>();
    ExportContainer exportContainer = new ExportContainer();
    exportContainer.setShippingLine("CMA");
    exportContainer.setBookingNo("Booking No");
    exportContainer.setContainer(new CommonContainerDTO());
    exportContainer.getContainer().setContainerNumber("Container Number");
    exportContainer.getContainer().setContainerISOCode("Container ISO Code");
    exportContainer.getContainer().setContainerFullOrEmpty("Container Full Or Empty");
    exportContainer.setShipCode("Ship Code");
    exportContainer.setCosmosISOCode("Cosmos ISO Code");
    exportContainer.setHpabISOCode("Hpab ISO Code");
    sizeDiscrepancyContainers.add(exportContainer);
    notificationService.sendContainerSizeDiscrepancyEmail(sizeDiscrepancyContainers);
  }

  // @Test
  // public void testSendNonStandardSealLineCodeEmail() {
  // notificationService.sendNonStandardSealLineCodeEmail(null);
  // }
  //
  // @Test
  // public void testSendWrongDoorEmail() {
  // notificationService.sendWrongDoorEmail(null);
  // }
  //
  // @Test
  // public void testSendWeightEmail() {
  // notificationService.sendWeightEmail(null);
  // }

}
