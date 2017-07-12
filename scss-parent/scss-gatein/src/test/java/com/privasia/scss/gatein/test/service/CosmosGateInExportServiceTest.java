/**
 * 
 */
package com.privasia.scss.gatein.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

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

  @Test
  public void testPopulateCosmosGateInExport() throws BusinessException {

    ExportContainer exportContainer = new ExportContainer();
    exportContainer.setContainer(new CommonContainerDTO());
    exportContainer.getContainer().setContainerNumber("PEKA00000000");
    List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
    exportContainers.add(exportContainer);


    ImportContainer importContainer = new ImportContainer();
    importContainer.setContainer(new CommonContainerDTO());
    importContainer.getContainer().setContainerNumber("POLI11111111");
    List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
    importContainers.add(importContainer);


    GateInResponse gateInResponse = new GateInResponse();
    gateInResponse.setExportContainers(exportContainers);
    gateInResponse.setImportContainers(null);

    // thrown.expect(BusinessException.class);
    // thrown.expectMessage("Error cound while fetching container in cosmos
    // "+exportContainer.getContainer().getContainerNumber());

    gateInResponse = cosmos.sendGateInReadRequest(null, gateInResponse);
    System.out.println("gateInResponse :" + gateInResponse);

    if (!(gateInResponse.getExportContainers() == null)) {
      gateInResponse.getExportContainers().forEach(container -> {
        System.out.println(container);
        Assert.assertNotNull(container.getBookingNo());
      });

    }

    if (!(gateInResponse.getImportContainers() == null)) {
      gateInResponse.getImportContainers().forEach(container -> {
        System.out.println(container);
        Assert.assertNotNull(container.getShippingAgent());
      });
    }



  }



}
