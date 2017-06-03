/**
 * 
 */
package com.privasia.scss.gatein.test.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.cosmos.service.CosmosGateInReadService;
import com.privasia.scss.gatein.test.GateInAbstractTest;

/**
 * @author Janaka
 *
 */
public class CosmosGateInExportServiceTest extends GateInAbstractTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private CosmosGateInReadService cosmosGateInReadService;
	
	@Test
	public void testFetchContainerPrimaryInfo() throws BusinessException{
		
		ExportContainer exportContainer = new ExportContainer();
		exportContainer.setContainer(new CommonContainerDTO());
		exportContainer.getContainer().setContainerNumber("ASYT98765420");
		
		//thrown.expect(BusinessException.class);
		//thrown.expectMessage("Error cound while fetching container in cosmos "+exportContainer.getContainer().getContainerNumber());
		
		List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
		exportContainers.add(exportContainer);
		
		exportContainers = cosmosGateInReadService.populateCosmosGateInExport(exportContainers);
		
		exportContainers.forEach(container ->{
			System.out.println(container);
			Assert.assertNotNull(container.getBookingNo());
		});
		
		
		
		
	}
	
	

}
