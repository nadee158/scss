/**
 * 
 */
package com.privasia.scss.gatein.test.service;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.gatein.exports.business.service.EarlyEntryService;
import com.privasia.scss.gatein.test.GateInAbstractTest;

/**
 * @author Janaka
 *
 */
public class EarlyEntryServiceTest extends GateInAbstractTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private EarlyEntryService earlyEntryService;
	
	@Test
	public void testIsContainerHasAOpening() throws BusinessException {
		
		ExportContainer exportContainer = new ExportContainer();
		exportContainer.setContainer(new CommonContainerDTO());
		exportContainer.getContainer().setContainerNumber("IPIN1111117");
		exportContainer.setShippingLine("EMC");
		exportContainer.setVesselETADate(LocalDateTime.of(2017, 06, 19, 00, 00));
		exportContainer.setVesselSCN("AZMS6");
		System.out.println("ETADate "+exportContainer.getVesselETADate());
		
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Container " + exportContainer.getContainer().getContainerNumber() + "does not have opening");
		
		boolean results = earlyEntryService.isContainerHasAOpening(exportContainer);
		
		System.out.println("results ****************************  "+results);
	}

}
