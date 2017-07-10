/**
 * 
 */
package com.privasia.scss.gatein.test.service;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.io.IOUtils;

import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.cosmos.service.AGSClientService;
import com.privasia.scss.gatein.test.GateInAbstractTest;

/**
 * @author Janaka
 *
 */
public class TestOldAndNewRequest extends GateInAbstractTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private AGSClientService agsClientService;

	@Test()
	public void testRequest() throws BusinessException {

		String request = "";

		ClassLoader classLoader = getClass().getClassLoader();
		try {
			request = IOUtils.toString(classLoader.getResourceAsStream("oldRequest"));
			System.out.println("request &&&&&&&&&&&&&&&& "+request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//12222
		String response = agsClientService.sendRequestXml(request, 12233);
		
		System.out.println("response &&&&&&&&&&&&&&&& "+response);
		
		Assert.assertNotNull(response);

	}
}
