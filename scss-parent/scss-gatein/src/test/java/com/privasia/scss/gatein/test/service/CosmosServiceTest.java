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
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.service.CosmosService;
import com.privasia.scss.gatein.test.GateInAbstractTest;

/**
 * @author Janaka
 *
 */
public class CosmosServiceTest extends GateInAbstractTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private CosmosService cosmos;
	
	@Test
	public void testSendGateInWriteRequest(){
		
		GateInWriteRequest gateInWriteRequest = new GateInWriteRequest();
		
		ExportContainer exportContainer = new ExportContainer();
		exportContainer.setContainer(new CommonContainerDTO());
		exportContainer.getContainer().setContainerNumber("ASYT98765420");
		List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
		exportContainers.add(exportContainer);
		
		ImportContainer importContainer = new ImportContainer();
		importContainer.setContainer(new CommonContainerDTO());
		importContainer.getContainer().setContainerNumber("ATOS12345611");
		importContainer.getContainer().setContainerFullOrEmpty("F");
		importContainer.setContainerPosition("A");
		List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
		importContainers.add(importContainer);
		
		gateInWriteRequest.setImportContainers(importContainers);
		gateInWriteRequest.setExportContainers(exportContainers);
		gateInWriteRequest.setUserName("TESTT");
		gateInWriteRequest.setHaulageCode("KN");
		gateInWriteRequest.setCosmosPort(12222);
		gateInWriteRequest.setTruckHeadNo("KN6785");
		gateInWriteRequest.setTruckPlateNo("WHY7778");
		gateInWriteRequest.setLaneNo("K7");// SELECT cli_unitno
		gateInWriteRequest.setImpExpFlag("I");
		
		GateInResponse gateInResponse = cosmos.sendGateInWriteRequest(gateInWriteRequest);
		
		Assert.assertNotNull(gateInResponse);
		
		
	}

}
