/**
 * 
 */
package com.privasia.scss.client.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.client.service.ClientService;
import com.privasia.scss.client.service.lps.LPSService;
import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/client")
public class ClientController {

	private ClientService clientService;
	
	private LPSService lPSService;
	
	@Autowired
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
	
	@Autowired
	public void setlPSService(LPSService lPSService) {
		this.lPSService = lPSService;
	}

	@RequestMapping(value = "/{webIp}/unitNo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getClientUnitNoByIp(@PathVariable String webIPAddress) {
		String unitNo = clientService.getClientUnitNoByIp(webIPAddress);
		return new ResponseEntity<String>(unitNo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/opengate/{clientID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> openGate(@PathVariable Long clientID) {
		String status = lPSService.openGate(clientID);
		 return new CustomResponseEntity<ApiResponseObject<?>>(
			        new ApiResponseObject<String>(HttpStatus.OK, status), HttpStatus.OK);
	}
	
}
