/**
 * 
 */
package com.privasia.scss.gatein.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.cosmos.service.CosmosService;
import com.privasia.scss.gatein.test.GateInAbstractTest;

/**
 * @author Janaka
 *
 */
public class CosmosGateInExportServiceTest extends GateInAbstractTest {


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Autowired
  private CosmosService cosmos;

  private static final Logger log = LoggerFactory.getLogger(CosmosGateInExportServiceTest.class);

  @Value("${async.wait.time}")
  private long asyncWaitTime;

  @Test
  public void testPopulateCosmosGateInExport() throws BusinessException {

    ExportContainer exportContainer = new ExportContainer();
    exportContainer.setContainer(new CommonContainerDTO());
    exportContainer.getContainer().setContainerNumber("ASYT98765420");
    List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
    exportContainers.add(exportContainer);

    ImportContainer importContainer = new ImportContainer();
    importContainer.setContainer(new CommonContainerDTO());
    importContainer.getContainer().setContainerNumber("ATOS12345611");
    List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
    importContainers.add(importContainer);

    GateInResponse gateInResponse = new GateInResponse();
    gateInResponse.setExportContainers(exportContainers);
    gateInResponse.setImportContainers(importContainers);

    // thrown.expect(BusinessException.class);
    // thrown.expectMessage("Error cound while fetching container in cosmos
    // "+exportContainer.getContainer().getContainerNumber());

    Future<GateInResponse> response = cosmos.sendGateInReadRequest(null, gateInResponse);

    while (true) {
      System.out.println("inside the loop");
      if (response.isDone()) {
        System.out.println("now done");
        try {
          response.get().getExportContainers().forEach(container -> {
            System.out.println(container);
            Assert.assertNotNull(container.getBookingNo());
          });

          response.get().getImportContainers().forEach(container -> {
            System.out.println(container);
            Assert.assertNotNull(container.getShippingAgent());
          });
        } catch (InterruptedException | ExecutionException e) {
          log.error("Error Occured while retrieve data data from cosmos");
          log.error(e.getMessage());
        }
        break;
      } else {
        System.out.println("not done yet");
      }

      try {
        System.out.println("waiting " + asyncWaitTime);
        Thread.sleep(5);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
        break;
      }
    }



  }



}
