/**
 * 
 */
package com.privasia.scss.gatein.test.service;

import javax.transaction.Transactional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.gatein.exports.business.service.SealValidationService;
import com.privasia.scss.gatein.test.GateInAbstractTest;

/**
 * @author Janaka
 *
 */
@Transactional
public class SealValidationServiceTest extends GateInAbstractTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private SealValidationService sealValidationService;
	
	@Test()
	public void validateSeal() throws BusinessException{
		
		ExportContainer exportContainer  = new ExportContainer();
		exportContainer.setContainer(new CommonContainerDTO());
		exportContainer.setSealAttribute(new CommonSealDTO());
		
		exportContainer.getSealAttribute().setSeal01Number("SEAL1");
		exportContainer.getContainer().setContainerFullOrEmpty("F");
		exportContainer.getContainer().setContainerNumber("BEMC1000001");
		exportContainer.setShippingLine("EMC");
		exportContainer.setDontValidateSeal(false);
		
		thrown.expect(BusinessException.class);
		thrown.expectMessage("Container " + exportContainer.getContainer().
				getContainerNumber() + " Seal 1 Prefix Seal No is Invalid");
		
		sealValidationService.validateSeal(exportContainer);
		
		
	}
	
	

}
