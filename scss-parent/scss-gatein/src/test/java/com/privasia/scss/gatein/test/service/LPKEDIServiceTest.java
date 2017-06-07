/**
 * 
 */
package com.privasia.scss.gatein.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.gatein.service.LPKEDIService;
import com.privasia.scss.gatein.test.GateInAbstractTest;

/**
 * @author Janaka
 *
 */
public class LPKEDIServiceTest extends GateInAbstractTest {
	
	@Autowired
	private LPKEDIService lpkediService;
	
	@Test
	public void testFindLPKEDITDigiMessage(){
		
		ExportContainer exportContainer = new ExportContainer();
		exportContainer.setContainer(new CommonContainerDTO());
		exportContainer.getContainer().setContainerNumber("DIVI11111111");
		exportContainer.setVesselSCN("SAR55");
		
		lpkediService.findLPKEDITDigiMessage(exportContainer);
		
		System.out.println(exportContainer);
		
		Assert.assertNotNull(exportContainer.getLpkApproval());
	}

}
